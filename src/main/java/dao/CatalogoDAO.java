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

    public List<Catalogo> cercaPerTitolo(String parteTitolo) {
        return em.createQuery("SELECT c FROM Catalogo c WHERE LOWER(c.titolo) LIKE LOWER(:titolo)", Catalogo.class)
                .setParameter("titolo", "%" + parteTitolo + "%")
                .getResultList();
    }

    public List<Catalogo> cercaPerAnno(int anno) {
        return em.createQuery("SELECT c FROM Catalogo c WHERE c.annoPubblicazione = :anno", Catalogo.class)
                .setParameter("anno", anno)
                .getResultList();
    }

    public List<Catalogo> cercaPerAutore(String autore) {
        return em.createQuery("SELECT c FROM Catalogo c WHERE c.autore = :autore", Catalogo.class)
                .setParameter("autore", autore)
                .getResultList();
    }
}


