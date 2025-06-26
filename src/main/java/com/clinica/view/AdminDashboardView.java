package com.clinica.view;

import javax.swing.*;
import java.awt.*;
import com.clinica.viewModel.AdminViewModel;
import com.clinica.viewModel.UserViewModel;

public class AdminDashboardView extends JFrame {

    private final AdminViewModel adminViewModel = new AdminViewModel();
    private final UserViewModel userViewModel = new UserViewModel();


    public AdminDashboardView() {
        setTitle("Panel de Administración");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        // Botones
        JButton btnCrearDoctor = new JButton("Crear Doctor");
        JButton btnCrearPaciente = new JButton("Crear Paciente");
        JButton btnAgendarConsulta = new JButton("Agendar Consulta");
        JButton btnModificarConsulta = new JButton("Modificar Consulta");
        JButton btnListarConsultasPorDoctor = new JButton("Listar Consultas por Doctor");

        // Agregar botones al panel
        panel.add(btnCrearDoctor);
        panel.add(btnCrearPaciente);
        panel.add(btnAgendarConsulta);
        panel.add(btnModificarConsulta);
        panel.add(btnListarConsultasPorDoctor);

        // Acción para cada botón (por ahora solo muestran mensaje)
        btnCrearDoctor.addActionListener(e -> {
            CrearDoctorDialog dialog = new CrearDoctorDialog(this, adminViewModel);
            dialog.setVisible(true);
        });

        btnCrearPaciente.addActionListener(e -> {
            CrearPacienteDialog dialog = new CrearPacienteDialog(this, adminViewModel);
            dialog.setVisible(true);
        });

        btnAgendarConsulta.addActionListener(e -> {
            CrearConsultaView crearConsulta = new CrearConsultaView(this, adminViewModel);
            crearConsulta.setVisible(true);
        });
        btnModificarConsulta.addActionListener(e -> {
            ModificarConsultaView modificarConsulta = new ModificarConsultaView(this, adminViewModel, userViewModel);
            modificarConsulta.setVisible(true);
        });

        btnListarConsultasPorDoctor.addActionListener(e -> {
            ListarConsultasPorDoctorView listarConsultas = new ListarConsultasPorDoctorView(this, adminViewModel, userViewModel, null);
                });

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminDashboardView view = new AdminDashboardView();
            view.setVisible(true);
        });
    }
}
