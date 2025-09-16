package eccezioni;

/**
 * Eccezione sollevata quando un utente non viene trovato
 * nel sistema durante un'operazione che lo richiede.
 * 
 * @author Pietro Mezzatesta
 */
public class UtenteNonTrovatoException extends Exception{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Costruttore per creare un'eccezione con un messaggio specifico.
	 * 
	 * @param msg Il messaggio di errore che descrive la causa dell'eccezione.
	 */
	public UtenteNonTrovatoException(String msg) {
		super(msg);
	}
}
