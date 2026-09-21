package mx.desarrollo.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.desarrollo.delegate.DelegateAsignacion;
import mx.desarrollo.delegate.DelegateProfesor;
import mx.desarrollo.delegate.DelegateUnidadAprendizaje;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.entity.ProfesorUnidad;
import mx.desarrollo.entity.UnidadAprendizaje;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Named("asignacionUI")
@ViewScoped
public class AsignacionBeanUI implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idAsignacionSel; // ID para saber si estamos editando
    private Integer idProfesorSel;
    private Integer idUnidadSel;
    private String diaSel;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    private List<Profesor> listaProfesores;
    private List<UnidadAprendizaje> listaUnidades;
    private List<ProfesorUnidad> listaAsignaciones;

    private DelegateAsignacion delegateAsignacion;
    private DelegateProfesor delegateProfesor;
    private DelegateUnidadAprendizaje delegateUnidad;

    @PostConstruct
    public void init() {
        this.delegateAsignacion = new DelegateAsignacion();
        this.delegateProfesor = new DelegateProfesor();
        this.delegateUnidad = new DelegateUnidadAprendizaje();

        cargarListas();
    }

    public void cargarListas() {
        this.listaProfesores = delegateProfesor.obtenerListaProfesores();
        this.listaUnidades = delegateUnidad.obtenerTodas();
        this.listaAsignaciones = delegateAsignacion.obtenerTodas();
    }

    public void prepararNuevaAsignacion() {
        limpiarFormulario();
        this.listaProfesores = delegateProfesor.obtenerListaProfesores();
        this.listaUnidades = delegateUnidad.obtenerTodas();
    }

    public void guardarAsignacion() {
        FacesContext context = FacesContext.getCurrentInstance();
        PrimeFaces pf = PrimeFaces.current();

        try {
            validarFormulario();

            Profesor prof = new Profesor();
            prof.setIdProfesor(idProfesorSel);

            UnidadAprendizaje uni = new UnidadAprendizaje();
            uni.setId(idUnidadSel);

            ProfesorUnidad asignacion = new ProfesorUnidad();
            asignacion.setProfesor(prof);
            asignacion.setUnidadAprendizaje(uni);
            asignacion.setDia(diaSel);
            asignacion.setHoraInicio(horaInicio);
            asignacion.setHoraFin(horaFin);

            delegateAsignacion.registrarAsignacion(asignacion);
            this.listaAsignaciones = delegateAsignacion.obtenerTodas();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Unidad asignada correctamente al profesor."));

            limpiarFormulario();
            pf.ajax().addCallbackParam("isSaved", true);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Asignación", e.getMessage()));
            pf.ajax().addCallbackParam("isSaved", false);
        }
    }

    public void actualizarAsignacion() {
        PrimeFaces pf = PrimeFaces.current();
        try {
            if (idAsignacionSel == null) {
                throw new Exception("No se ha seleccionado ninguna asignación para actualizar.");
            }
            validarFormulario();

            Profesor prof = new Profesor();
            prof.setIdProfesor(idProfesorSel);

            UnidadAprendizaje uni = new UnidadAprendizaje();
            uni.setId(idUnidadSel);

            ProfesorUnidad asignacion = new ProfesorUnidad();
            asignacion.setIdAsignacion(idAsignacionSel);
            asignacion.setProfesor(prof);
            asignacion.setUnidadAprendizaje(uni);
            asignacion.setDia(diaSel);
            asignacion.setHoraInicio(horaInicio);
            asignacion.setHoraFin(horaFin);

            delegateAsignacion.actualizarAsignacion(asignacion);
            this.listaAsignaciones = delegateAsignacion.obtenerTodas();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asignación actualizada correctamente."));

            limpiarFormulario();
            pf.ajax().addCallbackParam("isSaved", true);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Actualización", e.getMessage()));
            pf.ajax().addCallbackParam("isSaved", false);
        }
    }

    public void eliminarAsignacion() {
        PrimeFaces pf = PrimeFaces.current();
        try {
            if (idAsignacionSel == null) {
                throw new Exception("No se ha seleccionado ninguna asignación para eliminar.");
            }

            delegateAsignacion.eliminarAsignacion(idAsignacionSel);
            this.listaAsignaciones = delegateAsignacion.obtenerTodas();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asignación eliminada correctamente."));

            limpiarFormulario();
            pf.ajax().addCallbackParam("isSaved", true);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al Eliminar", e.getMessage()));
            pf.ajax().addCallbackParam("isSaved", false);
        }
    }

    private void validarFormulario() throws Exception {
        if (idProfesorSel == null || idUnidadSel == null || diaSel == null
                || horaInicio == null || horaFin == null) {
            throw new Exception("Todos los campos son obligatorios.");
        }

        if (!horaInicio.isBefore(horaFin)) {
            throw new Exception("La hora de inicio debe ser anterior a la hora de fin.");
        }
    }

    public void onAsignacionRowSelect(ProfesorUnidad asig) {
        if (asig != null) {
            this.idAsignacionSel = asig.getIdAsignacion();
            if (asig.getProfesor() != null) {
                this.idProfesorSel = asig.getProfesor().getIdProfesor();
            }
            if (asig.getUnidadAprendizaje() != null) {
                this.idUnidadSel = asig.getUnidadAprendizaje().getId();
            }
            this.diaSel = asig.getDia();
            this.horaInicio = asig.getHoraInicio();
            this.horaFin = asig.getHoraFin();
        }

        // Recargar listas al seleccionar mediante doble clic
        this.listaProfesores = delegateProfesor.obtenerListaProfesores();
        this.listaUnidades = delegateUnidad.obtenerTodas();
    }

    public void limpiarFormulario() {
        this.idAsignacionSel = null;
        this.idProfesorSel = null;
        this.idUnidadSel = null;
        this.diaSel = null;
        this.horaInicio = null;
        this.horaFin = null;
    }

    public int calcularTop(LocalTime horaInicio) {
        if (horaInicio == null) return 0;
        int minutosDesdeLas7 = (horaInicio.getHour() - 7) * 60 + horaInicio.getMinute();
        return (minutosDesdeLas7 * 70) / 60;
    }

    public int calcularHeight(LocalTime horaInicio, LocalTime horaFin) {
        if (horaInicio == null || horaFin == null) return 70;
        long minutosDuracion = java.time.Duration.between(horaInicio, horaFin).toMinutes();
        return (int) ((minutosDuracion * 70) / 60);
    }

    public int obtenerDiaIndex(String dia) {
        if (dia == null) return 0;
        switch (dia.toLowerCase()) {
            case "lunes": return 0;
            case "martes": return 1;
            case "miércoles": case "miercoles": return 2;
            case "jueves": return 3;
            case "viernes": return 4;
            case "sábado": case "sabado": return 5;
            default: return 0;
        }
    }

    public List<Profesor> getListaProfesoresOrdenados() {
        List<Profesor> profesores = delegateProfesor.obtenerListaProfesores();
        if (profesores != null) {
            profesores.sort(Comparator.comparing(Profesor::getApellidoPaterno, Comparator.nullsLast(String::compareTo))
                    .thenComparing(Profesor::getNombre, Comparator.nullsLast(String::compareTo)));
        }
        return profesores;
    }

    public List<Integer> getObtenerDiasSemana() {
        return Arrays.asList(0, 1, 2, 3, 4, 5);
    }

    public List<Integer> getHorasEstructura() {
        return Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
    }

    public List<ProfesorUnidad> obtenerAsignacionesPorProfesorYDia(Integer idProfesor, int diaIndex) {
        if (delegateAsignacion != null) {
            this.listaAsignaciones = delegateAsignacion.obtenerTodas();
        }

        List<ProfesorUnidad> filtradas = new ArrayList<>();
        if (listaAsignaciones != null) {
            for (ProfesorUnidad a : listaAsignaciones) {
                if (a.getProfesor() != null && a.getProfesor().getIdProfesor().equals(idProfesor)) {
                    // Corrección añadida aquí (getUnidadAprendizaje().getId())
                    if (a.getUnidadAprendizaje() != null && a.getUnidadAprendizaje().getId() != null) {
                        if (obtenerDiaIndex(a.getDia()) == diaIndex) {
                            filtradas.add(a);
                        }
                    }
                }
            }
        }
        return filtradas;
    }

    public Integer getIdAsignacionSel() { return idAsignacionSel; }
    public void setIdAsignacionSel(Integer idAsignacionSel) { this.idAsignacionSel = idAsignacionSel; }

    public Integer getIdProfesorSel() { return idProfesorSel; }
    public void setIdProfesorSel(Integer idProfesorSel) { this.idProfesorSel = idProfesorSel; }

    public Integer getIdUnidadSel() { return idUnidadSel; }
    public void setIdUnidadSel(Integer idUnidadSel) { this.idUnidadSel = idUnidadSel; }

    public String getDiaSel() { return diaSel; }
    public void setDiaSel(String diaSel) { this.diaSel = diaSel; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public List<Profesor> getListaProfesores() { return listaProfesores; }
    public List<UnidadAprendizaje> getListaUnidades() { return listaUnidades; }

    public List<ProfesorUnidad> getListaAsignaciones() { return listaAsignaciones; }
    public void setListaAsignaciones(List<ProfesorUnidad> listaAsignaciones) { this.listaAsignaciones = listaAsignaciones; }
}