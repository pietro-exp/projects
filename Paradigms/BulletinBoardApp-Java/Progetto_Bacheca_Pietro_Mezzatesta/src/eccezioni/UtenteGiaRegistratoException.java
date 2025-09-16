package eccezioni;

/**
 * Eccezione sollevata quando si tenta di registrare un utente
 * la cui email è già presente nel sistema.
 * 
 * @author Pietro Mezzatesta
 */
public class UtenteGiaRegistratoException extends Exception{
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore per creare un'eccezione con un messaggio specifico.
	 * 
	 * @param msg Il messaggio di errore che descrive la causa dell'eccezione.
	 */
	public UtenteGiaRegistratoException(String msg) {
		super(msg);
	}
}
