package Presentacion;

import Negocio.PastoServicio;
import Modelo.Pasto;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class GestionPastos extends JFrame {
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextField txtAreaHectareas;
    private JComboBox<String> cbEstadoPasto;
    private JTextField txtCapacidadAnimales;
    private JDateChooser fechaUltimaRotacion;
    private JCheckBox chkEstado;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JTable tablaPastos;
    private DefaultTableModel modeloTabla;
    private PastoServicio pastoServicio;

    public GestionPastos() {
        pastoServicio = new PastoServicio();
        configurarVentana();
        inicializarComponentes();
        configurarTabla();
        cargarPastos();
    }

    private void configurarVentana() {
        setTitle("Gestión de Pastos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Pasto"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(10);
        txtId.setEditable(false);
        panelFormulario.add(txtId, gbc);

        // Nombre
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);

        // Descripción
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setLineWrap(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        panelFormulario.add(scrollDescripcion, gbc);

        // Área en Hectáreas
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Área (Ha):"), gbc);
        gbc.gridx = 1;
        txtAreaHectareas = new JTextField(10);
        panelFormulario.add(txtAreaHectareas, gbc);

        // Estado del Pasto
        gbc.gridx = 0; gbc.gridy = 4;
        panelFormulario.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        cbEstadoPasto = new JComboBox<>(new String[]{"Disponible", "Ocupado", "Mantenimiento"});
        panelFormulario.add(cbEstadoPasto, gbc);

        // Capacidad de Animales
        gbc.gridx = 0; gbc.gridy = 5;
        panelFormulario.add(new JLabel("Capacidad:"), gbc);
        gbc.gridx = 1;
        txtCapacidadAnimales = new JTextField(10);
        panelFormulario.add(txtCapacidadAnimales, gbc);

        // Fecha Última Rotación
        gbc.gridx = 0; gbc.gridy = 6;
        panelFormulario.add(new JLabel("Última Rotación:"), gbc);
        gbc.gridx = 1;
        fechaUltimaRotacion = new JDateChooser();
        fechaUltimaRotacion.setDateFormatString("dd/MM/yyyy");
        panelFormulario.add(fechaUltimaRotacion, gbc);

        // Estado (Activo/Inactivo)
        gbc.gridx = 0; gbc.gridy = 7;
        panelFormulario.add(new JLabel("Activo:"), gbc);
        gbc.gridx = 1;
        chkEstado = new JCheckBox();
        chkEstado.setSelected(true);
        panelFormulario.add(chkEstado, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(0, 200, 83));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(e -> guardarPasto());
        
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(new Color(0, 120, 200));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setEnabled(false);
        btnActualizar.addActionListener(e -> actualizarPasto());
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(200, 0, 0));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(e -> eliminarPasto());
        
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Panel izquierdo (formulario + botones)
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.add(panelFormulario, BorderLayout.CENTER);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

        // Tabla
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Lista de Pastos"));
        
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaPastos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaPastos);
        panelTabla.add(scrollTabla);

        // Agregar paneles al frame
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelTabla, BorderLayout.CENTER);

        // Configurar eventos de la tabla
        tablaPastos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaPastos.getSelectedRow() != -1) {
                cargarDatosEnFormulario(tablaPastos.getSelectedRow());
            }
        });
    }

    private void configurarTabla() {
        String[] columnas = {
            "ID", "Nombre", "Descripción", "Área (Ha)", "Estado",
            "Capacidad", "Última Rotación", "Activo"
        };
        modeloTabla.setColumnIdentifiers(columnas);
        cargarPastos();
    }

    private void cargarPastos() {
        try {
            actualizarTabla(pastoServicio.obtenerPastos());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar los pastos: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla(List<Pasto> pastos) {
        modeloTabla.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Pasto pasto : pastos) {
            Object[] fila = {
                pasto.getId(),
                pasto.getNombre(),
                pasto.getDescripcion(),
                pasto.getAreaHectareas(),
                pasto.getEstadoPasto(),
                pasto.getCapacidadAnimales(),
                pasto.getFechaUltimaRotacion() != null ? 
                    sdf.format(pasto.getFechaUltimaRotacion()) : "",
                pasto.isEstado() ? "Activo" : "Inactivo"
            };
            modeloTabla.addRow(fila);
        }
    }

    private void guardarPasto() {
        try {
            validarCampos();
            
            Pasto pasto = new Pasto();
            obtenerDatosFormulario(pasto);
            
            pastoServicio.guardarPasto(pasto);
            cargarPastos();
            limpiarFormulario();
            
            JOptionPane.showMessageDialog(this,
                "Pasto guardado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al guardar en la base de datos: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarPasto() {
        try {
            validarCampos();
            
            int id = Integer.parseInt(txtId.getText());
            Pasto pasto = new Pasto();
            pasto.setId(id);
            obtenerDatosFormulario(pasto);
            
            pastoServicio.actualizarPasto(pasto);
            cargarPastos();
            limpiarFormulario();
            
            JOptionPane.showMessageDialog(this,
                "Pasto actualizado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al actualizar en la base de datos: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPasto() {
        try {
            int id = Integer.parseInt(txtId.getText());
            
            int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este pasto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
                
            if (confirmar == JOptionPane.YES_OPTION) {
                pastoServicio.eliminarPasto(id);
                cargarPastos();
                limpiarFormulario();
                
                JOptionPane.showMessageDialog(this,
                    "Pasto eliminado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al eliminar de la base de datos: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtAreaHectareas.setText("");
        cbEstadoPasto.setSelectedIndex(0);
        txtCapacidadAnimales.setText("");
        fechaUltimaRotacion.setDate(null);
        chkEstado.setSelected(true);
        
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        
        tablaPastos.clearSelection();
    }

    private void cargarDatosEnFormulario(int row) {
        try {
            Pasto pasto = pastoServicio.obtenerPastos().get(row);
            
            txtId.setText(String.valueOf(pasto.getId()));
            txtNombre.setText(pasto.getNombre());
            txtDescripcion.setText(pasto.getDescripcion());
            txtAreaHectareas.setText(String.valueOf(pasto.getAreaHectareas()));
            cbEstadoPasto.setSelectedItem(pasto.getEstadoPasto());
            txtCapacidadAnimales.setText(String.valueOf(pasto.getCapacidadAnimales()));
            fechaUltimaRotacion.setDate(pasto.getFechaUltimaRotacion());
            chkEstado.setSelected(pasto.isEstado());
            
            btnGuardar.setEnabled(false);
            btnActualizar.setEnabled(true);
            btnEliminar.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar datos del pasto: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void obtenerDatosFormulario(Pasto pasto) {
        pasto.setNombre(txtNombre.getText().trim());
        pasto.setDescripcion(txtDescripcion.getText().trim());
        pasto.setAreaHectareas(Double.parseDouble(txtAreaHectareas.getText().trim()));
        pasto.setEstadoPasto((String) cbEstadoPasto.getSelectedItem());
        pasto.setCapacidadAnimales(Integer.parseInt(txtCapacidadAnimales.getText().trim()));
        pasto.setFechaUltimaRotacion(fechaUltimaRotacion.getDate());
        pasto.setEstado(chkEstado.isSelected());
    }

    private void validarCampos() throws Exception {
        if (txtNombre.getText().trim().isEmpty()) {
            throw new Exception("El nombre es requerido");
        }
        
        if (txtAreaHectareas.getText().trim().isEmpty()) {
            throw new Exception("El área en hectáreas es requerida");
        }
        
        try {
            Double.parseDouble(txtAreaHectareas.getText().trim());
        } catch (NumberFormatException e) {
            throw new Exception("El área debe ser un número válido");
        }
        
        if (txtCapacidadAnimales.getText().trim().isEmpty()) {
            throw new Exception("La capacidad de animales es requerida");
        }
        
        try {
            Integer.parseInt(txtCapacidadAnimales.getText().trim());
        } catch (NumberFormatException e) {
            throw new Exception("La capacidad debe ser un número entero");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new GestionPastos().setVisible(true);
        });
    }
}
