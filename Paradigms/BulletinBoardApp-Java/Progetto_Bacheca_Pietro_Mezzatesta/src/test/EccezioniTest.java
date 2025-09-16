package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import eccezioni.*;

/**
 * Suite di test per tutte le classi di eccezione del progetto.
 * Testa i costruttori e l'ereditarietà delle eccezioni personalizzate.
 * 
 * @author Pietro Mezzatesta
 */
class EccezioniTest {

    // Test AnnuncioNonTrovatoException
    @Test
    void testAnnuncioNonTrovatoExceptionCostruttore() {
        String messaggio = "Annuncio non trovato";
        AnnuncioNonTrovatoException eccezione = new AnnuncioNonTrovatoException(messaggio);
        
        assertEquals(messaggio, eccezione.getMessage());
        assertTrue(eccezione instanceof Exception);
    }

    @Test
    void testAnnuncioNonTrovatoExceptionMessaggioNullo() {
        AnnuncioNonTrovatoException eccezione = new AnnuncioNonTrovatoException(null);
        assertNull(eccezione.getMessage());
    }

    @Test
    void testAnnuncioNonTrovatoExceptionMessaggioVuoto() {
        String messaggio = "";
        AnnuncioNonTrovatoException eccezione = new AnnuncioNonTrovatoException(messaggio);
        assertEquals(messaggio, eccezione.getMessage());
    }

    @Test
    void testAnnuncioNonTrovatoExceptionSerializable() {
        AnnuncioNonTrovatoException eccezione = new AnnuncioNonTrovatoException("Test");
        assertNotNull(eccezione);
        // Le eccezioni implementano automaticamente Serializable
        assertTrue(eccezione instanceof java.io.Serializable);
    }

    // Test DataScadenzaNonValidaException
    @Test
    void testDataScadenzaNonValidaExceptionCostruttore() {
        String messaggio = "Data di scadenza non valida";
        DataScadenzaNonValidaException eccezione = new DataScadenzaNonValidaException(messaggio);
        
        assertEquals(messaggio, eccezione.getMessage());
        assertTrue(eccezione instanceof Exception);
    }

    @Test
    void testDataScadenzaNonValidaExceptionMessaggioNullo() {
        DataScadenzaNonValidaException eccezione = new DataScadenzaNonValidaException(null);
        assertNull(eccezione.getMessage());
    }

    @Test
    void testDataScadenzaNonValidaExceptionMessaggioVuoto() {
        String messaggio = "";
        DataScadenzaNonValidaException eccezione = new DataScadenzaNonValidaException(messaggio);
        assertEquals(messaggio, eccezione.getMessage());
    }

    @Test
    void testDataScadenzaNonValidaExceptionSerializable() {
        DataScadenzaNonValidaException eccezione = new DataScadenzaNonValidaException("Test");
        assertTrue(eccezione instanceof java.io.Serializable);
    }

    // Test UtenteGiaRegistratoException
    @Test
    void testUtenteGiaRegistratoExceptionCostruttore() {
        String messaggio = "Utente già registrato";
        UtenteGiaRegistratoException eccezione = new UtenteGiaRegistratoException(messaggio);
        
        assertEquals(messaggio, eccezione.getMessage());
        assertTrue(eccezione instanceof Exception);
    }

    @Test
    void testUtenteGiaRegistratoExceptionMessaggioNullo() {
        UtenteGiaRegistratoException eccezione = new UtenteGiaRegistratoException(null);
        assertNull(eccezione.getMessage());
    }

    @Test
    void testUtenteGiaRegistratoExceptionMessaggioVuoto() {
        String messaggio = "";
        UtenteGiaRegistratoException eccezione = new UtenteGiaRegistratoException(messaggio);
        assertEquals(messaggio, eccezione.getMessage());
    }

    @Test
    void testUtenteGiaRegistratoExceptionSerializable() {
        UtenteGiaRegistratoException eccezione = new UtenteGiaRegistratoException("Test");
        assertTrue(eccezione instanceof java.io.Serializable);
    }

    // Test UtenteNonAutorizzatoException
    @Test
    void testUtenteNonAutorizzatoExceptionCostruttore() {
        String messaggio = "Utente non autorizzato";
        UtenteNonAutorizzatoException eccezione = new UtenteNonAutorizzatoException(messaggio);
        
        assertEquals(messaggio, eccezione.getMessage());
        assertTrue(eccezione instanceof Exception);
    }

    @Test
    void testUtenteNonAutorizzatoExceptionMessaggioNullo() {
        UtenteNonAutorizzatoException eccezione = new UtenteNonAutorizzatoException(null);
        assertNull(eccezione.getMessage());
    }

    @Test
    void testUtenteNonAutorizzatoExceptionMessaggioVuoto() {
        String messaggio = "";
        UtenteNonAutorizzatoException eccezione = new UtenteNonAutorizzatoException(messaggio);
        assertEquals(messaggio, eccezione.getMessage());
    }

    @Test
    void testUtenteNonAutorizzatoExceptionSerializable() {
        UtenteNonAutorizzatoException eccezione = new UtenteNonAutorizzatoException("Test");
        assertTrue(eccezione instanceof java.io.Serializable);
    }

