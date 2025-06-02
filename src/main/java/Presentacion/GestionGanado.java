package Presentacion;

import Datos.GanadoDAO;
import Modelo.Ganado;
import Util.RazaImagenes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.awt.event.ItemEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import java.io.File;

public class GestionGanado extends JFrame {
    private final GanadoDAO ganadoDAO;

    private JTextField txtId;
    private JTextField txtArete;
    private JComboBox<String> cboRaza;
    private JLabel lblImagenRaza;
    private JTextField txtFechaNacimiento;
    private JComboBox<String> cboGenero;
    private JTextField txtPeso;
    private JComboBox<String> cboEstadoSalud;
    private JComboBox<String> cboProposito;
    private JComboBox<String> cboEstadoProductivo;
    private JTable tablaGanado;
    private DefaultTableModel modeloTabla;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnEliminar;
    private Ganado ganadoSeleccionado;
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

    public GestionGanado() {
        this.ganadoDAO = new GanadoDAO();
        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Gestión de Ganado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(9, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Arete:"));
        txtArete = new JTextField();
        panelFormulario.add(txtArete);

        panelFormulario.add(new JLabel("Raza:"));
        cboRaza = new JComboBox<>(RazaImagenes.getRazas());
        cboRaza.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                actualizarImagenRaza();
            }
        });
        panelFormulario.add(cboRaza);

        panelFormulario.add(new JLabel("Fecha Nacimiento (YYYY-MM-DD):"));
        txtFechaNacimiento = new JTextField();
        panelFormulario.add(txtFechaNacimiento);

        panelFormulario.add(new JLabel("Género:"));
        cboGenero = new JComboBox<>(new String[]{"macho", "hembra"});
        panelFormulario.add(cboGenero);

        panelFormulario.add(new JLabel("Peso (kg):"));
        txtPeso = new JTextField();
        panelFormulario.add(txtPeso);

        panelFormulario.add(new JLabel("Estado de Salud:"));
        cboEstadoSalud = new JComboBox<>(new String[]{"sano", "enfermo", "en_tratamiento"});
        panelFormulario.add(cboEstadoSalud);

        panelFormulario.add(new JLabel("Propósito:"));
        cboProposito = new JComboBox<>(new String[]{"carne", "leche", "doble_proposito"});
        panelFormulario.add(cboProposito);

        panelFormulario.add(new JLabel("Estado Productivo:"));
        cboEstadoProductivo = new JComboBox<>(new String[]{"produccion", "seco", "gestacion"});
        panelFormulario.add(cboEstadoProductivo);

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

        // Imagen de la raza
        lblImagenRaza = new JLabel();
        lblImagenRaza.setPreferredSize(new Dimension(200, 150));
        panelSuperior.add(lblImagenRaza, BorderLayout.EAST);

        // Tabla
        modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "Arete", "Raza", "Fecha Nac.", "Género", "Peso", "Estado Salud", "Propósito", "Estado Prod."}, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaGanado = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaGanado);

        // Agregar componentes a la ventana
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Configurar eventos
        btnGuardar.addActionListener(e -> guardarGanado());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnEliminar.addActionListener(e -> eliminarGanado());

        tablaGanado.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaGanado.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    cargarGanadoSeleccionado(filaSeleccionada);
                    btnEliminar.setEnabled(true);
                } else {
                    ganadoSeleccionado = null;
                    btnEliminar.setEnabled(false);
                }
            }
        });
    }

    private void actualizarImagenRaza() {
        String raza = (String) cboRaza.getSelectedItem();
        if (raza != null) {
            String rutaImagen = "src/main/resources" + RazaImagenes.getImagenRuta(raza);
            File file = new File(rutaImagen);
            if (file.exists()) {
                ImageIcon imageIcon = new ImageIcon(rutaImagen);
                Image image = imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                lblImagenRaza.setIcon(new ImageIcon(image));
            } else {
                lblImagenRaza.setIcon(null);
                lblImagenRaza.setText("Imagen no disponible");
            }
        } else {
            lblImagenRaza.setIcon(null);
            lblImagenRaza.setText("");
        }
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Ganado> ganados = ganadoDAO.listarTodos();
        for (Ganado ganado : ganados) {
            modeloTabla.addRow(new Object[]{
                ganado.getId(),
                ganado.getArete(),
                ganado.getRaza(),
                ganado.getFechaNacimiento() != null ? formatoFecha.format(ganado.getFechaNacimiento()) : "",
                ganado.getGenero(),
                ganado.getPeso(),
                ganado.getEstadoSalud(),
                ganado.getProposito(),
                ganado.getEstadoProductivo()
            });
        }
    }

    private void guardarGanado() {
        if (txtArete.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El arete es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Ganado ganado = new Ganado();
            
            // Validar y convertir la fecha
            try {
                Date fecha = formatoFecha.parse(txtFechaNacimiento.getText().trim());
                ganado.setFechaNacimiento(fecha);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar y convertir el peso
            try {
                double peso = Double.parseDouble(txtPeso.getText().trim());
                ganado.setPeso(peso);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El peso debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Establecer otros campos
            ganado.setArete(txtArete.getText().trim());
            ganado.setRaza((String) cboRaza.getSelectedItem());
            ganado.setGenero(cboGenero.getSelectedItem().toString());
            ganado.setEstadoSalud(cboEstadoSalud.getSelectedItem().toString());
            ganado.setProposito(cboProposito.getSelectedItem().toString());
            ganado.setEstadoProductivo(cboEstadoProductivo.getSelectedItem().toString());
            
            // Si hay un ID, actualizar en lugar de insertar
            String idText = txtId.getText().trim();
            boolean exito;
            if (!idText.isEmpty()) {
                ganado.setId(Integer.parseInt(idText));
                exito = ganadoDAO.actualizar(ganado);
            } else {
                exito = ganadoDAO.insertar(ganado);
            }
            
            if (exito) {
                JOptionPane.showMessageDialog(this, "Operación exitosa", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarDatos(); // Recargar la tabla
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void eliminarGanado() {
        int filaSeleccionada = tablaGanado.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un ganado para eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar este ganado?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(tablaGanado.getValueAt(filaSeleccionada, 0).toString());
            boolean exito = ganadoDAO.eliminar(id);
            if (exito) {
                JOptionPane.showMessageDialog(this,
                    "Ganado eliminado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar el ganado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtArete.setText("");
        cboRaza.setSelectedIndex(0);
        actualizarImagenRaza();
        txtFechaNacimiento.setText("");
        txtPeso.setText("");
        cboGenero.setSelectedIndex(0);
        cboEstadoSalud.setSelectedIndex(0);
        cboProposito.setSelectedIndex(0);
        cboEstadoProductivo.setSelectedIndex(0);
        ganadoSeleccionado = null;
        tablaGanado.clearSelection();
        btnEliminar.setEnabled(false);
    }

    private void cargarGanadoSeleccionado(int fila) {
        ganadoSeleccionado = new Ganado();
        ganadoSeleccionado.setId((Integer) modeloTabla.getValueAt(fila, 0));
        ganadoSeleccionado.setArete((String) modeloTabla.getValueAt(fila, 1));
        ganadoSeleccionado.setRaza((String) modeloTabla.getValueAt(fila, 2));
        try {
            String fechaStr = (String) modeloTabla.getValueAt(fila, 3);
            if (fechaStr != null && !fechaStr.isEmpty()) {
                ganadoSeleccionado.setFechaNacimiento(formatoFecha.parse(fechaStr));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ganadoSeleccionado.setGenero((String) modeloTabla.getValueAt(fila, 4));
        ganadoSeleccionado.setPeso((Double) modeloTabla.getValueAt(fila, 5));
        ganadoSeleccionado.setEstadoSalud((String) modeloTabla.getValueAt(fila, 6));
        ganadoSeleccionado.setProposito((String) modeloTabla.getValueAt(fila, 7));
        ganadoSeleccionado.setEstadoProductivo((String) modeloTabla.getValueAt(fila, 8));

        cargarDatosGanado(ganadoSeleccionado);
    }

    private void cargarDatosGanado(Ganado ganado) {
        if (ganado != null) {
            txtId.setText(String.valueOf(ganado.getId()));
            txtArete.setText(ganado.getArete());
            cboRaza.setSelectedItem(ganado.getRaza());
            actualizarImagenRaza();
            if (ganado.getFechaNacimiento() != null) {
                txtFechaNacimiento.setText(formatoFecha.format(ganado.getFechaNacimiento()));
            }
            txtPeso.setText(String.valueOf(ganado.getPeso()));
            cboGenero.setSelectedItem(ganado.getGenero());
            cboEstadoSalud.setSelectedItem(ganado.getEstadoSalud());
            cboProposito.setSelectedItem(ganado.getProposito());
            cboEstadoProductivo.setSelectedItem(ganado.getEstadoProductivo());
        }
    }
}
