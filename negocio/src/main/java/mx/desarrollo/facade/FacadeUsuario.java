package mx.desarrollo.facade;

import mx.desarrollo.entity.Usuario;
import mx.desarrollo.persitence.dao.UsuarioDAO;

public class FacadeUsuario {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario login(String username, String password) {
        return usuarioDAO.login(username, password);
    }
}