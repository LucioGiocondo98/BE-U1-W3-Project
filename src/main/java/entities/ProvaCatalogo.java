package entities;

import dao.CatalogoDAO;
import dao.PrestitoDAO;
import dao.UtenteDAO;
import entities.*;

import enumeration.Periodicita;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.Scanner;

public class ProvaCatalogo {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
        EntityManager em = emf.createEntityManager();

        CatalogoDAO catalogoDAO = new CatalogoDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);
        UtenteDAO utenteDAO = new UtenteDAO(em);
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("\n*** MENU GESTIONE CATALOGO ***");
            System.out.println("1. Aggiungi elemento al catalogo (Libro o Rivista)");
            System.out.println("2. Rimuovi elemento per ISBN");
            System.out.println("3. Ricerca per ISBN");
            System.out.println("4. Ricerca per anno pubblicazione");
            System.out.println("5. Ricerca per autore");
            System.out.println("6. Ricerca per titolo o parte di esso");
            System.out.println("7. Ricerca prestiti attivi per tessera utente");
            System.out.println("8. Ricerca prestiti scaduti e non restituiti");
            System.out.println("9. Crea nuovo utente");
            System.out.println("10. Crea nuovo prestito");
            System.out.println("0. Esci");
            System.out.print("Scegli un'opzione: ");

            int scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1 -> {
                    System.out.println("Aggiungi: (1) Libro | (2) Rivista");
                    int tipo = Integer.parseInt(scanner.nextLine());

                    System.out.print("Titolo: ");
                    String titolo = scanner.nextLine();
                    System.out.print("Anno pubblicazione: ");
                    int anno = Integer.parseInt(scanner.nextLine());
                    System.out.print("Numero pagine: ");
                    int pagine = Integer.parseInt(scanner.nextLine());

                    if (tipo == 1) {
                        System.out.print("Autore: ");
                        String autore = scanner.nextLine();
                        System.out.print("Genere: ");
                        String genere = scanner.nextLine();

                        Libro libro = new Libro(0, titolo, anno, pagine, autore, genere);
                        catalogoDAO.salvaElemento(libro);
                        System.out.println("Libro aggiunto con successo.");
                    } else if (tipo == 2) {
                        System.out.print("Periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
                        String per = scanner.nextLine().toUpperCase();
                        Periodicita periodicita = Periodicita.valueOf(per);

                        Rivista rivista = new Rivista(0, titolo, anno, pagine, periodicita);
                        catalogoDAO.salvaElemento(rivista);
                        System.out.println("Rivista aggiunta con successo.");
                    } else {
                        System.out.println("Tipo non valido.");
                    }
                }

                case 2 -> {
                    System.out.print("Inserisci ISBN da rimuovere: ");
                    int isbn = Integer.parseInt(scanner.nextLine());
                    catalogoDAO.rimuoviPerISBN(isbn);
                    System.out.println("Elemento rimosso (se presente).");
                }

                case 3 -> {
                    System.out.print("Inserisci ISBN da cercare: ");
                    int isbn = Integer.parseInt(scanner.nextLine());
                    var trovato = catalogoDAO.trovaPerIsbn(isbn);
                    System.out.println(trovato != null ? trovato : "Nessun elemento trovato.");
                }

                case 4 -> {
                    System.out.print("Inserisci anno: ");
                    int anno = Integer.parseInt(scanner.nextLine());
                    var risultati = catalogoDAO.trovaPerAnno(anno);
                    risultati.forEach(System.out::println);
                }

                case 5 -> {
                    System.out.print("Inserisci autore: ");
                    String autore = scanner.nextLine();
                    var risultati = catalogoDAO.trovaPerAutore(autore);
                    risultati.forEach(System.out::println);
                }

                case 6 -> {
                    System.out.print("Inserisci titolo o parte: ");
                    String parte = scanner.nextLine();
                    var risultati = catalogoDAO.trovaPerTitolo(parte);
                    risultati.forEach(System.out::println);
                }

                case 7 -> {
                    System.out.print("Inserisci numero tessera utente: ");
                    int tessera = Integer.parseInt(scanner.nextLine());
                    var prestiti = prestitoDAO.trovaPrestitiAttiviPerUtente(tessera);
                    prestiti.forEach(System.out::println);
                }

                case 8 -> {
                    var scaduti = prestitoDAO.trovaPrestitiScaduti();
                    scaduti.forEach(System.out::println);
                }

                case 9 -> {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Cognome: ");
                    String cognome = scanner.nextLine();
                    System.out.print("Data di nascita (YYYY-MM-DD): ");
                    LocalDate data = LocalDate.parse(scanner.nextLine());

                    Utente utente = new Utente(nome,cognome,data);
                    utenteDAO.salvaUtente(utente);
                    System.out.println("Utente creato con tessera numero: " + utente.getNumeroTessera());
                }

                case 10 -> {
                    System.out.print("Numero tessera utente: ");
                    int tessera = Integer.parseInt(scanner.nextLine());
                    Utente utente = utenteDAO.trovaPerNumeroTessera(tessera);
                    if (utente == null) {
                        System.out.println("Utente non trovato.");
                        break;
                    }

                    System.out.print("ISBN elemento: ");
                    int isbn = Integer.parseInt(scanner.nextLine());
                    Catalogo elemento = catalogoDAO.trovaPerIsbn(isbn);
                    if (elemento == null) {
                        System.out.println("Elemento non trovato.");
                        break;
                    }

                    LocalDate inizio = LocalDate.now();
                    LocalDate finePrevista = inizio.plusDays(30);

                    Prestito prestito = new Prestito(utente, elemento, LocalDate.now());
                    prestito.setDataPrevistaRestituzione(finePrevista);

                    prestitoDAO.salvaPrestito(prestito);

                    System.out.println("Prestito registrato con scadenza al: " + finePrevista);
                }

                case 0 -> {
                    running = false;
                    System.out.println("Uscita...");
                }

                default -> System.out.println("Scelta non valida.");
            }
        }

        em.close();
        emf.close();
        scanner.close();
    }
}
