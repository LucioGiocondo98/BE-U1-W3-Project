package dao;

import entities.Prestito;
import entities.Utente;
import jakarta.persistence.EntityManager;

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
    public List<Prestito> prestitiInCorso(int numeroTessera) {
        return em.createQuery(
                "SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :tessera AND p.dataRestituzioneEffettiva IS NULL",
                Prestito.class
        ).setParameter("tessera", numeroTessera).getResultList();
    }
    public List<Prestito> prestitiScadutiNonRestituiti() {
        return em.createQuery(
                "SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < :oggi AND p.dataRestituzioneEffettiva IS NULL",
                Prestito.class
        ).setParameter("oggi", LocalDate.now()).getResultList();
    }
}
