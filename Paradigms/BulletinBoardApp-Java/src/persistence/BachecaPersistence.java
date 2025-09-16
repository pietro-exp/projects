package persistence;

import dominio.Bacheca;
import dominio.Utente;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * La classe BachecaPersistence gestisce la persistenza degli oggetti {@link Bacheca}
 * e delle collezioni di {@link Utente} su file, utilizzando la serializzazione.
 * <p>
 * Tutti i metodi di questa classe sono statici, il che la rende una classe di utilità
 * non istanziabile.
 *
 * @see Bacheca
 * @see Utente
 * * @author Pietro Mezzatesta
 */
public class BachecaPersistence {

	private static final String UTENTI_FILE = "utenti.ser";
	
	/**
	 * Salva l'oggetto {@link Bacheca} su un file specificato.
	 *
	 * @param bacheca L'oggetto bacheca da salvare.
	 * @param nomeFile Il nome del file su cui salvare.
	 */
	public static void salvaBacheca(Bacheca bacheca, String nomeFile) {
		try (
			FileOutputStream fileOut = new FileOutputStream(nomeFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
				){
			out.writeObject(bacheca);
		} catch(IOException i) {
			i.printStackTrace();
		}
		
	}
	
	/**
	 * Salva la mappa degli utenti registrati su un file.
	 *
	 * @param utenti La mappa di utenti da salvare, dove la chiave è l'email dell'utente.
	 * @throws IOException se si verifica un errore durante la scrittura del file.
	 * @see Utente
	 */
	public static void salvaUtenti(Map<String, Utente> utenti) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(UTENTI_FILE))) {
			oos.writeObject(utenti);
		}
	}
	
	/**
	 * Carica la mappa degli utenti da un file. Se il file non esiste, restituisce
	 * una nuova mappa vuota.
	 *
	 * @return La mappa degli utenti caricata dal file.
	 * @throws IOException se si verifica un errore durante la lettura del file.
	 * @throws ClassNotFoundException se la classe dell'oggetto serializzato non viene trovata.
	 * @see Utente
	 */
	public static Map<String, Utente> caricaUtenti() throws IOException, ClassNotFoundException {
		File utentiFile = new File(UTENTI_FILE);
		if (!utentiFile.exists()) {
			return new HashMap<>();
		}
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(utentiFile))) {
			return (Map<String, Utente>) ois.readObject();
		}
	}
	
	/**
	 * Carica un oggetto {@link Bacheca} da un file.
	 *
	 * @param nomeFile Il nome del file da cui caricare la Bacheca.
	 * @return L'oggetto Bacheca caricato, o {@code null} se il file non esiste
	 * o se si verificano errori durante la lettura.
	 * @see Bacheca
	 */
	public static Bacheca caricaBacheca(String nomeFile) {
		Bacheca bacheca = null;
		try (FileInputStream fileIn = new FileInputStream(nomeFile);
			ObjectInputStream in = new ObjectInputStream(fileIn)) {
			bacheca = (Bacheca) in.readObject();
		} catch (IOException | ClassNotFoundException i) {
			/*
			 * Se il file non esiste o la classe non è trovata, è restituito null
			 * e viene gestito questo caso nel codice che chiama il metodo.
			 */
			return null;
		}
		return bacheca;
	}
	
}
