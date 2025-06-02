package Negocio;

import Datos.VacasDAO;
import Modelo.Vacas;
import Util.Validaciones;
import java.util.List;
import java.util.Date;

public class VacasServicio {
    private final VacasDAO vacasDAO;
    
    public VacasServicio() {
        this.vacasDAO = new VacasDAO();
    }
    
    public boolean registrarVaca(Vacas vaca) {
        // Validaciones de negocio
        if (!validarDatosVaca(vaca)) {
            return false;
        }
        
        // Si no tiene fecha de registro, establecer la fecha actual
        if (vaca.getFechaAdquisicion() == null) {
            vaca.setFechaAdquisicion(new Date());
        }
        
        return vacasDAO.insertar(vaca);
    }
    
    public boolean actualizarVaca(Vacas vaca) {
        if (!validarDatosVaca(vaca)) {
            return false;
        }
        return vacasDAO.actualizar(vaca);
    }
    
    public boolean eliminarVaca(int id) {
        Vacas vaca = vacasDAO.obtenerPorId(id);
        if (vaca == null) {
            return false;
        }
        return vacasDAO.eliminar(id);
    }
    
    public List<Vacas> listarTodasVacas() {
        return vacasDAO.listarTodas();
    }
    
    public Vacas obtenerVacaPorId(int id) {
        return vacasDAO.obtenerPorId(id);
    }
    
    private boolean validarDatosVaca(Vacas vaca) {
        if (vaca == null) {
            return false;
        }
        
        // Validar código de identificación
        if (vaca.getCodigoIdentificacion() == null || 
            vaca.getCodigoIdentificacion().trim().isEmpty()) {
            return false;
        }
        
        // Validar nombre
        if (!Validaciones.validarNombre(vaca.getNombre())) {
            return false;
        }
        
        // Validar peso
        if (!Validaciones.validarPeso(vaca.getPesoKg())) {
            return false;
        }
        
        // Validar género
        String genero = vaca.getGenero();
        if (genero == null || (!genero.equals("macho") && !genero.equals("hembra"))) {
            return false;
        }
        
        // Validar propósito
        String proposito = vaca.getProposito();
        if (proposito == null || (!proposito.equals("carne") && 
            !proposito.equals("leche") && !proposito.equals("reproduccion") && 
            !proposito.equals("exhibicion"))) {
            return false;
        }
        
        // Validar estado productivo
        String estadoProductivo = vaca.getEstadoProductivo();
        if (estadoProductivo == null || (!estadoProductivo.equals("produccion") && 
            !estadoProductivo.equals("seca") && !estadoProductivo.equals("preñada") && 
            !estadoProductivo.equals("novilla") && !estadoProductivo.equals("descarte"))) {
            return false;
        }
        
        // Validar estado de salud
        String estadoSalud = vaca.getEstadoSalud();
        if (estadoSalud == null || (!estadoSalud.equals("sano") && 
            !estadoSalud.equals("enfermo") && !estadoSalud.equals("tratamiento") && 
            !estadoSalud.equals("cuarentena"))) {
            return false;
        }
        
        // Validar precio de compra
        if (!Validaciones.validarMonto(vaca.getPrecioCompra())) {
            return false;
        }
        
        return true;
    }
}
