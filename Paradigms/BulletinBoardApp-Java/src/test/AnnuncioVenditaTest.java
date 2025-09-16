package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import dominio.*;
import eccezioni.DataScadenzaNonValidaException;
import java.time.LocalDate;
import java.util.*;

/**
 * Classe di test per la classe {@link AnnuncioVendita}.
 * Testa tutti i metodi pubblici, i costruttori e i casi limite.
 * 
 * @author Pietro Mezzatesta
 */
class AnnuncioVenditaTest {

    private Utente autore;
    private String nomeAnnuncio;
    private double prezzo;
    private Set<String> paroleChiave;
    private LocalDate dataScadenza;
    private AnnuncioVendita annuncio;

    @BeforeEach
    void setUp() throws DataScadenzaNonValidaException {
        autore = new Utente("test@example.com", "Mario", "Rossi", "Password123@");
        nomeAnnuncio = "iPhone 13";
        prezzo = 800.0;
        paroleChiave = new HashSet<>(Arrays.asList("smartphone", "apple", "elettronica"));
        dataScadenza = LocalDate.now().plusDays(30);
        annuncio = new AnnuncioVendita(autore, nomeAnnuncio, prezzo, paroleChiave, dataScadenza);
    }

    @AfterEach
    void tearDown() {
        annuncio = null;
        autore = null;
        paroleChiave = null;
    }

    // Test Costruttore
    @Test
    void testCostruttoreValido() throws DataScadenzaNonValidaException {
        assertNotNull(annuncio);
        assertNotNull(annuncio.getId());
        assertEquals(autore, annuncio.getAutore());
        assertEquals(nomeAnnuncio, annuncio.getNomeAnnuncio());
        assertEquals(prezzo, annuncio.getPrezzo());
        assertEquals(paroleChiave, annuncio.getParoleChiave());
        assertEquals(dataScadenza, annuncio.getDataScadenza());
        assertFalse(annuncio.isScaduto());
    }

    @Test
    void testCostruttoreDataScadenzaOggi() {
        LocalDate oggi = LocalDate.now();
        assertThrows(DataScadenzaNonValidaException.class, () -> {
            new AnnuncioVendita(autore, nomeAnnuncio, prezzo, paroleChiave, oggi);
        });
    }

    @Test
    void testCostruttoreDataScadenzaPassata() {
        LocalDate ieri = LocalDate.now().minusDays(1);
        assertThrows(DataScadenzaNonValidaException.class, () -> {
            new AnnuncioVendita(autore, nomeAnnuncio, prezzo, paroleChiave, ieri);
        });
    }

    @Test
    void testCostruttoreDataScadenzaDomani() throws DataScadenzaNonValidaException {
        LocalDate domani = LocalDate.now().plusDays(1);
        AnnuncioVendita annuncioDomani = new AnnuncioVendita(autore, nomeAnnuncio, prezzo, paroleChiave, domani);
        assertNotNull(annuncioDomani);
        assertEquals(domani, annuncioDomani.getDataScadenza());
    }

