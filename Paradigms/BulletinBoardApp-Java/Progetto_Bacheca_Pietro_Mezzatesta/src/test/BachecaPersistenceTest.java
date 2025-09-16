package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import dominio.*;
import eccezioni.DataScadenzaNonValidaException;
import eccezioni.UtenteGiaRegistratoException;
import persistence.BachecaPersistence;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Classe di test per la classe {@link BachecaPersistence}.
 * Testa tutti i metodi di salvataggio e caricamento dei dati.
 * 
 * @author Pietro Mezzatesta
 */
class BachecaPersistenceTest {

    private Bacheca bacheca;
    private Map<String, Utente> utenti;
    private Utente utente1;
    private Utente utente2;
    private AnnuncioVendita annuncioVendita;
    private AnnuncioAcquisto annuncioAcquisto;

    @BeforeEach
    void setUp() throws UtenteGiaRegistratoException, DataScadenzaNonValidaException {
        bacheca = new Bacheca();
        utenti = new HashMap<>();
        
        utente1 = new Utente("test1@example.com", "Mario", "Rossi", "Password12@4!3");
        utente2 = new Utente("test2@example.com", "Luigi", "Bianchi", "Password456!!");
        
        utenti.put(utente1.getEmail(), utente1);
        utenti.put(utente2.getEmail(), utente2);
        
        bacheca.registraUtente(utente1);
        bacheca.registraUtente(utente2);
        
        Set<String> paroleChiave = new HashSet<>(Arrays.asList("smartphone", "elettronica"));
        
        annuncioVendita = new AnnuncioVendita(
            utente1, 
            "iPhone 13", 
            800.0, 
            paroleChiave, 
            LocalDate.now().plusDays(30)
        );
        
        annuncioAcquisto = new AnnuncioAcquisto(
            utente2, 
            "Samsung Galaxy", 
            600.0, 
            paroleChiave
        );
        
        bacheca.aggiungiAnnuncio(annuncioVendita);
        bacheca.aggiungiAnnuncio(annuncioAcquisto);
    }

