package com.clinica.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Admin extends User implements UserInterface {
    private String username;
    private String password;

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public void updateConsult(Consult consult) {
        // No implementa lógica directamente
    }

    @Override
    public void listConsultsByPatient(String patientId) {
        // No implementa lógica directamente
    }

    @Override
    public void listConsultsByDoctor(String doctorId) {
        // No implementa lógica directamente
    }

    @Override
    public void getMedRecordDetails(Patient patient) {
        // No implementa lógica directamente
    }
}
