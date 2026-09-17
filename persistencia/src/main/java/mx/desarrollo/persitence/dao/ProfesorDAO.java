package mx.desarrollo.persitence.dao;

import mx.desarrollo.entity.Profesor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ProfesorDAO {


    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistencePU");

    public void guardar(Profesor profesor) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(profesor);
            em.getTransaction().commit();
            System.out.println("Profesor guardado exitosamente en la BD.");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}