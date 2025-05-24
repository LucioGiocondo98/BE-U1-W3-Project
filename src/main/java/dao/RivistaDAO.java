package dao;

import entities.Libro;
import entities.Rivista;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Set;

public class RivistaDAO {
    private EntityManager em;

    public RivistaDAO(EntityManager em) {
        this.em = em;
    }

    public void salvaRivista(Rivista rivista) {
        em.getTransaction().begin();
        em.persist(rivista);
        em.getTransaction().commit();
    }
    public Rivista trovaPerISBN(int isbn) {
        return em.find(Rivista.class, isbn);
    }

    public void rimuoviRivista(int isbn) {
        Rivista rivista = trovaPerISBN(isbn);
        if (rivista != null) {
            em.getTransaction().begin();
            em.remove(rivista);
            em.getTransaction().commit();
        }
    }
    public List<Rivista> cercaPerAutore(String autore) {
        return (List<Rivista>) em.createQuery("SELECT r FROM Rivista r WHERE r.autore = :autore", Rivista.class)
                .setParameter("autore", autore).getResultList();
    }
    public List<Rivista> cercaPerAnno(int anno) {
        return em.createQuery("SELECT r FROM Rivista r WHERE r.annoPubblicazione = :anno", Rivista.class)
                .setParameter("anno", anno)
                .getResultList();
    }
    public List<Rivista> cercaPerTitolo(String parteTitolo) {
        return em.createQuery("SELECT r FROM Rivista r WHERE LOWER(r.titolo) LIKE LOWER(:titolo)", Rivista.class)
                .setParameter("titolo", "%" + parteTitolo + "%")
                .getResultList();
    }


}

