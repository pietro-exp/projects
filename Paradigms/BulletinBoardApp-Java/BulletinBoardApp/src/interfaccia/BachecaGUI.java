package interfaccia;

import dominio.Annuncio;
import dominio.AnnuncioAcquisto;
import dominio.AnnuncioVendita;
import dominio.Bacheca;
import dominio.Utente;
import eccezioni.AnnuncioNonTrovatoException;
import eccezioni.DataScadenzaNonValidaException;
import eccezioni.UtenteNonAutorizzatoException;
import persistence.BachecaPersistence;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * La classe BachecaGUI gestisce l'interfaccia grafica utente (GUI) per l'applicazione
 * della bacheca. Fornisce finestre, menu e componenti interattivi per
 * consentire all'utente di eseguire le operazioni supportate dalla classe
 * {@link Bacheca}.
 * <p>
 * L'interfaccia è costruita usando il framework Swing e un {@link CardLayout}
 * per gestire i diversi pannelli (menu, inserimento, ricerca, ecc.).
 *
 * @see Bacheca
 * * @author Pietro Mezzatesta
 */
public class BachecaGUI {

    private final Bacheca bacheca;
    private final Utente utenteCorrente;
    private JFrame frame;
    private JTextArea displayArea;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // Campi per l'inserimento annuncio
    private JTextField tipoField, nomeArticoloField, prezzoField, dataScadenzaField, paroleChiaveField;
    
    // Campi per la rimozione annuncio
    private JTextField idRimozioneField;

    // Campi per la modifica annuncio
    private JTextField idModificaField, nuovoNomeModificaField, nuovoPrezzoModificaField, nuoveParoleChiaveModificaField, nuovaDataScadenzaModificaField;

    /**
     * Costruttore per inizializzare l'interfaccia grafica.
     *
     * @param bacheca L'istanza della bacheca su cui operare.
     * @param utenteCorrente L'utente attualmente autenticato.
     */
    public BachecaGUI(Bacheca bacheca, Utente utenteCorrente) {
        this.bacheca = bacheca;
        this.utenteCorrente = utenteCorrente;
    }

