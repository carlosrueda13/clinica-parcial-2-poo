package com.clinica.view;

import com.clinica.model.Admin;
import com.clinica.model.Doctor;
import com.clinica.viewModel.AdminViewModel;
import com.clinica.viewModel.UserViewModel;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private final AdminViewModel adminViewModel;
    private final UserViewModel userViewModel;
    private final LoginSuccessListener loginListener;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeCombo;
    private JLabel statusLabel;

    public LoginView(AdminViewModel adminViewModel, UserViewModel userViewModel, LoginSuccessListener listener) {
        this.adminViewModel = adminViewModel;
        this.userViewModel = userViewModel;
        this.loginListener = listener;

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Inicio de Sesión - Clínica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Usuario
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Usuario:"), gbc);
        usernameField = new JTextField();
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Contraseña
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Contraseña:"), gbc);
        passwordField = new JPasswordField();
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Tipo de usuario
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Tipo de usuario:"), gbc);
        userTypeCombo = new JComboBox<>(new String[]{"Admin", "Doctor"});
        gbc.gridx = 1;
        add(userTypeCombo, gbc);

        // Botón login
        JButton loginButton = new JButton("Iniciar sesión");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(loginButton, gbc);

        // Estado
        statusLabel = new JLabel("");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 4;
        add(statusLabel, gbc);

        // Evento de login
        loginButton.addActionListener(e -> realizarLogin());
    }

    private void realizarLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String selectedType = (String) userTypeCombo.getSelectedItem();

        if ("Admin".equals(selectedType)) {
            Admin admin = adminViewModel.login(username, password);
            if (admin != null) {
                loginListener.onLoginSuccess(admin, "Admin");
                dispose();
            } else {
                statusLabel.setText("❌ Credenciales de administrador incorrectas.");
            }
        } else if ("Doctor".equals(selectedType)) {
            Doctor doctor = userViewModel.loginDoctor(username, password);
            if (doctor != null) {
                loginListener.onLoginSuccess(doctor, "Doctor");
                dispose();
            } else {
                statusLabel.setText("❌ Credenciales de doctor incorrectas.");
            }
        }
    }
}
