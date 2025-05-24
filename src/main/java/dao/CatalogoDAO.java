package dao;


import entities.Catalogo;
import entities.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CatalogoDAO {
        private EntityManager em;

    public CatalogoDAO(EntityManager em) {
        this.em = em;
    }
    public void salvaElemento(Catalogo elemento){
        em.getTransaction().begin();
        em.persist(elemento);
        em.getTransaction().commit();
    }
    public Catalogo trovaPerIsbn(int isbn){
        return em.find(Catalogo.class,isbn);
    }
    public void rimuoviPerISBN(int isbn) {
        Catalogo elemento = trovaPerIsbn(isbn);
        if (elemento != null) {
            em.getTransaction().begin();
            em.remove(elemento);
            em.getTransaction().commit();
        }
    }

    public List<Catalogo> trovaPerAnno(int anno) {
        TypedQuery<Catalogo> query = em.createQuery(
                "SELECT c FROM Catalogo c WHERE c.annoPubblicazione = :anno", Catalogo.class);
        query.setParameter("anno", anno);
        return query.getResultList();
    }

    public List<Libro> trovaPerAutore(String autore) {
        TypedQuery<Libro> query = em.createQuery(
                "SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class);
        query.setParameter("autore", autore);
        return query.getResultList();
    }


    public List<Catalogo> trovaPerTitolo(String parteTitolo) {
        TypedQuery<Catalogo> query = em.createQuery(
                "SELECT c FROM Catalogo c WHERE LOWER(c.titolo) LIKE LOWER(:titolo)", Catalogo.class);
        query.setParameter("titolo", "%" + parteTitolo + "%");
        return query.getResultList();
    }
}


