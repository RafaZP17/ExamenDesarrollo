package mx.desarrollo.delegate;

import mx.desarrollo.entity.ProfesorUnidad;
import mx.desarrollo.persitence.dao.ProfesorUnidadDAO;
import java.time.LocalTime;
import java.util.List;

public class DelegateAsignacion {

    private ProfesorUnidadDAO dao = new ProfesorUnidadDAO();

    public void registrarAsignacion(ProfesorUnidad nuevaAsignacion) throws Exception {
        // 1. Obtener todas las materias que el profesor ya tiene asignadas
        List<ProfesorUnidad> horarioExistente = dao.buscarPorProfesor(nuevaAsignacion.getProfesor().getIdProfesor());

        // 2. Validar traslape día por día
        for (ProfesorUnidad asignada : horarioExistente) {
            if (asignada.getDia().equalsIgnoreCase(nuevaAsignacion.getDia())) {

                // Regla matemática de traslape: (InicioA < FinB) Y (FinA > InicioB)
                boolean hayTraslape = nuevaAsignacion.getHoraInicio().isBefore(asignada.getHoraFin())
                        && nuevaAsignacion.getHoraFin().isAfter(asignada.getHoraInicio());

                if (hayTraslape) {
                    throw new Exception("Traslape de horario detectado: El profesor ya imparte "
                            + asignada.getUnidadAprendizaje().getNombre() + " el " + asignada.getDia()
                            + " de " + asignada.getHoraInicio() + " a " + asignada.getHoraFin());
                }
            }
        }

        // 3. Si aprueba la validación, se guarda en la BD
        dao.guardar(nuevaAsignacion);
    }
}