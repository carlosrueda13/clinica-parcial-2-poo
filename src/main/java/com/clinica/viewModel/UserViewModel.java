package com.clinica.viewModel;

import com.clinica.firebase.FirebaseInitializer;
import com.clinica.model.Consult;
import com.clinica.model.Doctor;
import com.clinica.model.MedRecord;
import com.clinica.model.Patient;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel {

    private final Firestore db;

    public UserViewModel() {
        this.db = FirebaseInitializer.getDatabase();
    }

    /**
     * Actualiza una consulta existente tanto en la colección de consultas como en el MedRecord correspondiente.
     * @param consult Objeto Consult actualizado
     * @return Mensaje para mostrar en la GUI
     */
    public String updateConsult(Consult consult) {
        try {
            String consultId = consult.getId();

            // 🔎 Verificar existencia de la consulta
            DocumentReference consultRef = db.collection("consults").document(consultId);
            ApiFuture<DocumentSnapshot> future = consultRef.get();
            DocumentSnapshot document = future.get();

            if (!document.exists()) {
                return "❌ La consulta con ID " + consultId + " no existe.";
            }

            // 🔁 Actualizar consulta en la colección "consults"
            consultRef.set(consult).get();

            // 📘 Actualizar consulta en el MedRecord
            String patientId = consult.getPatient().getId();
            DocumentReference medRecordRef = db.collection("medRecords").document(patientId);
            DocumentSnapshot medRecordDoc = medRecordRef.get().get();

            if (medRecordDoc.exists()) {
                MedRecord record = medRecordDoc.toObject(MedRecord.class);
                record.updateConsultInRecord(consult);
                medRecordRef.set(record).get();
                return "✅ Consulta y MedRecord actualizados correctamente.";
            } else {
                return "⚠️ Consulta actualizada, pero no se encontró un MedRecord asociado.";
            }

        } catch (Exception e) {
            return "❌ Error al actualizar la consulta: " + e.getMessage();
        }
    }

    public List<Consult> getConsultsByDoctor(Doctor doctor) {
        List<Consult> consults = new ArrayList<>();

        try {
            ApiFuture<QuerySnapshot> future = db.collection("consults")
                    .whereEqualTo("doctor.id", doctor.getId())
                    .get();

            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                Consult consult = doc.toObject(Consult.class);
                consults.add(consult);
            }

        } catch (Exception e) {
            System.out.println("❌ Error al obtener consultas del doctor: " + e.getMessage());
        }

        return consults;
    }

    public MedRecord getMedRecordByPatient(Patient patient) {
        try {
            DocumentReference medRecordRef = db.collection("medrecords")
                    .document(patient.getId());

            ApiFuture<DocumentSnapshot> future = medRecordRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                MedRecord medRecord = document.toObject(MedRecord.class);
                return medRecord;
            } else {
                System.out.println("📭 No se encontró historia clínica para el paciente con ID: " + patient.getId());
            }

        } catch (Exception e) {
            System.out.println("❌ Error al obtener el MedRecord: " + e.getMessage());
        }

        return null;
    }



    public List<Consult> getAllConsults() {
        Firestore db = FirebaseInitializer.getDatabase();
        List<Consult> consults = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> future = db.collection("consults").get();
            List<QueryDocumentSnapshot> docs = future.get().getDocuments();
            for (QueryDocumentSnapshot doc : docs) {
                consults.add(doc.toObject(Consult.class));
            }
        } catch (Exception e) {
            System.out.println("❌ Error al obtener todas las consultas: " + e.getMessage());
        }
        return consults;
    }

    public List<Consult> getConsultasByPatient(Patient patient) {
        try {
            DocumentReference medRecordRef = db.collection("medrecords")
                    .document(patient.getId());

            ApiFuture<DocumentSnapshot> future = medRecordRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                MedRecord medRecord = document.toObject(MedRecord.class);
                return medRecord.getConsults();
            } else {
                System.out.println("📭 No se encontró historia clínica para el paciente con ID: " + patient.getId());
            }

        } catch (Exception e) {
            System.out.println("❌ Error al obtener la historia clínica: " + e.getMessage());
        }

        return new ArrayList<>(); // retornar lista vacía si hay error
    }

    public Doctor loginDoctor(String username, String password) {
        try {
            Firestore db = FirebaseInitializer.getDatabase();
            ApiFuture<QuerySnapshot> future = db.collection("doctores")
                    .whereEqualTo("username", username)
                    .whereEqualTo("password", password)
                    .get();

            List<QueryDocumentSnapshot> docs = future.get().getDocuments();
            if (!docs.isEmpty()) {
                return docs.get(0).toObject(Doctor.class);
            }

        } catch (Exception e) {
            System.out.println("❌ Error al autenticar doctor: " + e.getMessage());
        }

        return null;
    }



}
