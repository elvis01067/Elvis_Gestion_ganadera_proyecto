package Presentacion;

import Modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SistemaLogin extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    
    // Credenciales fijas
    private static final HashMap<String, String[]> USUARIOS = new HashMap<>();
    static {
        // usuario -> [contraseña, rol]
        USUARIOS.put("admin", new String[]{"admin", "ADMIN"});
        USUARIOS.put("veterinario", new String[]{"1234", "VETERINARIO"});
        USUARIOS.put("operario", new String[]{"1234", "OPERARIO"});
    }

    public SistemaLogin() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("SISTEMA DE AUTENTICACIÓN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(240, 240, 240));
        setLayout(null);
    }

    private void inicializarComponentes() {
        // Título
        JLabel lblTitulo = new JLabel("SISTEMA DE AUTENTICACIÓN");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(80, 20, 300, 30);
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

        // Botón Iniciar Sesión
        btnIniciarSesion = new JButton("Iniciar sesión");
        btnIniciarSesion.setBounds(50, 220, 140, 35);
        btnIniciarSesion.setBackground(new Color(0, 200, 83)); // Verde
        btnIniciarSesion.setForeground(Color.WHITE);
        btnIniciarSesion.setFont(new Font("Arial", Font.BOLD, 14));
        btnIniciarSesion.setFocusPainted(false);
        btnIniciarSesion.setBorderPainted(false);
        btnIniciarSesion.addActionListener(event -> iniciarSesion());
        add(btnIniciarSesion);


    }

    private void iniciarSesion() {
        String nombreUsuario = txtUsuario.getText().trim().toLowerCase();
        String contrasena = new String(txtContrasena.getPassword());
        
        if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, complete todos los campos", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] credenciales = USUARIOS.get(nombreUsuario);
        if (credenciales != null && credenciales[0].equals(contrasena)) {
            Usuario usuario = new Usuario(1, nombreUsuario, credenciales[1], true);
            JOptionPane.showMessageDialog(this, 
                String.format("Bienvenido %s (%s)", 
                    usuario.getNombreUsuario(), 
                    usuario.getRol()), 
                "Inicio de sesión exitoso", 
                JOptionPane.INFORMATION_MESSAGE);
            abrirSistemaGestion(usuario);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Usuario o contraseña incorrectos\n\nUsuarios disponibles:\n" +
                "- admin/admin (Administrador)\n" +
                "- veterinario/1234 (Veterinario)\n" +
                "- operario/1234 (Operario)", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtContrasena.setText("");
        }
    }

    private void abrirSistemaGestion(Usuario usuario) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Abrir el módulo principal
                ModuloPrincipal moduloPrincipal = new ModuloPrincipal(usuario);
                moduloPrincipal.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al abrir el sistema: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new SistemaLogin().setVisible(true);
        });
    }
}