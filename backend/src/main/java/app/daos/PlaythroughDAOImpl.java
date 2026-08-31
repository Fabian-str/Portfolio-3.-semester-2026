package app.daos;

import app.entities.Playthrough;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class PlaythroughDAOImpl implements PlaythroughDAO {

    private final EntityManagerFactory emf;

    public PlaythroughDAOImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Playthrough create(Playthrough playthrough) {

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(playthrough);
            em.getTransaction().commit();
            return playthrough;
        }
    }

    @Override
    public Playthrough findById(Long id) {

        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Playthrough.class, id);
        }
    }

    @Override
    public List<Playthrough> findAll() {

        try (EntityManager em = emf.createEntityManager()) {
            return em
                .createQuery("SELECT p FROM Playthrough p ORDER BY p.createdAt DESC", Playthrough.class)
                .getResultList();
        }
    }

    @Override
    public Playthrough update(Playthrough playthrough) {

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Playthrough updated = em.merge(playthrough);
            em.getTransaction().commit();
            return updated;
        }
    }

    @Override
    public void delete(Long id) {

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Playthrough playthrough = em.find(Playthrough.class, id);

            if (playthrough != null) {
                em.remove(playthrough);
            }

            em.getTransaction().commit();
        }
    }
}