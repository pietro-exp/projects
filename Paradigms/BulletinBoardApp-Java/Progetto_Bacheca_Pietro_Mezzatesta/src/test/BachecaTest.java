package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import dominio.*;
import eccezioni.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Classe di test per la classe {@link Bacheca}.
 * Testa tutti i metodi pubblici e i casi limite.
 * 
 * @author Pietro Mezzatesta
 */
class BachecaTest {

    private Bacheca bacheca;
    private Utente utente1;
    private Utente utente2;
    private AnnuncioVendita annuncioVendita;
    private AnnuncioAcquisto annuncioAcquisto;
    private Set<String> paroleChiave;

    @BeforeEach
    void setUp() throws DataScadenzaNonValidaException {
        bacheca = new Bacheca();
        utente1 = new Utente("test1@example.com", "Mario", "Rossi", "Password123!");
        utente2 = new Utente("test2@example.com", "Luigi", "Bianchi", "Password456!");
        
        paroleChiave = new HashSet<>(Arrays.asList("elettronica", "smartphone", "android"));
        
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
    }

    @AfterEach
    void tearDown() {
        bacheca = null;
        utente1 = null;
        utente2 = null;
        annuncioVendita = null;
        annuncioAcquisto = null;
        paroleChiave = null;
    }

    // Test Costruttore
    @Test
    void testCostruttore() {
        assertNotNull(bacheca);
        assertEquals(0, bacheca.getNumeroAnnunci());
        assertEquals(0, bacheca.getNumeroUtentiRegistrati());
    }

    // Test Registrazione Utenti
    @Test
    void testRegistraUtenteValido() throws UtenteGiaRegistratoException {
        bacheca.registraUtente(utente1);
        assertEquals(1, bacheca.getNumeroUtentiRegistrati());
        assertTrue(bacheca.isUtenteRegistrato(utente1.getEmail()));
    }

