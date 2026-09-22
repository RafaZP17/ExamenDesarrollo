package mx.desarrollo.delegate;

import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persitence.dao.ProfesorDAO;

import java.util.List;

public class DelegateProfesor {


    private ProfesorDAO profesorDAO;

    public DelegateProfesor() {
        this.profesorDAO = new ProfesorDAO();
    }

    // Este es el método que validará todo antes de guardar
    public void registrarProfesor(Profesor profesor) throws Exception {

        // 1. Limpiar espacios en blanco a los extremos y estandarizar a MAYÚSCULAS
        String nombre = profesor.getNombre() != null ? profesor.getNombre().trim().toUpperCase() : "";
        String apPaterno = profesor.getApellidoPaterno() != null ? profesor.getApellidoPaterno().trim().toUpperCase() : "";
        String apMaterno = profesor.getApellidoMaterno() != null ? profesor.getApellidoMaterno().trim().toUpperCase() : "";
        String rfc = profesor.getRfc() != null ? profesor.getRfc().trim().toUpperCase() : "";

        // Actualizamos el objeto con los datos ya limpios
        profesor.setNombre(nombre);
        profesor.setApellidoPaterno(apPaterno);
        profesor.setApellidoMaterno(apMaterno);
        profesor.setRfc(rfc);

        // 2. Validar que no envíen campos en blanco
        if (nombre.isEmpty() || apPaterno.isEmpty() || apMaterno.isEmpty()) {
            throw new Exception("Error: El nombre y los apellidos son obligatorios y no pueden estar vacíos.");
        }

        // 3. Validar longitud máxima
        if (nombre.length() > 50 || apPaterno.length() > 50 || apMaterno.length() > 50) {
            throw new Exception("Error: El nombre y los apellidos no pueden exceder los 50 caracteres.");
        }

        // 4. Validar que los nombres solo contengan letras, espacios y acentos
        String regexLetras = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
        if (!nombre.matches(regexLetras) || !apPaterno.matches(regexLetras) || !apMaterno.matches(regexLetras)) {
            throw new Exception("Error: El nombre y los apellidos solo pueden contener letras.");
        }

        // 5. Validar longitud exacta del RFC
        if (rfc.length() != 13) {
            throw new Exception("Error: El RFC debe contener exactamente 13 caracteres.");
        }

        // 6. Validar estructura oficial del RFC (4 letras, 6 números, 3 alfanuméricos)
        String regexRfc = "^[A-ZÑ&]{4}\\d{6}[A-Z\\d]{3}$";
        if (!rfc.matches(regexRfc)) {
            throw new Exception("Error: El formato del RFC es inválido (Ejemplo esperado: ABCD123456XYZ).");
        }

        // 7. El candado de Base de Datos: Validar que el RFC no esté duplicado
        if (profesorDAO.existeRfc(rfc)) {
            throw new Exception("Error: El RFC '" + rfc + "' ya se encuentra registrado para otro profesor.");
        }

        // Luz verde: guardamos en MySQL
        profesorDAO.guardar(profesor);
    }

    //Metodo para actualizar a los profes registrados
    public void actualizarProfesor(Profesor profesor) throws Exception {
        if (profesor.getIdProfesor() == null) {
            throw new Exception("Error: No se puede actualizar un profesor sin ID.");
        }

        if (profesor.getNombre().length() > 50 ||
                profesor.getApellidoPaterno().length() > 50 ||
                profesor.getApellidoMaterno().length() > 50) {
            throw new Exception("Error: El nombre y los apellidos no pueden exceder los 50 caracteres.");
        }

        String rfc = profesor.getRfc();
        if (rfc == null || rfc.trim().isEmpty() || rfc.length() != 13) {
            throw new Exception("Error: El RFC debe contener exactamente 13 caracteres.");
        }

        profesorDAO.actualizar(profesor);
    }

    // Metodo para eliminar algun profesor registrado
    public void eliminarProfesor(Profesor profesor) throws Exception {
        if (profesor == null || profesor.getIdProfesor() == null) {
            throw new Exception("Error: Debe seleccionar un profesor para eliminar.");
        }

        profesorDAO.eliminar(profesor.getIdProfesor());
    }


    //Metodo que retorna la lista de profesores para mostrarse en la vista
    public List<Profesor> obtenerListaProfesores () {
        return profesorDAO.listar();
    }
}