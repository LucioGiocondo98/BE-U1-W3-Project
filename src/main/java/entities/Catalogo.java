package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cataloghi")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Catalogo {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int isbn;
    protected  String titolo;
@Column(name = "anno_di_pubblicazione")
    protected int annoPubblicazione;
@Column(name = "numero_di_pagine")
    protected int numeroPagine;

    public Catalogo(int isbn, String titolo, int annoPubblicazione, int numeroPagine) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;
    }
    public Catalogo(){}

    public int getIsbn() {
        return isbn;
    }

    public String getTitolo() {
        return titolo;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public int getNumeroPagine() {
        return numeroPagine;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public void setNumeroPagine(int numeroPagine) {
        this.numeroPagine = numeroPagine;
    }

    @Override
    public String toString() {
        return "Catalogo{" +
                "isbn=" + isbn +
                ", titolo='" + titolo + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", numeroPagine=" + numeroPagine +
                '}';
    }
}

