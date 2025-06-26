package com.clinica.view;

import com.clinica.model.Consult;
import com.clinica.model.Doctor;
import com.clinica.viewModel.UserViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ModificarConsultaDoctorView extends JDialog {

    private final Doctor doctor;
    private final UserViewModel userViewModel;

    private JComboBox<String> comboConsultas;
    private JTextField campoSintomas;
    private JTextField campoDiagnostico;
    private JTextField campoTratamiento;
    private JButton botonModificar;

    private List<Consult> consultas;

    public ModificarConsultaDoctorView(Frame parent, Doctor doctor, UserViewModel userViewModel) {
        super(parent, "Modificar Consulta (Doctor)", true);
        this.doctor = doctor;
        this.userViewModel = userViewModel;
        inicializarComponentes();
        cargarConsultas();
    }

    private void inicializarComponentes() {
        setLayout(new GridLayout(5, 2, 10, 10));

        comboConsultas = new JComboBox<>();
        campoSintomas = new JTextField();
        campoDiagnostico = new JTextField();
        campoTratamiento = new JTextField();
        botonModificar = new JButton("Actualizar Consulta");

        add(new JLabel("Consulta:"));
        add(comboConsultas);

        add(new JLabel("Síntomas:"));
        add(campoSintomas);

        add(new JLabel("Diagnóstico:"));
        add(campoDiagnostico);

        add(new JLabel("Tratamiento:"));
        add(campoTratamiento);

        add(new JLabel(""));
        add(botonModificar);

        botonModificar.addActionListener(this::modificarConsulta);

        setSize(500, 300);
        setLocationRelativeTo(null);
    }

    private void cargarConsultas() {
        consultas = userViewModel.getConsultsByDoctor(doctor);

        for (Consult c : consultas) {
            comboConsultas.addItem(c.getId() + " - " + c.getDate());
        }
    }

    private void modificarConsulta(ActionEvent e) {
        int index = comboConsultas.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una consulta.");
            return;
        }

        Consult original = consultas.get(index);

        original.setSymptoms(campoSintomas.getText());
        original.setDiagnosis(campoDiagnostico.getText());
        original.setTreatment(campoTratamiento.getText());

        userViewModel.updateConsult(original);
        JOptionPane.showMessageDialog(this, "✅ Consulta actualizada.");
        dispose();
    }
}
