package entities;

import enumeration.Periodicita;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("RIVISTA")
public class Rivista extends Catalogo {
    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;

    public Rivista(int isbn, String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(isbn, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }


    @Override
    public String toString() {
        return "Rivista{" +
                "isbn=" + isbn +
                ", periodicita=" + periodicita +
                ", titolo='" + titolo + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", numeroPagine=" + numeroPagine +
                '}';
    }
}