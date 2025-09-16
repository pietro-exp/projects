package main;

import dominio.Bacheca;
import dominio.Utente;
import interfaccia.BachecaCLI;
import interfaccia.BachecaGUI;
import jbook.util.Input;
import persistence.Autenticazione;
import persistence.BachecaPersistence;

import javax.swing.SwingUtilities;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * La classe Main è il punto di ingresso principale dell'applicazione per la gestione
 * di una bacheca di annunci.
 * Si occupa del caricamento iniziale dei dati, dell'autenticazione dell'utente
 * e della scelta e avvio dell'interfaccia utente (CLI o GUI).
 * 
 * @author Pietro Mezzatesta
 */
public class Main {
	
	/**
     * Il metodo main avvia l'applicazione.
     * <p>
     * Le operazioni eseguite sono:
     * <ul>
     * <li>Caricamento dei dati della bacheca e degli utenti da file persistenti.</li>
     * <li>Gestione dell'autenticazione o della registrazione di un utente.</li>
     * <li>Richiesta all'utente di scegliere tra l'interfaccia a riga di comando (CLI) o grafica (GUI).</li>
     * <li>Avvio dell'interfaccia scelta.</li>
     * </ul>
     *
     * @param args Argomenti della riga di comando (non utilizzati).
     */
    public static void main(String[] args) {
        Bacheca bacheca = null;
        Utente utenteCorrente = null;

        try {
            // Prima carica la bacheca
            File bachecaFile = new File("bacheca.ser");
            if (bachecaFile.exists()) {
                bacheca = BachecaPersistence.caricaBacheca(bachecaFile.getName());
                System.out.println("Bacheca caricata da file.");
            } else {
                bacheca = new Bacheca();
                System.out.println("Nuova bacheca creata.");
            }
            
            // Poi carica gli utenti e li imposta nella bacheca
            Map<String, Utente> utentiCaricati = BachecaPersistence.caricaUtenti();
            bacheca.setUtenti(utentiCaricati);
            System.out.println("Utenti caricati: " + utentiCaricati.size() + " utenti trovati.");
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore nel caricamento dei dati: " + e.getMessage());
            bacheca = new Bacheca();
            System.out.println("Creata una nuova bacheca vuota.");
        }

        // Chiamata centralizzata al servizio di autenticazione
        utenteCorrente = Autenticazione.gestisci(bacheca);

        String interfaccia = Input.readString("Scegli l'interfaccia (CLI/GUI): ").trim().toLowerCase();

        final Bacheca finalBacheca = bacheca;
        final Utente finalUtenteCorrente = utenteCorrente;

        if (interfaccia.equals("cli")) {
            BachecaCLI cli = new BachecaCLI(finalBacheca, finalUtenteCorrente);
            cli.run();
        } else if (interfaccia.equals("gui")) {
            SwingUtilities.invokeLater(() -> {
                BachecaGUI gui = new BachecaGUI(finalBacheca, finalUtenteCorrente);
                gui.createAndShowGUI();
            });
        } else {
            System.out.println("Scelta interfaccia non valida. Il programma terminerà.");
        }
    }
}