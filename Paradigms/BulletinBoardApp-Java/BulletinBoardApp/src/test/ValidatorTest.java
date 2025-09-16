package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import persistence.Validator;

/**
 * Classe di test per Validator.
 * Testa la validazione di email e password con regex.
 * 
 * @author Pietro Mezzatesta
 */
class ValidatorTest {

    // Test Email Valide
    @Test
    void testEmailValide() {
        String[] emailValide = {
            "test@example.com",
            "user.name@example.com",
            "user+tag@example.co.uk",
            "test_email@example-domain.com",
            "123@example.com",
            "a@b.co"
        };
        
        for (String email : emailValide) {
            assertTrue(Validator.isValidEmail(email), 
                "Email valida non riconosciuta: " + email);
        }
    }
    
    // Test Email Non Valide
    @Test
    void testEmailNonValide() {
        String[] emailNonValide = {
            null,
            "",
            "   ",
            "invalid",
            "@example.com",
            "test@",
            "test@.com",
            "test..email@example.com",
            "test@example",
            "test @example.com",
            "test@exam ple.com"
        };
        
        for (String email : emailNonValide) {
            assertFalse(Validator.isValidEmail(email), 
                "Email non valida accettata: " + email);
        }
    }
    
    // Test Password Valide
    @Test
    void testPasswordValide() {
        String[] passwordValide = {
            "Password1!",
            "Abc123@def",
            "MyP@ssw0rd",
            "Str0ng&Pass",
            "Valid1$Pass"
        };
        
        for (String password : passwordValide) {
            assertTrue(Validator.isValidPassword(password), 
                "Password valida non riconosciuta: " + password);
        }
    }
    
    // Test Password Non Valide
    @Test
    void testPasswordNonValide() {
        String[] passwordNonValide = {
            null,
            "",
            "short",
            "NoNumber!",
            "nouppercase1!",
            "NOLOWERCASE1!",
            "NoSpecialChar1",
            "12345678",
            "password",
            "Password",
            "Password1",
            "password!"
        };
        
        for (String password : passwordNonValide) {
            assertFalse(Validator.isValidPassword(password), 
                "Password non valida accettata: " + password);
        }
    }
    
    // Test Password Strength
    @Test
    void testPasswordStrength() {
        assertEquals(0, Validator.getPasswordStrength(null));
        assertEquals(0, Validator.getPasswordStrength(""));
        assertEquals(1, Validator.getPasswordStrength("weak"));
        assertEquals(3, Validator.getPasswordStrength("Passrd1"));
        assertEquals(4, Validator.getPasswordStrength("Str0ng@Password"));
        assertEquals(4, Validator.getPasswordStrength("VeryStr0ng@Password123"));
    }
    
    // Test Password Strength Description
    @Test
    void testPasswordStrengthDescription() {
        assertEquals("Molto debole", Validator.getPasswordStrengthDescription(0));
        assertEquals("Debole", Validator.getPasswordStrengthDescription(1));
        assertEquals("Media", Validator.getPasswordStrengthDescription(2));
        assertEquals("Forte", Validator.getPasswordStrengthDescription(3));
        assertEquals("Molto forte", Validator.getPasswordStrengthDescription(4));
    }
    
    // Test Password Requirements
    @Test
    void testPasswordRequirements() {
        String requirements = Validator.getPasswordRequirements();
        assertNotNull(requirements);
        assertTrue(requirements.contains("8 caratteri"));
        assertTrue(requirements.contains("maiuscola"));
        assertTrue(requirements.contains("minuscola"));
        assertTrue(requirements.contains("numero"));
        assertTrue(requirements.contains("speciale"));
    }
}