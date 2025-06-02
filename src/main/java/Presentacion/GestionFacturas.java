package Presentacion;

import Datos.ClienteDAO;
import Datos.FacturaDAO;
import Datos.ProveedorDAO;
import Modelo.Cliente;
import Modelo.DetalleFactura;
import Modelo.Factura;
import Modelo.Proveedor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class GestionFacturas extends JFrame {
    private final FacturaDAO facturaDAO;
    private final ClienteDAO clienteDAO;
    private final ProveedorDAO proveedorDAO;
    
    private JComboBox<String> cboTipoDocumento;
    private JComboBox<String> cboClienteProveedor;
    private JTextField txtNumeroFactura;
    private JTextField txtFecha;
    private JTextField txtConcepto;
    private JTextField txtCantidad;
    private JTextField txtPrecioUnitario;
    private JTextField txtIvaPorcentaje;
    private JTextField txtDescuentoPorcentaje;
    
    private JTable tablaDetalles;
    private DefaultTableModel modeloDetalles;
    
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblDescuento;
    private JLabel lblTotal;
    
    private Factura facturaActual;
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    private final DecimalFormat formatoDecimal = new DecimalFormat("#,##0.00");

    public GestionFacturas() {
        this.facturaDAO = new FacturaDAO();
        this.clienteDAO = new ClienteDAO();
        this.proveedorDAO = new ProveedorDAO();
        this.facturaActual = new Factura();
        this.facturaActual.setNumeroFactura(facturaDAO.generarNumeroFactura());
        
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Gestión de Facturas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        // Panel superior
        JPanel panelSuperior = new JPanel(new GridLayout(3, 4, 10, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tipo de documento
        panelSuperior.add(new JLabel("Tipo:"));
        cboTipoDocumento = new JComboBox<>(new String[]{"FACTURA_VENTA", "FACTURA_COMPRA"});
        cboTipoDocumento.addActionListener(e -> actualizarComboClienteProveedor());
        panelSuperior.add(cboTipoDocumento);

        // Número de factura
        panelSuperior.add(new JLabel("N° Factura:"));
        txtNumeroFactura = new JTextField(facturaActual.getNumeroFactura());
        txtNumeroFactura.setEditable(false);
        panelSuperior.add(txtNumeroFactura);

        // Cliente/Proveedor
        panelSuperior.add(new JLabel("Cliente/Proveedor:"));
        cboClienteProveedor = new JComboBox<>();
        panelSuperior.add(cboClienteProveedor);

        // Fecha
        panelSuperior.add(new JLabel("Fecha:"));
        txtFecha = new JTextField(formatoFecha.format(facturaActual.getFecha()));
        txtFecha.setEditable(false);
        panelSuperior.add(txtFecha);

        // Panel de detalles
        JPanel panelDetalles = new JPanel(new BorderLayout());
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles"));

        // Panel para agregar detalles
        JPanel panelAgregarDetalle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        panelAgregarDetalle.add(new JLabel("Concepto:"));
        txtConcepto = new JTextField(20);
        panelAgregarDetalle.add(txtConcepto);

        panelAgregarDetalle.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField(5);
        panelAgregarDetalle.add(txtCantidad);

        panelAgregarDetalle.add(new JLabel("Precio:"));
        txtPrecioUnitario = new JTextField(10);
        panelAgregarDetalle.add(txtPrecioUnitario);

        JButton btnAgregarDetalle = new JButton("Agregar");
        btnAgregarDetalle.addActionListener(e -> agregarDetalle());
        panelAgregarDetalle.add(btnAgregarDetalle);

        // Tabla de detalles
        modeloDetalles = new DefaultTableModel(
            new Object[]{"Concepto", "Cantidad", "Precio Unit.", "Subtotal"}, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaDetalles = new JTable(modeloDetalles);
        JScrollPane scrollDetalles = new JScrollPane(tablaDetalles);

        panelDetalles.add(panelAgregarDetalle, BorderLayout.NORTH);
        panelDetalles.add(scrollDetalles, BorderLayout.CENTER);

        // Panel de totales
        JPanel panelTotales = new JPanel(new GridLayout(5, 2, 10, 5));
        panelTotales.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelTotales.add(new JLabel("IVA %:"));
        txtIvaPorcentaje = new JTextField("12.00");
        txtIvaPorcentaje.addActionListener(e -> calcularTotales());
        panelTotales.add(txtIvaPorcentaje);

        panelTotales.add(new JLabel("Descuento %:"));
        txtDescuentoPorcentaje = new JTextField("0.00");
        txtDescuentoPorcentaje.addActionListener(e -> calcularTotales());
        panelTotales.add(txtDescuentoPorcentaje);

        panelTotales.add(new JLabel("Subtotal:"));
        lblSubtotal = new JLabel("0.00");
        panelTotales.add(lblSubtotal);

        panelTotales.add(new JLabel("IVA:"));
        lblIva = new JLabel("0.00");
        panelTotales.add(lblIva);

        panelTotales.add(new JLabel("Descuento:"));
        lblDescuento = new JLabel("0.00");
        panelTotales.add(lblDescuento);

        panelTotales.add(new JLabel("TOTAL:"));
        lblTotal = new JLabel("0.00");
        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD));
        panelTotales.add(lblTotal);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Guardar Factura");
        btnGuardar.addActionListener(e -> guardarFactura());
        panelBotones.add(btnGuardar);

        // Agregar todos los paneles
        add(panelSuperior, BorderLayout.NORTH);
        add(panelDetalles, BorderLayout.CENTER);
        
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.add(panelTotales, BorderLayout.NORTH);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);
        add(panelDerecho, BorderLayout.EAST);

        // Inicializar el combo de clientes/proveedores
        actualizarComboClienteProveedor();
    }

    private void actualizarComboClienteProveedor() {
        cboClienteProveedor.removeAllItems();
        
        if ("FACTURA_VENTA".equals(cboTipoDocumento.getSelectedItem())) {
            for (Cliente cliente : clienteDAO.listarTodos()) {
                cboClienteProveedor.addItem(cliente.getId() + " - " + 
                    cliente.getNombre() + " " + cliente.getApellido());
            }
        } else {
            for (Proveedor proveedor : proveedorDAO.listarTodos()) {
                cboClienteProveedor.addItem(proveedor.getId() + " - " + 
                    proveedor.getNombre() + " " + proveedor.getApellido());
            }
        }
    }

    private void agregarDetalle() {
        try {
            String concepto = txtConcepto.getText().trim();
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            double precioUnitario = Double.parseDouble(txtPrecioUnitario.getText().trim());

            if (concepto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El concepto es obligatorio");
                return;
            }

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0");
                return;
            }

            if (precioUnitario <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0");
                return;
            }

            DetalleFactura detalle = new DetalleFactura();
            detalle.setConcepto(concepto);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(precioUnitario);

            facturaActual.addDetalle(detalle);
            
            Vector<Object> fila = new Vector<>();
            fila.add(detalle.getConcepto());
            fila.add(detalle.getCantidad());
            fila.add(formatoDecimal.format(detalle.getPrecioUnitario()));
            fila.add(formatoDecimal.format(detalle.getSubtotal()));
            modeloDetalles.addRow(fila);

            // Limpiar campos
            txtConcepto.setText("");
            txtCantidad.setText("");
            txtPrecioUnitario.setText("");
            
            calcularTotales();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos");
        }
    }

    private void calcularTotales() {
        try {
            facturaActual.setIvaPorcentaje(Double.parseDouble(txtIvaPorcentaje.getText().trim()));
            facturaActual.setDescuentoPorcentaje(Double.parseDouble(txtDescuentoPorcentaje.getText().trim()));
            facturaActual.calcularTotales();

            lblSubtotal.setText(formatoDecimal.format(facturaActual.getSubtotal()));
            lblIva.setText(formatoDecimal.format(facturaActual.getIvaValor()));
            lblDescuento.setText(formatoDecimal.format(facturaActual.getDescuentoValor()));
            lblTotal.setText(formatoDecimal.format(facturaActual.getTotal()));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese porcentajes válidos");
        }
    }

    private void guardarFactura() {
        try {
            if (facturaActual.getDetalles().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe agregar al menos un detalle");
                return;
            }

            // Establecer tipo de documento
            String tipoDocumento = (String) cboTipoDocumento.getSelectedItem();
            if (tipoDocumento == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar el tipo de documento");
                return;
            }
            facturaActual.setTipoDocumento(tipoDocumento);

            // Establecer cliente o proveedor
            String seleccion = (String) cboClienteProveedor.getSelectedItem();
            if (seleccion == null || seleccion.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un " + 
                    ("FACTURA_VENTA".equals(tipoDocumento) ? "cliente" : "proveedor"));
                return;
            }

            try {
                int id = Integer.parseInt(seleccion.split(" - ")[0]);
                if ("FACTURA_VENTA".equals(tipoDocumento)) {
                    Cliente cliente = clienteDAO.obtenerPorId(id);
                    if (cliente == null) {
                        JOptionPane.showMessageDialog(this, "El cliente seleccionado no existe");
                        return;
                    }
                    facturaActual.setCliente(cliente);
                    facturaActual.setProveedor(null); // Asegurar que proveedor sea null
                } else {
                    Proveedor proveedor = proveedorDAO.obtenerPorId(id);
                    if (proveedor == null) {
                        JOptionPane.showMessageDialog(this, "El proveedor seleccionado no existe");
                        return;
                    }
                    facturaActual.setProveedor(proveedor);
                    facturaActual.setCliente(null); // Asegurar que cliente sea null
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error al obtener el ID del " + 
                    ("FACTURA_VENTA".equals(tipoDocumento) ? "cliente" : "proveedor"));
                return;
            }

            if (facturaDAO.insertar(facturaActual)) {
                JOptionPane.showMessageDialog(this, "Factura guardada exitosamente");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar la factura");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la factura: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