    @Test
    void testRegistraUtenteNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            bacheca.registraUtente(null);
        });
    }

    @Test
    void testRegistraUtenteDuplicato() throws UtenteGiaRegistratoException {
        bacheca.registraUtente(utente1);
        assertThrows(UtenteGiaRegistratoException.class, () -> {
            bacheca.registraUtente(utente1);
        });
    }

    @Test
    void testRegistraUtenteDuplicatoEmailDiversaCase() throws UtenteGiaRegistratoException {
        bacheca.registraUtente(utente1);
        Utente utenteEmailMaiuscola = new Utente("TEST1@EXAMPLE.COM", "Nome", "Cognome", "Passwordd123i@32");
        assertThrows(UtenteGiaRegistratoException.class, () -> {
            bacheca.registraUtente(utenteEmailMaiuscola);
        });
    }

    @Test
    void testIsUtenteRegistratoEmailNulla() {
        assertFalse(bacheca.isUtenteRegistrato(null));
    }

    @Test
    void testIsUtenteRegistratoEmailVuota() {
        assertFalse(bacheca.isUtenteRegistrato(""));
    }

    @Test
    void testIsUtenteRegistratoEmailSpazi() {
        assertFalse(bacheca.isUtenteRegistrato("   "));
    }

    @Test
    void testGetUtente() throws UtenteGiaRegistratoException {
        bacheca.registraUtente(utente1);
        Utente recuperato = bacheca.getUtente(utente1.getEmail());
        assertEquals(utente1, recuperato);
    }

    @Test
    void testGetUtenteInesistente() {
        assertNull(bacheca.getUtente("inesistente@example.com"));
    }

    @Test
    void testGetUtenteEmailNulla() {
        assertNull(bacheca.getUtente(null));
    }

    // Test Gestione Annunci
    @Test
    void testAggiungiAnnuncioVendita() {
        bacheca.aggiungiAnnuncio(annuncioVendita);
        assertEquals(1, bacheca.getNumeroAnnunci());
        assertNotNull(bacheca.getAnnuncioById(annuncioVendita.getId()));
    }

    @Test
    void testAggiungiAnnuncioAcquisto() {
        bacheca.aggiungiAnnuncio(annuncioAcquisto);
        assertEquals(1, bacheca.getNumeroAnnunci());
        assertNotNull(bacheca.getAnnuncioById(annuncioAcquisto.getId()));
    }

    @Test
    void testAggiungiAnnuncioNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            bacheca.aggiungiAnnuncio(null);
        });
    }

    @Test
    void testGetAnnuncioByIdInesistente() {
        assertNull(bacheca.getAnnuncioById("id-inesistente"));
    }

    @Test
    void testGetAnnuncioByIdNullo() {
        assertNull(bacheca.getAnnuncioById(null));
    }

    @Test
    void testGetAnnuncioByIdVuoto() {
        assertNull(bacheca.getAnnuncioById(""));
    }

    // Test Rimozione Annunci
    @Test
    void testRimuoviAnnuncioValido() throws AnnuncioNonTrovatoException, UtenteNonAutorizzatoException {
        bacheca.aggiungiAnnuncio(annuncioVendita);
        bacheca.rimuoviAnnuncio(annuncioVendita, utente1);
        assertEquals(0, bacheca.getNumeroAnnunci());
        assertNull(bacheca.getAnnuncioById(annuncioVendita.getId()));
    }

    @Test
    void testRimuoviAnnuncioNonAutorizzato() {
        bacheca.aggiungiAnnuncio(annuncioVendita);
        assertThrows(UtenteNonAutorizzatoException.class, () -> {
            bacheca.rimuoviAnnuncio(annuncioVendita, utente2);
        });
    }

    @Test
    void testRimuoviAnnuncioInesistente() {
        assertThrows(AnnuncioNonTrovatoException.class, () -> {
            bacheca.rimuoviAnnuncio(annuncioVendita, utente1);
        });
    }

    @Test
    void testRimuoviAnnuncioNullo() {
        assertThrows(AnnuncioNonTrovatoException.class, () -> {
            bacheca.rimuoviAnnuncio(null, utente1);
        });
    }

    @Test
    void testRimuoviAnnuncioUtenteNullo() {
        bacheca.aggiungiAnnuncio(annuncioVendita);
        assertThrows(UtenteNonAutorizzatoException.class, () -> {
            bacheca.rimuoviAnnuncio(annuncioVendita, null);
        });
    }

    // Test Ricerca Annunci
    @Test
    void testCercaAnnunciConRisultati() throws UtenteGiaRegistratoException {
        bacheca.registraUtente(utente1);
        bacheca.registraUtente(utente2);
        bacheca.aggiungiAnnuncio(annuncioVendita);
        
        Set<String> paroleRicerca = new HashSet<>(Arrays.asList("smartphone"));
        List<Annuncio> risultati = bacheca.cercaAnnunci(paroleRicerca, utente2);
        
        assertEquals(1, risultati.size());
        assertEquals(annuncioVendita, risultati.get(0));
    }

    @Test
    void testCercaAnnunciSenzaRisultati() throws UtenteGiaRegistratoException {
        bacheca.registraUtente(utente1);
        bacheca.registraUtente(utente2);
        bacheca.aggiungiAnnuncio(annuncioVendita);
        
        Set<String> paroleRicerca = new HashSet<>(Arrays.asList("automobile"));
        List<Annuncio> risultati = bacheca.cercaAnnunci(paroleRicerca, utente2);
        
        assertTrue(risultati.isEmpty());
    }

    @Test
    void testCercaAnnunciEscludePropri() throws UtenteGiaRegistratoException {
        bacheca.registraUtente(utente1);
        bacheca.aggiungiAnnuncio(annuncioVendita);
        
        Set<String> paroleRicerca = new HashSet<>(Arrays.asList("smartphone"));
        List<Annuncio> risultati = bacheca.cercaAnnunci(paroleRicerca, utente1);
        
        assertTrue(risultati.isEmpty()); // Non deve trovare i propri annunci
    }

    @Test
    void testCercaAnnunciEscludeAcquisti() throws UtenteGiaRegistratoException {
        bacheca.registraUtente(utente1);
        bacheca.registraUtente(utente2);
        bacheca.aggiungiAnnuncio(annuncioAcquisto); // Questo è un annuncio di acquisto
        
        Set<String> paroleRicerca = new HashSet<>(Arrays.asList("smartphone"));
        List<Annuncio> risultati = bacheca.cercaAnnunci(paroleRicerca, utente1);
        
        assertTrue(risultati.isEmpty()); // Non deve trovare annunci di acquisto
    }

    @Test
    void testCercaAnnunciParoleChiaveNulle() {
        List<Annuncio> risultati = bacheca.cercaAnnunci(null, utente1);
        assertTrue(risultati.isEmpty());
    }

    @Test
    void testCercaAnnunciParoleChiaveVuote() {
        List<Annuncio> risultati = bacheca.cercaAnnunci(new HashSet<>(), utente1);
        assertTrue(risultati.isEmpty());
    }

    @Test
    void testCercaAnnunciUtenteNullo() {
        Set<String> paroleRicerca = new HashSet<>(Arrays.asList("test"));
        assertThrows(IllegalArgumentException.class, () -> {
            bacheca.cercaAnnunci(paroleRicerca, null);
        });
    }

    @Test
    void testCercaAnnunciCaseSensitive() throws UtenteGiaRegistratoException {
        bacheca.registraUtente(utente1);
        bacheca.registraUtente(utente2);
        bacheca.aggiungiAnnuncio(annuncioVendita);
        
        Set<String> paroleRicerca = new HashSet<>(Arrays.asList("SMARTPHONE"));
        List<Annuncio> risultati = bacheca.cercaAnnunci(paroleRicerca, utente2);
        
        assertEquals(1, risultati.size()); // Deve trovare anche con case diverso
    }

    // Test Modifica Annunci
    @Test
    void testModificaAnnuncioValido() throws AnnuncioNonTrovatoException, UtenteNonAutorizzatoException {
        bacheca.aggiungiAnnuncio(annuncioVendita);
        
        String nuovoNome = "iPhone 14";
        double nuovoPrezzo = 900.0;
        Set<String> nuoveParoleChiave = new HashSet<>(Arrays.asList("apple", "nuovo"));
        
        try {
			bacheca.modificaAnnuncio(annuncioVendita.getId(), utente1, nuovoNome, nuovoPrezzo, nuoveParoleChiave, null);
		} catch (AnnuncioNonTrovatoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UtenteNonAutorizzatoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataScadenzaNonValidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Annuncio modificato = bacheca.getAnnuncioById(annuncioVendita.getId());
        assertEquals(nuovoNome, modificato.getNomeAnnuncio());
        assertEquals(nuovoPrezzo, modificato.getPrezzo());
        assertEquals(nuoveParoleChiave, modificato.getParoleChiave());
    }

    @Test
    void testModificaAnnuncioNonAutorizzato() {
        bacheca.aggiungiAnnuncio(annuncioVendita);
        
        assertThrows(UtenteNonAutorizzatoException.class, () -> {
            bacheca.modificaAnnuncio(annuncioVendita.getId(), utente2, "Nuovo Nome", 100.0, new HashSet<>(), null);
        });
    }

    @Test
    void testModificaAnnuncioInesistente() {
        assertThrows(AnnuncioNonTrovatoException.class, () -> {
            bacheca.modificaAnnuncio("id-inesistente", utente1, "Nome", 100.0, new HashSet<>(), null);
        });
    }

    @Test
    void testModificaAnnuncioIdNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            bacheca.modificaAnnuncio(null, utente1, "Nome", 100.0, new HashSet<>(), null);
        });
    }

    @Test
    void testModificaAnnuncioUtenteNullo() {
        bacheca.aggiungiAnnuncio(annuncioVendita);
        
        assertThrows(UtenteNonAutorizzatoException.class, () -> {
            bacheca.modificaAnnuncio(annuncioVendita.getId(), null, "Nome", 100.0, new HashSet<>(), null);
        });
    }

    // Test Pulizia Bacheca
    @Test
    void testPulisciBachecaSenzaAnnunciScaduti() {
        bacheca.aggiungiAnnuncio(annuncioVendita); // Non scaduto
        bacheca.aggiungiAnnuncio(annuncioAcquisto); // Gli acquisti non scadono
        
        int rimossi = bacheca.pulisciBacheca();
        
        assertEquals(0, rimossi);
        assertEquals(2, bacheca.getNumeroAnnunci());
    }

    @Test
    void testPulisciBachecaConAnnunciScaduti() throws DataScadenzaNonValidaException {
        // Il problema è che non possiamo creare un annuncio con data nel passato
        // quindi creiamo un annuncio con una classe anonima che simula la scadenza
        Set<String> paroleTest = new HashSet<>(Arrays.asList("test"));
        
        AnnuncioVendita annuncioScaduto = new AnnuncioVendita(
            utente1, 
            "Prodotto Scaduto", 
            100.0, 
            paroleTest, 
            LocalDate.now().plusDays(1) // Data valida per il costruttore
        ) {
            @Override
            public boolean isScaduto() {
                return true; // Forza la scadenza per il test
            }
        };
        
        bacheca.aggiungiAnnuncio(annuncioVendita); // Non scaduto
        bacheca.aggiungiAnnuncio(annuncioScaduto); // Scaduto
        
        int rimossi = bacheca.pulisciBacheca();
        
        assertEquals(1, rimossi);
        assertEquals(1, bacheca.getNumeroAnnunci());
        assertNotNull(bacheca.getAnnuncioById(annuncioVendita.getId()));
        assertNull(bacheca.getAnnuncioById(annuncioScaduto.getId()));
    }

    @Test
    void testPulisciBachecaVuota() {
        int rimossi = bacheca.pulisciBacheca();
        assertEquals(0, rimossi);
    }

    // Test Gestione Annunci per Autore
    @Test
    void testGetAnnunciPerAutore() {
        bacheca.aggiungiAnnuncio(annuncioVendita); // Autore: utente1
        bacheca.aggiungiAnnuncio(annuncioAcquisto); // Autore: utente2
        
        List<Annuncio> annunciUtente1 = bacheca.getAnnunciPerAutore(utente1);
        List<Annuncio> annunciUtente2 = bacheca.getAnnunciPerAutore(utente2);
        
        assertEquals(1, annunciUtente1.size());
        assertEquals(annuncioVendita, annunciUtente1.get(0));
        
        assertEquals(1, annunciUtente2.size());
        assertEquals(annuncioAcquisto, annunciUtente2.get(0));
    }

    @Test
    void testGetAnnunciPerAutoreNessunAnnuncio() {
        List<Annuncio> annunci = bacheca.getAnnunciPerAutore(utente1);
        assertTrue(annunci.isEmpty());
    }

    @Test
    void testGetAnnunciPerAutoreNullo() {
        List<Annuncio> annunci = bacheca.getAnnunciPerAutore(null);
        assertTrue(annunci.isEmpty());
    }

    // Test Getter Collections
    @Test
    void testGetUtentiRegistrati() throws UtenteGiaRegistratoException {
        bacheca.registraUtente(utente1);
        bacheca.registraUtente(utente2);
        
        Map<String, Utente> utenti = bacheca.getUtentiRegistrati();
        assertEquals(2, utenti.size());
        assertTrue(utenti.containsKey(utente1.getEmail()));
        assertTrue(utenti.containsKey(utente2.getEmail()));
        
        // Verifica che sia una copia (modifica non deve influenzare l'originale)
        utenti.clear();
        assertEquals(2, bacheca.getNumeroUtentiRegistrati());
    }

    @Test
    void testGetAnnunci() {
        bacheca.aggiungiAnnuncio(annuncioVendita);
        bacheca.aggiungiAnnuncio(annuncioAcquisto);
        
        Map<String, Annuncio> annunci = bacheca.getAnnunci();
        assertEquals(2, annunci.size());
        assertTrue(annunci.containsKey(annuncioVendita.getId()));
        assertTrue(annunci.containsKey(annuncioAcquisto.getId()));
        
        // Verifica che sia una copia (modifica non deve influenzare l'originale)
        annunci.clear();
        assertEquals(2, bacheca.getNumeroAnnunci());
    }

    // Test SetUtenti
    @Test
    void testSetUtenti() {
        Map<String, Utente> nuoviUtenti = new HashMap<>();
        nuoviUtenti.put(utente1.getEmail(), utente1);
        nuoviUtenti.put(utente2.getEmail(), utente2);
        
        bacheca.setUtenti(nuoviUtenti);
        
        assertEquals(2, bacheca.getNumeroUtentiRegistrati());
        assertTrue(bacheca.isUtenteRegistrato(utente1.getEmail()));
        assertTrue(bacheca.isUtenteRegistrato(utente2.getEmail()));
    }

    @Test
    void testSetUtentiNullo() {
        bacheca.setUtenti(null);
        assertEquals(0, bacheca.getNumeroUtentiRegistrati());
    }

    @Test
    void testSetUtentiConEmailMaiuscole() {
        Map<String, Utente> nuoviUtenti = new HashMap<>();
        nuoviUtenti.put("TEST1@EXAMPLE.COM", utente1);
        
        bacheca.setUtenti(nuoviUtenti);
        
        assertTrue(bacheca.isUtenteRegistrato("test1@example.com"));
        assertTrue(bacheca.isUtenteRegistrato("TEST1@EXAMPLE.COM"));
    }

    // Test Iterator
    @Test
    void testIterator() {
        bacheca.aggiungiAnnuncio(annuncioVendita);
        bacheca.aggiungiAnnuncio(annuncioAcquisto);
        
        List<Annuncio> annunciIterati = new ArrayList<>();
        for (Annuncio annuncio : bacheca) {
            annunciIterati.add(annuncio);
        }
        
        assertEquals(2, annunciIterati.size());
        assertTrue(annunciIterati.contains(annuncioVendita));
        assertTrue(annunciIterati.contains(annuncioAcquisto));
    }

    @Test
    void testIteratorBachecaVuota() {
        List<Annuncio> annunciIterati = new ArrayList<>();
        for (Annuncio annuncio : bacheca) {
            annunciIterati.add(annuncio);
        }
        assertTrue(annunciIterati.isEmpty());
    }

    // Test Contatori
    @Test
    void testGetNumeroAnnunci() {
        assertEquals(0, bacheca.getNumeroAnnunci());
        
        bacheca.aggiungiAnnuncio(annuncioVendita);
        assertEquals(1, bacheca.getNumeroAnnunci());
        
        bacheca.aggiungiAnnuncio(annuncioAcquisto);
        assertEquals(2, bacheca.getNumeroAnnunci());
    }

    @Test
    void testGetNumeroUtentiRegistrati() throws UtenteGiaRegistratoException {
        assertEquals(0, bacheca.getNumeroUtentiRegistrati());
        
        bacheca.registraUtente(utente1);
        assertEquals(1, bacheca.getNumeroUtentiRegistrati());
        
        bacheca.registraUtente(utente2);
        assertEquals(2, bacheca.getNumeroUtentiRegistrati());
    }

    // Test Edge Cases
    @Test
    void testRicercaConParoleChiaveConSpazi() throws UtenteGiaRegistratoException {
        bacheca.registraUtente(utente1);
        bacheca.registraUtente(utente2);
        bacheca.aggiungiAnnuncio(annuncioVendita);
        
        Set<String> paroleRicerca = new HashSet<>(Arrays.asList("  smartphone  ", "", null));
        List<Annuncio> risultati = bacheca.cercaAnnunci(paroleRicerca, utente2);
        
        assertEquals(1, risultati.size());
    }

    @Test 
    void testModificaAnnuncioConValoriNulli() throws AnnuncioNonTrovatoException, UtenteNonAutorizzatoException {
        bacheca.aggiungiAnnuncio(annuncioVendita);
        
        String nomeOriginale = annuncioVendita.getNomeAnnuncio();
        double prezzoOriginale = annuncioVendita.getPrezzo();
        Set<String> paroleChiaveOriginali = annuncioVendita.getParoleChiave();
        
        // Passa valori nulli - non dovrebbe modificare nulla
        try {
			bacheca.modificaAnnuncio(annuncioVendita.getId(), utente1, null, -1.0, null, null);
		} catch (AnnuncioNonTrovatoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UtenteNonAutorizzatoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataScadenzaNonValidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Annuncio annuncioModificato = bacheca.getAnnuncioById(annuncioVendita.getId());
        assertEquals(nomeOriginale, annuncioModificato.getNomeAnnuncio());
        assertEquals(prezzoOriginale, annuncioModificato.getPrezzo());
        assertEquals(paroleChiaveOriginali, annuncioModificato.getParoleChiave());
    }
}