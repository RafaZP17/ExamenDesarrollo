package mx.desarrollo.integration;

import mx.desarrollo.facade.FacadeAlumno;
import mx.desarrollo.facade.FacadeAsignacion;
import mx.desarrollo.facade.FacadeUnidadAprendizaje;
import mx.desarrollo.facade.FacadeUsuario;

public class ServiceFacadeLocator {
    private static FacadeUsuario facadeUsuario;
    private static FacadeAlumno facadeAlumno;
    private static FacadeUnidadAprendizaje facadeUnidadAprendizaje;
    private static FacadeAsignacion facadeAsignacion;


    public static FacadeAlumno getInstanceFacadeAlumno() {
        if (facadeAlumno == null) {
            facadeAlumno = new FacadeAlumno();
        }
        return facadeAlumno;
    }

    public static FacadeUsuario getInstanceFacadeUsuario() {
        if (facadeUsuario == null) {
            facadeUsuario = new FacadeUsuario();
        }
        return facadeUsuario;
    }



    public static FacadeUnidadAprendizaje getInstanceFacadeUnidadAprendizaje() {
        if (facadeUnidadAprendizaje == null) {
            facadeUnidadAprendizaje = new FacadeUnidadAprendizaje();
        }
        return facadeUnidadAprendizaje;
    }

    public static FacadeAsignacion getInstanceFacadeAsignacion() {
        if (facadeAsignacion == null) {
            facadeAsignacion = new FacadeAsignacion();
        }
        return facadeAsignacion;
    }
}