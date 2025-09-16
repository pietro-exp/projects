package dominio;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

import persistence.Validator;

/**
 * Classe che rappresenta un utente registrato nel sistema della bacheca.
 * Un utente è identificato in modo univoco dalla sua email.
 * L'uso dell'email come identificatore principale assicura che non ci siano
 * duplicati nel sistema, garantisce un riferimento univoco e immutabile.
 *
 * @author Pietro Mezzatesta
 */
public class Utente implements Serializable{
	private static final long serialVersionUID = 1L;
	private final String email;
	private String nome;
	private String cognome;
	private final String passwordHash; // Hash della password invece del testo in chiaro
	private final String salt; // Salt per la sicurezza
	
	/**
	 * Costruttore per la creazione di un nuovo utente.
	 *
	 * @param email L'indirizzo email dell'utente, usato come identificatore univoco.
	 * @param nome Il nome dell'utente.
	 * @param cognome Il cognome dell'utente.
	 * @param password La password dell'utente (verrà hashata automaticamente).
	 * @throws IllegalArgumentException se l'email o la password sono nulle o vuote.
	 */
	public Utente(String email, String nome, String cognome, String password) {
		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("L'email non può essere nulla o vuota.");
		}
		
		if(!Validator.isValidEmail(email)) {
			throw new IllegalArgumentException("Formato email non valido. Inserire un'email nel formato: nome@dominio.ext");
		}
		if (password == null || password.isBlank()) {
			throw new IllegalArgumentException("La password non può essere nulla o vuota.");
		}
		
		if(!Validator.isValidPassword(password)) {
			throw new IllegalArgumentException("Password non valida. " + Validator.getPasswordRequirements());
		}
		
		if (nome == null || nome.isBlank()) {
			throw new IllegalArgumentException("Il nome non può essere nullo o vuoto.");
		}
		if (cognome == null || cognome.isBlank()) {
			throw new IllegalArgumentException("Il cognome non può essere nullo o vuoto.");
		}
		
		this.email = email.toLowerCase().trim(); // Normalizza email
		this.nome = nome.trim();
		this.cognome = cognome.trim();
		this.salt = generateSalt();
		this.passwordHash = hashPassword(password, this.salt);
	}

	/**
	 * Genera un salt casuale per la sicurezza della password.
	 *
	 * @return Una stringa Base64 contenente il salt.
	 */
	private String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	/**
	 * Calcola l'hash di una password utilizzando SHA-256 e il salt.
	 *
	 * @param password La password in chiaro.
	 * @param salt Il salt da utilizzare.
	 * @return L'hash della password come stringa Base64.
	 */
	private String hashPassword(String password, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(Base64.getDecoder().decode(salt));
			byte[] hashedPassword = md.digest(password.getBytes());
			return Base64.getEncoder().encodeToString(hashedPassword);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Algoritmo SHA-256 non disponibile", e);
		}
	}

	/**
	 * Restituisce il nome dell'utente.
	 *
	 * @return Il nome dell'utente.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Imposta un nuovo nome per l'utente.
	 *
	 * @param nome Il nuovo nome da assegnare.
	 * @throws IllegalArgumentException se il nome è nullo o vuoto.
	 */
	public void setNome(String nome) {
		if (nome == null || nome.isBlank()) {
			throw new IllegalArgumentException("Il nome non può essere nullo o vuoto.");
		}
		this.nome = nome.trim();
	}

	/**
	 * Restituisce il cognome dell'utente.
	 *
	 * @return Il cognome dell'utente.
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * Imposta un nuovo cognome per l'utente.
	 *
	 * @param cognome Il nuovo cognome da assegnare.
	 * @throws IllegalArgumentException se il cognome è nullo o vuoto.
	 */
	public void setCognome(String cognome) {
		if (cognome == null || cognome.isBlank()) {
			throw new IllegalArgumentException("Il cognome non può essere nullo o vuoto.");
		}
		this.cognome = cognome.trim();
	}

	/**
	 * Restituisce l'indirizzo email dell'utente.
	 *
	 * @return L'email dell'utente.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Verifica se la password fornita corrisponde a quella dell'utente.
	 *
	 * @param password La password da verificare.
	 * @return {@code true} se le password corrispondono, {@code false} altrimenti.
	 */
	public boolean checkPassword(String password) {
		if (password == null) {
			return false;
		}
		String hashedInput = hashPassword(password, this.salt);
		return this.passwordHash.equals(hashedInput);
	}
	
	/**
	 * Genera un codice hash per l'oggetto Utente, basato sull'email.
	 *
	 * @return Un intero che rappresenta il codice hash dell'oggetto.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(email);
	}
	
	/**
	 * Confronta due oggetti Utente per verificarne l'uguaglianza.
	 * Il confronto si basa unicamente sull'email, che è considerata
	 * l'identificatore univoco dell'utente.
	 *
	 * @param obj L'oggetto da confrontare.
	 * @return {@code true} se gli oggetti Utente hanno la stessa email, {@code false} altrimenti.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Utente utente = (Utente) obj;
		return Objects.equals(email, utente.email);
	}
	
	/**
	 * Restituisce una rappresentazione in stringa dell'oggetto Utente.
	 *
	 * @return Una stringa formattata con i dettagli dell'utente.
	 */
	@Override
	public String toString() {
		 return String.format("Utente{nome=%s, cognome=%s, email='%s'}",
				nome, cognome, email);
	}
}
