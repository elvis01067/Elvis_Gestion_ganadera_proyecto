package Presentacion;

import Modelo.Proveedor;
import Negocio.ProveedorServicio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class GestionProveedores extends JFrame {
    private final ProveedorServicio proveedorServicio;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCedulaRuc;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JComboBox<String> cboTipoProducto;
    private JTable tablaProveedores;
    private DefaultTableModel modeloTabla;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnEliminar;
    private Proveedor proveedorSeleccionado;

    public GestionProveedores() {
        this.proveedorServicio = new ProveedorServicio();
        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Gestión de Proveedores");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelFormulario.add(txtApellido);

        panelFormulario.add(new JLabel("Cédula/RUC:"));
        txtCedulaRuc = new JTextField();
        panelFormulario.add(txtCedulaRuc);

        panelFormulario.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelFormulario.add(txtDireccion);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);

        panelFormulario.add(new JLabel("Tipo de Producto:"));
        cboTipoProducto = new JComboBox<>(new String[]{"alimentos", "medicina", "equipos"});
        panelFormulario.add(cboTipoProducto);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setEnabled(false);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEliminar);

        // Panel superior (formulario + botones)
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        // Tabla
        modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Apellido", "Cédula/RUC", "Dirección", "Teléfono", "Email", "Tipo Producto"}, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaProveedores = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaProveedores);

        // Agregar componentes a la ventana
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Configurar eventos
        btnGuardar.addActionListener(e -> guardarProveedor());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnEliminar.addActionListener(e -> eliminarProveedor());

        tablaProveedores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaProveedores.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    cargarProveedorSeleccionado(filaSeleccionada);
                    btnEliminar.setEnabled(true);
                } else {
                    proveedorSeleccionado = null;
                    btnEliminar.setEnabled(false);
                }
            }
        });
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        for (Proveedor proveedor : proveedores) {
            modeloTabla.addRow(new Object[]{
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getApellido(),
                proveedor.getCedulaRuc(),
                proveedor.getDireccion(),
                proveedor.getTelefono(),
                proveedor.getEmail(),
                proveedor.getTipoProducto()
            });
        }
    }

    private void guardarProveedor() {
        try {
            Proveedor proveedor = new Proveedor();
            if (proveedorSeleccionado != null) {
                proveedor.setId(proveedorSeleccionado.getId());
            }
            proveedor.setNombre(txtNombre.getText().trim());
            proveedor.setApellido(txtApellido.getText().trim());
            proveedor.setCedulaRuc(txtCedulaRuc.getText().trim());
            proveedor.setDireccion(txtDireccion.getText().trim());
            proveedor.setTelefono(txtTelefono.getText().trim());
            proveedor.setEmail(txtEmail.getText().trim());
            proveedor.setTipoProducto(cboTipoProducto.getSelectedItem().toString());
            proveedor.setFechaRegistro(new Date());
            proveedor.setEstado(true);

            boolean exito;
            if (proveedorSeleccionado == null) {
                exito = proveedorServicio.registrarProveedor(proveedor);
            } else {
                exito = proveedorServicio.actualizarProveedor(proveedor);
            }

            if (exito) {
                JOptionPane.showMessageDialog(this,
                    "Proveedor guardado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al guardar el proveedor",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al guardar el proveedor: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void eliminarProveedor() {
        if (proveedorSeleccionado == null) {
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar este proveedor?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = proveedorServicio.eliminarProveedor(proveedorSeleccionado.getId());
            if (exito) {
                JOptionPane.showMessageDialog(this,
                    "Proveedor eliminado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar el proveedor",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtCedulaRuc.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        cboTipoProducto.setSelectedIndex(0);
        proveedorSeleccionado = null;
        btnEliminar.setEnabled(false);
        tablaProveedores.clearSelection();
    }

    private void cargarProveedorSeleccionado(int fila) {
        proveedorSeleccionado = new Proveedor();
        proveedorSeleccionado.setId((Integer) modeloTabla.getValueAt(fila, 0));
        proveedorSeleccionado.setNombre((String) modeloTabla.getValueAt(fila, 1));
        proveedorSeleccionado.setApellido((String) modeloTabla.getValueAt(fila, 2));
        proveedorSeleccionado.setCedulaRuc((String) modeloTabla.getValueAt(fila, 3));
        proveedorSeleccionado.setDireccion((String) modeloTabla.getValueAt(fila, 4));
        proveedorSeleccionado.setTelefono((String) modeloTabla.getValueAt(fila, 5));
        proveedorSeleccionado.setEmail((String) modeloTabla.getValueAt(fila, 6));
        proveedorSeleccionado.setTipoProducto((String) modeloTabla.getValueAt(fila, 7));

        txtNombre.setText(proveedorSeleccionado.getNombre());
        txtApellido.setText(proveedorSeleccionado.getApellido());
        txtCedulaRuc.setText(proveedorSeleccionado.getCedulaRuc());
        txtDireccion.setText(proveedorSeleccionado.getDireccion());
        txtTelefono.setText(proveedorSeleccionado.getTelefono());
        txtEmail.setText(proveedorSeleccionado.getEmail());
        cboTipoProducto.setSelectedItem(proveedorSeleccionado.getTipoProducto());
    }
}
