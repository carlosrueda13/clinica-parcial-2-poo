package com.clinica.view;

import com.clinica.model.Consult;
import com.clinica.model.Doctor;
import com.clinica.viewModel.AdminViewModel;
import com.clinica.viewModel.UserViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ListarConsultasPorDoctorView extends JDialog {

    private final AdminViewModel adminViewModel;
    private final UserViewModel userViewModel;

    private final Doctor fixedDoctor;  // null si es admin
    private JComboBox<Doctor> doctorComboBox;
    private JTextArea resultArea;

    public ListarConsultasPorDoctorView(JFrame parent, AdminViewModel adminViewModel, UserViewModel userViewModel, Doctor doctor) {
        super(parent, "Consultas por Doctor", true);
        this.adminViewModel = adminViewModel;
        this.userViewModel = userViewModel;
        this.fixedDoctor = doctor;

        initComponents();
        if (fixedDoctor == null) {
            loadDoctors();
        } else {
            loadConsults(fixedDoctor);
        }

        setSize(600, 450);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // Top
        JPanel topPanel = new JPanel();
        if (fixedDoctor == null) {
            doctorComboBox = new JComboBox<>();
            JButton searchButton = new JButton("Buscar Consultas");

            topPanel.add(new JLabel("Selecciona el Doctor:"));
            topPanel.add(doctorComboBox);
            topPanel.add(searchButton);

            searchButton.addActionListener(this::onSearch);
        } else {
            topPanel.add(new JLabel("Consultas asignadas al Dr. " + fixedDoctor.getName()));
        }

        // Center
        resultArea = new JTextArea(20, 50);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void loadDoctors() {
        try {
            List<Doctor> doctorList = adminViewModel.obtenerDoctores();
            doctorComboBox.removeAllItems();
            for (Doctor doctor : doctorList) {
                doctorComboBox.addItem(doctor);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar doctores: " + e.getMessage());
        }
    }

    private void onSearch(ActionEvent e) {
        Doctor selectedDoctor = (Doctor) doctorComboBox.getSelectedItem();
        if (selectedDoctor == null) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un doctor.");
            return;
        }
        loadConsults(selectedDoctor);
    }

    private void loadConsults(Doctor doctor) {
        List<Consult> consults = userViewModel.getConsultsByDoctor(doctor);

        resultArea.setText("");
        if (consults == null || consults.isEmpty()) {
            resultArea.append("No se encontraron consultas para el doctor seleccionado.\n");
            return;
        }

        for (Consult c : consults) {
            resultArea.append("🗓 Fecha: " + c.getDate() + "\n");
            resultArea.append("👤 Paciente: " + c.getPatient().getName() + " (ID: " + c.getPatient().getId() + ")\n");
            resultArea.append("🤒 Síntomas: " + c.getSymptoms() + "\n");
            resultArea.append("🔬 Diagnóstico: " + c.getDiagnosis() + "\n");
            resultArea.append("💊 Tratamiento: " + c.getTreatment() + "\n");
            resultArea.append("-------------------------------------------\n");
        }
    }
}
