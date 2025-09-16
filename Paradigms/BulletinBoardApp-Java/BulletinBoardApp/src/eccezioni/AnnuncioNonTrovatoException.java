package eccezioni;

/**
 * Eccezione sollevata quando un annuncio non viene trovato
 * nella bacheca, ad esempio durante un'operazione di rimozione
 * o modifica.
 * 
 * @author Pietro Mezzatesta
 */
public class AnnuncioNonTrovatoException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore per creare un'eccezione con un messaggio specifico.
	 * 
	 * @param msg Il messaggio di errore che descrive la causa dell'eccezione.
	 */
	public AnnuncioNonTrovatoException(String msg) {
		super(msg);
	}

}
