package dao;


import entities.Catalogo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CatalogoDAO {
        private EntityManager em;

        public CatalogoDAO(EntityManager em) {
            this.em = em;
        }

        public void add(Catalogo catalogo) {
            em.persist(catalogo);
        }

        public void remove(int isbn) {
            Catalogo catalogo = em.find(Catalogo.class, isbn);
            if (catalogo != null) {
                em.remove(catalogo);
            }
        }

        public Catalogo findByIsbn(int isbn) {
            return em.find(Catalogo.class, isbn);
        }

        public List<Catalogo> findByAnnoPubblicazione(int anno) {
            TypedQuery<Catalogo> query = em.createQuery("SELECT c FROM Catalogo c WHERE c.annoPubblicazione = :anno", Catalogo.class);
            query.setParameter("anno", anno);
            return query.getResultList();
        }

        public List<Catalogo> findByTitolo(String titolo) {
            TypedQuery<Catalogo> query = em.createQuery("SELECT c FROM Catalogo c WHERE c.titolo LIKE :titolo", Catalogo.class);
            query.setParameter("titolo", "%" + titolo + "%");
            return query.getResultList();
        }
    }


