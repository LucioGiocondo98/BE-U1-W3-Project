package dao;

import entities.Catalogo;
import entities.Prestito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class PrestitoDAO {
    private EntityManager em;

    public PrestitoDAO(EntityManager em) {
        this.em = em;
    }


    public void salvaPrestito(Prestito prestito) {
        em.getTransaction().begin();
        em.persist(prestito);
        em.getTransaction().commit();
    }
    public List<Prestito> trovaPrestitiAttiviPerUtente(int numeroTessera) {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :tessera AND p.dataRestituzioneEffettiva IS NULL",
                Prestito.class);
        query.setParameter("tessera", numeroTessera);
        return query.getResultList();
    }

    // Prestiti scaduti e non ancora restituiti
    public List<Prestito> trovaPrestitiScaduti() {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p WHERE p.dataPrevistaRestituzione < :oggi AND p.dataRestituzioneEffettiva IS NULL",
                Prestito.class);
        query.setParameter("oggi", LocalDate.now());
        return query.getResultList();
    }
}
