package mx.desarrollo.delegate;

import mx.desarrollo.entity.ProfesorUnidad;
import mx.desarrollo.persitence.dao.ProfesorUnidadDAO;
import java.time.LocalTime;
import java.util.List;

public class DelegateAsignacion {

    private ProfesorUnidadDAO dao = new ProfesorUnidadDAO();

    public void registrarAsignacion(ProfesorUnidad nuevaAsignacion) throws Exception {
        List<ProfesorUnidad> horarioExistente = dao.buscarPorProfesor(nuevaAsignacion.getProfesor().getIdProfesor());

        for (ProfesorUnidad asignada : horarioExistente) {
            if (asignada.getDia().equalsIgnoreCase(nuevaAsignacion.getDia())) {

                boolean hayTraslape = nuevaAsignacion.getHoraInicio().isBefore(asignada.getHoraFin())
                        && nuevaAsignacion.getHoraFin().isAfter(asignada.getHoraInicio());

                if (hayTraslape) {
                    throw new Exception("Traslape de horario detectado: El profesor ya imparte "
                            + asignada.getUnidadAprendizaje().getNombre() + " el " + asignada.getDia()
                            + " de " + asignada.getHoraInicio() + " a " + asignada.getHoraFin());
                }
            }
        }

        dao.guardar(nuevaAsignacion);
    }

    public void actualizarAsignacion(ProfesorUnidad asignacionActualizada) throws Exception {
        List<ProfesorUnidad> horarioExistente = dao.buscarPorProfesor(asignacionActualizada.getProfesor().getIdProfesor());

        for (ProfesorUnidad asignada : horarioExistente) {
            // Ignoramos la misma asignación que estamos editando para que no choque consigo misma
            if (asignada.getIdAsignacion() == asignacionActualizada.getIdAsignacion()) {
                continue;
            }

            if (asignada.getDia().equalsIgnoreCase(asignacionActualizada.getDia())) {
                boolean hayTraslape = asignacionActualizada.getHoraInicio().isBefore(asignada.getHoraFin())
                        && asignacionActualizada.getHoraFin().isAfter(asignada.getHoraInicio());

                if (hayTraslape) {
                    throw new Exception("Traslape de horario detectado: El profesor ya imparte "
                            + asignada.getUnidadAprendizaje().getNombre() + " el " + asignada.getDia()
                            + " de " + asignada.getHoraInicio() + " a " + asignada.getHoraFin());
                }
            }
        }

        dao.actualizar(asignacionActualizada); // Asegúrate de tener este método en tu ProfesorUnidadDAO
    }

    public void eliminarAsignacion(int idAsignacion) {
        dao.eliminar(idAsignacion); // Asegúrate de tener este método en tu ProfesorUnidadDAO
    }

    public List<ProfesorUnidad> obtenerAsignacionesPorProfesor(Integer idProfesor) {
        return dao.buscarPorProfesor(idProfesor);
    }

    public List<ProfesorUnidad> obtenerTodas() {
        return dao.obtenerTodas();
    }
}