    /**
     * Crea e rende visibile la GUI principale dell'applicazione.
     * Imposta il frame principale, il layout e i pannelli per le diverse funzionalità.
     */
    public void createAndShowGUI() {
        frame = new JFrame("Bacheca Annunci - Benvenuto, " + utenteCorrente.getNome());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel menuPanel = createMenuPanel();
        JPanel inserimentoPanel = createInserimentoPanel();
        JPanel ricercaPanel = createRicercaPanel();
        JPanel rimozionePanel = createRimozionePanel();
        JPanel mieiAnnunciPanel = createMieiAnnunciPanel();
        JPanel modificaAnnuncioPanel = createModificaAnnuncioPanel();

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(inserimentoPanel, "Inserisci Annuncio");
        mainPanel.add(ricercaPanel, "Cerca Annunci");
        mainPanel.add(rimozionePanel, "Rimuovi Annuncio");
        mainPanel.add(mieiAnnunciPanel, "Miei Annunci");
        mainPanel.add(modificaAnnuncioPanel, "Modifica Annuncio");

        displayArea = new JTextArea(15, 60);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        
        frame.add(mainPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        cardLayout.show(mainPanel, "Menu");
        frame.setVisible(true);
    }
    
    /**
     * Crea il pannello del menu principale con i bottoni per le varie operazioni.
     *
     * @return Un {@link JPanel} configurato come menu.
     */
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton inserisciButton = new JButton("Inserisci un nuovo annuncio");
        JButton cercaButton = new JButton("Cerca articoli");
        JButton rimuoviButton = new JButton("Rimuovi un annuncio");
        JButton mieiAnnunciButton = new JButton("Visualizza i miei annunci");
        JButton modificaAnnuncioButton = new JButton("Modifica un mio annuncio");
        JButton esciButton = new JButton("Esci e salva");
        
        inserisciButton.addActionListener(e -> {
            clearInputFields();
            cardLayout.show(mainPanel, "Inserisci Annuncio");
        });
        cercaButton.addActionListener(e -> {
            clearInputFields();
            cardLayout.show(mainPanel, "Cerca Annunci");
        });
        rimuoviButton.addActionListener(e -> {
            clearInputFields();
            cardLayout.show(mainPanel, "Rimuovi Annuncio");
        });
        mieiAnnunciButton.addActionListener(e -> {
            clearInputFields();
            gestisciMieiAnnunci();
            cardLayout.show(mainPanel, "Miei Annunci");
        });
        modificaAnnuncioButton.addActionListener(e -> {
            clearInputFields();
            cardLayout.show(mainPanel, "Modifica Annuncio");
        });
        esciButton.addActionListener(e -> {
            try {
                BachecaPersistence.salvaBacheca(bacheca, "bacheca.ser");
                BachecaPersistence.salvaUtenti(bacheca.getUtentiRegistrati());
                JOptionPane.showMessageDialog(frame, "Bacheca salvata. Arrivederci!");
                System.exit(0);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Errore nel salvataggio: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        panel.add(inserisciButton);
        panel.add(cercaButton);
        panel.add(rimuoviButton);
        panel.add(mieiAnnunciButton);
        panel.add(modificaAnnuncioButton);
        panel.add(esciButton);
        return panel;
    }
    
    /**
     * Crea il pannello per l'inserimento di un nuovo annuncio.
     *
     * @return Un {@link JPanel} per l'inserimento di annunci.
     */
    private JPanel createInserimentoPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        
        panel.add(new JLabel("Tipo (A/V):"));
        tipoField = new JTextField();
        panel.add(tipoField);
        
        panel.add(new JLabel("Nome Articolo:"));
        nomeArticoloField = new JTextField();
        panel.add(nomeArticoloField);
        
        panel.add(new JLabel("Prezzo:"));
        prezzoField = new JTextField();
        panel.add(prezzoField);
        
        panel.add(new JLabel("Data Scadenza (YYYY-MM-DD):"));
        dataScadenzaField = new JTextField();
        panel.add(dataScadenzaField);
        
        panel.add(new JLabel("Parole Chiave:"));
        paroleChiaveField = new JTextField();
        panel.add(paroleChiaveField);
        
        JButton salvaButton = new JButton("Salva Annuncio");
        salvaButton.addActionListener(e -> gestisciNuovoAnnuncio());
        panel.add(salvaButton);
        
        JButton tornaAlMenuButton = new JButton("Torna al menu");
        tornaAlMenuButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        panel.add(tornaAlMenuButton);

        return panel;
    }

    /**
     * Crea il pannello per la ricerca di annunci.
     *
     * @return Un {@link JPanel} per la ricerca di annunci.
     */
    private JPanel createRicercaPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        panel.add(new JLabel("Parole Chiave:"));
        JTextField ricercaField = new JTextField(20);
        panel.add(ricercaField);
        
        JButton cercaButton = new JButton("Cerca");
        cercaButton.addActionListener(e -> {
            String query = ricercaField.getText();
            gestisciRicercaAnnunci(query);
        });
        panel.add(cercaButton);
        
        JButton tornaAlMenuButton = new JButton("Torna al menu");
        tornaAlMenuButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        panel.add(tornaAlMenuButton);
        
        return panel;
    }
    
    /**
     * Crea il pannello per la rimozione di un annuncio.
     *
     * @return Un {@link JPanel} per la rimozione di annunci.
     */
    private JPanel createRimozionePanel() {
        JPanel panel = new JPanel(new FlowLayout());

        panel.add(new JLabel("ID annuncio da rimuovere:"));
        idRimozioneField = new JTextField(20);
        panel.add(idRimozioneField);

        JButton rimuoviButton = new JButton("Rimuovi");
        rimuoviButton.addActionListener(e -> gestisciRimozioneAnnuncio());
        panel.add(rimuoviButton);

        JButton tornaAlMenuButton = new JButton("Torna al menu");
        tornaAlMenuButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        panel.add(tornaAlMenuButton);

        return panel;
    }

    /**
     * Crea il pannello per visualizzare gli annunci dell'utente corrente.
     *
     * @return Un {@link JPanel} per la visualizzazione dei propri annunci.
     */
    private JPanel createMieiAnnunciPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton mostraMieiAnnunciButton = new JButton("Mostra i miei annunci");
        mostraMieiAnnunciButton.addActionListener(e -> gestisciMieiAnnunci());
        panel.add(mostraMieiAnnunciButton);

        JButton tornaAlMenuButton = new JButton("Torna al menu");
        tornaAlMenuButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        panel.add(tornaAlMenuButton);

        return panel;
    }
    
