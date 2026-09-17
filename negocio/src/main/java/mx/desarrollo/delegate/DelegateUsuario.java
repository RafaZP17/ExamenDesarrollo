package mx.desarrollo.delegate;

import mx.desarrollo.entity.Usuario;
import mx.desarrollo.integration.ServiceFacadeLocator;

public class DelegateUsuario {

    public Usuario login(String username, String password) {
        return ServiceFacadeLocator.getInstanceFacadeUsuario().login(username, password);
    }
}