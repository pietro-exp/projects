package persistence;

import dominio.Bacheca;
import dominio.Utente;
import eccezioni.UtenteGiaRegistratoException;
import eccezioni.UtenteNonAutorizzatoException;
import eccezioni.UtenteNonTrovatoException;
import jbook.util.Input;

/**
 * La classe Autenticazione gestisce il flusso di login e registrazione
 * degli utenti. Interagisce con la classe {@link Bacheca} per verificare
 * e registrare gli utenti, fornendo un'interfaccia a riga di comando
 * per l'input dell'utente.
 * <p>
 * Tutti i metodi di questa classe sono statici e non richiedono la
 * creazione di un'istanza.
 *
 * @author Pietro Mezzatesta
 */
public class Autenticazione {

    /**
     * Gestisce l'intero processo di autenticazione, chiedendo all'utente
     * se vuole accedere o registrarsi. Il ciclo continua fino a quando
     * un utente non viene autenticato con successo.
     *
     * @param bacheca L'istanza di {@link Bacheca} su cui operare.
     * @return L'oggetto {@link Utente} che ha completato l'autenticazione.
     */
    public static Utente gestisci(Bacheca bacheca) {
        Utente utenteCorrente = null;
        boolean autenticato = false;

        while (!autenticato) {
            System.out.println("Sei un utente registrato? (s/n): ");
            String risposta = Input.readString().trim().toLowerCase();

            try {
                if (risposta.equals("s")) {
                    utenteCorrente = gestisciAccesso(bacheca);
                    autenticato = true;
                } else if (risposta.equals("n")) {
                    utenteCorrente = gestisciRegistrazione(bacheca);
                    autenticato = true;
                } else {
                    System.out.println("Scelta non valida. Riprova.");
                }
            } catch (Exception e) {
                System.err.println("Errore: " + e.getMessage());
            }
        }
        return utenteCorrente;
    }

    /**
     * Gestisce il processo di registrazione di un nuovo utente.
     * Richiede nome, cognome, email e password all'utente.
     * Salva i dati dell'utente su file dopo una registrazione di successo.
     *
     * @param bacheca L'istanza di {@link Bacheca} in cui registrare il nuovo utente.
     * @return L'oggetto {@link Utente} appena creato e registrato.
     * @throws Exception per gestire {@link UtenteGiaRegistratoException} e problemi di I/O.
     */
    private static Utente gestisciRegistrazione(Bacheca bacheca) throws Exception {
        String nome = Input.readString("Nome: ").trim();
        String cognome = Input.readString("Cognome: ").trim();
        String email = Input.readString("Email: ").trim().toLowerCase();
        
        System.out.println("DEBUG: Verifico se email '" + email + "' è già registrata...");
        System.out.println("DEBUG: Utenti attualmente registrati: " + bacheca.getUtentiRegistrati().size());
        
        // Validazione email
        if (!Validator.isValidEmail(email)) {
            throw new IllegalArgumentException(
                "Formato email non valido. Utilizzare il formato: nome@dominio.ext"
            );
        }
        
        if (bacheca.isUtenteRegistrato(email)) {
            throw new UtenteGiaRegistratoException("Utente con questa email già esistente.");
        }
        
        // Mostra requisiti password prima dell'inserimento
        System.out.println("\n" + Validator.getPasswordRequirements());
        String password = Input.readString("Password: ");
        
        // Validazione password con feedback sulla forza
        if (!Validator.isValidPassword(password)) {
            int strength = Validator.getPasswordStrength(password);
            String strengthDesc = Validator.getPasswordStrengthDescription(strength);
            throw new IllegalArgumentException(
                "Password " + strengthDesc + ". " + Validator.getPasswordRequirements()
            );
        }
        
        // Validazione input
        if (nome.isEmpty() || cognome.isEmpty()) {
            throw new IllegalArgumentException("Tutti i campi sono obbligatori.");
        }
        
        Utente nuovoUtente = new Utente(email, nome, cognome, password);
        bacheca.registraUtente(nuovoUtente);
        
        System.out.println("DEBUG: Utente registrato nella bacheca. Totale utenti: " + bacheca.getUtentiRegistrati().size());
        
        try {
            BachecaPersistence.salvaUtenti(bacheca.getUtentiRegistrati());
            System.out.println("DEBUG: Dati salvati su file con successo.");
        } catch (Exception e) {
            System.err.println("ERRORE nel salvataggio: " + e.getMessage());
            throw e;
        }
        
        int passwordStrength = Validator.getPasswordStrength(password);
        String strengthMessage = Validator.getPasswordStrengthDescription(passwordStrength);
        System.out.println("Password " + strengthMessage + " accettata.");
        System.out.println("Registrazione completata. Benvenuto, " + nuovoUtente.getNome() + "!");
        
        return nuovoUtente;
    }

    /**
     * Gestisce il processo di accesso per un utente esistente.
     * Richiede l'email e la password per verificare le credenziali.
     *
     * @param bacheca L'istanza di {@link Bacheca} da cui recuperare i dati dell'utente.
     * @return L'oggetto {@link Utente} se l'accesso ha successo.
     * @throws Exception per gestire {@link UtenteNonTrovatoException} e {@link UtenteNonAutorizzatoException}.
     */
    private static Utente gestisciAccesso(Bacheca bacheca) throws Exception {
        String email = Input.readString("Email: ").trim().toLowerCase(); // Normalizza email
        
        // DEBUG: Mostra stato utenti
        System.out.println("DEBUG: Cerco utente con email '" + email + "'");
        System.out.println("DEBUG: Utenti registrati: " + bacheca.getUtentiRegistrati().size());
        System.out.println("DEBUG: Lista email registrate: " + bacheca.getUtentiRegistrati().keySet());
        
        if (!bacheca.isUtenteRegistrato(email)) {
            throw new UtenteNonTrovatoException("Utente non trovato. Per favore, registrati.");
        }
        
        Utente utenteSalvato = bacheca.getUtente(email);
        if (utenteSalvato == null) {
            throw new UtenteNonTrovatoException("Errore nel recupero dati utente.");
        }
        
        String passwordInserita = Input.readString("Password: ");
        if (!utenteSalvato.checkPassword(passwordInserita)) {
            throw new UtenteNonAutorizzatoException("Credenziali non corrette.");
        }
        
        System.out.println("Accesso completato. Bentornato, " + utenteSalvato.getNome() + "!\n");
        return utenteSalvato;
    }
}