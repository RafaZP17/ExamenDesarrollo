package mx.desarrollo.ui;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.delegate.DelegateProfesor; // Ojo: si esta sale roja, presiona Alt+Enter para corregir la ruta
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import java.io.Serializable;
import java.util.List;

@Named("profesorBean")
@ViewScoped
public class ProfesorBeanUI implements Serializable {

    private static final long serialVersionUID = 1L;
    private Profesor profesor;
    private DelegateProfesor delegate;

    private List<Profesor> listaProfesores;

    public ProfesorBeanUI() {
        this.profesor = new Profesor();
        this.delegate = new DelegateProfesor();
        this.listaProfesores = this.delegate.obtenerListaProfesores();
    }

    public void guardarProfesor() {
        FacesContext context = FacesContext.getCurrentInstance();
        PrimeFaces pf = PrimeFaces.current();

        try {
            delegate.registrarProfesor(profesor);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Profesor registrado correctamente."));
            this.profesor = new Profesor();
            this.listaProfesores = this.delegate.obtenerListaProfesores();
            pf.ajax().addCallbackParam("isSaved", true);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            pf.ajax().addCallbackParam("isSaved", false);
        }
    }

    public void actualizarProfesor() {
        FacesContext context = FacesContext.getCurrentInstance();
        PrimeFaces pf = PrimeFaces.current();

        try {
            delegate.actualizarProfesor(profesor);

            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Profesor actualizado correctamente."));

            this.profesor = new Profesor();
            this.listaProfesores = this.delegate.obtenerListaProfesores();
            pf.ajax().addCallbackParam("isSaved", true);
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            pf.ajax().addCallbackParam("isSaved", false);
        }
    }

    public void eliminarProfesor() {
        FacesContext context = FacesContext.getCurrentInstance();
        PrimeFaces pf = PrimeFaces.current();

        try {
            delegate.eliminarProfesor(profesor);
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Profesor eliminado correctamente."));
            this.profesor = new Profesor();
            this.listaProfesores = this.delegate.obtenerListaProfesores();
            pf.ajax().addCallbackParam("isSaved", true);
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            pf.ajax().addCallbackParam("isSaved", false);
        }
    }

    public void limpiarFormulario() {
        this.profesor = new Profesor();
        this.profesor.setIdProfesor(null);
    }

    public void onRowSelect(SelectEvent<Profesor> event) {
        this.profesor = event.getObject();
    }

    public Profesor getProfesor() {
        if (this.profesor == null) {
            this.profesor = new Profesor();
        }
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Profesor> getListaProfesores() { return listaProfesores;}

    public void setListaProfesores(List<Profesor> listaProfesores) { this.listaProfesores = listaProfesores;}
}