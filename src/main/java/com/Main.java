package com;

import com.clinica.model.Admin;
import com.clinica.model.Doctor;
import com.clinica.view.*;
import com.clinica.viewModel.AdminViewModel;
import com.clinica.viewModel.UserViewModel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminViewModel adminVM = new AdminViewModel();
            UserViewModel userVM = new UserViewModel();

            LoginView loginView = new LoginView(adminVM, userVM, (user, role) -> {
                if ("Admin".equals(role) && user instanceof Admin) {
                    AdminDashboardView adminView = new AdminDashboardView();
                    adminView.setVisible(true);
                } else if ("Doctor".equals(role) && user instanceof Doctor) {
                    DoctorDashboardView doctorView = new DoctorDashboardView((Doctor) user, userVM);
                    doctorView.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "❌ Tipo de usuario desconocido.");
                }
            });

            loginView.setVisible(true);
        });
    }
}
