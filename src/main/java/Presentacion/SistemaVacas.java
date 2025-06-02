package Presentacion;

import Modelo.Vacas;
import Modelo.Usuario;
import Negocio.VacasServicio;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import com.toedter.calendar.JDateChooser;
import javax.swing.table.DefaultTableModel;

public class SistemaVacas extends JFrame {
    private final VacasServicio vacasServicio;
    private final Usuario usuarioActual;
    private JTable tablaVacas;
    
    // Campos del formulario
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtRaza;
    private JDateChooser fechaNacimiento;
    private JComboBox<String> cbGenero;
    private JTextField txtPesoKg;
    private JComboBox<String> cbEstadoSalud;
    private JComboBox<String> cbProposito;
    private JComboBox<String> cbEstadoProductivo;
    private JComboBox<String> cbPasto;
    private JComboBox<String> cbMadre;
    private JComboBox<String> cbPadre;
    private JDateChooser fechaAdquisicion;
    private JTextField txtPrecioCompra;
    private JCheckBox chkEstado;
    
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    
    private DefaultTableModel modeloTabla;
    
    public SistemaVacas(Usuario usuario) {
        System.out.println("Iniciando SistemaVacas para usuario: " + usuario.getNombreUsuario());
        System.out.println("Rol del usuario: " + usuario.getRol());
        
        this.vacasServicio = new VacasServicio();
        this.usuarioActual = usuario;
        
        try {
            configurarVentana();
            System.out.println("Ventana configurada");
            
            inicializarComponentes();
            System.out.println("Componentes inicializados");
            
            cargarDatos();
            System.out.println("Datos cargados");
            
            configurarPermisos();
            System.out.println("Permisos configurados");
        } catch (Exception e) {
            System.err.println("Error al inicializar SistemaVacas: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Error al inicializar el sistema: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void configurarPermisos() {
        // Configurar permisos según el rol del usuario
        boolean esAdmin = "ADMIN".equalsIgnoreCase(usuarioActual.getRol());
        boolean esVeterinario = "VETERINARIO".equalsIgnoreCase(usuarioActual.getRol());
        
        // Por defecto, solo los administradores y veterinarios pueden modificar datos
        btnGuardar.setEnabled(esAdmin || esVeterinario);
        btnActualizar.setEnabled(esAdmin || esVeterinario);
        btnEliminar.setEnabled(esAdmin);
        
        // Campos del formulario
        txtId.setEnabled(false); // El ID nunca se modifica manualmente
        txtNombre.setEnabled(esAdmin || esVeterinario);
        txtRaza.setEnabled(esAdmin || esVeterinario);
        fechaNacimiento.setEnabled(esAdmin || esVeterinario);
        cbGenero.setEnabled(esAdmin || esVeterinario);
        txtPesoKg.setEnabled(esAdmin || esVeterinario);
        cbEstadoSalud.setEnabled(esAdmin || esVeterinario);
        cbProposito.setEnabled(esAdmin || esVeterinario);
        cbEstadoProductivo.setEnabled(esAdmin || esVeterinario);
        cbPasto.setEnabled(esAdmin || esVeterinario);
        cbMadre.setEnabled(esAdmin || esVeterinario);
        cbPadre.setEnabled(esAdmin || esVeterinario);
        fechaAdquisicion.setEnabled(esAdmin || esVeterinario);
        txtPrecioCompra.setEnabled(esAdmin);
        chkEstado.setEnabled(esAdmin);
    }
    
    private void configurarVentana() {
        setTitle("Gestión de Ganado - " + usuarioActual.getRol());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        

        
        // Configurar el ícono y el tema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void inicializarComponentes() {
        // Panel principal con scroll
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        add(scrollPane);
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Campos del formulario - Columna 1
        int y = 0;
        
        // ID
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(10);
        txtId.setEditable(false);
        panelFormulario.add(txtId, gbc);
        
        // Nombre
        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);
        
        // Raza
        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulario.add(new JLabel("Raza:"), gbc);
        gbc.gridx = 1;
        txtRaza = new JTextField(20);
        panelFormulario.add(txtRaza, gbc);
        
        // Fecha Nacimiento
        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulario.add(new JLabel("Fecha Nacimiento:"), gbc);
        gbc.gridx = 1;
        fechaNacimiento = new JDateChooser();
        fechaNacimiento.setDateFormatString("dd/MM/yyyy");
        panelFormulario.add(fechaNacimiento, gbc);
        
        // Género
        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulario.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        cbGenero = new JComboBox<>(new String[]{"macho", "hembra"});
        panelFormulario.add(cbGenero, gbc);
        
        // Peso
        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panelFormulario.add(new JLabel("Peso (kg):"), gbc);
        gbc.gridx = 1;
        txtPesoKg = new JTextField(10);
        panelFormulario.add(txtPesoKg, gbc);
        
        // Campos del formulario - Columna 2
        y = 0;
        gbc.gridx = 2; gbc.gridy = y;
        gbc.insets = new Insets(5, 15, 5, 5); // Más espacio entre columnas
        
        // Estado de Salud
        panelFormulario.add(new JLabel("Estado de Salud:"), gbc);
        gbc.gridx = 3;
        cbEstadoSalud = new JComboBox<>(new String[]{"sano", "enfermo", "tratamiento", "cuarentena"});
        panelFormulario.add(cbEstadoSalud, gbc);
        
        // Propósito
        y++;
        gbc.gridx = 2; gbc.gridy = y;
        panelFormulario.add(new JLabel("Propósito:"), gbc);
        gbc.gridx = 3;
        cbProposito = new JComboBox<>(new String[]{"carne", "leche", "reproduccion", "exhibicion"});
        panelFormulario.add(cbProposito, gbc);
        
        // Estado Productivo
        y++;
        gbc.gridx = 2; gbc.gridy = y;
        panelFormulario.add(new JLabel("Estado Productivo:"), gbc);
        gbc.gridx = 3;
        cbEstadoProductivo = new JComboBox<>(new String[]{"produccion", "seca", "preñada", "novilla", "descarte"});
        panelFormulario.add(cbEstadoProductivo, gbc);
        
        // Pasto
        y++;
        gbc.gridx = 2; gbc.gridy = y;
        panelFormulario.add(new JLabel("Pasto:"), gbc);
        gbc.gridx = 3;
        cbPasto = new JComboBox<>(); // Se llenará desde la base de datos
        panelFormulario.add(cbPasto, gbc);
        
        // Madre
        y++;
        gbc.gridx = 2; gbc.gridy = y;
        panelFormulario.add(new JLabel("Madre:"), gbc);
        gbc.gridx = 3;
        cbMadre = new JComboBox<>(); // Se llenará con las vacas hembra
        panelFormulario.add(cbMadre, gbc);
        
        // Padre
        y++;
        gbc.gridx = 2; gbc.gridy = y;
        panelFormulario.add(new JLabel("Padre:"), gbc);
        gbc.gridx = 3;
        cbPadre = new JComboBox<>(); // Se llenará con las vacas macho
        panelFormulario.add(cbPadre, gbc);
        
        // Fecha Adquisición y Precio
        y++;
        gbc.gridx = 2; gbc.gridy = y;
        panelFormulario.add(new JLabel("Fecha Adquisición:"), gbc);
        gbc.gridx = 3;
        fechaAdquisicion = new JDateChooser();
        fechaAdquisicion.setDateFormatString("dd/MM/yyyy");
        panelFormulario.add(fechaAdquisicion, gbc);
        
        y++;
        gbc.gridx = 2; gbc.gridy = y;
        panelFormulario.add(new JLabel("Precio Compra:"), gbc);
        gbc.gridx = 3;
        txtPrecioCompra = new JTextField(10);
        panelFormulario.add(txtPrecioCompra, gbc);
        
        // Estado (activo/inactivo)
        y++;
        gbc.gridx = 2; gbc.gridy = y;
        panelFormulario.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 3;
        chkEstado = new JCheckBox("Activo");
        chkEstado.setSelected(true);
        panelFormulario.add(chkEstado, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        
        btnGuardar.addActionListener(e -> guardarVaca());
        btnActualizar.addActionListener(e -> actualizarVaca());
        btnEliminar.addActionListener(e -> eliminarVaca());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        // Tabla de vacas
        String[] columnas = {"ID", "Nombre", "Raza", "Fecha Nac.", "Género", "Peso", "Estado Salud", 
                          "Propósito", "Estado Prod.", "Pasto", "Madre", "Padre", "Fecha Adq.", "Precio", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tablaVacas = new JTable(modeloTabla);
        tablaVacas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tablaVacas.getSelectedRow();
                if (row != -1) {
                    cargarDatosEnFormulario(row);
                }
            }
        });
        
        JScrollPane scrollPaneTabla = new JScrollPane(tablaVacas);
        
        // Agregar componentes al panel principal
        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        panelPrincipal.add(scrollPaneTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
    }
    
    
    private void cargarDatos() {
        List<Vacas> vacas = vacasServicio.listarTodasVacas();
        modeloTabla.setRowCount(0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Vacas vaca : vacas) {
            Object[] fila = {
                vaca.getId(),
                vaca.getNombre(),
                vaca.getRaza(),
                vaca.getFechaNacimiento() != null ? sdf.format(vaca.getFechaNacimiento()) : "",
                vaca.getGenero(),
                vaca.getPesoKg(),
                vaca.getEstadoSalud(),
                vaca.getProposito(),
                vaca.getEstadoProductivo(),
                vaca.getIdPasto(),
                vaca.getIdMadre(),
                vaca.getIdPadre(),
                vaca.getFechaAdquisicion() != null ? sdf.format(vaca.getFechaAdquisicion()) : "",
                vaca.getPrecioCompra(),
                vaca.isEstado() ? "Activo" : "Inactivo"
            };
            modeloTabla.addRow(fila);
        }
    }

    private void guardarVaca() {
        try {
            Vacas vaca = obtenerDatosFormulario();
            if (vacasServicio.registrarVaca(vaca)) {
                JOptionPane.showMessageDialog(this, "Vaca registrada exitosamente");
                limpiarFormulario();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar la vaca");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void actualizarVaca() {
        // TODO: Implementar actualización
    }
    
    private void eliminarVaca() {
        // TODO: Implementar eliminación
    }
    
    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtRaza.setText("");
        fechaNacimiento.setDate(null);
        txtPesoKg.setText("");
        cbGenero.setSelectedIndex(0);
        cbProposito.setSelectedIndex(0);
        cbEstadoSalud.setSelectedIndex(0);
        cbEstadoProductivo.setSelectedIndex(0);
        cbPasto.setSelectedIndex(-1);
        cbMadre.setSelectedIndex(-1);
        cbPadre.setSelectedIndex(-1);
        fechaAdquisicion.setDate(null);
        txtPrecioCompra.setText("");
        chkEstado.setSelected(true);
        
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
    
    private Vacas obtenerDatosFormulario() {
        Vacas vaca = new Vacas();
        
        if (!txtId.getText().isEmpty()) {
            vaca.setId(Integer.parseInt(txtId.getText()));
        }
        
        vaca.setNombre(txtNombre.getText());
        vaca.setRaza(txtRaza.getText());
        vaca.setFechaNacimiento(fechaNacimiento.getDate());
        vaca.setPesoKg(Double.parseDouble(txtPesoKg.getText()));
        vaca.setGenero((String) cbGenero.getSelectedItem());
        vaca.setProposito((String) cbProposito.getSelectedItem());
        vaca.setEstadoSalud((String) cbEstadoSalud.getSelectedItem());
        vaca.setEstadoProductivo((String) cbEstadoProductivo.getSelectedItem());
        
        Object pastoSeleccionado = cbPasto.getSelectedItem();
        if (pastoSeleccionado != null) {
            vaca.setIdPasto(Integer.parseInt(pastoSeleccionado.toString()));
        }
        
        Object madreSeleccionada = cbMadre.getSelectedItem();
        if (madreSeleccionada != null) {
            vaca.setIdMadre(Integer.parseInt(madreSeleccionada.toString()));
        }
        
        Object padreSeleccionado = cbPadre.getSelectedItem();
        if (padreSeleccionado != null) {
            vaca.setIdPadre(Integer.parseInt(padreSeleccionado.toString()));
        }
        
        vaca.setFechaAdquisicion(fechaAdquisicion.getDate());
        
        if (!txtPrecioCompra.getText().isEmpty()) {
            vaca.setPrecioCompra(Double.parseDouble(txtPrecioCompra.getText()));
        }
        
        vaca.setEstado(chkEstado.isSelected());
        
        return vaca;
    }
    
    private void cargarDatosEnFormulario(int row) {
        txtId.setText(modeloTabla.getValueAt(row, 0).toString());
        txtNombre.setText((String) modeloTabla.getValueAt(row, 1));
        txtRaza.setText((String) modeloTabla.getValueAt(row, 2));
        
        // Convertir fecha de nacimiento
        String fechaNacStr = (String) modeloTabla.getValueAt(row, 3);
        if (!fechaNacStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaNacimiento.setDate(sdf.parse(fechaNacStr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        cbGenero.setSelectedItem(modeloTabla.getValueAt(row, 4));
        txtPesoKg.setText(modeloTabla.getValueAt(row, 5).toString());
        cbEstadoSalud.setSelectedItem(modeloTabla.getValueAt(row, 6));
        cbProposito.setSelectedItem(modeloTabla.getValueAt(row, 7));
        cbEstadoProductivo.setSelectedItem(modeloTabla.getValueAt(row, 8));
        
        // Seleccionar pasto, madre y padre si existen
        Object idPasto = modeloTabla.getValueAt(row, 9);
        if (idPasto != null) {
            for (int i = 0; i < cbPasto.getItemCount(); i++) {
                if (cbPasto.getItemAt(i).toString().equals(idPasto.toString())) {
                    cbPasto.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        Object idMadre = modeloTabla.getValueAt(row, 10);
        if (idMadre != null) {
            for (int i = 0; i < cbMadre.getItemCount(); i++) {
                if (cbMadre.getItemAt(i).toString().equals(idMadre.toString())) {
                    cbMadre.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        Object idPadre = modeloTabla.getValueAt(row, 11);
        if (idPadre != null) {
            for (int i = 0; i < cbPadre.getItemCount(); i++) {
                if (cbPadre.getItemAt(i).toString().equals(idPadre.toString())) {
                    cbPadre.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        // Convertir fecha de adquisición
        String fechaAdqStr = (String) modeloTabla.getValueAt(row, 12);
        if (!fechaAdqStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaAdquisicion.setDate(sdf.parse(fechaAdqStr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        txtPrecioCompra.setText(modeloTabla.getValueAt(row, 13).toString());
        chkEstado.setSelected(modeloTabla.getValueAt(row, 14).equals("Activo"));
        
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }
    

}
