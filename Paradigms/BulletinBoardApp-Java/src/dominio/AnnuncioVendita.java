package dominio;

import java.time.LocalDate;
import java.util.Set;

import eccezioni.DataScadenzaNonValidaException;

/**
 * Rappresenta un annuncio di vendita (sottoclasse di Annuncio).
 * A differenza di un annuncio di acquisto, ha una data di scadenza
 * dopo la quale non è più considerato valido.
 * 
 * @author Pietro Mezzatesta
 */
public class AnnuncioVendita extends Annuncio{
	private static final long serialVersionUID = 1L;

	private LocalDate dataScadenza;
	
	/**
     * Costruttore per la creazione di un nuovo annuncio di vendita.
     * Chiama il costruttore della superclasse {@link Annuncio} e inizializza
     * la data di scadenza, verificando che sia futura.
     *
     * @param autore L'utente che ha creato l'annuncio.
     * @param nomeAnnuncio Il nome o titolo dell'annuncio.
     * @param prezzo Il prezzo dell'articolo o servizio.
     * @param paroleChiave Un insieme di parole chiave per la ricerca.
     * @param dataScadenza La data di scadenza dell'annuncio.
     * @throws DataScadenzaNonValidaException se la data di scadenza non è posteriore alla data odierna.
     * @throws IllegalArgumentException se uno dei parametri non è valido.
     */
	public AnnuncioVendita(Utente autore, String nomeAnnuncio, double prezzo, Set<String> paroleChiave, LocalDate dataScadenza) throws DataScadenzaNonValidaException {
		super(autore, nomeAnnuncio, prezzo, paroleChiave);
		
		if (dataScadenza == null) {
			throw new IllegalArgumentException("La data di scadenza non può essere nulla.");
		}
		
		if (!dataScadenza.isAfter(LocalDate.now())) {
			throw new DataScadenzaNonValidaException("La data di scadenza deve essere posteriore alla data odierna.");
		}
		
		this.dataScadenza = dataScadenza;
	}
	
	/**
     * Restituisce la data di scadenza dell'annuncio.
     *
     * @return La data di scadenza.
     */
	public LocalDate getDataScadenza() {
		return dataScadenza;
	}
	
	 /**
     * Imposta una nuova data di scadenza per l'annuncio.
     * La nuova data deve essere posteriore alla data odierna.
     *
     * @param nuovaDataScadenza La nuova data di scadenza da impostare.
     * @throws DataScadenzaNonValidaException se la data non è futura.
     * @throws IllegalArgumentException se la data è nulla.
     */
    public void setDataScadenza(LocalDate nuovaDataScadenza) throws DataScadenzaNonValidaException {
        if (nuovaDataScadenza == null) {
            throw new IllegalArgumentException("La data di scadenza non può essere nulla.");
        }
        
        if (!nuovaDataScadenza.isAfter(LocalDate.now())) {
            throw new DataScadenzaNonValidaException("La data di scadenza deve essere posteriore alla data odierna.");
        }
        
        this.dataScadenza = nuovaDataScadenza;
    }
	
	/**
     * Verifica se l'annuncio è scaduto confrontando la sua data di scadenza
     * con la data odierna.
     *
     * @return {@code true} se la data di scadenza è precedente o uguale alla data odierna, {@code false} altrimenti.
     */
	@Override
	public boolean isScaduto() {
	    return dataScadenza.isBefore(LocalDate.now()) || dataScadenza.isEqual(LocalDate.now());
	}

	 /**
     * Restituisce una rappresentazione in stringa dell'oggetto AnnuncioVendita,
     * includendo i dettagli della superclasse e la data di scadenza.
     *
     * @return Una stringa formattata con i dettagli dell'annuncio di vendita.
     */
	@Override
	public String toString() {
		return super.toString() + ", Tipo: Vendita, Scadenza: " + dataScadenza;
	}
}