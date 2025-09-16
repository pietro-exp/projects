package dominio;

import java.util.Set;

/**
 * Rappresenta un annuncio di acquisto, pubblicato da un utente che 
 * intende acquistare un determinato prodotto o servizio (sottoclasse di Annuncio).
 * <p>
 * Un annuncio di acquisto, per sua natura, non ha una data di scadenza.
 * 
 * @author Pietro Mezzatesta
 */
public class AnnuncioAcquisto extends Annuncio{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore per la creazione di un nuovo annuncio di acquisto.
	 * Chiama il costruttore della superclasse {@link Annuncio} per inizializzare
	 * le proprietà comuni.
	 *
	 * @param autore L'utente che ha creato l'annuncio.
	 * @param nomeAnnuncio Il nome o titolo dell'annuncio.
	 * @param prezzo Il prezzo massimo che l'utente è disposto a pagare.
	 * @param paroleChiave Un insieme di parole chiave per la ricerca.
	 */
	public AnnuncioAcquisto(Utente autore, String nomeAnnuncio, double prezzo, Set<String> paroleChiave) {
		// Chiama il costruttore della superclasse Annuncio
		super(autore, nomeAnnuncio, prezzo, paroleChiave);
	}

	/**
	 * Questo metodo restituisce sempre {@code false}, poiché un annuncio di acquisto
	 * non ha una data di scadenza e non può scadere.
	 *
	 * @return Restituisce sempre {@code false}.
	 */
	@Override
	public boolean isScaduto() {
		return false;
	}

}
