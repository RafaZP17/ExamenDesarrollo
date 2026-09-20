package mx.desarrollo.helper;

import mx.desarrollo.entity.UnidadAprendizaje;
import mx.desarrollo.integration.ServiceFacadeLocator;

import java.io.Serializable;
import java.util.List;

public class CatalogoHelper implements Serializable {
    public List<UnidadAprendizaje> obtenerTodas(){
            return ServiceFacadeLocator.getInstanceFacadeUnidadAprendizaje().obtenerTodas();
    }
    public boolean guardar(UnidadAprendizaje unidad){
        return ServiceFacadeLocator.getInstanceFacadeUnidadAprendizaje().guardar(unidad);
    }

    public boolean actualizar(UnidadAprendizaje unidad){
        return ServiceFacadeLocator.getInstanceFacadeUnidadAprendizaje().actualizar(unidad);
    }

    public void eliminar(UnidadAprendizaje unidad){
        ServiceFacadeLocator.getInstanceFacadeUnidadAprendizaje().eliminar(unidad);
    }
}
