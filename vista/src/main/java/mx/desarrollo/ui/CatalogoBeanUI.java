package mx.desarrollo.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.entity.UnidadAprendizaje;
import mx.desarrollo.helper.CatalogoHelper;
import org.primefaces.event.SelectEvent;

import java.io.Serializable;
import java.util.List;

    @Named("catalogoUI")
    @ViewScoped

    public class CatalogoBeanUI implements Serializable {
        private CatalogoHelper catalogoHelper;
        private List<UnidadAprendizaje> listaUnidades;
        private UnidadAprendizaje unidad;


        public CatalogoBeanUI() {
            catalogoHelper = new CatalogoHelper();
        }

        @PostConstruct
        public void init() {
            unidad = new UnidadAprendizaje();
            cargarLista();
        }

        private void cargarLista() {
            listaUnidades = catalogoHelper.obtenerTodas();
        }

        public void guardar() {
            if (validarCampos()) {
                boolean resultado = catalogoHelper.guardar(unidad);
                mostrarResultado(resultado);
            }
        }

        public void actualizar() {
            if (validarCampos()) {
                boolean resultado = catalogoHelper.actualizar(unidad);
                mostrarResultado(resultado);
            }
        }

        public void eliminar() {
            FacesContext context = FacesContext.getCurrentInstance();
            org.primefaces.PrimeFaces pf = org.primefaces.PrimeFaces.current();

            try {
                if (unidad == null || unidad.getId() == null) {
                    throw new Exception("Error: Debe seleccionar una unidad para eliminar.");
                }

                catalogoHelper.eliminar(unidad);

                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Unidad eliminada correctamente."));

                this.unidad = new UnidadAprendizaje();
                cargarLista();

                pf.ajax().addCallbackParam("isSaved", true);
            } catch (Exception e) {
                e.printStackTrace();
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
                pf.ajax().addCallbackParam("isSaved", false);
            }
        }

        public void nuevo() {
            unidad = new UnidadAprendizaje();
        }

        private boolean validarCampos() {
            if (unidad.getNombre() == null || unidad.getNombre().isBlank()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Nombre requerido", "Ingresa el nombre de la unidad"));
                return false;
            }
            return true;
        }

        private void mostrarResultado(boolean ok) {
            org.primefaces.PrimeFaces pf = org.primefaces.PrimeFaces.current();
            FacesContext context = FacesContext.getCurrentInstance();

            if (ok) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Unidad guardada correctamente"));
                this.unidad = new UnidadAprendizaje();
                cargarLista();

                pf.ajax().addCallbackParam("isSaved", true);
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las horas deben de estar entre 0 y 4"));
                pf.ajax().addCallbackParam("isSaved", false);
            }
        }

        public void onRowSelect(SelectEvent<UnidadAprendizaje> event) {
            this.unidad = event.getObject();
        }

        public List<UnidadAprendizaje> getListaUnidades() {
            return listaUnidades;
        }

        public void setListaUnidades(List<UnidadAprendizaje> lista) {
            this.listaUnidades = lista;
        }

        public UnidadAprendizaje getUnidad() {
            if (unidad == null) {
                unidad = new UnidadAprendizaje();
            }
            return unidad;
        }

        public void setUnidad(UnidadAprendizaje unidad) {
            this.unidad = unidad;
        }

        public void limpiarFormulario() {
            this.unidad = new UnidadAprendizaje();
        }
    }