    /**
     * Crea il pannello per la modifica di un annuncio.
     *
     * @return Un {@link JPanel} per la modifica di annunci.
     */
    private JPanel createModificaAnnuncioPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        panel.add(new JLabel("ID annuncio da modificare:"));
        idModificaField = new JTextField();
        panel.add(idModificaField);

        panel.add(new JLabel("Nuovo nome articolo:"));
        nuovoNomeModificaField = new JTextField();
        panel.add(nuovoNomeModificaField);

        panel.add(new JLabel("Nuovo prezzo:"));
        nuovoPrezzoModificaField = new JTextField();
        panel.add(nuovoPrezzoModificaField);

        panel.add(new JLabel("Nuove parole chiave:"));
        nuoveParoleChiaveModificaField = new JTextField();
        panel.add(nuoveParoleChiaveModificaField);

        panel.add(new JLabel("Nuova data di scadenza (YYYY-MM-DD):"));
        nuovaDataScadenzaModificaField = new JTextField();
        panel.add(nuovaDataScadenzaModificaField);

        JButton modificaButton = new JButton("Modifica");
        modificaButton.addActionListener(e -> gestisciModificaAnnuncio());
        panel.add(modificaButton);

        JButton tornaAlMenuButton = new JButton("Torna al menu");
        tornaAlMenuButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        panel.add(tornaAlMenuButton);

