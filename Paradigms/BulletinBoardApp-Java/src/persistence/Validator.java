package persistence;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Classe utility per la validazione di email e password.
 * Fornisce metodi statici per verificare la conformità di email e password
 * secondo criteri prestabiliti.
 * 
 * @author Pietro Mezzatesta
 */
public class Validator {
    
    // Regex per validazione email
    private static final String EMAIL_REGEX = 
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    // Criteri password:
    // - Minimo 8 caratteri
    // - Almeno una lettera maiuscola
    // - Almeno una lettera minuscola
    // - Almeno un numero
    // - Almeno un carattere speciale (@$!%*?&)
    private static final String PASSWORD_REGEX = 
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    
    /**
     * Valida un indirizzo email.
     * 
     * @param email L'email da validare
     * @return true se l'email è valida, false altrimenti
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        
        Matcher matcher = EMAIL_PATTERN.matcher(email.trim());
        return matcher.matches();
    }
    
    /**
     * Valida una password secondo i criteri di sicurezza.
     * La password deve contenere:
     * - Almeno 8 caratteri
     * - Almeno una lettera maiuscola
     * - Almeno una lettera minuscola
     * - Almeno un numero
     * - Almeno un carattere speciale (@$!%*?&)
     * 
     * @param password La password da validare
     * @return true se la password è valida, false altrimenti
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }
    
    /**
     * Fornisce una descrizione dei requisiti per una password valida.
     * 
     * @return String con i requisiti della password
     */
    public static String getPasswordRequirements() {
        return "La password deve contenere:\n" +
               "- Almeno 8 caratteri\n" +
               "- Almeno una lettera maiuscola\n" +
               "- Almeno una lettera minuscola\n" +
               "- Almeno un numero\n" +
               "- Almeno un carattere speciale (@$!%*?&)";
    }
    
    /**
     * Verifica la forza di una password.
     * 
     * @param password La password da verificare
     * @return Un valore da 0 (debole) a 4 (molto forte)
     */
    public static int getPasswordStrength(String password) {
        if (password == null || password.isEmpty()) {
            return 0;
        }
        
        int strength = 0;
        
        // Lunghezza
        if (password.length() >= 8) strength++;
        if (password.length() >= 12) strength++;
        
        // Varietà di caratteri
        if (password.matches(".*[a-z].*")) strength++;
        if (password.matches(".*[A-Z].*")) strength++;
        if (password.matches(".*\\d.*")) strength++;
        if (password.matches(".*[@$!%*?&#].*")) strength++;
        
        return Math.min(strength, 4);
    }
    
    /**
     * Converte il livello di forza della password in una stringa descrittiva.
     * 
     * @param strength Il livello di forza (0-4)
     * @return Descrizione testuale della forza
     */
    public static String getPasswordStrengthDescription(int strength) {
        switch (strength) {
            case 0: return "Molto debole";
            case 1: return "Debole";
            case 2: return "Media";
            case 3: return "Forte";
            case 4: return "Molto forte";
            default: return "Non valida";
        }
    }
}