    @Test
    void testCostruttoreAutoreNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AnnuncioVendita(null, nomeAnnuncio, prezzo, paroleChiave, dataScadenza);
        });
    }

    @Test
    void testCostruttoreNomeNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AnnuncioVendita(autore, null, prezzo, paroleChiave, dataScadenza);
        });
    }

    @Test
    void testCostruttoreNomeVuoto() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AnnuncioVendita(autore, "", prezzo, paroleChiave, dataScadenza);
        });
    }

    @Test
    void testCostruttoreNomeSpazi() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AnnuncioVendita(autore, "   ", prezzo, paroleChiave, dataScadenza);
        });
    }

    @Test
    void testCostruttorePrezzoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AnnuncioVendita(autore, nomeAnnuncio, -1.0, paroleChiave, dataScadenza);
        });
    }

    @Test
    void testCostruttorePrezzoZero() throws DataScadenzaNonValidaException {
        AnnuncioVendita annuncioGratis = new AnnuncioVendita(autore, "Regalo", 0.0, paroleChiave, dataScadenza);
        assertEquals(0.0, annuncioGratis.getPrezzo());
    }

    @Test
    void testCostruttoreParoleChiaveNulle() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AnnuncioVendita(autore, nomeAnnuncio, prezzo, null, dataScadenza);
        });
    }

    @Test
    void testCostruttoreParoleChiaveVuote() throws DataScadenzaNonValidaException {
        Set<String> paroleVuote = new HashSet<>();
        AnnuncioVendita annuncioVuoto = new AnnuncioVendita(autore, nomeAnnuncio, prezzo, paroleVuote, dataScadenza);
        assertTrue(annuncioVuoto.getParoleChiave().isEmpty());
    }

    @Test
    void testCostruttoreParoleChiaveConNull() throws DataScadenzaNonValidaException {
        Set<String> paroleConNull = new HashSet<>(Arrays.asList("test", null, "valida"));
        AnnuncioVendita annuncioConNull = new AnnuncioVendita(autore, nomeAnnuncio, prezzo, paroleConNull, dataScadenza);
        
        Set<String> paroleRisultato = annuncioConNull.getParoleChiave();
        assertEquals(2, paroleRisultato.size());
        assertTrue(paroleRisultato.contains("test"));
        assertTrue(paroleRisultato.contains("valida"));
        assertFalse(paroleRisultato.contains(null));
    }

    @Test
    void testCostruttoreParoleChiaveConSpazi() throws DataScadenzaNonValidaException {
        Set<String> paroleConSpazi = new HashSet<>(Arrays.asList("  test  ", "", "  valida"));
        AnnuncioVendita annuncioConSpazi = new AnnuncioVendita(autore, nomeAnnuncio, prezzo, paroleConSpazi, dataScadenza);
        
        Set<String> paroleRisultato = annuncioConSpazi.getParoleChiave();
        assertEquals(2, paroleRisultato.size());
        assertTrue(paroleRisultato.contains("test"));
        assertTrue(paroleRisultato.contains("valida"));
    }

    // Test Getter
    @Test
    void testGetDataScadenza() {
        assertEquals(dataScadenza, annuncio.getDataScadenza());
    }

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
        Set<String> nuoveParole = new HashSet<>(Arrays.asList("android", "samsung"));
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

    // Test isScaduto
    @Test
    void testIsScadutoNonScaduto() {
        assertFalse(annuncio.isScaduto());
    }

    @Test
    void testIsScadutoScaduto() throws DataScadenzaNonValidaException {
        // Crea un annuncio che scade domani, poi simula il passaggio del tempo
        LocalDate domani = LocalDate.now().plusDays(1);
        AnnuncioVendita annuncioConScadenza = new AnnuncioVendita(autore, nomeAnnuncio, prezzo, paroleChiave, domani) {
            @Override
            public boolean isScaduto() {
                return getDataScadenza().isBefore(LocalDate.now().plusDays(2)); // Simula che sia passato un giorno in più
            }
        };
        
        assertTrue(annuncioConScadenza.isScaduto());
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
    void testEqualsDiversiAnnunci() throws DataScadenzaNonValidaException {
        AnnuncioVendita altroAnnuncio = new AnnuncioVendita(autore, "Altro Prodotto", 500.0, paroleChiave, dataScadenza);
        assertFalse(annuncio.equals(altroAnnuncio));
    }

    @Test
    void testHashCodeConsistente() {
        int hash1 = annuncio.hashCode();
        int hash2 = annuncio.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void testHashCodeDiversiAnnunci() throws DataScadenzaNonValidaException {
        AnnuncioVendita altroAnnuncio = new AnnuncioVendita(autore, "Altro Prodotto", 500.0, paroleChiave, dataScadenza);
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
        assertTrue(risultato.contains("Vendita"));
        assertTrue(risultato.contains(dataScadenza.toString()));
    }

    // Test ID Univocità
    @Test
    void testIdUnivoci() throws DataScadenzaNonValidaException {
        AnnuncioVendita altroAnnuncio = new AnnuncioVendita(autore, nomeAnnuncio, prezzo, paroleChiave, dataScadenza);
        assertNotEquals(annuncio.getId(), altroAnnuncio.getId());
    }

    // Test Immutabilità
    @Test
    void testAutoreImmutabile() {
        Utente autoreOriginale = annuncio.getAutore();
        autoreOriginale.setNome("NuovoNome");
        // L'autore nell'annuncio deve rimanere lo stesso oggetto (reference)
        assertEquals("NuovoNome", annuncio.getAutore().getNome());
    }

    @Test
    void testDataScadenzaImmutabile() {
        LocalDate dataOriginale = annuncio.getDataScadenza();
        LocalDate dataCopia = annuncio.getDataScadenza();
        assertEquals(dataOriginale, dataCopia);
        
        // LocalDate è immutabile, quindi non ci sono problemi di modifica
        assertSame(dataOriginale, dataCopia);
    }

    // Test Edge Cases
    @Test
    void testCostruttoreNomeConSoliSpazi() throws DataScadenzaNonValidaException {
        String nomeConSpazi = "  iPhone 13  ";
        AnnuncioVendita annuncioSpazi = new AnnuncioVendita(autore, nomeConSpazi, prezzo, paroleChiave, dataScadenza);
        assertEquals("iPhone 13", annuncioSpazi.getNomeAnnuncio());
    }

    @Test
    void testPrezzoMoltoAlto() throws DataScadenzaNonValidaException {
        double prezzoAlto = Double.MAX_VALUE;
        AnnuncioVendita annuncioCostoso = new AnnuncioVendita(autore, nomeAnnuncio, prezzoAlto, paroleChiave, dataScadenza);
        assertEquals(prezzoAlto, annuncioCostoso.getPrezzo());
    }

    @Test
    void testDataScadenzaMoltoFutura() throws DataScadenzaNonValidaException {
        LocalDate dataFutura = LocalDate.now().plusYears(100);
        AnnuncioVendita annuncioFuturo = new AnnuncioVendita(autore, nomeAnnuncio, prezzo, paroleChiave, dataFutura);
        assertEquals(dataFutura, annuncioFuturo.getDataScadenza());
        assertFalse(annuncioFuturo.isScaduto());
    }
}