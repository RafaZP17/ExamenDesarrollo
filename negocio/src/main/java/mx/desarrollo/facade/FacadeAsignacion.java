package mx.desarrollo.facade;

import mx.desarrollo.delegate.DelegateAsignacion;
import mx.desarrollo.entity.ProfesorUnidad;

public class FacadeAsignacion {

    private final DelegateAsignacion delegate = new DelegateAsignacion();

    public void registrarAsignacion(ProfesorUnidad asignacion) throws Exception {
        delegate.registrarAsignacion(asignacion);
    }
}