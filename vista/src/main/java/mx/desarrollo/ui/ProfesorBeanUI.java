package mx.desarrollo.ui;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.delegate.DelegateProfesor; // Ojo: si esta sale roja, presiona Alt+Enter para corregir la ruta

@Named("profesorBean")
@RequestScoped
public class ProfesorBeanUI {

    private Profesor profesor;
    private DelegateProfesor delegate;

    public ProfesorBeanUI() {
        this.profesor = new Profesor();
        this.delegate = new DelegateProfesor();
    }

    public void guardarProfesor() {
        try {
            delegate.registrarProfesor(profesor);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Profesor registrado correctamente."));
            profesor = new Profesor();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
}