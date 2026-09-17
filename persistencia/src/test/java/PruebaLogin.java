import mx.desarrollo.entity.Usuario;
import mx.desarrollo.persitence.dao.UsuarioDAO;

public class PruebaLogin {

    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();

        System.out.println("=== PRUEBA 1: Credenciales Correctas ===");
        Usuario usuarioValido = dao.login("Ian Ludwig", "admin123");
        if (usuarioValido != null) {
            System.out.println("EXITO: Usuario autenticado correctamente -> " + usuarioValido.getUsername());
        } else {
            System.out.println("ERROR: No se encontro al usuario o la contraseña falló.");
        }

        System.out.println("\n=== PRUEBA 2: Credenciales Incorrectas ===");
        Usuario usuarioInvalido = dao.login("Ian Ludwig", "clave_falsa");
        if (usuarioInvalido == null) {
            System.out.println("EXITO: El sistema rechazo correctamente las credenciales falsas.");
        } else {
            System.out.println("ERROR: El sistema permitio el acceso con contraseña incorrecta.");
        }
    }
}