    // Test UtenteNonTrovatoException
    @Test
    void testUtenteNonTrovatoExceptionCostruttore() {
        String messaggio = "Utente non trovato";
        UtenteNonTrovatoException eccezione = new UtenteNonTrovatoException(messaggio);
        
        assertEquals(messaggio, eccezione.getMessage());
        assertTrue(eccezione instanceof Exception);
    }

    @Test
    void testUtenteNonTrovatoExceptionMessaggioNullo() {
        UtenteNonTrovatoException eccezione = new UtenteNonTrovatoException(null);
        assertNull(eccezione.getMessage());
    }

    @Test
    void testUtenteNonTrovatoExceptionMessaggioVuoto() {
        String messaggio = "";
        UtenteNonTrovatoException eccezione = new UtenteNonTrovatoException(messaggio);
        assertEquals(messaggio, eccezione.getMessage());
    }

    @Test
    void testUtenteNonTrovatoExceptionSerializable() {
        UtenteNonTrovatoException eccezione = new UtenteNonTrovatoException("Test");
        assertTrue(eccezione instanceof java.io.Serializable);
    }

    // Test di lancio e cattura delle eccezioni
    @Test
    void testLancioAnnuncioNonTrovatoException() {
        assertThrows(AnnuncioNonTrovatoException.class, () -> {
            throw new AnnuncioNonTrovatoException("Test lancio eccezione");
        });
    }

    @Test
    void testLancioDataScadenzaNonValidaException() {
        assertThrows(DataScadenzaNonValidaException.class, () -> {
            throw new DataScadenzaNonValidaException("Test lancio eccezione");
        });
    }

    @Test
    void testLancioUtenteGiaRegistratoException() {
        assertThrows(UtenteGiaRegistratoException.class, () -> {
            throw new UtenteGiaRegistratoException("Test lancio eccezione");
        });
    }

    @Test
    void testLancioUtenteNonAutorizzatoException() {
        assertThrows(UtenteNonAutorizzatoException.class, () -> {
            throw new UtenteNonAutorizzatoException("Test lancio eccezione");
        });
    }

    @Test
    void testLancioUtenteNonTrovatoException() {
        assertThrows(UtenteNonTrovatoException.class, () -> {
            throw new UtenteNonTrovatoException("Test lancio eccezione");
        });
    }

    // Test cattura di Exception generica
    @Test
    void testCatturaEccezioneGenerica() {
        try {
            throw new AnnuncioNonTrovatoException("Test");
        } catch (Exception e) {
            assertTrue(e instanceof AnnuncioNonTrovatoException);
            assertEquals("Test", e.getMessage());
        }
    }

    // Test instanceof per tutte le eccezioni
    @Test
    void testInstanceOfTutteEccezioni() {
        Exception[] eccezioni = {
            new AnnuncioNonTrovatoException("test"),
            new DataScadenzaNonValidaException("test"),
            new UtenteGiaRegistratoException("test"),
            new UtenteNonAutorizzatoException("test"),
            new UtenteNonTrovatoException("test")
        };
        
        for (Exception e : eccezioni) {
            assertTrue(e instanceof Exception);
            assertTrue(e instanceof Throwable);
            assertTrue(e instanceof java.io.Serializable);
        }
    }

    // Test getMessage con stringhe lunghe
    @Test
    void testMessaggiLunghi() {
        String messaggioLungo = "Questo è un messaggio molto lungo ".repeat(100);
        
        AnnuncioNonTrovatoException eccezione = new AnnuncioNonTrovatoException(messaggioLungo);
        assertEquals(messaggioLungo, eccezione.getMessage());
        assertTrue(eccezione.getMessage().length() > 1000);
    }

    // Test caratteri speciali nei messaggi
    @Test
    void testCaratteriSpecialiNeiMessaggi() {
        String messaggioSpeciale = "Errore con àccèntì, émojì: 🚫, e símbolí: @#$%^&*()";
        
        UtenteNonTrovatoException eccezione = new UtenteNonTrovatoException(messaggioSpeciale);
        assertEquals(messaggioSpeciale, eccezione.getMessage());
    }

    // Test causa delle eccezioni (anche se non implementata nei costruttori attuali)
    @Test
    void testCausaNulla() {
        AnnuncioNonTrovatoException eccezione = new AnnuncioNonTrovatoException("Test");
        assertNull(eccezione.getCause());
    }

    // Test stack trace
    @Test
    void testStackTrace() {
        AnnuncioNonTrovatoException eccezione = new AnnuncioNonTrovatoException("Test");
        StackTraceElement[] stackTrace = eccezione.getStackTrace();
        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
    }

    // Test toString delle eccezioni
    @Test
    void testToStringEccezioni() {
        String messaggio = "Messaggio di test";
        AnnuncioNonTrovatoException eccezione = new AnnuncioNonTrovatoException(messaggio);
        
        String toString = eccezione.toString();
        assertTrue(toString.contains("AnnuncioNonTrovatoException"));
        assertTrue(toString.contains(messaggio));
    }
}