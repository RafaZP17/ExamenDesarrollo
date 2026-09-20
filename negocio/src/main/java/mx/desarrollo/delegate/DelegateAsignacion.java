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

    public List<ProfesorUnidad> obtenerAsignacionesPorProfesor(Integer idProfesor) {
        return dao.buscarPorProfesor(idProfesor);
    }

    public List<ProfesorUnidad> obtenerTodas() {
        return dao.obtenerTodas(); // Asegúrate de que tu ProfesorUnidadDAO también tenga este método implementado
    }
}