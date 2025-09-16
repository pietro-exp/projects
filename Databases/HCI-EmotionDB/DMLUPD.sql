--=========================================
-- Operazioni DML di modifica più comuni
-- ===================================================

-- 1. Inserimento di un nuovo utente
-- (Operazione frequente: Registrazione nuovo utente)
INSERT INTO utente (nome, cognome, email, password, sesso, data_nascita, lingua, data_registrazione)
VALUES ('Luca', 'Bianchi', 'luca.bianchi@example.com', 'passwordSicura123', 'M', '1992-07-11', 'ITA', '2024-07-07');

-- 2. Aggiunta di un feedback a un’interazione
-- (Operazione frequente: Invio valutazione post-interazione)
INSERT INTO feedback (valutazione, commento, timestamp_feedback, id_utente, id_interazione)
VALUES (8.5, 'Molto utile e chiaro!', '2024-03-29', 10, 6);

-- 3. Modifica dello stato operativo di un robot
-- (Operazione frequente: Cambiamento di stato in seguito a manutenzione)
UPDATE robot
SET stato_operativo = 'Manutenzione'
WHERE id_robot = 57;

-- 4. Attivazione di una configurazione robot
-- (Operazione frequente: Applicazione preferenze utente)
UPDATE configurazione_robot
SET attiva = TRUE, data_applicazione = CURRENT_TIMESTAMP
WHERE id_configurazione = 8;

-- 5. Cambio della lingua preferita di un utente
-- (Operazione frequente: Modifica preferenze di interfaccia)
UPDATE utente
SET lingua = 'ENG'
WHERE id_utente = 6;

-- 6. Eliminazione di un contenuto multimediale obsoleto
-- (Operazione frequente: Pulizia dati non più rilevanti)
DELETE FROM contenuto_multimediale
WHERE id_contenuto = 3;

-- 7. Aggiornamento del tono di un messaggio robot
-- (Operazione frequente: Correzione stilistica)
UPDATE messaggio
SET tono = 'Empatico'
WHERE id_messaggio = 7;

-- 8. Rimozione di un feedback errato
-- (Operazione frequente: Cancellazione richiesta dall’utente)
DELETE FROM feedback
WHERE id_feedback = 4;

-- 9. Aggiornamento timestamp ultima connessione interfaccia
-- (Operazione frequente: aggiornamento dopo connessione)
UPDATE interfaccia_canale
SET ultima_connessione = CURRENT_TIMESTAMP
WHERE id_interfaccia = 2;

-- 10. Inserimento di una nuova configurazione privacy
INSERT INTO config_privacy (consenso_dati_personali, consenso_marketing, consenso_profilazione, data_consenso, data_scadenza, id_utente)
VALUES (TRUE, FALSE, TRUE, '2025-04-22', '2026-12-31', 8);

-- 11. Attivazione di sottotitoli e screen reader per esigenze di accessibilità
UPDATE accessibilita
SET sottotitoli_audio = TRUE, supporto_screen_reader = TRUE
WHERE id_utente = 10;

-- 12. Registrazione di una nuova emozione rilevata in una interazione
INSERT INTO emozione (nome, descrizione, intensita, categoria_plutchik, emozione_opposta, id_interazione, id_messaggio)
VALUES ('Gioia', 'L’utente ha mostrato un comportamento positivo e rilassato', 0.85, 'emozione primaria', 'Tristezza', 3, 7);

-- 13. Inserimento di una traduzione in una nuova lingua per un messaggio
INSERT INTO traduzione (testo_tradotto, lingua, id_messaggio)
VALUES ('You did a great job today!', 'ENG', 9);

-- 14. Modifica del tono del messaggio per migliorare l’esperienza utente
UPDATE messaggio
SET tono = 'Rassicurante'
WHERE id_messaggio = 9;

-- 15. Eliminazione di una sessione scaduta (per motivi di sicurezza o pulizia del DB)
DELETE FROM sessione
WHERE data_fine < CURRENT_DATE - INTERVAL '1 month';

-- 16. Correzione della data di ultima modifica nel profilo utente
UPDATE profilo_utente
SET ultima_modifica = CURRENT_DATE
WHERE id_profilo = 1;

-- 17. Inserimento di una metrica relativa a un’interazione
-- (Operazione frequente: Monitoraggio dell’interazione per analisi e apprendimento)

INSERT INTO metrica (nome, valore_metrica, descrizione, timestamp_metrica, tipo_metrica, errore, numero_errori, id_interazione)
VALUES (
  'TempoRisposta', 2.5, 'Tempo impiegato dal robot per rispondere all’utente',
  CURRENT_TIMESTAMP, 'Tempo di Risposta', 'Nessuno', 0, 5
);

-- 18. Rilevazione di un nuovo errore
--(Operazione di frequenza media)
INSERT INTO log_errori (
  codice_errore, livello_gravita, azione_correttiva,
  stack_trace, messaggio_errore, timestamp_errore,
  componente_sistema, stato_risoluzione, tecnico_assegnato, id_sessione
)
VALUES (
  'DB_CONN_02', 9.9, 'Riavvio modulo connessione',
  'Traceback (most recent call last)...', 'Connessione database fallita',
  CURRENT_TIMESTAMP, 'Database', 'In Lavorazione', 'Mario Rossi', 12
);

-- 19. Modifica delle priorità di una configurazione robot.
-- Frequenza media-alta
UPDATE configurazione_robot
SET priorita = 2
WHERE id_configurazione = 9;

-- 20. Aggiornamento emozione prevalente nello storico
-- Frequenza alta, usata dai modello IA per migliorare lo stile comunicativo in base all'emozioni dell'utente.
UPDATE storico_emotivo
SET emozione_prevalente = 'Fiducia'
WHERE id_storico = 4;

-- 21. Correzione del parametro di configurazione robot
-- Frequenza: media. L'utente può decidere di modificare la configurazione del robot desiderato.
UPDATE configurazione_robot
SET valore_parametro = 6
WHERE nome_parametro = 'volume_voce' AND id_robot = 60;

-- 22. Aggiornamento parametri autenticazione interfaccia.
-- L'utente può decidere di usare un altro metodo di autenticazione.
UPDATE interfaccia_canale
SET parametri_autenticazione = 'mfa'
WHERE id_interfaccia = 2;
