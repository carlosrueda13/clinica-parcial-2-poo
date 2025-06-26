package com.clinica.view;

import com.clinica.model.Doctor;
import com.clinica.viewModel.AdminViewModel;

import javax.swing.*;
import java.awt.*;

public class CrearDoctorDialog extends JDialog {

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtPhone;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtSpecialty;

    private AdminViewModel viewModel;

    public CrearDoctorDialog(Frame parent, AdminViewModel viewModel) {
        super(parent, "Crear Doctor", true);
        this.viewModel = viewModel;

        setLayout(new GridLayout(7, 2, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Campos del formulario
        add(new JLabel("ID:"));
        txtId = new JTextField();
        add(txtId);

        add(new JLabel("Nombre:"));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("Teléfono:"));
        txtPhone = new JTextField();
        add(txtPhone);

        add(new JLabel("Usuario:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        add(new JLabel("Especialidad:"));
        txtSpecialty = new JTextField();
        add(txtSpecialty);

        // Botón de crear
        JButton btnCrear = new JButton("Crear Doctor");
        btnCrear.addActionListener(e -> crearDoctor());
        add(btnCrear);

        // Botón de cerrar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }

    private void crearDoctor() {
        Doctor doctor = Doctor.builder()
                .id(txtId.getText())
                .name(txtName.getText())
                .phone(txtPhone.getText())
                .username(txtUsername.getText())
                .password(new String(txtPassword.getPassword()))
                .specialty(txtSpecialty.getText())
                .build();

        String resultado = viewModel.createDoctor(doctor);
        JOptionPane.showMessageDialog(this, resultado);
        if (resultado.contains("✅")) {
            dispose();
        }
    }
}
