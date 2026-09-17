package mx.desarrollo.persitence.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import mx.desarrollo.entity.Usuario;
import mx.desarrollo.persitence.persistence.HibernateUtil;
import java.util.List;

public class UsuarioDAO {

    public Usuario login(String username, String password) {
        Usuario usuario = null;
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();

            String jpql = "SELECT u FROM Usuario u WHERE u.username = :user AND u.password = :pass";
            TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
            query.setParameter("user", username);
            query.setParameter("pass", password);

            List<Usuario> resultados = query.getResultList();
            if (!resultados.isEmpty()) {
                usuario = resultados.get(0);
            }
        } catch (Exception e) {
            System.err.println("Error en UsuarioDAO.login: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return usuario;
    }
}