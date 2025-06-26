package com.clinica.view;

import com.clinica.model.Consult;
import com.clinica.model.Patient;
import com.clinica.viewModel.AdminViewModel;
import com.clinica.viewModel.UserViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class VerMedRecordView extends JDialog {

    private JComboBox<Patient> patientComboBox;
    private JTextArea resultArea;
    private final AdminViewModel adminViewModel;
    private final UserViewModel userViewModel;

    public VerMedRecordView(JFrame parent, AdminViewModel adminViewModel, UserViewModel userViewModel) {
        super(parent, "Historia Clínica del Paciente", true);
        this.adminViewModel = adminViewModel;
        this.userViewModel = userViewModel;

        initComponents();
        loadPatients();

        setSize(600, 500);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        patientComboBox = new JComboBox<>();
        JButton searchButton = new JButton("Consultar Historia Clínica");

        resultArea = new JTextArea(20, 50);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Selecciona el Paciente:"));
        topPanel.add(patientComboBox);
        topPanel.add(searchButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        searchButton.addActionListener(this::onSearch);
    }

    private void loadPatients() {
        List<Patient> patients = adminViewModel.obtenerPacientes();

        patientComboBox.removeAllItems();
        for (Patient patient : patients) {
            patientComboBox.addItem(patient);
        }
    }

    private void onSearch(ActionEvent e) {
        Patient selectedPatient = (Patient) patientComboBox.getSelectedItem();

        if (selectedPatient == null) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un paciente.");
            return;
        }

        List<Consult> consults = userViewModel.getConsultasByPatient(selectedPatient);

        resultArea.setText("");
        if (consults == null || consults.isEmpty()) {
            resultArea.append("El paciente no tiene historia clínica registrada.\n");
            return;
        }

        resultArea.append("🩺 Historia clínica de " + selectedPatient.getName() + " (ID: " + selectedPatient.getId() + ")\n");
        resultArea.append("Total de consultas: " + consults.size() + "\n\n");

        for (Consult c : consults) {
            resultArea.append("📅 Fecha: " + c.getDate() + "\n");
            resultArea.append("👨‍⚕️ Doctor: " + (c.getDoctor() != null ? c.getDoctor().getName() : "Desconocido") + "\n");
            resultArea.append("🩺 Especialidad: " + (c.getDoctor() != null ? c.getDoctor().getSpecialty() : "Desconocida") + "\n");
            resultArea.append("🤕 Síntomas: " + c.getSymptoms() + "\n");
            resultArea.append("🔬 Diagnóstico: " + c.getDiagnosis() + "\n");
            resultArea.append("💊 Tratamiento: " + c.getTreatment() + "\n");
            resultArea.append("────────────────────────────────────────────\n");
        }
    }
}
