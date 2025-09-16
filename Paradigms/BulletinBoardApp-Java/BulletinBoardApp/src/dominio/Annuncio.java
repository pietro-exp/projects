package dominio;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Classe astratta che rappresenta un annuncio generico nella bacheca.
 * Fornisce le proprietà e i metodi comuni a tutti i tipi di annuncio,
 * come l'ID, il nome, l'autore, il prezzo e le parole chiave ad esso associate.
 * 
 * @author Pietro Mezzatesta
 */
public abstract class Annuncio implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private final String id;
	private final Utente autore;
	
	private String nomeAnnuncio;
	private double prezzo;
	private Set<String> paroleChiave;
	
	/**
	 * Costruttore per la creazione di un nuovo annuncio. L'ID dell'annuncio
	 * viene generato automaticamente.
	 * 
	 * @param autore L'utente che ha creato l'annuncio.
	 * @param nomeAnnuncio Il nome o titolo dell'annuncio.
	 * @param prezzo Il prezzo dell'articolo o servizio.
	 * @param paroleChiave Un insieme di parole chiave per la ricerca.
	 * @throws IllegalArgumentException se uno dei parametri non è valido.
	 */
	public Annuncio(Utente autore, String nomeAnnuncio, double prezzo, Set<String> paroleChiave) {
		if (autore == null) {
			throw new IllegalArgumentException("L'autore non può essere nullo.");
		}
		if (nomeAnnuncio == null || nomeAnnuncio.isBlank()) {
			throw new IllegalArgumentException("Il nome dell'annuncio non può essere nullo o vuoto.");
		}
		if (prezzo < 0) {
			throw new IllegalArgumentException("Il prezzo non può essere negativo.");
		}
		if (paroleChiave == null) {
			throw new IllegalArgumentException("Le parole chiave non possono essere nulle.");
		}
		
		// L'ID viene generato una sola volta nel costruttore.
		this.id = UUID.randomUUID().toString();
		this.autore = autore;
		this.nomeAnnuncio = nomeAnnuncio.trim();
		this.prezzo = prezzo;
		// Normalizza le parole chiave: rimuove null, trim, e filtra stringhe vuote
		this.paroleChiave = paroleChiave.stream()
				.filter(Objects::nonNull)
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.collect(Collectors.toSet());
	}

	/**
	 * Restituisce l'ID univoco dell'annuncio.
	 *
	 * @return L'ID dell'annuncio.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Restituisce il nome dell'annuncio.
	 *
	 * @return Il nome dell'annuncio.
	 */
	public String getNomeAnnuncio() {
		return nomeAnnuncio;
	}

	/**
	 * Restituisce il prezzo associato all'annuncio.
	 *
	 * @return Il prezzo.
	 */
	public double getPrezzo() {
		return prezzo;
	}
	
	/**
	 * Restituisce l'autore dell'annuncio.
	 *
	 * @return L'oggetto {@link Utente} che ha creato l'annuncio.
	 */
	public Utente getAutore() {
		return autore;
	}
	
	/**
	 * Restituisce una copia delle parole chiave associate all'annuncio.
	 *
	 * @return Una copia del {@link Set} di parole chiave.
	 */
	public Set<String> getParoleChiave() {
		return new HashSet<>(paroleChiave);
	}
	
	/**
	 * Imposta un nuovo nome per l'annuncio.
	 *
	 * @param nomeAnnuncio Il nuovo nome da assegnare.
	 * @throws IllegalArgumentException se il nome è nullo o vuoto.
	 */
	public void setNomeAnnuncio(String nomeAnnuncio) {
		if (nomeAnnuncio == null || nomeAnnuncio.isBlank()) {
			throw new IllegalArgumentException("Il nome dell'annuncio non può essere nullo o vuoto.");
		}
		this.nomeAnnuncio = nomeAnnuncio.trim();
	}

	/**
	 * Imposta un nuovo prezzo per l'annuncio.
	 *
	 * @param prezzo Il nuovo prezzo da assegnare.
	 * @throws IllegalArgumentException se il prezzo è negativo.
	 */
	public void setPrezzo(double prezzo) {
		if (prezzo < 0) {
			throw new IllegalArgumentException("Il prezzo non può essere negativo.");
		}
		this.prezzo = prezzo;
	}

	/**
	 * Imposta un nuovo insieme di parole chiave per l'annuncio.
	 *
	 * @param paroleChiave Il nuovo {@link Set} di parole chiave.
	 * @throws IllegalArgumentException se le parole chiave sono nulle.
	 */
	public void setParoleChiave(Set<String> paroleChiave) {
		if (paroleChiave == null) {
			throw new IllegalArgumentException("Le parole chiave non possono essere nulle.");
		}
		// Normalizza le parole chiave: rimuove null, trim, e filtra stringhe vuote
		this.paroleChiave = paroleChiave.stream()
				.filter(Objects::nonNull)
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.collect(Collectors.toSet());
	}
	
	/**
	 * Restituisce una rappresentazione in stringa dell'annuncio.
	 *
	 * @return Una stringa formattata con i dettagli dell'annuncio.
	 */
	@Override
	public String toString() {
		return "ID: " + id + ", Autore: " + autore.getEmail() + ", Articolo: " + nomeAnnuncio + 
		       ", Prezzo: " + prezzo + ", Parole Chiave: " + paroleChiave;
	}
	
	/**
	 * Genera un codice hash per l'oggetto Annuncio, basato sull'ID.
	 *
	 * @return Un intero che rappresenta il codice hash dell'oggetto.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	/**
	 * Confronta due oggetti Annuncio per verificarne l'uguaglianza.
	 * Il confronto si basa unicamente sull'ID.
	 *
	 * @param obj L'oggetto da confrontare.
	 * @return {@code true} se gli oggetti Annuncio hanno lo stesso ID, {@code false} altrimenti.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Annuncio annuncio = (Annuncio) obj;
		return Objects.equals(id, annuncio.id);
	}
	
	/**
	 * Metodo astratto che deve essere implementato dalle sottoclassi
	 * per determinare se un annuncio è scaduto.
	 *
	 * @return {@code true} se l'annuncio è scaduto, {@code false} altrimenti.
	 */
	public abstract boolean isScaduto();
}