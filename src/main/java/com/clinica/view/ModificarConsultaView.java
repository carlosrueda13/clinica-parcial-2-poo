package com.clinica.view;

import com.clinica.model.Consult;
import com.clinica.model.Doctor;
import com.clinica.model.Patient;
import com.clinica.viewModel.AdminViewModel;
import com.clinica.viewModel.UserViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

public class ModificarConsultaView extends JDialog {

    private final AdminViewModel adminViewModel;
    private final UserViewModel userViewModel;

    private JComboBox<String> comboConsultas;
    private JComboBox<String> comboPacientes;
    private JComboBox<String> comboDoctores;
    private JTextField campoFecha;
    private JButton botonModificar;

    private List<Consult> consultas;
    private Map<String, String> mapaPacientes;
    private Map<String, String> mapaDoctores;

    public ModificarConsultaView(Frame parent, AdminViewModel adminViewModel, UserViewModel userViewModel) {
        super(parent, "Modificar Consulta", true);
        this.adminViewModel = adminViewModel;
        this.userViewModel = userViewModel;
        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {
        setLayout(new GridLayout(5, 2, 10, 10));

        comboConsultas = new JComboBox<>();
        comboPacientes = new JComboBox<>();
        comboDoctores = new JComboBox<>();
        campoFecha = new JTextField();
        botonModificar = new JButton("Modificar Consulta");

        add(new JLabel("Seleccionar Consulta:"));
        add(comboConsultas);

        add(new JLabel("Nuevo Paciente:"));
        add(comboPacientes);

        add(new JLabel("Nuevo Doctor:"));
        add(comboDoctores);

        add(new JLabel("Nueva Fecha (YYYY-MM-DD):"));
        add(campoFecha);

        add(new JLabel(""));
        add(botonModificar);

        botonModificar.addActionListener(this::modificarConsulta);

        setSize(500, 300);
        setLocationRelativeTo(null);
    }

    private void cargarDatos() {
        consultas = userViewModel.getAllConsults();  // Este método debe existir en UserViewModel

        for (Consult c : consultas) {
            comboConsultas.addItem(c.getId() + " - " + c.getDate());
        }

        mapaPacientes = adminViewModel.listarPacientes();
        for (String nombre : mapaPacientes.keySet()) {
            comboPacientes.addItem(nombre);
        }

        mapaDoctores = adminViewModel.listarDoctores();
        for (String nombre : mapaDoctores.keySet()) {
            comboDoctores.addItem(nombre);
        }
    }

    private void modificarConsulta(ActionEvent e) {
        int index = comboConsultas.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una consulta.");
            return;
        }

        String nombrePaciente = (String) comboPacientes.getSelectedItem();
        String nombreDoctor = (String) comboDoctores.getSelectedItem();
        String fecha = campoFecha.getText();

        if (nombrePaciente == null || nombreDoctor == null || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        Consult consultaOriginal = consultas.get(index);

        Patient paciente = Patient.builder().id(mapaPacientes.get(nombrePaciente)).name(nombrePaciente).build();
        Doctor doctor = Doctor.builder().id(mapaDoctores.get(nombreDoctor)).name(nombreDoctor).build();

        Consult consultaModificada = Consult.builder()
                .id(consultaOriginal.getId())
                .patient(paciente)
                .doctor(doctor)
                .date(fecha)
                .symptoms(consultaOriginal.getSymptoms())
                .diagnosis(consultaOriginal.getDiagnosis())
                .treatment(consultaOriginal.getTreatment())
                .build();

        userViewModel.updateConsult(consultaModificada);
        JOptionPane.showMessageDialog(this, "✅ Consulta modificada correctamente.");
        dispose();
    }
}

