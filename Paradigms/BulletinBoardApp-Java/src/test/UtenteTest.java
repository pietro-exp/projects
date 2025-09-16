package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import dominio.Utente;

/**
 * Classe di test per la classe {@link Utente}.
 * Testa tutti i metodi pubblici, i costruttori e i casi limite.
 * 
 * @author Pietro Mezzatesta
 */
class UtenteTest {

    private Utente utente;
    private String emailTest;
    private String nomeTest;
    private String cognomeTest;
    private String passwordTest;

    @BeforeEach
    void setUp() {
        emailTest = "test@example.com";
        nomeTest = "Mario";
        cognomeTest = "Rossi";
        passwordTest = "Password123!";
        utente = new Utente(emailTest, nomeTest, cognomeTest, passwordTest);
    }

    @AfterEach
    void tearDown() {
        utente = null;
    }

    // Test Costruttore
    @Test
    void testCostruttoreValido() {
        assertNotNull(utente);
        assertEquals(emailTest.toLowerCase(), utente.getEmail());
        assertEquals(nomeTest, utente.getNome());
        assertEquals(cognomeTest, utente.getCognome());
        assertTrue(utente.checkPassword(passwordTest));
    }

    @Test
    void testCostruttoreEmailNulla() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente(null, nomeTest, cognomeTest, passwordTest);
        });
    }

    @Test
    void testCostruttoreEmailVuota() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente("", nomeTest, cognomeTest, passwordTest);
        });
    }

    @Test
    void testCostruttoreEmailSpaziBianchi() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente("   ", nomeTest, cognomeTest, passwordTest);
        });
    }

    @Test
    void testCostruttoreNomeNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente(emailTest, null, cognomeTest, passwordTest);
        });
    }

    @Test
    void testCostruttoreNomeVuoto() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente(emailTest, "", cognomeTest, passwordTest);
        });
    }

    @Test
    void testCostruttoreCognomeNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente(emailTest, nomeTest, null, passwordTest);
        });
    }

    @Test
    void testCostruttoreCognomeVuoto() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente(emailTest, nomeTest, "", passwordTest);
        });
    }

    @Test
    void testCostruttorePasswordNulla() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente(emailTest, nomeTest, cognomeTest, null);
        });
    }

    @Test
    void testCostruttorePasswordVuota() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Utente(emailTest, nomeTest, cognomeTest, "");
        });
    }

    @Test
    void testEmailNormalizzata() {
        Utente utenteEmailMaiuscola = new Utente("TEST@EXAMPLE.COM", nomeTest, cognomeTest, passwordTest);
        assertEquals("test@example.com", utenteEmailMaiuscola.getEmail());
    }

    @Test
    void testEmailConSpazi() {
        Utente utenteEmailSpazi = new Utente("  test@example.com  ", nomeTest, cognomeTest, passwordTest);
        assertEquals("test@example.com", utenteEmailSpazi.getEmail());
    }

    // Test Getter
    @Test
    void testGetNome() {
        assertEquals(nomeTest, utente.getNome());
    }

    @Test
    void testGetCognome() {
        assertEquals(cognomeTest, utente.getCognome());
    }

    @Test
    void testGetEmail() {
        assertEquals(emailTest.toLowerCase(), utente.getEmail());
    }

    // Test Setter
    @Test
    void testSetNomeValido() {
        String nuovoNome = "Luigi";
        utente.setNome(nuovoNome);
        assertEquals(nuovoNome, utente.getNome());
    }

    @Test
    void testSetNomeConSpazi() {
        String nuovoNome = "  Luigi  ";
        utente.setNome(nuovoNome);
        assertEquals("Luigi", utente.getNome());
    }

    @Test
    void testSetNomeNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            utente.setNome(null);
        });
    }

    @Test
    void testSetNomeVuoto() {
        assertThrows(IllegalArgumentException.class, () -> {
            utente.setNome("");
        });
    }

    @Test
    void testSetCognomeValido() {
        String nuovoCognome = "Bianchi";
        utente.setCognome(nuovoCognome);
        assertEquals(nuovoCognome, utente.getCognome());
    }

    @Test
    void testSetCognomeConSpazi() {
        String nuovoCognome = "  Bianchi  ";
        utente.setCognome(nuovoCognome);
        assertEquals("Bianchi", utente.getCognome());
    }

    @Test
    void testSetCognomeNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            utente.setCognome(null);
        });
    }

    @Test
    void testSetCognomeVuoto() {
        assertThrows(IllegalArgumentException.class, () -> {
            utente.setCognome("");
        });
    }

    // Test CheckPassword
    @Test
    void testCheckPasswordCorretta() {
        assertTrue(utente.checkPassword(passwordTest));
    }

    @Test
    void testCheckPasswordScorretta() {
        assertFalse(utente.checkPassword("passwordSbagliata"));
    }

    @Test
    void testCheckPasswordNulla() {
        assertFalse(utente.checkPassword(null));
    }

    @Test
    void testCheckPasswordVuota() {
        assertFalse(utente.checkPassword(""));
    }

    @Test
    void testCheckPasswordCaseSensitive() {
        assertFalse(utente.checkPassword(passwordTest.toUpperCase()));
    }

    // Test Equals e HashCode
    @Test
    void testEqualsStessoOggetto() {
        assertTrue(utente.equals(utente));
    }

    @Test
    void testEqualsOggettoNullo() {
        assertFalse(utente.equals(null));
    }

    @Test
    void testEqualsClasseDiversa() {
        assertFalse(utente.equals("stringa"));
    }

    @Test
    void testEqualsStessaEmail() {
        Utente altroUtente = new Utente(emailTest, "AltroNome", "AltroCognome", "altraPassword13@");
        assertTrue(utente.equals(altroUtente));
    }

    @Test
    void testEqualsEmailDiversa() {
        Utente altroUtente = new Utente("altro@example.com", nomeTest, cognomeTest, passwordTest);
        assertFalse(utente.equals(altroUtente));
    }

    @Test
    void testHashCodeConsistente() {
        int hashCode1 = utente.hashCode();
        int hashCode2 = utente.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void testHashCodeUgualePerEmailUguale() {
        Utente altroUtente = new Utente(emailTest, "AltroNome", "AltroCognome", "altra%Password$23!");
        assertEquals(utente.hashCode(), altroUtente.hashCode());
    }

    // Test ToString
    @Test
    void testToString() {
        String risultato = utente.toString();
        assertTrue(risultato.contains(nomeTest));
        assertTrue(risultato.contains(cognomeTest));
        assertTrue(risultato.contains(emailTest.toLowerCase()));
        assertFalse(risultato.contains(passwordTest)); // La password non deve essere visibile
    }

    // Test Sicurezza Password
    @Test
    void testPasswordHashingConsistente() {
        String password = "testPassword123@";
        Utente utente1 = new Utente("test1@example.com", "Nome", "Cognome", password);
        Utente utente2 = new Utente("test2@example.com", "Nome", "Cognome", password);
        
        assertTrue(utente1.checkPassword(password));
        assertTrue(utente2.checkPassword(password));
        // Gli hash dovrebbero essere diversi a causa del salt diverso
        // (Non possiamo testare direttamente gli hash essendo privati)
    }

    @Test
    void testPasswordModificata() {
        assertTrue(utente.checkPassword(passwordTest));
        // Se la password fosse modificabile, dovremmo testare che quella vecchia non funzioni più
        // Ma nel nostro design la password è final, quindi questo test verifica solo la consistenza
    }
}