/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.desarrollo.helper;

import mx.desarrollo.delegate.DelegateUsuario;
import mx.desarrollo.entity.Usuario;

public class LoginHelper {
    private DelegateUsuario delegateUsuario = new DelegateUsuario();

    public Usuario login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return null;
        }
        return delegateUsuario.login(username, password);
    }
}