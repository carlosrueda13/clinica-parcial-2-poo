package com;

import com.clinica.firebase.FirebaseInitializer;
import com.clinica.model.Admin;
import com.clinica.model.Doctor;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

public class CrearUsuariosIniciales {

    public static void crearAdmin() {
        Firestore db = FirebaseInitializer.getDatabase();
        Admin admin = Admin.builder()
                .id("admin001")
                .name("Administrador Principal")
                .username("admin")
                .password("admin123")
                .build();

        DocumentReference ref = db.collection("admins").document(admin.getId());
        ApiFuture<WriteResult> result = ref.set(admin);
        try {
            System.out.println("✅ Admin creado: " + result.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("❌ Error al crear admin: " + e.getMessage());
        }
    }

    public static void crearDoctor() {
        Firestore db = FirebaseInitializer.getDatabase();
        Doctor doctor = Doctor.builder()
                .id("doctor001")
                .name("Dr. Juan Pérez")
                .specialty("Medicina General")
                .username("doctor")
                .password("doctor123")
                .build();

        DocumentReference ref = db.collection("doctores").document(doctor.getId());
        ApiFuture<WriteResult> result = ref.set(doctor);
        try {
            System.out.println("✅ Doctor creado: " + result.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("❌ Error al crear doctor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        crearAdmin();
        crearDoctor();
    }
}
