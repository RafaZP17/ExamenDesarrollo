package mx.desarrollo.integration;

import mx.desarrollo.facade.FacadeUsuario;
import mx.desarrollo.facade.FacadeAlumno;

public class ServiceFacadeLocator {
    private static FacadeUsuario facadeUsuario;
    private static FacadeAlumno facadeAlumno;

    public static FacadeUsuario getInstanceFacadeUsuario() {
        if (facadeUsuario == null) {
            facadeUsuario = new FacadeUsuario();
        }
        return facadeUsuario;
    }

    public static FacadeAlumno getInstanceFacadeAlumno() {
        if (facadeAlumno == null) {
            facadeAlumno = new FacadeAlumno();
        }
        return facadeAlumno;
    }
}
