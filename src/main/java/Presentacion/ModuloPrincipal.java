package Presentacion;

import Modelo.Usuario;
import javax.swing.*;
import java.awt.*;


public class ModuloPrincipal extends JFrame {
    private final Usuario usuarioActual;
    private JPanel panelBotones;
    
    public ModuloPrincipal(Usuario usuario) {
        this.usuarioActual = usuario;
        configurarVentana();
        inicializarComponentes();
    }
    
    private void configurarVentana() {
        setTitle("Sistema de Gestión Ganadera Elvis - " + usuarioActual.getRol());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Configurar el ícono
        try {
            setIconImage(new ImageIcon(getClass().getResource("/imagenes/vaca.png")).getImage());
        } catch (Exception e) {
            System.err.println("Error al cargar el ícono: " + e.getMessage());
        }
    }
    
    private void inicializarComponentes() {
        // Panel superior con información del usuario
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(51, 122, 183));
        panelSuperior.setPreferredSize(new Dimension(800, 100));
        
        JLabel lblBienvenida = new JLabel("Bienvenido, " + usuarioActual.getNombreUsuario());
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        lblBienvenida.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelSuperior.add(lblBienvenida, BorderLayout.WEST);
        
        // Botón de cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        JPanel panelCerrarSesion = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelCerrarSesion.setOpaque(false);
        panelCerrarSesion.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelCerrarSesion.add(btnCerrarSesion);
        panelSuperior.add(panelCerrarSesion, BorderLayout.EAST);
        
        // Panel central con botones de módulos
        panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Crear botones de módulos
        JButton btnGanado = crearBotonModulo("Gestión de Ganado", "/imagenes/vaca.png", e -> abrirGestionGanado());
        JButton btnPastos = crearBotonModulo("Gestión de Pastos", "/imagenes/pasto.png", e -> abrirGestionPastos());
        JButton btnClientes = crearBotonModulo("Gestión de Clientes", "/imagenes/cliente.png", e -> abrirGestionClientes());
        JButton btnProveedores = crearBotonModulo("Gestión de Proveedores", "/imagenes/proveedor.png", e -> abrirGestionProveedores());
        JButton btnFacturas = crearBotonModulo("Gestión de Facturas", "/imagenes/factura.png", e -> abrirGestionFacturas());
        JButton btnSanitario = crearBotonModulo("Gestión Sanitaria", "/imagenes/salud.png", e -> abrirGestionSanitaria());
        
        // Agregar botones al panel
        gbc.gridx = 0; gbc.gridy = 0;
        
        // Solo mostrar estos botones si es admin
        if ("ADMIN".equalsIgnoreCase(usuarioActual.getRol())) {
            gbc.gridx = 1;
            panelBotones.add(btnPastos, gbc);
            gbc.gridx = 2;
            panelBotones.add(btnClientes, gbc);
            gbc.gridx = 3;
            panelBotones.add(btnProveedores, gbc);
            gbc.gridx = 4;
            panelBotones.add(btnFacturas, gbc);
        }
        // Estos botones siempre son visibles
        gbc.gridx = 0;
        panelBotones.add(btnGanado, gbc);
        gbc.gridx = 5;
        panelBotones.add(btnSanitario, gbc);
        
        // Agregar paneles a la ventana
        add(panelSuperior, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
    }
    
    private JButton crearBotonModulo(String texto, String rutaIcono, java.awt.event.ActionListener listener) {
        JButton boton = new JButton();
        boton.setLayout(new BorderLayout());
        
        // Configurar icono
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource(rutaIcono));
            Image img = icono.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.err.println("Error al cargar el ícono para " + texto + ": " + e.getMessage());
        }
        
        // Configurar texto
        boton.setText(texto);
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        
        // Configurar apariencia
        boton.setPreferredSize(new Dimension(200, 200));
        boton.setBackground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Configurar eventos
        boton.addActionListener(listener);
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(240, 240, 240));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.WHITE);
            }
        });
        
        return boton;
    }
    
    private void abrirGestionGanado() {
        SwingUtilities.invokeLater(() -> {
            try {
                GestionGanado gestionGanado = new GestionGanado();
                gestionGanado.setVisible(true);
            } catch (Exception e) {
                mostrarError("Error al abrir Gestión de Ganado", e);
            }
        });
    }
    
    private void abrirGestionPastos() {
        if (!"ADMIN".equalsIgnoreCase(usuarioActual.getRol())) {
            JOptionPane.showMessageDialog(this,
                "No tiene permisos para acceder a este módulo",
                "Acceso denegado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            try {
                GestionPastos gestionPastos = new GestionPastos();
                gestionPastos.setVisible(true);
            } catch (Exception e) {
                mostrarError("Error al abrir Gestión de Pastos", e);
            }
        });
    }
    
    private void abrirGestionClientes() {
        if (!"ADMIN".equalsIgnoreCase(usuarioActual.getRol())) {
            JOptionPane.showMessageDialog(this,
                "No tiene permisos para acceder a este módulo",
                "Acceso denegado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            try {
                GestionClientes gestionClientes = new GestionClientes();
                gestionClientes.setVisible(true);
            } catch (Exception e) {
                mostrarError("Error al abrir Gestión de Clientes", e);
            }
        });
    }
    
    private void abrirGestionProveedores() {
        if (!"ADMIN".equalsIgnoreCase(usuarioActual.getRol())) {
            JOptionPane.showMessageDialog(this,
                "No tiene permisos para acceder a este módulo",
                "Acceso denegado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            try {
                GestionProveedores gestionProveedores = new GestionProveedores();
                gestionProveedores.setVisible(true);
            } catch (Exception e) {
                mostrarError("Error al abrir Gestión de Proveedores", e);
            }
        });
    }
    
    private void abrirGestionFacturas() {
        if (!"ADMIN".equalsIgnoreCase(usuarioActual.getRol())) {
            JOptionPane.showMessageDialog(this,
                "No tiene permisos para acceder a este módulo",
                "Acceso denegado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            try {
                GestionFacturas gestionFacturas = new GestionFacturas();
                gestionFacturas.setVisible(true);
            } catch (Exception e) {
                mostrarError("Error al abrir Gestión de Facturas", e);
            }
        });
    }
    
    private void abrirGestionSanitaria() {
        SwingUtilities.invokeLater(() -> {
            try {
                GestionSanitaria gestionSanitaria = new GestionSanitaria();
                gestionSanitaria.setVisible(true);
            } catch (Exception e) {
                mostrarError("Error al abrir Gestión Sanitaria", e);
            }
        });
    }
    
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar cierre de sesión",
            JOptionPane.YES_NO_OPTION);
            
        if (confirmacion == JOptionPane.YES_OPTION) {
            dispose();
            new SistemaLogin().setVisible(true);
        }
    }
    
    private void mostrarError(String mensaje, Exception e) {
        JOptionPane.showMessageDialog(this,
            mensaje + "\nError: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
