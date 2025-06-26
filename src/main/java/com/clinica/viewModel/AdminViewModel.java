package com.clinica.viewModel;

import com.clinica.firebase.FirebaseInitializer;
import com.clinica.model.*;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class AdminViewModel {

    private final Firestore db;

    public AdminViewModel() {
        this.db = FirebaseInitializer.getDatabase();
    }

    // Crear doctor y devolver mensaje para la vista
    public String createDoctor(Doctor doctor) {
        try {
            DocumentReference docRef = db.collection("doctores").document(doctor.getId());

            // Verificar si el doctor ya existe
            DocumentSnapshot snapshot = docRef.get().get();
            if (snapshot.exists()) {
                return "❌ El doctor con ID " + doctor.getId() + " ya está registrado.";
            }

            // Guardar nuevo doctor
            docRef.set(doctor).get();
            return "✅ Doctor creado exitosamente con ID: " + doctor.getId();

        } catch (Exception e) {
            return "❌ Error al crear el doctor: " + e.getMessage();
        }
    }

    public String createPatient(UserDTO data) {
        try {
            DocumentReference docRef = db.collection("patients").document(data.getId());

            // Verificar si ya existe
            DocumentSnapshot document = docRef.get().get();
            if (document.exists()) {
                return "⚠️ El paciente con ID " + data.getId() + " ya existe.";
            }

            // Crear nuevo paciente desde DTO
            Patient patient = Patient.builder()
                    .id(data.getId())
                    .name(data.getName())
                    .phone(data.getPhone())
                    .medRecord(data.getMedRecord()) // puede ser null
                    .build();

            // Guardar en Firestore
            docRef.set(patient).get();
            return "✅ Paciente creado con éxito: " + patient.getName();

        } catch (Exception e) {
            return "❌ Error al crear paciente: " + e.getMessage();
        }
    }

    public String createConsult(Consult consult) {
        try {
            // 🔍 Verificar que el paciente existe
            DocumentReference patientRef = db.collection("patients").document(consult.getPatient().getId());
            DocumentSnapshot patientSnapshot = patientRef.get().get();

            if (!patientSnapshot.exists()) {
                return "❌ El paciente con ID " + consult.getPatient().getId() + " no existe.";
            }

            // ➕ Agregar la consulta a la lista del paciente
            Patient patient = patientSnapshot.toObject(Patient.class);
            if (patient.getConsults() == null) {
                patient.setConsults(new ArrayList<>());
            }
            patient.getConsults().add(consult);

            // 📥 Guardar la consulta
            db.collection("consults").document(consult.getId()).set(consult).get();

            // 📤 Actualizar el paciente con la nueva lista de consultas
            db.collection("patients").document(patient.getId()).set(patient).get();

            // 🧠 Crear o actualizar MedRecord
            MedRecord.manageMedRecord(patient, consult);

            return "✅ Consulta registrada correctamente con ID: " + consult.getId();

        } catch (Exception e) {
            return "❌ Error al crear la consulta: " + e.getMessage();
        }
    }

    public Map<String, String> listarPacientes() {
        Map<String, String> pacientesMap = new LinkedHashMap<>();
        Firestore db = FirebaseInitializer.getDatabase();

        try {
            ApiFuture<QuerySnapshot> future = db.collection("patients").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                Patient p = doc.toObject(Patient.class);
                pacientesMap.put(p.getName(), p.getId());
            }

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("❌ Error al listar pacientes: " + e.getMessage());
        }

        return pacientesMap;
    }

    public Map<String, String> listarDoctores() {
        Map<String, String> doctoresMap = new LinkedHashMap<>();
        Firestore db = FirebaseInitializer.getDatabase();

        try {
            ApiFuture<QuerySnapshot> future = db.collection("doctores").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                Doctor d = doc.toObject(Doctor.class);
                doctoresMap.put(d.getName(), d.getId());
            }

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("❌ Error al listar doctores: " + e.getMessage());
        }

        return doctoresMap;
    }

    public List<Doctor> obtenerDoctores() {
        List<Doctor> doctorList = new ArrayList<>();
        Firestore db = FirebaseInitializer.getDatabase();

        try {
            ApiFuture<QuerySnapshot> future = db.collection("doctores").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                doctorList.add(doc.toObject(Doctor.class));
            }

        } catch (Exception e) {
            System.out.println("❌ Error al listar doctores: " + e.getMessage());
        }

        return doctorList;
    }

    public List<Patient> obtenerPacientes() {
        List<Patient> patients = new ArrayList<>();

        try {
            ApiFuture<QuerySnapshot> future = db.collection("patients").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                Patient patient = doc.toObject(Patient.class);
                patients.add(patient);
            }

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("❌ Error al obtener lista de pacientes: " + e.getMessage());
            Thread.currentThread().interrupt(); // buena práctica para restaurar estado de interrupción
        }

        return patients;
    }

    public Admin login(String username, String password) {
        Firestore db = FirebaseInitializer.getDatabase();

        try {
            // 🔍 Buscar admin con username y password
            ApiFuture<QuerySnapshot> future = db.collection("admins")
                    .whereEqualTo("username", username)
                    .whereEqualTo("password", password)
                    .get();

            List<QueryDocumentSnapshot> docs = future.get().getDocuments();

            if (!docs.isEmpty()) {
                return docs.get(0).toObject(Admin.class);
            }

        } catch (Exception e) {
            System.out.println("❌ Error al autenticar admin: " + e.getMessage());
        }

        return null;
    }



}

