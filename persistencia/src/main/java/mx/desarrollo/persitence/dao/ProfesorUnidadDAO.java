package mx.desarrollo.persitence.dao;

import mx.desarrollo.entity.ProfesorUnidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import mx.desarrollo.persitence.persistence.HibernateUtil;

import java.util.List;
import java.util.ArrayList;

public class ProfesorUnidadDAO {

    /**
     * Consulta todas las asignaciones y horarios que tiene un profesor en particular.
     * Sirve para que la capa de negocio verifique si hay traslape de horario.
     */
    public List<ProfesorUnidad> buscarPorProfesor(int idProfesor) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            TypedQuery<ProfesorUnidad> query = em.createQuery(
                    "SELECT pu FROM ProfesorUnidad pu " +
                            "JOIN FETCH pu.unidadAprendizaje u " +
                            "JOIN FETCH pu.profesor p " +
                            "WHERE p.idProfesor = :idProfesor",
                    ProfesorUnidad.class
            );
            query.setParameter("idProfesor", idProfesor);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public List<ProfesorUnidad> obtenerTodas() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            TypedQuery<ProfesorUnidad> query = em.createQuery(
                    "SELECT pu FROM ProfesorUnidad pu " +
                            "JOIN FETCH pu.unidadAprendizaje " +
                            "JOIN FETCH pu.profesor",
                    ProfesorUnidad.class
            );
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    /**
     * Guarda la nueva asignación de la unidad con el profesor, el día y las horas.
     */
    public void guardar(ProfesorUnidad asignacion) throws Exception {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(asignacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza una asignación existente en la base de datos.
     */
    public void actualizar(ProfesorUnidad asignacion) throws Exception {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(asignacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Elimina una asignación a partir de su ID.
     */
    public void eliminar(int idAsignacion) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ProfesorUnidad asignacion = em.find(ProfesorUnidad.class, idAsignacion);
            if (asignacion != null) {
                em.remove(asignacion);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}