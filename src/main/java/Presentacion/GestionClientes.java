package Presentacion;

import Modelo.Cliente;

import Negocio.ClienteServicio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;

public class GestionClientes extends JFrame {
    private final ClienteServicio clienteServicio;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCedulaRuc;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JComboBox<String> cboTipo;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnEliminar;
    private Cliente clienteSeleccionado;

    public GestionClientes() {
        this.clienteServicio = new ClienteServicio();
        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Gestión de Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos del formulario
        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtCedulaRuc = new JTextField(15);
        txtDireccion = new JTextField(30);
        txtTelefono = new JTextField(15);
        txtEmail = new JTextField(25);
        cboTipo = new JComboBox<>(new String[]{"Persona", "Empresa"});

        // Agregar campos al panel
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtApellido, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Cédula/RUC:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtCedulaRuc, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtDireccion, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtTelefono, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panelFormulario.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        panelFormulario.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(cboTipo, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setEnabled(false);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEliminar);

        // Tabla de clientes
        modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Apellido", "Cédula/RUC", "Dirección", "Teléfono", "Email", "Tipo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaClientes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);

        // Configurar eventos
        btnGuardar.addActionListener(e -> guardarCliente());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnEliminar.addActionListener(e -> eliminarCliente());

        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaClientes.getSelectedRow();
                if (fila >= 0) {
                    cargarClienteSeleccionado(fila);
                }
            }
        });

        // Agregar componentes a la ventana
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Cliente> clientes = clienteServicio.listarTodosClientes();
        for (Cliente cliente : clientes) {
            modeloTabla.addRow(new Object[]{
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getCedulaRuc(),
                cliente.getDireccion(),
                cliente.getTelefono(),
                cliente.getEmail(),
                cliente.getTipo()
            });
        }
    }

    private void guardarCliente() {
        try {
            Cliente cliente = new Cliente();
            if (clienteSeleccionado != null) {
                cliente.setId(clienteSeleccionado.getId());
            }
            cliente.setNombre(txtNombre.getText().trim());
            cliente.setApellido(txtApellido.getText().trim());
            cliente.setCedulaRuc(txtCedulaRuc.getText().trim());
            cliente.setDireccion(txtDireccion.getText().trim());
            cliente.setTelefono(txtTelefono.getText().trim());
            cliente.setEmail(txtEmail.getText().trim());
            cliente.setTipo(cboTipo.getSelectedItem().toString());
            cliente.setFechaRegistro(new Date());
            cliente.setEstado(true);


            boolean exito;
            if (clienteSeleccionado == null) {
                exito = clienteServicio.registrarCliente(cliente);
            } else {
                exito = clienteServicio.actualizarCliente(cliente);
            }

            if (exito) {
                JOptionPane.showMessageDialog(this,
                    "Cliente guardado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al guardar el cliente",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente() {
        if (clienteSeleccionado == null) {
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar este cliente?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                if (clienteServicio.eliminarCliente(clienteSeleccionado.getId())) {
                    JOptionPane.showMessageDialog(this,
                        "Cliente eliminado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Error al eliminar el cliente",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarClienteSeleccionado(int fila) {
        int id = (int) modeloTabla.getValueAt(fila, 0);
        clienteSeleccionado = clienteServicio.obtenerClientePorId(id);
        if (clienteSeleccionado != null) {
            txtNombre.setText(clienteSeleccionado.getNombre());
            txtApellido.setText(clienteSeleccionado.getApellido());
            txtCedulaRuc.setText(clienteSeleccionado.getCedulaRuc());
            txtDireccion.setText(clienteSeleccionado.getDireccion());
            txtTelefono.setText(clienteSeleccionado.getTelefono());
            txtEmail.setText(clienteSeleccionado.getEmail());
            cboTipo.setSelectedItem(clienteSeleccionado.getTipo());
            btnEliminar.setEnabled(true);
        }
    }

    private void limpiarFormulario() {
        clienteSeleccionado = null;
        txtNombre.setText("");
        txtApellido.setText("");
        txtCedulaRuc.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        cboTipo.setSelectedIndex(0);
        btnEliminar.setEnabled(false);
        tablaClientes.clearSelection();
    }
}
