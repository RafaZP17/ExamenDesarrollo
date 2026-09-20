package mx.desarrollo.facade;

import mx.desarrollo.delegate.DelegateUnidadAprendizaje;
import mx.desarrollo.entity.UnidadAprendizaje;

import java.util.List;

public class FacadeUnidadAprendizaje {
    private final DelegateUnidadAprendizaje delegateUnidadAprendizaje;

    public FacadeUnidadAprendizaje() {
        this.delegateUnidadAprendizaje = new DelegateUnidadAprendizaje();
    }

    public List<UnidadAprendizaje> obtenerTodas(){
        return delegateUnidadAprendizaje.obtenerTodas();
    }

    public boolean guardar(UnidadAprendizaje unidad){
        return delegateUnidadAprendizaje.guardar(unidad);
    }

    public boolean actualizar(UnidadAprendizaje unidad){
        return delegateUnidadAprendizaje.actualizar(unidad);
    }

    public void eliminar(UnidadAprendizaje unidad){
        delegateUnidadAprendizaje.eliminar(unidad);
    }
}
