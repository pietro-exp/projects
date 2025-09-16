package interfaccia;

import jbook.util.Input;
import persistence.BachecaPersistence;
import dominio.Annuncio;
import dominio.AnnuncioAcquisto;
import dominio.AnnuncioVendita;
import dominio.Bacheca;
import dominio.Utente;
import eccezioni.AnnuncioNonTrovatoException;
import eccezioni.DataScadenzaNonValidaException;
import eccezioni.UtenteNonAutorizzatoException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * La classe BachecaCLI rappresenta l'interfaccia a riga di comando per l'applicazione
 * della bacheca. Gestisce l'interazione con l'utente, mostrando menu,
 * raccogliendo input e chiamando i metodi appropriati della classe {@link Bacheca}.
 * <p>
 * L'istanza di questa classe viene creata per un utente specifico
 * dopo l'autenticazione.
 *
 * @author Pietro Mezzatesta
 */
public class BachecaCLI {
	private final Bacheca bacheca;
	private final Utente utenteCorrente;
	private static final String BACHECA_FILE = "bacheca.ser";
	
	/**
	 * Costruttore per inizializzare l'interfaccia a riga di comando.
	 *
	 * @param bacheca L'istanza della bacheca su cui operare.
	 * @param utenteCorrente L'utente attualmente autenticato.
	 */
	public BachecaCLI(Bacheca bacheca, Utente utenteCorrente) {
		this.bacheca = bacheca;
		this.utenteCorrente = utenteCorrente;
	}
	
	/**
	 * Mostra il menu principale delle operazioni disponibili all'utente.
	 * Questo metodo è privato in quanto è una funzionalità interna all'interfaccia utente.
	 */
	private void mostraMenu() {
		System.out.println("\n === Menu Principale ===\n");
		System.out.println("1) Inserisci un nuovo annuncio");
		System.out.println("2) Rimuovi un annuncio");
		System.out.println("3) Cerca articoli per parola chiave");
		System.out.println("4) Pulisci la bacheca (rimuovi annunci scaduti)");
        System.out.println("5) Visualizza i miei annunci");
        System.out.println("6) Modifica un mio annuncio");
        System.out.println("7) Salva la bacheca");
        System.out.println("8) Esci e salva");
		System.out.println("Scegli l'operazione: ");
	}
	
	/**
	 * Gestisce il processo di creazione e inserimento di un nuovo annuncio.
	 * Chiede all'utente il tipo di annuncio (acquisto o vendita) e i relativi dettagli.
	 *
	 * @see Bacheca#aggiungiAnnuncio(Annuncio)
	 */
	private void gestisciNuovoAnnuncio() {
		String tipo = Input.readString("Vuoi inserire un annuncio di acquisto (A) o di vendita (V)? ").trim().toUpperCase();
		String nomeArticolo = Input.readString("Nome dell'articolo: ");
		
		double prezzo;
		try {
			prezzo = Input.readDouble("Prezzo: ");
		} catch(NumberFormatException e) {
			System.err.println("Il prezzo deve essere un numero. Operazione annullata.");
			return;
		}
		
		String paroleChiaveInput = Input.readString("Parole chiave (separate da virgola): ");
		String[] paroleChiaveArray = paroleChiaveInput.split(",");
		
		Set<String> paroleChiaveSet = Arrays.stream(paroleChiaveArray).map(String::trim).collect(Collectors.toSet());
	
		Annuncio nuovoAnnuncio;
		
		if("V".equals(tipo)) {
			String dataScadenzaStr = Input.readString("Data di scadenza (YYYY-MM-DD): ");
			try {
				LocalDate dataScadenza = LocalDate.parse(dataScadenzaStr);
				nuovoAnnuncio = new AnnuncioVendita(utenteCorrente, nomeArticolo, prezzo, paroleChiaveSet, dataScadenza);
				bacheca.aggiungiAnnuncio(nuovoAnnuncio);
				System.out.println("Annuncio di vendita aggiunto con successo.");
				System.out.println("ID annuncio: " + nuovoAnnuncio.getId());
			} catch(DateTimeParseException e) {
				System.err.println("Formato data non valido. Operazione annullata");
			}catch(DataScadenzaNonValidaException e) {
				System.err.println(e.getMessage());
			}
		} else if ("A".equals(tipo)) {
			try {
				nuovoAnnuncio = new AnnuncioAcquisto(utenteCorrente, nomeArticolo, prezzo, paroleChiaveSet);
				bacheca.aggiungiAnnuncio(nuovoAnnuncio);
				System.out.println("Annuncio di acquisto aggiunto con successo.");
				System.out.println("ID annuncio: " + nuovoAnnuncio.getId());
				
				List<Annuncio> annunciCorrelati = bacheca.cercaAnnunci(paroleChiaveSet, utenteCorrente);
				if(!annunciCorrelati.isEmpty()) {
					System.out.println("\n === Articoli che potrebbero interessarti ===");
					annunciCorrelati.forEach(System.out::println);
					//ciclo for-each per la formattazione migliore
				}
			}catch(Exception e) {
				System.err.println("Errore: " + e.getMessage());
			}
		} else {
			System.out.println("Tipo di annuncio non valido. Operazione annullata.");
		}
	}
	
