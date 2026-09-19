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

    //Metodo que retorna la lista de profesores para mostrarse en la vista
    public List<Profesor> obtenerListaProfesores () {
        return profesorDAO.listar();
    }
}