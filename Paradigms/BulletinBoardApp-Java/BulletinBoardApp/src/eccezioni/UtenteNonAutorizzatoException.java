package eccezioni;

/**
 * Eccezione sollevata quando un'operazione non può essere eseguita
 * a causa di permessi insufficienti, ad esempio quando un utente
 * tenta di modificare o rimuovere l'annuncio di un altro.
 * 
 * @author Pietro Mezzatesta
 */
public class UtenteNonAutorizzatoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Costruttore per creare un'eccezione con un messaggio specifico.
	 * 
	 * @param msg Il messaggio di errore che descrive la causa dell'eccezione.
	 */
	public UtenteNonAutorizzatoException(String msg) {
		super(msg);
	}
	
}
