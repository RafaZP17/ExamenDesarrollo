package mx.desarrollo.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.entity.ProfesorUnidad;
import mx.desarrollo.entity.UnidadAprendizaje;
import mx.desarrollo.integration.ServiceFacadeLocator;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Named("asignacionUI")
@ViewScoped
public class AsignacionBeanUI implements Serializable {

    private Integer idProfesorSel;
    private Integer idUnidadSel;
    private String diaSel;
    private String horaInicioStr; // Formato "HH:mm"
    private String horaFinStr;

    private List<Profesor> listaProfesores;
    private List<UnidadAprendizaje> listaUnidades;

    @PostConstruct
    public void init() {
        // Cargar listas desde la base de datos a través de los facades correspondientes
        // listaUnidades = ServiceFacadeLocator.getInstanceFacadeUnidadAprendizaje().obtenerTodas();
        // listaProfesores = ...
    }

    public void guardarAsignacion() {
        try {
            if (idProfesorSel == null || idUnidadSel == null || diaSel == null
                    || horaInicioStr.isEmpty() || horaFinStr.isEmpty()) {
                throw new Exception("Todos los campos son obligatorios.");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime horaInicio = LocalTime.parse(horaInicioStr, formatter);
            LocalTime horaFin = LocalTime.parse(horaFinStr, formatter);

            if (!horaInicio.isBefore(horaFin)) {
                throw new Exception("La hora de inicio debe ser anterior a la hora de fin.");
            }

            Profesor prof = new Profesor();
            prof.setIdProfesor(idProfesorSel);

            // Ajuste usando la entidad de tu compañero:
            UnidadAprendizaje uni = new UnidadAprendizaje();
            uni.setId(idUnidadSel);

            ProfesorUnidad asignacion = new ProfesorUnidad();
            asignacion.setProfesor(prof);
            asignacion.setUnidadAprendizaje(uni);
            asignacion.setDia(diaSel);
            asignacion.setHoraInicio(horaInicio);
            asignacion.setHoraFin(horaFin);

            // Llamada a la capa de negocio
            ServiceFacadeLocator.getInstanceFacadeAsignacion().registrarAsignacion(asignacion);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Unidad asignada correctamente al profesor."));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Asignación", e.getMessage()));
        }
    }

    public Integer getIdProfesorSel() { return idProfesorSel; }
    public void setIdProfesorSel(Integer idProfesorSel) { this.idProfesorSel = idProfesorSel; }

    public Integer getIdUnidadSel() { return idUnidadSel; }
    public void setIdUnidadSel(Integer idUnidadSel) { this.idUnidadSel = idUnidadSel; }

    public String getDiaSel() { return diaSel; }
    public void setDiaSel(String diaSel) { this.diaSel = diaSel; }

    public String getHoraInicioStr() { return horaInicioStr; }
    public void setHoraInicioStr(String horaInicioStr) { this.horaInicioStr = horaInicioStr; }

    public String getHoraFinStr() { return horaFinStr; }
    public void setHoraFinStr(String horaFinStr) { this.horaFinStr = horaFinStr; }

    public List<Profesor> getListaProfesores() { return listaProfesores; }
    public List<UnidadAprendizaje> getListaUnidades() { return listaUnidades; }
}