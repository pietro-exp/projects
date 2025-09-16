package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import dominio.*;
import java.util.*;

/**
 * Classe di test per la classe {@link AnnuncioAcquisto}.
 * Testa tutti i metodi pubblici, i costruttori e i casi limite.
 * 
 * @author Pietro Mezzatesta
 */
class AnnuncioAcquistoTest {

    private Utente autore;
    private String nomeAnnuncio;
    private double prezzo;
    private Set<String> paroleChiave;
    private AnnuncioAcquisto annuncio;

    @BeforeEach
    void setUp() {
        autore = new Utente("test@example.com", "Mario", "Rossi", "Password123!");
        nomeAnnuncio = "Samsung Galaxy";
        prezzo = 600.0;
        paroleChiave = new HashSet<>(Arrays.asList("smartphone", "android", "elettronica"));
        annuncio = new AnnuncioAcquisto(autore, nomeAnnuncio, prezzo, paroleChiave);
    }

    @AfterEach
    void tearDown() {
        annuncio = null;
        autore = null;
        paroleChiave = null;
    }

    // Test Costruttore
    @Test
    void testCostruttoreValido() {
        assertNotNull(annuncio);
        assertNotNull(annuncio.getId());
        assertEquals(autore, annuncio.getAutore());
        assertEquals(nomeAnnuncio, annuncio.getNomeAnnuncio());
        assertEquals(prezzo, annuncio.getPrezzo());
        assertEquals(paroleChiave, annuncio.getParoleChiave());
        assertFalse(annuncio.isScaduto()); // Gli annunci di acquisto non scadono mai
    }

