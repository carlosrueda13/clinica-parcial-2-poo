package com.clinica.view;

import com.clinica.model.Consult;
import com.clinica.model.Doctor;
import com.clinica.model.Patient;
import com.clinica.viewModel.AdminViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class CrearConsultaView extends JDialog {

    private final AdminViewModel adminViewModel;

    private JComboBox<String> comboPacientes;
    private JComboBox<String> comboDoctores;
    private JTextField campoFecha;
    private JButton botonCrear;

    private Map<String, String> mapaPacientes;
    private Map<String, String> mapaDoctores;

    public CrearConsultaView(Frame parent, AdminViewModel viewModel) {
        super(parent, "Agendar Consulta", true);
        this.adminViewModel = viewModel;
        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {
        setLayout(new GridLayout(4, 2, 10, 10));

        comboPacientes = new JComboBox<>();
        comboDoctores = new JComboBox<>();
        campoFecha = new JTextField();
        botonCrear = new JButton("Crear Consulta");

        add(new JLabel("Seleccionar Paciente:"));
        add(comboPacientes);

        add(new JLabel("Seleccionar Doctor:"));
        add(comboDoctores);

        add(new JLabel("Fecha (YYYY-MM-DD):"));
        add(campoFecha);

        add(new JLabel(""));
        add(botonCrear);

        botonCrear.addActionListener(this::crearConsulta);

        setSize(400, 250);
        setLocationRelativeTo(null);
    }

    private void cargarDatos() {
        mapaPacientes = adminViewModel.listarPacientes();
        mapaDoctores = adminViewModel.listarDoctores();

        for (String nombre : mapaPacientes.keySet()) {
            comboPacientes.addItem(nombre);
        }

        for (String nombre : mapaDoctores.keySet()) {
            comboDoctores.addItem(nombre);
        }
    }

    private void crearConsulta(ActionEvent e) {
        String nombrePaciente = (String) comboPacientes.getSelectedItem();
        String nombreDoctor = (String) comboDoctores.getSelectedItem();
        String fecha = campoFecha.getText();

        if (nombrePaciente == null || nombreDoctor == null || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        String idPaciente = mapaPacientes.get(nombrePaciente);
        String idDoctor = mapaDoctores.get(nombreDoctor);

        Patient paciente = Patient.builder().id(idPaciente).name(nombrePaciente).build();
        Doctor doctor = Doctor.builder().id(idDoctor).name(nombreDoctor).build();

        Consult consult = Consult.builder()
                .id("CONSULT-" + System.currentTimeMillis())  // ID único simple
                .patient(paciente)
                .doctor(doctor)
                .date(fecha)
                .build();

        adminViewModel.createConsult(consult);

        JOptionPane.showMessageDialog(this, "✅ Consulta creada exitosamente.");
        dispose();
    }
}

