package mx.desarrollo.delegate;
//Aqui se valida las horas de 0 a 4 horas

import mx.desarrollo.entity.UnidadAprendizaje;
import mx.desarrollo.persitence.integration.ServiceLocator;

import java.util.List;

public class DelegateUnidadAprendizaje {

    public List<UnidadAprendizaje> obtenerTodas(){
        return ServiceLocator.getInstanceUnidadAprendizajeDAO().obtenerTodos();
    }

    public boolean guardar(UnidadAprendizaje unidad){
        if(!validarHoras(unidad)) return false;
        ServiceLocator.getInstanceUnidadAprendizajeDAO().save(unidad);
        return true;
    }

    public boolean actualizar(UnidadAprendizaje unidad){
        if(!validarHoras(unidad)) return false;
        ServiceLocator.getInstanceUnidadAprendizajeDAO().save(unidad);
        return true;
    }

    public void eliminar(UnidadAprendizaje unidad){
        ServiceLocator.getInstanceUnidadAprendizajeDAO().delete(unidad);
    }

    private boolean validarHoras(UnidadAprendizaje unidad){
        return validarRango(unidad.getHorasClase())
                && validarRango(unidad.getHorasTaller())
                && validarRango(unidad.getHorasLaboratorio());
    }

    private boolean validarRango(Integer horas){
        return horas != null && horas >= 0 && horas <= 4;
    }
}
