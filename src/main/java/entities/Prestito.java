package entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.StringJoiner;

@Entity
public class Prestito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Utente utente;

    @ManyToOne
    private Catalogo elementoPrestato;

    @Column(name = "data_inizio")
    private LocalDate dataInizio;

    @Column(name = "data_prevista_restituzione")
    private LocalDate dataPrevistaRestituzione;

    @Column(name = "data_restituzione_effettiva")
    private LocalDate dataRestituzioneEffettiva;

    public Prestito() {}

    public Prestito(Utente utente, Catalogo elementoPrestato, LocalDate dataInizio) {
        this.utente = utente;
        this.elementoPrestato = elementoPrestato;
        this.dataInizio = dataInizio;
        this.dataPrevistaRestituzione = dataInizio.plusDays(30);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Catalogo getElementoPrestato() {
        return elementoPrestato;
    }

    public void setElementoPrestato(Catalogo elementoPrestato) {
        this.elementoPrestato = elementoPrestato;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataPrevistaRestituzione() {
        return dataPrevistaRestituzione;
    }

    public void setDataPrevistaRestituzione(LocalDate dataPrevistaRestituzione) {
        this.dataPrevistaRestituzione = dataPrevistaRestituzione;
    }

    public LocalDate getDataRestituzioneEffettiva() {
        return dataRestituzioneEffettiva;
    }

    public void setDataRestituzioneEffettiva(LocalDate dataRestituzioneEffettiva) {
        this.dataRestituzioneEffettiva = dataRestituzioneEffettiva;
    }


    @Override
    public String toString() {
        return "\
                Prestito{
                    id=$id,
                    utente=$utente,
                    elementoPrestato=$elementoPrestato,
                    dataInizio=$dataInizio,
                    dataPrevistaRestituzione=$dataPrevistaRestituzione,
                    dataRestituzioneEffettiva=$dataRestituzioneEffettiva
                }"
    }
}
