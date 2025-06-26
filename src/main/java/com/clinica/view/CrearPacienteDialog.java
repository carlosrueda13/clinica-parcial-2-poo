package com.clinica.view;

import com.clinica.model.UserDTO;
import com.clinica.viewModel.AdminViewModel;

import javax.swing.*;
import java.awt.*;

public class CrearPacienteDialog extends JDialog {

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtPhone;

    private AdminViewModel viewModel;

    public CrearPacienteDialog(Frame parent, AdminViewModel viewModel) {
        super(parent, "Crear Paciente", true);
        this.viewModel = viewModel;

        setLayout(new GridLayout(4, 2, 10, 10));
        setSize(350, 200);
        setLocationRelativeTo(parent);

        // Campos
        add(new JLabel("ID:"));
        txtId = new JTextField();
        add(txtId);

        add(new JLabel("Nombre:"));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("Teléfono:"));
        txtPhone = new JTextField();
        add(txtPhone);

        // Botón crear
        JButton btnCrear = new JButton("Crear Paciente");
        btnCrear.addActionListener(e -> crearPaciente());
        add(btnCrear);

        // Botón cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }

    private void crearPaciente() {
        UserDTO pacienteDTO = new UserDTO();
        pacienteDTO.setId(txtId.getText());
        pacienteDTO.setName(txtName.getText());
        pacienteDTO.setPhone(txtPhone.getText());

        String resultado = viewModel.createPatient(pacienteDTO);
        JOptionPane.showMessageDialog(this, resultado);
        if (resultado.contains("✅")) {
            dispose();
        }
    }
}
