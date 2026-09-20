package mx.desarrollo.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.desarrollo.entity.UnidadAprendizaje;
import mx.desarrollo.helper.CatalogoHelper;

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
            if (validarCampos()) mostrarResultado(catalogoHelper.guardar(unidad));
        }

        public void actualizar() {
            if (validarCampos()) mostrarResultado(catalogoHelper.actualizar(unidad));
        }

        public void eliminar(UnidadAprendizaje u) {
            catalogoHelper.eliminar(u);
            cargarLista();
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
            if (ok) {
                FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Unidad guardada correctamente"));
                unidad = new UnidadAprendizaje();
                cargarLista();
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las horas deben de estar entre 0 y 4"));
            }
        }

        public List<UnidadAprendizaje> getListaUnidades() {
            return listaUnidades;
        }

        public void setListaUnidades(List<UnidadAprendizaje> lista) {
            this.listaUnidades = lista;
        }

        public UnidadAprendizaje getUnidad() {
            return unidad; }

        public void setUnidad(UnidadAprendizaje unidad) {
            this.unidad = unidad; }
    }

