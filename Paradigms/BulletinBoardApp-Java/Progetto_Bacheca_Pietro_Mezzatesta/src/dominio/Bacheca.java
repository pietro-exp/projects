package dominio;

import eccezioni.AnnuncioNonTrovatoException;
import eccezioni.DataScadenzaNonValidaException;
import eccezioni.UtenteGiaRegistratoException;
import eccezioni.UtenteNonAutorizzatoException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * La classe Bacheca gestisce la logica centrale dell'applicazione,
 * inclusa la gestione degli annunci e degli utenti. Permette la
 * pubblicazione, la modifica, la ricerca e la rimozione di annunci.
 *
 * <p>
 * Per garantire un accesso efficiente ai dati, la classe utilizza le {@link Map}
 * per la gestione sia degli annunci che degli utenti. Questo approccio offre due vantaggi
 * principali:
 * <ul>
 * <li><b>Gestione degli annunci:</b> L'utilizzo di una mappa con l'ID dell'annuncio come chiave
 * permette di accedere, modificare o rimuovere un annuncio in tempo costante (O(1)), evitando
 * la necessità di scorrere una lista intera. </li>
 * <li><b>Gestione degli utenti:</b> Analogamente, la mappa degli utenti, che utilizza l'email
 * come chiave, assicura che operazioni come la verifica della registrazione o il recupero di un utente
 * siano estremamente veloci, essenziali per le funzionalità di autenticazione e autorizzazione. </li>
 * </ul>
 * Tenere traccia degli utenti registrati è fondamentale per implementare la logica di business,
 * come la verifica delle autorizzazioni (es. solo l'autore può modificare un annuncio) e
 * l'esclusione dei propri annunci dai risultati di ricerca.
 *
 * @author Pietro Mezzatesta
 */
public class Bacheca implements Serializable, Iterable<Annuncio> {
    private static final long serialVersionUID = 1L;
    private final Map<String, Annuncio> annunci;
    private final Map<String, Utente> utentiRegistrati;

    public Bacheca() {
        this.annunci = new HashMap<>();
        this.utentiRegistrati = new HashMap<>();
    }

    /**
     * Aggiunge un annuncio alla bacheca. L'annuncio non può essere nullo
     * e non può avere un id già esistente.
     *
     * @param annuncio L'annuncio da aggiungere alla bacheca.
     * @throws IllegalArgumentException se l'annuncio è nullo o ha un ID già esistente.
     */
    public void aggiungiAnnuncio(Annuncio annuncio) {
        if (annuncio == null) {
            throw new IllegalArgumentException("L'annuncio non può essere nullo.");
        }
        if (annuncio.getId() == null || annuncio.getId().isBlank()) {
            throw new IllegalArgumentException("L'ID dell'annuncio non può essere nullo o vuoto.");
        }
        if (annunci.containsKey(annuncio.getId())) {
            throw new IllegalArgumentException("Un annuncio con questo ID esiste già.");
        }
        this.annunci.put(annuncio.getId(), annuncio);
    }
    
    /**
     * Rimuove un annuncio dalla bacheca. L'operazione è consentita solo
     * all'autore dell'annuncio.
     *
     * @param annuncioDaRimuovere L'annuncio da rimuovere.
     * @param utente L'utente che sta tentando di rimuovere l'annuncio.
     * @throws AnnuncioNonTrovatoException se l'annuncio non è presente nella bacheca.
     * @throws UtenteNonAutorizzatoException se l'utente non è l'autore dell'annuncio.
     */
    public void rimuoviAnnuncio(Annuncio annuncioDaRimuovere, Utente utente)
            throws AnnuncioNonTrovatoException, UtenteNonAutorizzatoException {
        if (annuncioDaRimuovere == null) {
            throw new AnnuncioNonTrovatoException("L'annuncio da rimuovere non può essere nullo.");
        }
        if (utente == null) {
            throw new UtenteNonAutorizzatoException("L'utente non può essere nullo.");
        }
        if (!annunci.containsKey(annuncioDaRimuovere.getId())) {
            throw new AnnuncioNonTrovatoException("L'annuncio da rimuovere non è stato trovato.");
        }
        
        if (!Objects.equals(annuncioDaRimuovere.getAutore().getEmail(), utente.getEmail())) {
            throw new UtenteNonAutorizzatoException("Solo l'autore può rimuovere l'annuncio.");
        }
        
        annunci.remove(annuncioDaRimuovere.getId());
    }

    /**
     * Cerca gli annunci di vendita che contengano una o più parole chiave.
     * Esclude i propri annunci dai risultati della ricerca.
     *
     * @param paroleChiave Un set di stringhe che rappresentano i termini di ricerca.
     * @param utenteCorrente L'utente che sta eseguendo la ricerca.
     * @return Una lista di annunci che soddisfano i criteri di ricerca
     * @see AnnuncioVendita
     */
    public List<Annuncio> cercaAnnunci(Set<String> paroleChiave, Utente utenteCorrente) {
        if (paroleChiave == null || paroleChiave.isEmpty()) {
            return new ArrayList<>();
        }
        if (utenteCorrente == null) {
            throw new IllegalArgumentException("L'utente corrente non può essere nullo.");
        }
        
        // Normalizza le parole chiave di ricerca (lowercase e trim)
        Set<String> paroleChiaveNormalizzate = paroleChiave.stream()
                .filter(Objects::nonNull)
                .map(String::toLowerCase)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
        
        if (paroleChiaveNormalizzate.isEmpty()) {
            return new ArrayList<>();
        }
        
        return annunci.values().stream()
                .filter(annuncio -> annuncio instanceof AnnuncioVendita) // Solo annunci di vendita
                .filter(annuncio -> !annuncio.getAutore().equals(utenteCorrente)) // Esclude i propri annunci
                .filter(annuncio -> {
                    // Normalizza le parole chiave dell'annuncio per il confronto
                    Set<String> paroleChiaveAnnuncio = annuncio.getParoleChiave().stream()
                            .filter(Objects::nonNull)
                            .map(String::toLowerCase)
                            .map(String::trim)
                            .collect(Collectors.toSet());
                    return !Collections.disjoint(paroleChiaveAnnuncio, paroleChiaveNormalizzate);
                })
                .collect(Collectors.toList());
    }

    /**
     * Controlla se un utente con una specifica email è già registrato.
     *
     * @param email L'email da verificare.
     * @return {@code true} se l'utente è registrato, {@code false} altrimenti.
     */
    public boolean isUtenteRegistrato(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return utentiRegistrati.containsKey(email.toLowerCase().trim());
    }

    /**
     * Rimuove tutti gli annunci di vendita che sono scaduti.
     * Utilizza il metodo {@code isScaduto()} di {@link AnnuncioVendita}
     * per determinare se l'annuncio deve essere rimosso.
     * <p>
     * Questo metodo non rimuove gli annunci di acquisto, in quanto non posseggono
     * una data di scadenza.
     *
     * @return Il numero di annunci rimossi.
     */
    public int pulisciBacheca() {
        int contatore = 0;
        Iterator<Map.Entry<String, Annuncio>> iterator = annunci.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, Annuncio> entry = iterator.next();
            Annuncio annuncio = entry.getValue();
            if (annuncio instanceof AnnuncioVendita && annuncio.isScaduto()) {
                iterator.remove();
                contatore++;
            }
        }
        return contatore;
    }

    /**
     * Registra un nuovo utente nella bacheca.
     *
     * @param utente L'oggetto Utente da registrare.
     * @throws UtenteGiaRegistratoException se un utente con la stessa email è già registrato
     * @throws IllegalArgumentException se l'utente è nullo o ha dati non validi
     */
    public void registraUtente(Utente utente) throws UtenteGiaRegistratoException {
        if (utente == null) {
            throw new IllegalArgumentException("L'utente non può essere nullo.");
        }
        if (utente.getEmail() == null || utente.getEmail().isBlank()) {
            throw new IllegalArgumentException("L'email dell'utente non può essere nulla o vuota.");
        }
        
        String emailNormalizzata = utente.getEmail().toLowerCase().trim();
        if (utentiRegistrati.containsKey(emailNormalizzata)) {
            throw new UtenteGiaRegistratoException("Un utente con questa email è già registrato.");
        }
        utentiRegistrati.put(emailNormalizzata, utente);
    }

    /**
     * Modifica i dettagli di un annuncio esistente. L'operazione è consentita solo
     * all'autore dell'annuncio. I parametri null o vuoti vengono ignorati.
     *
     * @param id L'ID dell'annuncio da modificare
     * @param utenteCorrente L'utente che sta tentando di modificare l'annuncio
     * @param nuovoNome Il nuovo nome per l'annuncio (facoltativo)
     * @param nuovoPrezzo Il nuovo prezzo per l'annuncio (facoltativo, deve essere > 0)
     * @param nuoveParoleChiave Le nuove parole chiave (facoltative)
     * @param nuovaDataScadenza La nuova data di scadenza (solo per AnnuncioVendita, facoltativa)
     * @throws AnnuncioNonTrovatoException se l'annuncio non è stato trovato
     * @throws UtenteNonAutorizzatoException se l'utente non è l'autore dell'annuncio
     * @throws IllegalArgumentException se i parametri non sono validi
     * @throws DataScadenzaNonValidaException se la data di scadenza non è valida
     */
    public void modificaAnnuncio(
            String id,
            Utente utenteCorrente,
            String nuovoNome,
            Double nuovoPrezzo,
            Set<String> nuoveParoleChiave,
            LocalDate nuovaDataScadenza
    ) throws AnnuncioNonTrovatoException, UtenteNonAutorizzatoException, DataScadenzaNonValidaException {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("L'ID non può essere nullo o vuoto.");
        }
        if (utenteCorrente == null) {
            throw new UtenteNonAutorizzatoException("L'utente corrente non può essere nullo.");
        }

        Annuncio annuncioDaModificare = annunci.get(id);
        if (annuncioDaModificare == null) {
            throw new AnnuncioNonTrovatoException("Annuncio con ID: " + id + " non trovato.");
        }
        if (!annuncioDaModificare.getAutore().equals(utenteCorrente)) {
            throw new UtenteNonAutorizzatoException("Solo l'autore può modificare l'annuncio.");
        }

        if (nuovoNome != null && !nuovoNome.isBlank()) {
            annuncioDaModificare.setNomeAnnuncio(nuovoNome.trim());
        }

        if (nuovoPrezzo != null && nuovoPrezzo > 0) {
            annuncioDaModificare.setPrezzo(nuovoPrezzo);
        }

        if (nuoveParoleChiave != null && !nuoveParoleChiave.isEmpty()) {
            Set<String> paroleChiaveNormalizzate = nuoveParoleChiave.stream()
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());
            if (!paroleChiaveNormalizzate.isEmpty()) {
                annuncioDaModificare.setParoleChiave(paroleChiaveNormalizzate);
            }
        }

        if (annuncioDaModificare instanceof AnnuncioVendita && nuovaDataScadenza != null) {
            ((AnnuncioVendita) annuncioDaModificare).setDataScadenza(nuovaDataScadenza);
        }
    }


    /**
     * Recupera una lista di tutti gli annunci pubblicati da un utente specifico.
     *
     * @param utente L'utente di cui recuperare gli annunci.
     * @return Una lista di annunci pubblicati dall'utente specificato.
     */
    public List<Annuncio> getAnnunciPerAutore(Utente utente) {
        if (utente == null) {
            return new ArrayList<>();
        }
        return annunci.values().stream()
                .filter(annuncio -> annuncio.getAutore().equals(utente))
                .collect(Collectors.toList());
    }
    
    /**
     * Recupera un utente registrato tramite la sua email.
     *
     * @param email L'email dell'utente da recuperare.
     * @return L'oggetto Utente se trovato, altrimenti {@code null}.
     */
    public Utente getUtente(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }
        return utentiRegistrati.get(email.toLowerCase().trim());
    }
    
    /**
     * Recupera un annuncio tramite il suo ID.
     *
     * @param id L'ID dell'annuncio da recuperare.
     * @return L'oggetto Annuncio se trovato, altrimenti {@code null}.
     */
    public Annuncio getAnnuncioById(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }
        return annunci.get(id);
    }
    
    /**
     * Restituisce una copia della mappa di tutti gli utenti registrati.
     *
     * @return Una nuova mappa degli utenti registrati.
     */
    public Map<String, Utente> getUtentiRegistrati() {
        return new HashMap<>(utentiRegistrati);
    }
    
    /**
     * Restituisce una copia della mappa di tutti gli annunci presenti nella bacheca.
     *
     * @return Una nuova mappa degli annunci.
     */
    public Map<String, Annuncio> getAnnunci() {
        return new HashMap<>(annunci);
    }
    
    /**
     * Sostituisce la mappa degli utenti registrati con una nuova mappa.
     *
     * @param utenti La nuova mappa di utenti.
     */
    public void setUtenti(Map<String, Utente> utenti) {
        this.utentiRegistrati.clear();
        if (utenti != null) {
            // Normalizza le email come chiavi
            utenti.forEach((email, utente) -> {
                if (email != null && utente != null) {
                    this.utentiRegistrati.put(email.toLowerCase().trim(), utente);
                }
            });
        }
    }
    
    /**
     * Restituisce il numero totale di annunci nella bacheca.
     *
     * @return Il numero di annunci.
     */
    public int getNumeroAnnunci() {
        return annunci.size();
    }
    
    /**
     * Restituisce il numero totale di utenti registrati.
     *
     * @return Il numero di utenti registrati.
     */
    public int getNumeroUtentiRegistrati() {
        return utentiRegistrati.size();
    }
    
    /**
     * Restituisce un iteratore per scorrere tutti gli annunci della bacheca.
     *
     * @return Un iteratore per la collezione di annunci.
     */
    @Override
    public Iterator<Annuncio> iterator() {
        return annunci.values().iterator();
    }
}