        return panel;
    }

    // === Metodi Controllore ===
    
    /**
     * Gestisce il salvataggio di un nuovo annuncio in base all'input dell'utente.
     *
     * @see Bacheca#aggiungiAnnuncio(Annuncio)
     */
    private void gestisciNuovoAnnuncio() {
        try {
            String tipo = tipoField.getText().trim().toUpperCase();
            String nomeArticolo = nomeArticoloField.getText();
            double prezzo = Double.parseDouble(prezzoField.getText());
            String dataScadenzaStr = dataScadenzaField.getText();
            String paroleChiaveInput = paroleChiaveField.getText();
            Set<String> paroleChiaveSet = Arrays.stream(paroleChiaveInput.split(",")).map(String::trim).collect(Collectors.toSet());

            Annuncio nuovoAnnuncio;

            if ("V".equals(tipo)) {
                LocalDate dataScadenza = LocalDate.parse(dataScadenzaStr);
                nuovoAnnuncio = new AnnuncioVendita(utenteCorrente, nomeArticolo, prezzo, paroleChiaveSet, dataScadenza);
                bacheca.aggiungiAnnuncio(nuovoAnnuncio);
                displayArea.setText("Annuncio di vendita aggiunto con successo.\nID: " + nuovoAnnuncio.getId());
            } else if ("A".equals(tipo)) {
                nuovoAnnuncio = new AnnuncioAcquisto(utenteCorrente, nomeArticolo, prezzo, paroleChiaveSet);
                bacheca.aggiungiAnnuncio(nuovoAnnuncio);
                displayArea.setText("Annuncio di acquisto aggiunto con successo.\nID: " + nuovoAnnuncio.getId());
                
                List<Annuncio> annunciCorrelati = bacheca.cercaAnnunci(paroleChiaveSet, utenteCorrente);
                if (!annunciCorrelati.isEmpty()) {
                    displayArea.append("\n=== Articoli che potrebbero interessarti ===\n");
                    for (Annuncio annuncio : annunciCorrelati) {
                        displayArea.append("ID: " + annuncio.getId() + ", Nome: " + annuncio.getNomeAnnuncio() + "\n");
                    }
                }
            } else {
                displayArea.setText("Tipo di annuncio non valido. Operazione annullata.");
            }
            // Aggiunto per pulire i campi dopo un salvataggio riuscito
            clearInputFields();
        } catch (NumberFormatException | DateTimeParseException e) {
            displayArea.setText("Errore di formato dati. Assicurati che prezzo e data siano corretti.");
        } catch (DataScadenzaNonValidaException e) {
            displayArea.setText(e.getMessage());
        }
    }
    
    /**
    * Gestisce la rimozione di un annuncio dopo la pressione del pulsante "Rimuovi".
    *
    * @throws AnnuncioNonTrovatoException se l'annuncio specificato non viene trovato.
    * @throws UtenteNonAutorizzatoException se l'utente corrente non è l'autore dell'annuncio.
    * @see Bacheca#rimuoviAnnuncio(Annuncio, Utente)
    */
    private void gestisciRimozioneAnnuncio() {
        String id = idRimozioneField.getText().trim();
        Annuncio annuncioDaRimuovere = bacheca.getAnnuncioById(id);

        try {
            if (annuncioDaRimuovere == null) {
                throw new AnnuncioNonTrovatoException("Annuncio con ID " + id + " non trovato.");
            }
            bacheca.rimuoviAnnuncio(annuncioDaRimuovere, utenteCorrente);
            
            // Visualizza i dettagli dell'annuncio rimosso
            displayArea.setText("Annuncio rimosso con successo:\n");
            displayArea.append("ID: " + annuncioDaRimuovere.getId() + "\n");
            displayArea.append("Nome: " + annuncioDaRimuovere.getNomeAnnuncio() + "\n");
            displayArea.append("Prezzo: " + annuncioDaRimuovere.getPrezzo() + "\n");
            displayArea.append("Parole chiave: " + annuncioDaRimuovere.getParoleChiave() + "\n");
            
        } catch(AnnuncioNonTrovatoException | UtenteNonAutorizzatoException e) {
            displayArea.setText("Errore: " + e.getMessage());
        }
        // Pulisci il campo dopo l'operazione
        idRimozioneField.setText("");
    }

    /**
     * Gestisce la ricerca di annunci in base alle parole chiave inserite dall'utente.
     *
     * @param query Le parole chiave da cercare, separate da virgola.
     * @see Bacheca#cercaAnnunci(Set, Utente)
     */
    private void gestisciRicercaAnnunci(String query) {
        Set<String> paroleChiaveSet = Arrays.stream(query.split(","))
                                            .map(String::trim)
                                            .collect(Collectors.toSet());
        
        List<Annuncio> risultati = bacheca.cercaAnnunci(paroleChiaveSet, utenteCorrente);
        
        if (risultati.isEmpty()) {
            displayArea.setText("Nessun annuncio trovato con quelle parole chiave.");
        } else {
            displayArea.setText("=== Risultati della ricerca ===\n");
            for (Annuncio annuncio : risultati) {
                displayArea.append("ID: " + annuncio.getId() + "\n");
                displayArea.append("Nome: " + annuncio.getNomeAnnuncio() + "\n");
                displayArea.append("Prezzo: " + annuncio.getPrezzo() + "\n");
                displayArea.append("Autore: " + annuncio.getAutore().getNome() + " " + annuncio.getAutore().getCognome() + "\n");
                if (annuncio instanceof AnnuncioVendita) {
                    AnnuncioVendita annuncioVendita = (AnnuncioVendita) annuncio;
                    displayArea.append("Data di scadenza: " + annuncioVendita.getDataScadenza() + "\n");
                }
                displayArea.append("---\n");
            }
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
            displayArea.setText("Non hai ancora pubblicato nessun annuncio.");
        } else {
            displayArea.setText("=== I miei annunci ===\n");
            for (Annuncio annuncio : mieiAnnunci) {
            	 if (annuncio instanceof AnnuncioVendita) {
                     AnnuncioVendita annuncioVendita = (AnnuncioVendita) annuncio;
                     displayArea.append("Tipo: Annuncio di Vendita\n");
                     displayArea.append("Data di scadenza: " + annuncioVendita.getDataScadenza() + "\n");
            	 }
            	 else if (annuncio instanceof AnnuncioAcquisto) {
                     displayArea.append("Tipo: Annuncio di Acquisto\n");
                 }
                displayArea.append("Nome: " + annuncio.getNomeAnnuncio() + "\n");
                displayArea.append("Prezzo: " + annuncio.getPrezzo() + "\n");
                displayArea.append("Parole chiave: " + annuncio.getParoleChiave() + "\n");
                displayArea.append("ID: " + annuncio.getId() + "\n");
                displayArea.append("---\n");
            }
        }
    }

    /**
     * Gestisce la modifica di un annuncio esistente in base all'input dell'utente.
     *
     * @throws AnnuncioNonTrovatoException se l'annuncio specificato non viene trovato.
     * @throws UtenteNonAutorizzatoException se l'utente corrente non è l'autore dell'annuncio.
     * @throws NumberFormatException se il prezzo inserito non è un numero valido.
     * @throws DataScadenzaNonValidaException Nel caso in cui il formato della data non sia corretto.
     * @see Bacheca#modificaAnnuncio(Annuncio, Utente, String, double, Set)
     */
    private void gestisciModificaAnnuncio() {
        String id = idModificaField.getText().trim();
        String nuovoNome = nuovoNomeModificaField.getText();
        String nuovoPrezzoStr = nuovoPrezzoModificaField.getText();
        String nuoveParoleChiaveStr = nuoveParoleChiaveModificaField.getText();
        String nuovaDataScadenzaStr = nuovaDataScadenzaModificaField.getText();

        try {
            Annuncio annuncioDaModificare = bacheca.getAnnuncioById(id);
            if (annuncioDaModificare == null) {
                JOptionPane.showMessageDialog(frame, "Annuncio con ID " + id + " non trovato.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!annuncioDaModificare.getAutore().equals(utenteCorrente)) {
                JOptionPane.showMessageDialog(frame, "Puoi modificare solo i tuoi annunci.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Logica di aggiornamento dei campi
            String nome = nuovoNome.isEmpty() ? annuncioDaModificare.getNomeAnnuncio() : nuovoNome;
            double prezzo = nuovoPrezzoStr.isEmpty() ? annuncioDaModificare.getPrezzo() : Double.parseDouble(nuovoPrezzoStr);
            Set<String> paroleChiave = nuoveParoleChiaveStr.isEmpty() ? annuncioDaModificare.getParoleChiave() : 
                Arrays.stream(nuoveParoleChiaveStr.split(",")).map(String::trim).collect(Collectors.toSet());
            
            // Gestione data scadenza (solo per AnnuncioVendita)
            LocalDate nuovaDataScadenza = null;
            if (annuncioDaModificare instanceof AnnuncioVendita && !nuovaDataScadenzaStr.isEmpty()) {
                nuovaDataScadenza = LocalDate.parse(nuovaDataScadenzaStr);
            }

            // Chiama il metodo modificato con il nuovo parametro
            bacheca.modificaAnnuncio(id, utenteCorrente, nome, prezzo, paroleChiave, nuovaDataScadenza);
            
            JOptionPane.showMessageDialog(frame, "Annuncio modificato con successo!");
            clearInputFields();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Prezzo non valido.", "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(frame, "Formato data non valido (usa YYYY-MM-DD).", "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (DataScadenzaNonValidaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (AnnuncioNonTrovatoException | UtenteNonAutorizzatoException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearInputFields() {
        tipoField.setText("");
        nomeArticoloField.setText("");
        prezzoField.setText("");
        dataScadenzaField.setText("");
        paroleChiaveField.setText("");
        idRimozioneField.setText("");
        idModificaField.setText("");
        nuovoNomeModificaField.setText("");
        nuovoPrezzoModificaField.setText("");
        nuoveParoleChiaveModificaField.setText("");
        nuovaDataScadenzaModificaField.setText("");
    }
}
