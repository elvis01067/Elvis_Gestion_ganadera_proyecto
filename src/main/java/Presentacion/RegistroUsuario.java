package Presentacion;

import Datos.ConexionDB;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroUsuario extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JComboBox<String> cbRol;
    private JButton btnRegistrar;
    private JButton btnCancelar;

    public RegistroUsuario() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Registro de Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(240, 240, 240));
        setLayout(null);
    }

    private void inicializarComponentes() {
        // Título
        JLabel lblTitulo = new JLabel("REGISTRO DE USUARIO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(100, 20, 300, 30);
        add(lblTitulo);

        // Usuario
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsuario.setBounds(50, 80, 100, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(50, 105, 300, 30);
        add(txtUsuario);

        // Contraseña
        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(new Font("Arial", Font.PLAIN, 14));
        lblContrasena.setBounds(50, 145, 100, 25);
        add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(50, 170, 300, 30);
        add(txtContrasena);

        // Rol
        JLabel lblRol = new JLabel("Rol");
        lblRol.setFont(new Font("Arial", Font.PLAIN, 14));
        lblRol.setBounds(50, 210, 100, 25);
        add(lblRol);

        cbRol = new JComboBox<>(new String[]{"admin", "veterinario"});
        cbRol.setBounds(50, 235, 300, 30);
        add(cbRol);

        // Botones
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(50, 280, 140, 35);
        btnRegistrar.setBackground(new Color(0, 200, 83));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.addActionListener(e -> registrarUsuario());
        add(btnRegistrar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(210, 280, 140, 35);
        btnCancelar.setBackground(new Color(200, 0, 0));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }

    private void registrarUsuario() {
        String nombreUsuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());
        String rol = (String) cbRol.getSelectedItem();

        if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, complete todos los campos",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO usuarios (nombre_usuario, contrasena, rol, estado) VALUES (?, MD5(?), ?, TRUE)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasena);
            stmt.setString(3, rol);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this,
                "Usuario registrado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                JOptionPane.showMessageDialog(this,
                    "El nombre de usuario ya existe",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al registrar usuario: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace();
        }
    }
}
