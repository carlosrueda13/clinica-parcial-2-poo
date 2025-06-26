package com.clinica.view;

import com.clinica.model.Doctor;
import com.clinica.viewModel.UserViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DoctorDashboardView extends JFrame {

    private final Doctor doctor;
    private final UserViewModel userViewModel;

    public DoctorDashboardView(Doctor doctor, UserViewModel userViewModel) {
        this.doctor = doctor;
        this.userViewModel = userViewModel;
        setTitle("Panel del Doctor - " + doctor.getName());
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton btnModificarConsulta = new JButton("Modificar Consulta");
        JButton btnVerHistoria = new JButton("Historia Clínica de Paciente");
        JButton btnVerMisConsultas = new JButton("Ver Mis Consultas");

        btnModificarConsulta.addActionListener(this::abrirModificarConsulta);
        btnVerHistoria.addActionListener(this::abrirVerHistoria);
        btnVerMisConsultas.addActionListener(this::abrirMisConsultas);

        panel.add(btnModificarConsulta);
        panel.add(btnVerHistoria);
        panel.add(btnVerMisConsultas);

        add(panel, BorderLayout.CENTER);
    }

    private void abrirModificarConsulta(ActionEvent e) {
        new ModificarConsultaDoctorView(this, doctor, userViewModel).setVisible(true);
    }

    private void abrirVerHistoria(ActionEvent e) {
        new VerMedRecordView(this, null, userViewModel).setVisible(true);
    }

    private void abrirMisConsultas(ActionEvent e) {
        new ListarConsultasPorDoctorView(this, null, userViewModel, doctor).setVisible(true);
    }
}