    @Test
    void testCostruttoreAutoreNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AnnuncioAcquisto(null, nomeAnnuncio, prezzo, paroleChiave);
        });
    }

    @Test
    void testCostruttoreNomeNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AnnuncioAcquisto(autore, null, prezzo, paroleChiave);
        });
    }

    @Test
    void testCostruttoreNomeVuoto() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AnnuncioAcquisto(autore, "", prezzo, paroleChiave);
        });
    }

    @Test
    void testCostruttoreNomeSpazi() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AnnuncioAcquisto(autore, "   ", prezzo, paroleChiave);
        });
    }

    @Test
    void testCostruttorePrezzoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AnnuncioAcquisto(autore, nomeAnnuncio, -1.0, paroleChiave);
        });
    }

    @Test
    void testCostruttorePrezzoZero() {
        AnnuncioAcquisto annuncioGratis = new AnnuncioAcquisto(autore, "Regalo", 0.0, paroleChiave);
        assertEquals(0.0, annuncioGratis.getPrezzo());
    }

    @Test
    void testCostruttoreParoleChiaveNulle() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AnnuncioAcquisto(autore, nomeAnnuncio, prezzo, null);
        });
    }

    @Test
    void testCostruttoreParoleChiaveVuote() {
        Set<String> paroleVuote = new HashSet<>();
        AnnuncioAcquisto annuncioVuoto = new AnnuncioAcquisto(autore, nomeAnnuncio, prezzo, paroleVuote);
        assertTrue(annuncioVuoto.getParoleChiave().isEmpty());
    }

    @Test
    void testCostruttoreParoleChiaveConNull() {
        Set<String> paroleConNull = new HashSet<>(Arrays.asList("test", null, "valida"));
        AnnuncioAcquisto annuncioConNull = new AnnuncioAcquisto(autore, nomeAnnuncio, prezzo, paroleConNull);
        
        Set<String> paroleRisultato = annuncioConNull.getParoleChiave();
        assertEquals(2, paroleRisultato.size());
        assertTrue(paroleRisultato.contains("test"));
        assertTrue(paroleRisultato.contains("valida"));
        assertFalse(paroleRisultato.contains(null));
    }

    @Test
    void testCostruttoreParoleChiaveConSpazi() {
        Set<String> paroleConSpazi = new HashSet<>(Arrays.asList("  test  ", "", "  valida"));
        AnnuncioAcquisto annuncioConSpazi = new AnnuncioAcquisto(autore, nomeAnnuncio, prezzo, paroleConSpazi);
        
        Set<String> paroleRisultato = annuncioConSpazi.getParoleChiave();
        assertEquals(2, paroleRisultato.size());
        assertTrue(paroleRisultato.contains("test"));
        assertTrue(paroleRisultato.contains("valida"));
    }

    // Test Getter
    @Test
    void testGetId() {
        assertNotNull(annuncio.getId());
        assertFalse(annuncio.getId().isEmpty());
    }

    @Test
    void testGetAutore() {
        assertEquals(autore, annuncio.getAutore());
    }

    @Test
    void testGetNomeAnnuncio() {
        assertEquals(nomeAnnuncio, annuncio.getNomeAnnuncio());
    }

    @Test
    void testGetPrezzo() {
        assertEquals(prezzo, annuncio.getPrezzo());
    }

    @Test
    void testGetParoleChiave() {
        Set<String> paroleOttenute = annuncio.getParoleChiave();
        assertEquals(paroleChiave, paroleOttenute);
        
        // Verifica che sia una copia (modifica non deve influenzare l'originale)
        paroleOttenute.add("nuova");
        assertNotEquals(paroleOttenute, annuncio.getParoleChiave());
    }

    // Test Setter
    @Test
    void testSetNomeAnnuncioValido() {
        String nuovoNome = "iPhone 14";
        annuncio.setNomeAnnuncio(nuovoNome);
        assertEquals(nuovoNome, annuncio.getNomeAnnuncio());
    }

    @Test
    void testSetNomeAnnuncioConSpazi() {
        String nuovoNome = "  iPhone 14  ";
        annuncio.setNomeAnnuncio(nuovoNome);
        assertEquals("iPhone 14", annuncio.getNomeAnnuncio());
    }

    @Test
    void testSetNomeAnnuncioNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            annuncio.setNomeAnnuncio(null);
        });
    }

    @Test
    void testSetNomeAnnuncioVuoto() {
        assertThrows(IllegalArgumentException.class, () -> {
            annuncio.setNomeAnnuncio("");
        });
    }

    @Test
    void testSetPrezzoValido() {
        double nuovoPrezzo = 750.0;
        annuncio.setPrezzo(nuovoPrezzo);
        assertEquals(nuovoPrezzo, annuncio.getPrezzo());
    }

    @Test
    void testSetPrezzoZero() {
        annuncio.setPrezzo(0.0);
        assertEquals(0.0, annuncio.getPrezzo());
    }

    @Test
    void testSetPrezzoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            annuncio.setPrezzo(-1.0);
        });
    }

    @Test
    void testSetParoleChiaveValide() {
        Set<String> nuoveParole = new HashSet<>(Arrays.asList("iphone", "apple"));
        annuncio.setParoleChiave(nuoveParole);
        assertEquals(nuoveParole, annuncio.getParoleChiave());
    }

    @Test
    void testSetParoleChiaveNulle() {
        assertThrows(IllegalArgumentException.class, () -> {
            annuncio.setParoleChiave(null);
        });
    }

    @Test
    void testSetParoleChiaveConNull() {
        Set<String> paroleConNull = new HashSet<>(Arrays.asList("test", null, "valida"));
        annuncio.setParoleChiave(paroleConNull);
        
        Set<String> paroleRisultato = annuncio.getParoleChiave();
        assertEquals(2, paroleRisultato.size());
        assertFalse(paroleRisultato.contains(null));
    }

    // Test isScaduto - Caratteristica principale degli annunci di acquisto
    @Test
    void testIsScadutoSempreFalse() {
        assertFalse(annuncio.isScaduto());
        
        // Verifica che rimanga sempre false anche nel tempo
        // (simulazione di passaggio del tempo)
        assertFalse(annuncio.isScaduto());
    }

    @Test
    void testIsScadutoConsistente() {
        // L'annuncio di acquisto non deve mai scadere
        for (int i = 0; i < 100; i++) {
            assertFalse(annuncio.isScaduto());
        }
    }

    // Test Equals e HashCode
    @Test
    void testEqualsStessoOggetto() {
        assertTrue(annuncio.equals(annuncio));
    }

    @Test
    void testEqualsOggettoNullo() {
        assertFalse(annuncio.equals(null));
    }

    @Test
    void testEqualsClasseDiversa() {
        assertFalse(annuncio.equals("stringa"));
    }

    @Test
    void testEqualsDiversiAnnunci() {
        AnnuncioAcquisto altroAnnuncio = new AnnuncioAcquisto(autore, "Altro Prodotto", 500.0, paroleChiave);
        assertFalse(annuncio.equals(altroAnnuncio));
    }

    @Test
    void testHashCodeConsistente() {
        int hash1 = annuncio.hashCode();
        int hash2 = annuncio.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void testHashCodeDiversiAnnunci() {
        AnnuncioAcquisto altroAnnuncio = new AnnuncioAcquisto(autore, "Altro Prodotto", 500.0, paroleChiave);
        assertNotEquals(annuncio.hashCode(), altroAnnuncio.hashCode());
    }

    // Test ToString
    @Test
    void testToString() {
        String risultato = annuncio.toString();
        assertTrue(risultato.contains(annuncio.getId()));
        assertTrue(risultato.contains(autore.getEmail()));
        assertTrue(risultato.contains(nomeAnnuncio));
        assertTrue(risultato.contains(String.valueOf(prezzo)));
        // Per gli annunci di acquisto non dovrebbe contenere informazioni sulla scadenza
        assertFalse(risultato.contains("Scadenza"));
        assertFalse(risultato.contains("Vendita"));
    }

    // Test ID Univocità
    @Test
    void testIdUnivoci() {
        AnnuncioAcquisto altroAnnuncio = new AnnuncioAcquisto(autore, nomeAnnuncio, prezzo, paroleChiave);
        assertNotEquals(annuncio.getId(), altroAnnuncio.getId());
    }

    // Test confronto con AnnuncioVendita
    @Test
    void testDiversoDaAnnuncioVendita() throws Exception {
        // Creazione di un annuncio di vendita per confronto
        AnnuncioVendita annuncioVendita = new AnnuncioVendita(
            autore, nomeAnnuncio, prezzo, paroleChiave, 
            java.time.LocalDate.now().plusDays(30)
        );
        
        // Due tipi diversi di annuncio non devono essere uguali
        assertNotEquals(annuncio, annuncioVendita);
        assertNotEquals(annuncioVendita, annuncio);
        
        // Verifica comportamento isScaduto diverso
        assertFalse(annuncio.isScaduto()); // Acquisto non scade mai
        assertFalse(annuncioVendita.isScaduto()); // Vendita non ancora scaduta
    }

    // Test Immutabilità
    @Test
    void testAutoreReference() {
        Utente autoreOriginale = annuncio.getAutore();
        autoreOriginale.setNome("NuovoNome");
        // L'autore nell'annuncio deve rimanere lo stesso oggetto (reference)
        assertEquals("NuovoNome", annuncio.getAutore().getNome());
    }

    // Test Edge Cases
    @Test
    void testCostruttoreNomeConSoliSpazi() {
        String nomeConSpazi = "  Samsung Galaxy  ";
        AnnuncioAcquisto annuncioSpazi = new AnnuncioAcquisto(autore, nomeConSpazi, prezzo, paroleChiave);
        assertEquals("Samsung Galaxy", annuncioSpazi.getNomeAnnuncio());
    }

    @Test
    void testPrezzoMoltoAlto() {
        double prezzoAlto = Double.MAX_VALUE;
        AnnuncioAcquisto annuncioCostoso = new AnnuncioAcquisto(autore, nomeAnnuncio, prezzoAlto, paroleChiave);
        assertEquals(prezzoAlto, annuncioCostoso.getPrezzo());
    }

    @Test
    void testParoleChiaveMolteNumerose() {
        Set<String> molteParole = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            molteParole.add("parola" + i);
        }
        
        AnnuncioAcquisto annuncioMolteParole = new AnnuncioAcquisto(autore, nomeAnnuncio, prezzo, molteParole);
        assertEquals(1000, annuncioMolteParole.getParoleChiave().size());
    }

    @Test
    void testParoleChiaveCaratteriSpeciali() {
        Set<String> paroleSpeciali = new HashSet<>(Arrays.asList(
            "test@email.com", 
            "prezzo€500", 
            "spazio test",
            "àccènti",
            "123numeri"
        ));
        
        AnnuncioAcquisto annuncioSpeciale = new AnnuncioAcquisto(autore, nomeAnnuncio, prezzo, paroleSpeciali);
        assertEquals(paroleSpeciali, annuncioSpeciale.getParoleChiave());
    }

    // Test Prestazioni
    @Test
    void testCreazioneMoltiAnnunci() {
        Set<String> idGenerati = new HashSet<>();
        
        // Crea molti annunci e verifica che abbiano ID univoci
        for (int i = 0; i < 1000; i++) {
            AnnuncioAcquisto nuovoAnnuncio = new AnnuncioAcquisto(
                autore, 
                "Prodotto " + i, 
                100.0 + i, 
                paroleChiave
            );
            
            String id = nuovoAnnuncio.getId();
            assertNotNull(id);
            assertFalse(idGenerati.contains(id), "ID duplicato trovato: " + id);
            idGenerati.add(id);
        }
        
        assertEquals(1000, idGenerati.size());
    }
}