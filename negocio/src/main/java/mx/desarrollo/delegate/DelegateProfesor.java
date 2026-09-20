package mx.desarrollo.delegate;

import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persitence.dao.ProfesorDAO;

import java.util.Comparator;
import java.util.List;

public class DelegateProfesor {


    private ProfesorDAO profesorDAO;

    public DelegateProfesor() {
        this.profesorDAO = new ProfesorDAO();
    }

    // Este es el método que validará todo antes de guardar
    public void registrarProfesor(Profesor profesor) throws Exception {

        //  Válida que los campos de texto no superen los 50 caracteres
        if (profesor.getNombre().length() > 50 ||
                profesor.getApellidoPaterno().length() > 50 ||
                profesor.getApellidoMaterno().length() > 50) {
            throw new Exception("Error: El nombre y los apellidos no pueden exceder los 50 caracteres.");
        }

        //  Validar el RFC
        String rfc = profesor.getRfc();
        if (rfc == null || rfc.trim().isEmpty() || rfc.length() != 13) {
            throw new Exception("Error: El RFC debe contener exactamente 13 caracteres.");
        }

        // Si sobrevive a las validaciones de arriba, le damos luz verde al DAO para guardarlo en MySQL
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

        if (lista != null && !lista.isEmpty()) {
            lista.sort(Comparator.comparing(Profesor::getNombre, String.CASE_INSENSITIVE_ORDER));
        }

        return lista;
    }
}