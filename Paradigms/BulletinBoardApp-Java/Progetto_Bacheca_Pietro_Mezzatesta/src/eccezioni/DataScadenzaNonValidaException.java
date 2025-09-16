package eccezioni;

/**
 * Eccezione sollevata quando si tenta di creare un annuncio di vendita
 * con una data di scadenza non valida, ad esempio una data che è
 * già passata o è la data odierna.
 * 
 * @author Pietro Mezzatesta
 */

public class DataScadenzaNonValidaException extends Exception{
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore per creare un'eccezione con un messaggio specifico.
	 * 
	 * @param msg Il messaggio di errore che descrive la causa dell'eccezione.
	 */
	public DataScadenzaNonValidaException(String msg) {
		super(msg);
	}
}