    @AfterEach
    void tearDown() {
        // Pulisci i file temporanei
        String[] files = {"bacheca_test.ser", "utenti.ser", "bacheca_null_test.ser", 
                         "bacheca_vuota_test.ser", "bacheca_corrotto_test.ser", 
                         "bacheca_vuoto_test.ser", "bacheca_integrazione_test.ser",
                         "bacheca_grande_test.ser"};
        
        for (String filename : files) {
            File file = new File(filename);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    // Test Salvataggio Bacheca
    @Test
    void testSalvaBachecaValida() {
        String nomeFile = "bacheca_test.ser";
        
        assertDoesNotThrow(() -> {
            BachecaPersistence.salvaBacheca(bacheca, nomeFile);
        });
        
        File file = new File(nomeFile);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void testSalvaBachecaNulla() {
        String nomeFile = "bacheca_null_test.ser";
        
        assertDoesNotThrow(() -> {
            BachecaPersistence.salvaBacheca(null, nomeFile);
        });
        
        File file = new File(nomeFile);
        assertTrue(file.exists());
    }

    @Test
    void testSalvaBachecaVuota() {
        Bacheca bachecaVuota = new Bacheca();
        String nomeFile = "bacheca_vuota_test.ser";
        
        assertDoesNotThrow(() -> {
            BachecaPersistence.salvaBacheca(bachecaVuota, nomeFile);
        });
        
        File file = new File(nomeFile);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    // Test Caricamento Bacheca
    @Test
    void testCaricaBachecaValida() {
        String nomeFile = "bacheca_test.ser";
        
        // Prima salva
        BachecaPersistence.salvaBacheca(bacheca, nomeFile);
        
        // Poi carica
        Bacheca bachecaCaricata = BachecaPersistence.caricaBacheca(nomeFile);
        
        assertNotNull(bachecaCaricata);
        assertEquals(bacheca.getNumeroAnnunci(), bachecaCaricata.getNumeroAnnunci());
        assertEquals(bacheca.getNumeroUtentiRegistrati(), bachecaCaricata.getNumeroUtentiRegistrati());
        
        // Verifica che gli annunci siano stati caricati correttamente
        assertNotNull(bachecaCaricata.getAnnuncioById(annuncioVendita.getId()));
        assertNotNull(bachecaCaricata.getAnnuncioById(annuncioAcquisto.getId()));
    }

    @Test
    void testCaricaBachecaFileInesistente() {
        Bacheca bachecaCaricata = BachecaPersistence.caricaBacheca("file_inesistente.ser");
        assertNull(bachecaCaricata);
    }

    @Test
    void testCaricaBachecaFileCorotto() throws IOException {
        String nomeFile = "bacheca_corrotto_test.ser";
        
        // Crea un file corrotto
        try (FileWriter writer = new FileWriter(nomeFile)) {
            writer.write("Questo non è un file serializzato valido");
        }
        
        Bacheca bachecaCaricata = BachecaPersistence.caricaBacheca(nomeFile);
        assertNull(bachecaCaricata);
    }

    @Test
    void testCaricaBachecaFileVuoto() throws IOException {
        String nomeFile = "bacheca_vuoto_test.ser";
        
        // Crea un file vuoto
        new File(nomeFile).createNewFile();
        
        Bacheca bachecaCaricata = BachecaPersistence.caricaBacheca(nomeFile);
        assertNull(bachecaCaricata);
    }

    // Test Salvataggio Utenti
    @Test
    void testSalvaUtentiValidi() {
        assertDoesNotThrow(() -> {
            BachecaPersistence.salvaUtenti(utenti);
        });
        
        File file = new File("utenti.ser");
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void testSalvaUtentiVuoti() {
        Map<String, Utente> utentiVuoti = new HashMap<>();
        
        assertDoesNotThrow(() -> {
            BachecaPersistence.salvaUtenti(utentiVuoti);
        });
        
        File file = new File("utenti.ser");
        assertTrue(file.exists());
    }

    @Test
    void testSalvaUtentiNulli() {
        assertDoesNotThrow(() -> {
            BachecaPersistence.salvaUtenti(null);
        });
        
        File file = new File("utenti.ser");
        assertTrue(file.exists());
    }

    // Test Caricamento Utenti
    @Test
    void testCaricaUtentiValidi() throws IOException, ClassNotFoundException {
        // Prima salva
        BachecaPersistence.salvaUtenti(utenti);
        
        // Poi carica
        Map<String, Utente> utentiCaricati = BachecaPersistence.caricaUtenti();
        
        assertNotNull(utentiCaricati);
        assertEquals(2, utentiCaricati.size());
        assertTrue(utentiCaricati.containsKey(utente1.getEmail()));
        assertTrue(utentiCaricati.containsKey(utente2.getEmail()));
        
        Utente utente1Caricato = utentiCaricati.get(utente1.getEmail());
        assertEquals(utente1.getNome(), utente1Caricato.getNome());
        assertEquals(utente1.getCognome(), utente1Caricato.getCognome());
        assertEquals(utente1.getEmail(), utente1Caricato.getEmail());
        assertTrue(utente1Caricato.checkPassword("Password12@4!3"));
    }

    @Test
    void testCaricaUtentiFileInesistente() throws IOException, ClassNotFoundException {
        // Assicurati che il file non esista
        File utentiFile = new File("utenti.ser");
        if (utentiFile.exists()) {
            utentiFile.delete();
        }
        
        Map<String, Utente> utentiCaricati = BachecaPersistence.caricaUtenti();
        
        assertNotNull(utentiCaricati);
        assertTrue(utentiCaricati.isEmpty());
    }

    // Test Integrazione Completa
    @Test
    void testSalvaECaricaCompleto() throws IOException, ClassNotFoundException {
        String bachecaFile = "bacheca_integrazione_test.ser";
        
        // Salva bacheca e utenti
        BachecaPersistence.salvaBacheca(bacheca, bachecaFile);
        BachecaPersistence.salvaUtenti(utenti);
        
        // Carica bacheca e utenti
        Bacheca bachecaCaricata = BachecaPersistence.caricaBacheca(bachecaFile);
        Map<String, Utente> utentiCaricati = BachecaPersistence.caricaUtenti();
        
        assertNotNull(bachecaCaricata);
        assertNotNull(utentiCaricati);
        
        // Integra gli utenti nella bacheca caricata
        bachecaCaricata.setUtenti(utentiCaricati);
        
        // Verifica integrità dei dati
        assertEquals(bacheca.getNumeroAnnunci(), bachecaCaricata.getNumeroAnnunci());
        assertEquals(bacheca.getNumeroUtentiRegistrati(), bachecaCaricata.getNumeroUtentiRegistrati());
        
        // Verifica funzionalità
        assertTrue(bachecaCaricata.isUtenteRegistrato(utente1.getEmail()));
        assertTrue(bachecaCaricata.isUtenteRegistrato(utente2.getEmail()));
        
        Utente utenteRecuperato = bachecaCaricata.getUtente(utente1.getEmail());
        assertNotNull(utenteRecuperato);
        assertTrue(utenteRecuperato.checkPassword("Password12@4!3"));
    }

    // Test Sicurezza Password
    @Test
    void testPasswordNonVisibiliDopoSalvataggio() throws IOException, ClassNotFoundException {
        // Salva utenti con password
        BachecaPersistence.salvaUtenti(utenti);
        
        // Verifica che le password funzionino dopo il caricamento
        Map<String, Utente> utentiCaricati = BachecaPersistence.caricaUtenti();
        Utente utenteCaricato = utentiCaricati.get(utente1.getEmail());
        assertNotNull(utenteCaricato);
        assertTrue(utenteCaricato.checkPassword("Password12@4!3"));
        assertFalse(utenteCaricato.checkPassword("passwordSbagliata"));
    }
}