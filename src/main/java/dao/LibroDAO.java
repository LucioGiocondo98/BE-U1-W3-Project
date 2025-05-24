package dao;

public class LibroDAO {
    private EntityManager em;

    public LibroDAO(EntityManager em) {
        this.em = em;
    }

    public void salvaLibro(Libro libro) {
        em.getTransaction().begin();
        em.persist(libro);
        em.getTransaction().commit();
    }

    public Libro trovaPerISBN(int isbn) {
        return em.find(Libro.class, isbn);
    }

    public void rimuoviLibro(int isbn) {
        Libro libro = trovaPerISBN(isbn);
        if (libro != null) {
            em.getTransaction().begin();
            em.remove(libro);
            em.getTransaction().commit();
        }
    }

    public List<Libro> cercaPerAutore(String autore) {
        return em.createQuery("SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class)
                .setParameter("autore", autore)
                .getResultList();
    }

}