	/**
	 * Gestisce la rimozione di un annuncio. Chiede all'utente l'ID dell'annuncio
	 * e verifica che sia l'autore.
	 *
	 * @throws AnnuncioNonTrovatoException se l'annuncio specificato non viene trovato.
	 * @throws UtenteNonAutorizzatoException se l'utente corrente non è l'autore dell'annuncio.
	 * @see Bacheca#rimuoviAnnuncio(Annuncio, Utente)
	 */
	private void gestisciRimozioneAnnuncio() {
		String id = Input.readString("Inserisci l'ID dell'annuncio da rimuovere: ");
		Annuncio annuncioDaRimuovere = bacheca.getAnnuncioById(id);
		
		try {
			bacheca.rimuoviAnnuncio(annuncioDaRimuovere, utenteCorrente);
			System.out.println("Annuncio rimosso con successo.");
		} catch(AnnuncioNonTrovatoException | UtenteNonAutorizzatoException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Gestisce la ricerca di annunci. Chiede all'utente le parole chiave e
	 * mostra i risultati della ricerca.
	 *
	 * @see Bacheca#cercaAnnunci(Set, Utente)
	 */
	private void gestisciRicercaAnnunci() {
		String paroleChiaveInput = Input.readString("Inserisci le parole chiave per la ricerca (separate da virgola): ");
		String[] paroleChiaveArray = paroleChiaveInput.split(",");
		
		Set<String> paroleChiaveSet = Arrays.stream(paroleChiaveArray).map(String::trim).collect(Collectors.toSet());
		
		List<Annuncio> risultati = bacheca.cercaAnnunci(paroleChiaveSet, utenteCorrente);
		
		if(risultati.isEmpty()) {
			System.out.println("Nessun annuncio trovato con quelle parole chiave.");
		} else {
			System.out.println("\n === Risultati della ricerca === ");
			risultati.forEach(System.out::println);
		}
	}

	/**
	 * Visualizza tutti gli annunci pubblicati dall'utente corrente.
	 *
	 * @see Bacheca#getAnnunciPerAutore(Utente)
	 */
    private void gestisciMieiAnnunci() {
        List<Annuncio> mieiAnnunci = bacheca.getAnnunciPerAutore(utenteCorrente);
        if (mieiAnnunci.isEmpty()) {
            System.out.println("Non hai ancora pubblicato nessun annuncio.");
        } else {
            System.out.println("\n === I miei annunci === ");
            for (Annuncio annuncio : mieiAnnunci) {
            	if (annuncio instanceof AnnuncioVendita) {
                    AnnuncioVendita annuncioVendita = (AnnuncioVendita) annuncio;
                    System.out.println("Tipo: Annuncio di Vendita\n");
                    System.out.println("Data di scadenza: " + annuncioVendita.getDataScadenza() + "\n");
            	}
            	else if (annuncio instanceof AnnuncioAcquisto) {
            		System.out.println("Tipo: Annuncio di Acquisto\n");
                }
            	System.out.println("Nome: " + annuncio.getNomeAnnuncio() + "\n");
            	System.out.println("Prezzo: " + annuncio.getPrezzo() + "\n");
            	System.out.println("Parole chiave: " + annuncio.getParoleChiave() + "\n");
            	System.out.println("ID: " + annuncio.getId() + "\n");
            	System.out.println("---\n");
            }
        }
    }

    /**
	 * Gestisce la modifica di un annuncio esistente. Chiede all'utente l'ID
	 * dell'annuncio e i nuovi dettagli da aggiornare.
	 *
	 * @throws AnnuncioNonTrovatoException se l'annuncio specificato non viene trovato.
	 * @throws UtenteNonAutorizzatoException se l'utente corrente non è l'autore dell'annuncio.
	 * @throws NumberFormatException se il prezzo inserito non è un numero valido.
	 * @see Bacheca#modificaAnnuncio(String, Utente, String, double, Set)
	 */
    private void gestisciModificaAnnuncio() {
        String id = Input.readString("Inserisci l'ID dell'annuncio da modificare: ");
        try {
            Annuncio annuncioDaModificare = bacheca.getAnnuncioById(id);
            if (annuncioDaModificare == null) {
                throw new AnnuncioNonTrovatoException("Annuncio con ID " + id + " non trovato.");
            }
            if (!annuncioDaModificare.getAutore().equals(utenteCorrente)) {
                throw new UtenteNonAutorizzatoException("Puoi modificare solo i tuoi annunci.");
            }

            System.out.println("Modifica l'annuncio: " + annuncioDaModificare.getNomeAnnuncio());
            String nuovoNome = Input.readString("Nuovo nome articolo (lascia vuoto per non cambiare): ");
            String nuovoPrezzoStr = Input.readString("Nuovo prezzo (lascia vuoto per non cambiare): ");
            String nuoveParoleChiaveStr = Input.readString("Nuove parole chiave (lascia vuoto per non cambiare): ");
            String nuovaData = Input.readString("Nuova data di scadenza (lasciare vuoto per non cambiare o nel caso di annuncio di acquisto): ");
            
            // Logica di aggiornamento dei campi
            String nome = nuovoNome.isEmpty() ? annuncioDaModificare.getNomeAnnuncio() : nuovoNome;
            double prezzo = nuovoPrezzoStr.isEmpty() ? annuncioDaModificare.getPrezzo() : Double.parseDouble(nuovoPrezzoStr);
            Set<String> paroleChiave = nuoveParoleChiaveStr.isEmpty() ? annuncioDaModificare.getParoleChiave() : 
                Arrays.stream(nuoveParoleChiaveStr.split(",")).map(String::trim).collect(Collectors.toSet());
            
            if(!nuovaData.isBlank() || !nuovaData.isEmpty() && annuncioDaModificare instanceof AnnuncioVendita) {
            	LocalDate newDate = LocalDate.parse(nuovaData);
            	try {
					bacheca.modificaAnnuncio(id, utenteCorrente, nuovoNome, prezzo, paroleChiave, newDate);
				} catch (DataScadenzaNonValidaException e) {
					e.printStackTrace();
				}
            }
            
            try {
				bacheca.modificaAnnuncio(id, utenteCorrente, nome, prezzo, paroleChiave, null);
			} catch (DataScadenzaNonValidaException e) {
				e.printStackTrace();
			}
            System.out.println("Annuncio modificato con successo!");
        } catch (AnnuncioNonTrovatoException | UtenteNonAutorizzatoException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Prezzo non valido. Operazione annullata.");
        }
    }
	
	/**
	 * Avvia il ciclo principale dell'applicazione a riga di comando.
	 * Mostra un menu e gestisce le scelte dell'utente fino a quando non decide di uscire.
	 */
	public void run() {
		System.out.println("Benvenuto nella Bacheca di Annunci, " + utenteCorrente.getNome() + "!");
		
		boolean inEsecuzione = true;
		while(inEsecuzione) {
			mostraMenu();
			String scelta = Input.readString();
			
			try {
				switch(scelta) {
				case "1" -> gestisciNuovoAnnuncio();
				case "2" -> gestisciRimozioneAnnuncio();
				case "3" -> gestisciRicercaAnnunci();
				case "4" -> {
					bacheca.pulisciBacheca();
					System.out.println("Bacheca pulita dagli annunci scaduti.");
				}
				case "7" -> {
					BachecaPersistence.salvaBacheca(bacheca, BACHECA_FILE);
					System.out.println("Bacheca salvata su file: " + BACHECA_FILE + ".");
				}
				case "8" -> {
					inEsecuzione = false;
					BachecaPersistence.salvaBacheca(bacheca, BACHECA_FILE);
					System.out.println("La bacheca è stata salvata sul file: " + BACHECA_FILE + ". Arrivederci!");
				}
                case "5" -> gestisciMieiAnnunci();
                case "6" -> gestisciModificaAnnuncio();
				default -> System.out.println("Scelta non valida. Riprova.");
				}
			} catch(Exception e) {
				System.err.println("Errore: " + e.getMessage());
			}
		}
	}	
}
