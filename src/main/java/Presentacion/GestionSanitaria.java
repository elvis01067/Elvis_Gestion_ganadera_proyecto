package Presentacion;

import Datos.RegistroSanitarioDAO;
import Modelo.RegistroSanitario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GestionSanitaria extends JFrame {
    private final RegistroSanitarioDAO registroSanitarioDAO;
    private JComboBox<String> cboTipoRegistro;
    private JTextField txtFecha;
    private JTextField txtDescripcion;
    private JTextField txtProducto;
    private JTextField txtDosis;
    private JTextField txtProximo;
    private JTextArea txtObservaciones;
    private JButton btnGuardar;
    private JTable tablaRegistros;
    private DefaultTableModel modeloTabla;
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

    public GestionSanitaria() {
        this.registroSanitarioDAO = new RegistroSanitarioDAO();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Registro Sanitario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tipo de registro
        panelPrincipal.add(new JLabel("Tipo:"), gbc);
        cboTipoRegistro = new JComboBox<>(new String[]{"VITAMINA", "TRATAMIENTO", "DESPARACITACION"});
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelPrincipal.add(cboTipoRegistro, gbc);

        // Fecha
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.0;
        panelPrincipal.add(new JLabel("Fecha:"), gbc);
        txtFecha = new JTextField(10);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelPrincipal.add(txtFecha, gbc);

        // Descripción
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0.0;
        panelPrincipal.add(new JLabel("Descripción:"), gbc);
        txtDescripcion = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelPrincipal.add(txtDescripcion, gbc);

        // Producto
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 0.0;
        panelPrincipal.add(new JLabel("Producto:"), gbc);
        txtProducto = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelPrincipal.add(txtProducto, gbc);

        // Dosis
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.weightx = 0.0;
        panelPrincipal.add(new JLabel("Dosis:"), gbc);
        txtDosis = new JTextField(10);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelPrincipal.add(txtDosis, gbc);

        // Próximo tratamiento
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.weightx = 0.0;
        panelPrincipal.add(new JLabel("Próximo:"), gbc);
        txtProximo = new JTextField(10);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelPrincipal.add(txtProximo, gbc);

        // Observaciones
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.weightx = 0.0;
        panelPrincipal.add(new JLabel("Observaciones:"), gbc);
        txtObservaciones = new JTextArea(4, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        JScrollPane scrollObservaciones = new JScrollPane(txtObservaciones);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelPrincipal.add(scrollObservaciones, gbc);

        // Botón guardar
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        btnGuardar = new JButton("Guardar Registro");
        btnGuardar.addActionListener(e -> guardarRegistro());
        panelPrincipal.add(btnGuardar, gbc);

        // Tabla de registros
        modeloTabla = new DefaultTableModel(
            new Object[]{"Fecha", "Tipo", "Descripción", "Producto", "Dosis", "Próximo"}, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaRegistros = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaRegistros);

        // Agregar panel principal con padding
        JPanel panelConPadding = new JPanel(new BorderLayout());
        panelConPadding.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelConPadding.add(panelPrincipal, BorderLayout.CENTER);
        add(panelConPadding);
    }

    private void guardarRegistro() {
        try {
            RegistroSanitario registro = new RegistroSanitario();
            registro.setGanadoId(1); // ID temporal
            registro.setTipoRegistro(cboTipoRegistro.getSelectedItem().toString());
            registro.setFecha(formatoFecha.parse(txtFecha.getText().trim()));
            registro.setDescripcion(txtDescripcion.getText().trim());
            registro.setProductoUsado(txtProducto.getText().trim());
            registro.setDosis(txtDosis.getText().trim());
            registro.setProximoTratamiento(formatoFecha.parse(txtProximo.getText().trim()));
            registro.setObservaciones(txtObservaciones.getText().trim());

            if (registroSanitarioDAO.insertar(registro)) {
                JOptionPane.showMessageDialog(this, "Registro guardado exitosamente");
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el registro");
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtFecha.setText("");
        txtDescripcion.setText("");
        txtProducto.setText("");
        txtDosis.setText("");
        txtProximo.setText("");
        txtObservaciones.setText("");
    }
}
