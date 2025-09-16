package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import dominio.*;
import eccezioni.*;
import persistence.BachecaPersistence;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

/**
 * Test di debug per identificare e risolvere i problemi principali
 * del sistema prima di eseguire i test complessi.
 * 
 * @author Pietro Mezzatesta
 */
class DebugTest {

    private Bacheca bacheca;
    private Utente utente;

    @BeforeEach
    void setUp() {
        bacheca = new Bacheca();
        utente = new Utente("test@example.com", "Mario", "Rossi", "Password123!");
    }

    @AfterEach
    void tearDown() {
        // Pulizia file
        String[] files = {"debug_test.ser", "utenti.ser"};
        for (String filename : files) {
            File file = new File(filename);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Test
    void testUtenteBasic() {
        // Test creazione utente
        assertNotNull(utente);
        assertEquals("test@example.com", utente.getEmail());
        assertEquals("Mario", utente.getNome());
        assertTrue(utente.checkPassword("Password123!"));
        assertFalse(utente.checkPassword("wrong"));
        
        System.out.println("✓ Test utente base: PASSED");
    }

    @Test
    void testBachecaBasic() throws Exception {
        // Test registrazione
        bacheca.registraUtente(utente);
        assertEquals(1, bacheca.getNumeroUtentiRegistrati());
        assertTrue(bacheca.isUtenteRegistrato(utente.getEmail()));
        
        // Test recupero utente
        Utente recuperato = bacheca.getUtente(utente.getEmail());
        assertNotNull(recuperato);
        assertEquals(utente.getEmail(), recuperato.getEmail());
        
        System.out.println("✓ Test bacheca base: PASSED");
    }

    @Test
    void testAnnuncioVenditaBasic() throws Exception {
        Set<String> parole = new HashSet<>(Arrays.asList("test", "prodotto"));
        
        AnnuncioVendita annuncio = new AnnuncioVendita(
            utente, 
            "Test Prodotto", 
            100.0, 
            parole, 
            LocalDate.now().plusDays(10)
        );
        
        assertNotNull(annuncio);
        assertEquals("Test Prodotto", annuncio.getNomeAnnuncio());
        assertEquals(100.0, annuncio.getPrezzo());
        assertFalse(annuncio.isScaduto());
        
        System.out.println("✓ Test annuncio vendita base: PASSED");
    }

    @Test
    void testAnnuncioAcquistoBasic() {
        Set<String> parole = new HashSet<>(Arrays.asList("test", "prodotto"));
        
        AnnuncioAcquisto annuncio = new AnnuncioAcquisto(
            utente, 
            "Cerco Prodotto", 
            90.0, 
            parole
        );
        
        assertNotNull(annuncio);
        assertEquals("Cerco Prodotto", annuncio.getNomeAnnuncio());
        assertEquals(90.0, annuncio.getPrezzo());
        assertFalse(annuncio.isScaduto()); // Gli acquisti non scadono mai
        
        System.out.println("✓ Test annuncio acquisto base: PASSED");
    }

    @Test
    void testAggiungiAnnuncio() throws Exception {
        bacheca.registraUtente(utente);
        
        Set<String> parole = new HashSet<>(Arrays.asList("test"));
        AnnuncioVendita annuncio = new AnnuncioVendita(
            utente, 
            "Prodotto Test", 
            100.0, 
            parole, 
            LocalDate.now().plusDays(10)
        );
        
        bacheca.aggiungiAnnuncio(annuncio);
        assertEquals(1, bacheca.getNumeroAnnunci());
        
        Annuncio recuperato = bacheca.getAnnuncioById(annuncio.getId());
        assertNotNull(recuperato);
        assertEquals(annuncio.getId(), recuperato.getId());
        
        System.out.println("✓ Test aggiungi annuncio: PASSED");
    }

    @Test
    void testRicercaBase() throws Exception {
        bacheca.registraUtente(utente);
        
        Utente altroUtente = new Utente("altro@example.com", "Luigi", "Bianchi", "Password456!");
        bacheca.registraUtente(altroUtente);
        
        Set<String> parole = new HashSet<>(Arrays.asList("smartphone", "apple"));
        AnnuncioVendita annuncio = new AnnuncioVendita(
            utente, 
            "iPhone", 
            800.0, 
            parole, 
            LocalDate.now().plusDays(15)
        );
        
        bacheca.aggiungiAnnuncio(annuncio);
        
        // L'altro utente cerca
        Set<String> ricerca = new HashSet<>(Arrays.asList("smartphone"));
        List<Annuncio> risultati = bacheca.cercaAnnunci(ricerca, altroUtente);
        
        assertEquals(1, risultati.size());
        assertEquals(annuncio, risultati.get(0));
        
        // L'utente che ha creato l'annuncio non deve trovarlo
        List<Annuncio> risultatiAutore = bacheca.cercaAnnunci(ricerca, utente);
        assertTrue(risultatiAutore.isEmpty());
        
        System.out.println("✓ Test ricerca base: PASSED");
    }

    @Test
    void testPulisciaBachecaDebug() throws Exception {
        bacheca.registraUtente(utente);
        
        Set<String> parole = new HashSet<>(Arrays.asList("test"));
        
        // Annuncio normale (non scaduto)
        AnnuncioVendita annuncioNormale = new AnnuncioVendita(
            utente, 
            "Normale", 
            100.0, 
            parole, 
            LocalDate.now().plusDays(10)
        );
        
        // Annuncio che simula essere scaduto
        AnnuncioVendita annuncioScaduto = new AnnuncioVendita(
            utente, 
            "Scaduto", 
            100.0, 
            parole, 
            LocalDate.now().plusDays(1) // Data valida per costruttore
        ) {
            @Override
            public boolean isScaduto() {
                return true; // Forza scadenza
            }
        };
        
        // Annuncio acquisto (non deve mai essere rimosso)
        AnnuncioAcquisto annuncioAcquisto = new AnnuncioAcquisto(
            utente, 
            "Acquisto", 
            90.0, 
            parole
        );
        
        bacheca.aggiungiAnnuncio(annuncioNormale);
        bacheca.aggiungiAnnuncio(annuncioScaduto);
        bacheca.aggiungiAnnuncio(annuncioAcquisto);
        
        assertEquals(3, bacheca.getNumeroAnnunci());
        
        // Debug: verifica stato prima della pulizia
        System.out.println("Prima pulizia:");
        System.out.println("- Normale scaduto: " + annuncioNormale.isScaduto());
        System.out.println("- Forzato scaduto: " + annuncioScaduto.isScaduto());
        System.out.println("- Acquisto scaduto: " + annuncioAcquisto.isScaduto());
        
        int rimossi = bacheca.pulisciBacheca();
        
        System.out.println("Dopo pulizia:");
        System.out.println("- Annunci rimossi: " + rimossi);
        System.out.println("- Annunci rimasti: " + bacheca.getNumeroAnnunci());
        
        assertEquals(1, rimossi);
        assertEquals(2, bacheca.getNumeroAnnunci());
        
        // Verifica quali sono rimasti
        assertNotNull(bacheca.getAnnuncioById(annuncioNormale.getId()));
        assertNull(bacheca.getAnnuncioById(annuncioScaduto.getId()));
        assertNotNull(bacheca.getAnnuncioById(annuncioAcquisto.getId()));
        
        System.out.println("✓ Test pulizia bacheca: PASSED");
    }

    @Test
    void testPersistenzaDebug() throws Exception {
        bacheca.registraUtente(utente);
        
        Set<String> parole = new HashSet<>(Arrays.asList("test"));
        AnnuncioVendita annuncio = new AnnuncioVendita(
            utente, 
            "Test Persistenza", 
            100.0, 
            parole, 
            LocalDate.now().plusDays(10)
        );
        bacheca.aggiungiAnnuncio(annuncio);
        
        // Salvataggio
        String filename = "debug_test.ser";
        
        System.out.println("Salvando bacheca...");
        BachecaPersistence.salvaBacheca(bacheca, filename);
        BachecaPersistence.salvaUtenti(bacheca.getUtentiRegistrati());
        
        File file = new File(filename);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
        System.out.println("File salvato: " + file.length() + " bytes");
        
        // Caricamento
        System.out.println("Caricando bacheca...");
        Bacheca bachecaCaricata = BachecaPersistence.caricaBacheca(filename);
        assertNotNull(bachecaCaricata);
        
        Map<String, Utente> utentiCaricati = BachecaPersistence.caricaUtenti();
        assertNotNull(utentiCaricati);
        assertFalse(utentiCaricati.isEmpty());
        
        bachecaCaricata.setUtenti(utentiCaricati);
        
        System.out.println("Bacheca caricata:");
        System.out.println("- Annunci: " + bachecaCaricata.getNumeroAnnunci());
        System.out.println("- Utenti: " + bachecaCaricata.getNumeroUtentiRegistrati());
        
        assertEquals(1, bachecaCaricata.getNumeroAnnunci());
        assertEquals(1, bachecaCaricata.getNumeroUtentiRegistrati());
        
        // Test funzionalità post-caricamento
        assertTrue(bachecaCaricata.isUtenteRegistrato(utente.getEmail()));
        Utente utenteCaricato = bachecaCaricata.getUtente(utente.getEmail());
        assertNotNull(utenteCaricato);
        assertTrue(utenteCaricato.checkPassword("Password123!"));
        
        System.out.println("✓ Test persistenza: PASSED");
    }

    @Test
    void testModificaAnnuncio() throws Exception {
        bacheca.registraUtente(utente);
        
        Set<String> parole = new HashSet<>(Arrays.asList("test"));
        AnnuncioVendita annuncio = new AnnuncioVendita(
            utente, 
            "Nome Originale", 
            100.0, 
            parole, 
            LocalDate.now().plusDays(10)
        );
        bacheca.aggiungiAnnuncio(annuncio);
        
        // Modifica
        String nuovoNome = "Nome Modificato";
        double nuovoPrezzo = 150.0;
        Set<String> nuoveParole = new HashSet<>(Arrays.asList("modificato"));
        
        bacheca.modificaAnnuncio(
            annuncio.getId(), 
            utente, 
            nuovoNome, 
            nuovoPrezzo, 
            nuoveParole,
            null
        );
        
        Annuncio modificato = bacheca.getAnnuncioById(annuncio.getId());
        assertEquals(nuovoNome, modificato.getNomeAnnuncio());
        assertEquals(nuovoPrezzo, modificato.getPrezzo());
        assertEquals(nuoveParole, modificato.getParoleChiave());
        
        System.out.println("✓ Test modifica annuncio: PASSED");
    }

    @Test
    void testRimuoviAnnuncio() throws Exception {
        bacheca.registraUtente(utente);
        
        Set<String> parole = new HashSet<>(Arrays.asList("test"));
        AnnuncioVendita annuncio = new AnnuncioVendita(
            utente, 
            "Da Rimuovere", 
            100.0, 
            parole, 
            LocalDate.now().plusDays(10)
        );
        bacheca.aggiungiAnnuncio(annuncio);
        assertEquals(1, bacheca.getNumeroAnnunci());
        
        bacheca.rimuoviAnnuncio(annuncio, utente);
        assertEquals(0, bacheca.getNumeroAnnunci());
        assertNull(bacheca.getAnnuncioById(annuncio.getId()));
        
        System.out.println("✓ Test rimozione annuncio: PASSED");
    }
}