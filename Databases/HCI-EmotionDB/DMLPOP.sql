--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

-- Started on 2025-07-06 18:15:02

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5092 (class 0 OID 25350)
-- Dependencies: 251
-- Data for Name: utente; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.utente (id_utente, nome, cognome, email, password, sesso, data_nascita, lingua, data_registrazione) VALUES (6, 'Marco', 'Rossi', 'marco.rossi@email.com', 'password123', 'M', '1990-05-20', 'it', '2025-06-12');
INSERT INTO public.utente (id_utente, nome, cognome, email, password, sesso, data_nascita, lingua, data_registrazione) VALUES (7, 'Elena', 'Bianchi', 'elena.bianchi@email.com', 'securePass1', 'F', '1988-11-12', 'en', '2025-06-12');
INSERT INTO public.utente (id_utente, nome, cognome, email, password, sesso, data_nascita, lingua, data_registrazione) VALUES (8, 'Mario', 'Verdi', 'mario.verdi@email.com', 'password1234', 'M', '2000-12-02', 'sp', '2025-06-12');
INSERT INTO public.utente (id_utente, nome, cognome, email, password, sesso, data_nascita, lingua, data_registrazione) VALUES (9, 'Carlo', 'Ferrari', 'carlo.ferrari@email.com', 'securePassword7', 'M', '2004-04-04', 'de', '2025-06-12');
INSERT INTO public.utente (id_utente, nome, cognome, email, password, sesso, data_nascita, lingua, data_registrazione) VALUES (10, 'Francesco', 'Greco', 'frencesco.greco@email.com', 'pass1234567', 'M', '2001-08-22', 'en', '2025-06-12');
INSERT INTO public.utente (id_utente, nome, cognome, email, password, sesso, data_nascita, lingua, data_registrazione) VALUES (11, 'Marta', 'Russo', 'marta.russo@email.com', 'prova1234', 'F', '2011-02-02', 'it', '2025-06-12');
INSERT INTO public.utente (id_utente, nome, cognome, email, password, sesso, data_nascita, lingua, data_registrazione) VALUES (12, 'Giulia', 'Bortoli', 'giulia.bortoli@email.com', 'giulia1234', 'F', '20003-03-29', 'fr', '2025-06-12');
INSERT INTO public.utente (id_utente, nome, cognome, email, password, sesso, data_nascita, lingua, data_registrazione) VALUES (13, 'Caterina', 'Romano', 'caterina.romano@email.com', 'Cate12345', 'F', '1999-01-09', 'it', '2025-06-12');
INSERT INTO public.utente (id_utente, nome, cognome, email, password, sesso, data_nascita, lingua, data_registrazione) VALUES (14, 'Sandro', 'Boni', 'sandro.boni@email.com', 'passwordSicura345', 'M', '2005-12-25', 'sp', '2025-06-12');
INSERT INTO public.utente (id_utente, nome, cognome, email, password, sesso, data_nascita, lingua, data_registrazione) VALUES (15, 'Teresa', 'Ricci', 'teresa.ricci@email.com', 'ricciTeresa123', 'F', '2001-12-31', 'en', '2025-06-12');


--
-- TOC entry 5058 (class 0 OID 25179)
-- Dependencies: 217
-- Data for Name: accessibilita; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.accessibilita (id_accessibilita, contrasto_alto, dimensioni_testo, data_configurazione, riduzione_animazioni, navigazione_tastiera, sottotitoli_audio, lingua_segni, supporto_screen_reader, note_speciali, id_utente) VALUES (1, true, 'grande', '2024-06-10', true, true, false, false, true, 'Ipovisione', 13);
INSERT INTO public.accessibilita (id_accessibilita, contrasto_alto, dimensioni_testo, data_configurazione, riduzione_animazioni, navigazione_tastiera, sottotitoli_audio, lingua_segni, supporto_screen_reader, note_speciali, id_utente) VALUES (2, false, 'normale', '2024-06-01', false, false, false, false, false, NULL, 10);
INSERT INTO public.accessibilita (id_accessibilita, contrasto_alto, dimensioni_testo, data_configurazione, riduzione_animazioni, navigazione_tastiera, sottotitoli_audio, lingua_segni, supporto_screen_reader, note_speciali, id_utente) VALUES (3, true, 'piccolo', '2024-06-03', true, true, true, true, true, 'Utente con disabilità visive e uditive', 7);
INSERT INTO public.accessibilita (id_accessibilita, contrasto_alto, dimensioni_testo, data_configurazione, riduzione_animazioni, navigazione_tastiera, sottotitoli_audio, lingua_segni, supporto_screen_reader, note_speciali, id_utente) VALUES (4, false, 'grande', '2024-06-05', false, true, true, false, true, 'Richiede sottotitoli', 11);
INSERT INTO public.accessibilita (id_accessibilita, contrasto_alto, dimensioni_testo, data_configurazione, riduzione_animazioni, navigazione_tastiera, sottotitoli_audio, lingua_segni, supporto_screen_reader, note_speciali, id_utente) VALUES (5, true, 'normale', '2024-06-06', false, false, false, true, false, NULL, 14);


--
-- TOC entry 5060 (class 0 OID 25193)
-- Dependencies: 219
-- Data for Name: config_privacy; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.config_privacy (id_config, consenso_dati_personali, consenso_marketing, consenso_profilazione, data_consenso, data_scadenza, id_utente) VALUES (4, true, false, false, '2024-06-10', '2024-12-31', 14);
INSERT INTO public.config_privacy (id_config, consenso_dati_personali, consenso_marketing, consenso_profilazione, data_consenso, data_scadenza, id_utente) VALUES (5, true, true, false, '2024-03-22', '2024-09-22', 10);
INSERT INTO public.config_privacy (id_config, consenso_dati_personali, consenso_marketing, consenso_profilazione, data_consenso, data_scadenza, id_utente) VALUES (1, true, true, true, '2024-06-01', '2025-06-01', 11);
INSERT INTO public.config_privacy (id_config, consenso_dati_personali, consenso_marketing, consenso_profilazione, data_consenso, data_scadenza, id_utente) VALUES (2, true, false, true, '2024-05-20', '2025-05-20', 13);
INSERT INTO public.config_privacy (id_config, consenso_dati_personali, consenso_marketing, consenso_profilazione, data_consenso, data_scadenza, id_utente) VALUES (3, false, false, false, '2024-04-15', '2025-05-15', 7);


--
-- TOC entry 5084 (class 0 OID 25318)
-- Dependencies: 243
-- Data for Name: robot; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.robot (id_robot, nome_robot, modello, versione_software, data_attivazione, livello_autonomia, localizzazione, ultimo_update, ore_operative, stato_operativo, capacita_multimodale) VALUES (52, 'EVA', 'XR-2000', 'v1.5.3', '2024-01-01 00:00:00', '2 - assistenza parziale', 'San Francisco', '2025-06-12', 250, 'On', 'visivo, testuale, uditivo');
INSERT INTO public.robot (id_robot, nome_robot, modello, versione_software, data_attivazione, livello_autonomia, localizzazione, ultimo_update, ore_operative, stato_operativo, capacita_multimodale) VALUES (53, 'ALFRED', 'XR-1500', 'v1.4.9', '2023-10-15 00:00:00', '3 - autonomia condizionata', 'Roma', '2025-06-12', 400, 'Manutenzione', 'visivo, propriocettivo, uditivo');
INSERT INTO public.robot (id_robot, nome_robot, modello, versione_software, data_attivazione, livello_autonomia, localizzazione, ultimo_update, ore_operative, stato_operativo, capacita_multimodale) VALUES (54, 'GCT', 'GC-1', 'v1.8', '2023-04-02 00:00:00', '5 - autonomia completa', 'Milano', '2023-02-04', 600, 'Standby', 'propriocettivo, visivo, uditivo, testuale');
INSERT INTO public.robot (id_robot, nome_robot, modello, versione_software, data_attivazione, livello_autonomia, localizzazione, ultimo_update, ore_operative, stato_operativo, capacita_multimodale) VALUES (55, 'ANX', 'ANX2.0', 'v1.1', '2023-10-15 00:00:00', '0 - nessuna autonomia', 'Varsavia', '2024-12-02', 100, 'Manutenzione', 'uditivo, testuale');
INSERT INTO public.robot (id_robot, nome_robot, modello, versione_software, data_attivazione, livello_autonomia, localizzazione, ultimo_update, ore_operative, stato_operativo, capacita_multimodale) VALUES (56, 'IMIX', 'IMI-201', 'v2.4', '2024-01-01 00:00:00', '4 - alta autonomia', 'Mosca', '2025-06-10', 200, 'Off', 'propriocettivo');
INSERT INTO public.robot (id_robot, nome_robot, modello, versione_software, data_attivazione, livello_autonomia, localizzazione, ultimo_update, ore_operative, stato_operativo, capacita_multimodale) VALUES (57, 'RYZO', 'RR-202', 'v9.3.2', '2023-10-15 00:00:00', '5 - autonomia completa', 'Tokyo', '2025-06-05', 20, 'Paused', 'testuale');
INSERT INTO public.robot (id_robot, nome_robot, modello, versione_software, data_attivazione, livello_autonomia, localizzazione, ultimo_update, ore_operative, stato_operativo, capacita_multimodale) VALUES (58, 'TERRY', 'T-9', 'v1.3.0', '2024-01-01 00:00:00', '3 - autonomia condizionata', 'Mumbai', '2024-08-26', 230, 'Emergenza', 'tattile');
INSERT INTO public.robot (id_robot, nome_robot, modello, versione_software, data_attivazione, livello_autonomia, localizzazione, ultimo_update, ore_operative, stato_operativo, capacita_multimodale) VALUES (59, 'POPPY', 'P-1532', 'v8.1', '2023-10-15 00:00:00', '2 - assistenza parziale', 'Brasilia', '2025-06-12', 120, 'Manutenzione', 'uditivo');
INSERT INTO public.robot (id_robot, nome_robot, modello, versione_software, data_attivazione, livello_autonomia, localizzazione, ultimo_update, ore_operative, stato_operativo, capacita_multimodale) VALUES (60, 'XIA', 'XIA-2024', 'v3.2.3', '2024-01-01 00:00:00', '1 - supporto umano', 'Madrid', '2025-03-23', 2, 'Error', 'visivo, uditivo');
INSERT INTO public.robot (id_robot, nome_robot, modello, versione_software, data_attivazione, livello_autonomia, localizzazione, ultimo_update, ore_operative, stato_operativo, capacita_multimodale) VALUES (61, 'ZYX', 'ZYX-2026', 'v1.2.1', '2023-10-15 00:00:00', '0 - nessuna autonomia', 'Dubai', '2025-01-23', 1000, 'Standby', 'visivo,tattile');


--
-- TOC entry 5062 (class 0 OID 25202)
-- Dependencies: 221
-- Data for Name: configurazione_robot; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.configurazione_robot (id_configurazione, tipo_configurazione, nome_parametro, valore_parametro, modalita_conversazione, priorita, attiva, data_applicazione, id_robot, id_utente) VALUES (6, 'comunicazione', 'volume_voce', 7, 'Conversazione', 5, true, '2024-06-10 10:00:00', 60, 7);
INSERT INTO public.configurazione_robot (id_configurazione, tipo_configurazione, nome_parametro, valore_parametro, modalita_conversazione, priorita, attiva, data_applicazione, id_robot, id_utente) VALUES (7, 'emotiva', 'tono_empatico', 8, 'Empatia', 6, true, '2024-06-11 09:30:00', 52, 7);
INSERT INTO public.configurazione_robot (id_configurazione, tipo_configurazione, nome_parametro, valore_parametro, modalita_conversazione, priorita, attiva, data_applicazione, id_robot, id_utente) VALUES (8, 'comportamento', 'risposta_rapida', 5, 'Supporto', 3, false, NULL, 58, 13);
INSERT INTO public.configurazione_robot (id_configurazione, tipo_configurazione, nome_parametro, valore_parametro, modalita_conversazione, priorita, attiva, data_applicazione, id_robot, id_utente) VALUES (9, 'educativa', 'livello_dettaglio', 9, 'Educazione', 8, true, '2024-06-12 08:45:00', 57, 14);
INSERT INTO public.configurazione_robot (id_configurazione, tipo_configurazione, nome_parametro, valore_parametro, modalita_conversazione, priorita, attiva, data_applicazione, id_robot, id_utente) VALUES (10, 'intrattenimento', 'giochi_attivi', 6, 'Intrattenimento', 4, false, NULL, 54, 10);


--
-- TOC entry 5086 (class 0 OID 25326)
-- Dependencies: 245
-- Data for Name: sessione; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sessione (id_sessione, token, data_inizio, data_fine, id_utente) VALUES (9, 'eyJhbGciOiJIUzI1NiI345', '2025-06-15 12:39:32.427523', NULL, 7);
INSERT INTO public.sessione (id_sessione, token, data_inizio, data_fine, id_utente) VALUES (10, 'QwOErVcertKIUzKLnjHU02', '2025-06-04 12:23:33.23', '2025-06-05 23:12:42.3', 13);
INSERT INTO public.sessione (id_sessione, token, data_inizio, data_fine, id_utente) VALUES (11, 'jkbBhbUCVtyuxcGCVHcv12', '2024-05-11 23:34:43.12', '2024-05-12 12:56:27.77', 11);
INSERT INTO public.sessione (id_sessione, token, data_inizio, data_fine, id_utente) VALUES (12, 'JBByuVYJHYVhvHhbH1232B', '2025-06-07 22:11:22.56', '2025-06-08 11:22:56.45', 14);
INSERT INTO public.sessione (id_sessione, token, data_inizio, data_fine, id_utente) VALUES (13, 'jbedBUiOIijJNn123KKn29', '2023-04-22 10:30:54.45', '2023-04-23 21:09:17.12', 10);


--
-- TOC entry 5070 (class 0 OID 25238)
-- Dependencies: 229
-- Data for Name: interazione; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.interazione (id_interazione, "timestamp", contenuto_input, tipo_input, contesto_conversazione, id_sessione, id_robot) VALUES (2, '2025-06-15 12:57:01.533924', 'Ciao, come ti senti oggi?', 'testo', 'accoglienza', 9, 52);
INSERT INTO public.interazione (id_interazione, "timestamp", contenuto_input, tipo_input, contesto_conversazione, id_sessione, id_robot) VALUES (3, '2025-06-10 10:22:33', '😢', 'emoji', 'emozione tristezza', 10, 60);
INSERT INTO public.interazione (id_interazione, "timestamp", contenuto_input, tipo_input, contesto_conversazione, id_sessione, id_robot) VALUES (4, '2025-06-10 10:23:45', 'Riproduci musica rilassante', 'voce', 'stress', 11, 58);
INSERT INTO public.interazione (id_interazione, "timestamp", contenuto_input, tipo_input, contesto_conversazione, id_sessione, id_robot) VALUES (5, '2025-06-10 10:24:17', 'Voglio meditare', 'testo', 'richiesta benessere', 12, 57);
INSERT INTO public.interazione (id_interazione, "timestamp", contenuto_input, tipo_input, contesto_conversazione, id_sessione, id_robot) VALUES (6, '2025-06-10 10:25:09', 'Gesto: mano alzata', 'gesto', 'interazione fisica', 13, 54);


--
-- TOC entry 5064 (class 0 OID 25213)
-- Dependencies: 223
-- Data for Name: contenuto_multimediale; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.contenuto_multimediale (id_contenuto, tipo_contenuto, data_caricamento, percorso_file, descrizione, id_interazione) VALUES (1, 'Audio', '2024-06-10 00:00:00', '/media/audio/saluto_iniziale.mp3', 'Audio di benvenuto del robot', 2);
INSERT INTO public.contenuto_multimediale (id_contenuto, tipo_contenuto, data_caricamento, percorso_file, descrizione, id_interazione) VALUES (2, 'Immagine', '2024-06-10 00:00:00', '/media/images/emoji_tristezza.png', 'Emoji visualizzata per empatizzare con l’utente triste', 3);
INSERT INTO public.contenuto_multimediale (id_contenuto, tipo_contenuto, data_caricamento, percorso_file, descrizione, id_interazione) VALUES (3, 'Audio', '2024-06-11 00:00:00', '/media/audio/playlist_rilassante.mp3', 'Brano rilassante riprodotto su richiesta', 4);
INSERT INTO public.contenuto_multimediale (id_contenuto, tipo_contenuto, data_caricamento, percorso_file, descrizione, id_interazione) VALUES (4, 'Video', '2024-06-12 00:00:00', '/media/video/congratulazioni.mp4', 'Video motivazionale mostrato dopo il feedback positivo', 5);
INSERT INTO public.contenuto_multimediale (id_contenuto, tipo_contenuto, data_caricamento, percorso_file, descrizione, id_interazione) VALUES (5, 'Contenuto interattivo', '2024-06-12 00:00:00', '/media/respirazione_guidata/index.html', 'Esercizio interattivo di respirazione proposto in caso di stress', 6);


--
-- TOC entry 5076 (class 0 OID 25275)
-- Dependencies: 235
-- Data for Name: messaggio; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.messaggio (id_messaggio, testo, lingua, tono, timestamp_messaggio, contesto_conversazione, intensita_emozione, id_robot, tipo_messaggio, tipo_output) VALUES (6, 'Benvenuto! Come ti senti oggi?', 'ITA', 'Empatico', '2025-06-15 09:00:00', 'accoglienza', 0.60, 52, 'Risposta', 'Testuale');
INSERT INTO public.messaggio (id_messaggio, testo, lingua, tono, timestamp_messaggio, contesto_conversazione, intensita_emozione, id_robot, tipo_messaggio, tipo_output) VALUES (7, 'Mi dispiace che tu ti senta triste 😔', 'ITA', 'Rassicurante', '2025-06-15 09:05:00', 'supporto emotivo', 0.90, 60, 'Risposta', 'Testuale');
INSERT INTO public.messaggio (id_messaggio, testo, lingua, tono, timestamp_messaggio, contesto_conversazione, intensita_emozione, id_robot, tipo_messaggio, tipo_output) VALUES (8, 'Avvio la tua playlist rilassante.', 'ITA', 'Informativo', '2025-06-15 13:45:26.979213', 'richiesta musica', 0.45, 58, 'Suggerimento', 'Audio');
INSERT INTO public.messaggio (id_messaggio, testo, lingua, tono, timestamp_messaggio, contesto_conversazione, intensita_emozione, id_robot, tipo_messaggio, tipo_output) VALUES (9, 'Hai fatto un ottimo lavoro oggi!', 'ITA', 'Incoraggiante', '2025-06-15 18:30:00', 'feedback positivo', 0.70, 57, 'Risposta', 'Testuale');
INSERT INTO public.messaggio (id_messaggio, testo, lingua, tono, timestamp_messaggio, contesto_conversazione, intensita_emozione, id_robot, tipo_messaggio, tipo_output) VALUES (10, 'Ti consiglio una breve pausa. Vuoi iniziare una sessione di respirazione guidata?', 'ITA', 'Persuasivo', '2025-06-15 13:45:26.979213', 'stress', 0.80, 54, 'Suggerimento', 'Multimodale');


--
-- TOC entry 5066 (class 0 OID 25221)
-- Dependencies: 225
-- Data for Name: emozione; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.emozione (id_emozione, nome, descrizione, intensita, categoria_plutchik, emozione_opposta, id_messaggio, id_interazione) VALUES (11, 'Gioia', 'Emozione positiva di accoglienza', 0.80, 'emozione primaria', 'Tristezza', 6, 2);
INSERT INTO public.emozione (id_emozione, nome, descrizione, intensita, categoria_plutchik, emozione_opposta, id_messaggio, id_interazione) VALUES (12, 'Tristezza', 'Emozione negativa legata alla malinconia', 0.90, 'emozione primaria', 'Gioia', 7, 3);
INSERT INTO public.emozione (id_emozione, nome, descrizione, intensita, categoria_plutchik, emozione_opposta, id_messaggio, id_interazione) VALUES (13, 'Paura', 'Sensazione di tensione o allerta percepita', 0.70, 'emozione primaria', 'Rabbia', 8, 4);
INSERT INTO public.emozione (id_emozione, nome, descrizione, intensita, categoria_plutchik, emozione_opposta, id_messaggio, id_interazione) VALUES (14, 'Fiducia', 'Apertura verso un miglioramento emotivo', 0.65, 'emozione primaria', 'Disgusto', 9, 5);
INSERT INTO public.emozione (id_emozione, nome, descrizione, intensita, categoria_plutchik, emozione_opposta, id_messaggio, id_interazione) VALUES (15, 'Aggressivita', 'Atteggiamento reattivo con forte energia', 0.60, 'emozione secondaria', 'Sottomissione', 10, 6);


--
-- TOC entry 5068 (class 0 OID 25230)
-- Dependencies: 227
-- Data for Name: feedback; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.feedback (id_feedback, valutazione, commento, timestamp_feedback, id_utente, id_interazione) VALUES (1, 8.5, 'La risposta è stata molto empatica e utile.', '2025-06-15 15:37:18.813455', 7, 2);
INSERT INTO public.feedback (id_feedback, valutazione, commento, timestamp_feedback, id_utente, id_interazione) VALUES (2, 6.0, 'Un po’ lenta la risposta, ma buona comprensione.', '2024-06-10 14:27:00', 13, 3);
INSERT INTO public.feedback (id_feedback, valutazione, commento, timestamp_feedback, id_utente, id_interazione) VALUES (3, 9.0, 'La musica consigliata era perfetta per rilassarmi.', '2024-06-10 14:29:00', 11, 4);
INSERT INTO public.feedback (id_feedback, valutazione, commento, timestamp_feedback, id_utente, id_interazione) VALUES (4, 7.5, 'Apprezzo il tono positivo, ma poteva essere più dettagliato.', '2024-06-10 14:31:00', 14, 5);


--
-- TOC entry 5072 (class 0 OID 25249)
-- Dependencies: 231
-- Data for Name: interfaccia_canale; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.interfaccia_canale (id_interfaccia, tipo_canale, ultima_connessione, modalita_output_supportate, modalita_input_supportate, parametri_autenticazione, configurazione_json, nome_canale, url_endpoint, attivo, data_creazione) VALUES (2, 'chatbot', '2025-05-22 22:30:21', 'testo,immagini,audio', 'testo,emoji,audio,file', 'token', '{"tema": "scuro", "dimensione_font": "media"}', 'ChatWeb', 'https://chatweb.example.com', true, '2025-06-15 12:04:51.840624');
INSERT INTO public.interfaccia_canale (id_interfaccia, tipo_canale, ultima_connessione, modalita_output_supportate, modalita_input_supportate, parametri_autenticazione, configurazione_json, nome_canale, url_endpoint, attivo, data_creazione) VALUES (3, 'robot', '2025-06-15 12:04:51.840624', 'voce, movimento, luci, immagini, tocco, vibrazione', 'voce, tatto, propriocettivo, ambiente, gesti', 'password', '{"lingua": "ita", "velovità movimento": "media"}', 'robot fisico', 'https://robot.example.com', false, '2025-06-15 12:04:51.840624');
INSERT INTO public.interfaccia_canale (id_interfaccia, tipo_canale, ultima_connessione, modalita_output_supportate, modalita_input_supportate, parametri_autenticazione, configurazione_json, nome_canale, url_endpoint, attivo, data_creazione) VALUES (4, 'assistente vocale', '2025-06-15 12:04:51.840624', 'audio', 'voce', 'mfa', '{"volume": "alto", "lingua": "ita"}', 'Siri', 'https://apple.example.com', true, '2025-06-15 12:04:51.840624');
INSERT INTO public.interfaccia_canale (id_interfaccia, tipo_canale, ultima_connessione, modalita_output_supportate, modalita_input_supportate, parametri_autenticazione, configurazione_json, nome_canale, url_endpoint, attivo, data_creazione) VALUES (5, 'interfaccia VR', '2025-06-15 12:04:51.840624', 'visivo, audio spaziale, tatto, animazioni', 'movimento, voce', 'token', '{"risoluzione": "4K", "immersivo": true, "lingua": "ita"}', 'Sony VR', 'https://Sony.example.com', false, '2025-06-15 12:04:51.840624');
INSERT INTO public.interfaccia_canale (id_interfaccia, tipo_canale, ultima_connessione, modalita_output_supportate, modalita_input_supportate, parametri_autenticazione, configurazione_json, nome_canale, url_endpoint, attivo, data_creazione) VALUES (6, 'app desktop', '2025-06-15 12:04:51.840624', 'testo,emoji,file', 'testo,immagini,file,audio', 'token', '{"tema": "scuro", "dimensione_font": "grande", "lingua": "spa"}', 'Chat GPT', 'https://chatgpt.example.com', true, '2025-06-15 12:04:51.840624');


--
-- TOC entry 5095 (class 0 OID 25573)
-- Dependencies: 254
-- Data for Name: interazione_interfaccia; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.interazione_interfaccia (id_interazione, id_interfaccia) VALUES (2, 3);
INSERT INTO public.interazione_interfaccia (id_interazione, id_interfaccia) VALUES (3, 4);
INSERT INTO public.interazione_interfaccia (id_interazione, id_interfaccia) VALUES (4, 6);
INSERT INTO public.interazione_interfaccia (id_interazione, id_interfaccia) VALUES (5, 6);
INSERT INTO public.interazione_interfaccia (id_interazione, id_interfaccia) VALUES (6, 2);


--
-- TOC entry 5074 (class 0 OID 25262)
-- Dependencies: 233
-- Data for Name: log_errori; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.log_errori (id_log, codice_errore, livello_gravita, azione_correttiva, stack_trace, messaggio_errore, timestamp_errore, componente_sistema, stato_risoluzione, tecnico_assegnato, id_robot, id_sessione) VALUES (1, 'DB_CONN_02', 7.5, 'Riavviare il servizio database', 'Stack trace esempio 1', 'Connessione database fallita', '2025-06-15 10:00:00', 'Database', 'In Lavorazione', 'Mario Rossi', 52, 9);
INSERT INTO public.log_errori (id_log, codice_errore, livello_gravita, azione_correttiva, stack_trace, messaggio_errore, timestamp_errore, componente_sistema, stato_risoluzione, tecnico_assegnato, id_robot, id_sessione) VALUES (2, 'API_FAIL_03', 5.0, 'Verificare endpoint API', 'Stack trace esempio 2', 'Servizio esterno non disponibile', '2025-06-15 15:55:56.81713', 'API Gateway', 'Aperto', 'Luca Bianchi', 60, 10);
INSERT INTO public.log_errori (id_log, codice_errore, livello_gravita, azione_correttiva, stack_trace, messaggio_errore, timestamp_errore, componente_sistema, stato_risoluzione, tecnico_assegnato, id_robot, id_sessione) VALUES (3, 'ROBOT_MOVE_07', 9.0, 'Controllare sensori motore', 'Stack trace esempio 3', 'Movimento robot bloccato', '2025-06-15 15:55:56.81713', 'Robot Hardware', 'In Attesa', 'Giulia Verdi', 58, 13);
INSERT INTO public.log_errori (id_log, codice_errore, livello_gravita, azione_correttiva, stack_trace, messaggio_errore, timestamp_errore, componente_sistema, stato_risoluzione, tecnico_assegnato, id_robot, id_sessione) VALUES (4, 'UNKNOWN', 3.0, 'Verificare log generali di sistema', 'Stack trace generico', 'Errore generico di sistema', '2025-06-15 15:55:56.81713', 'Core Logic', 'Aperto', 'Alessandro Neri', NULL, NULL);


--
-- TOC entry 5078 (class 0 OID 25286)
-- Dependencies: 237
-- Data for Name: metrica; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.metrica (id_metrica, nome, valore_metrica, descrizione, timestamp_metrica, tipo_metrica, errore, numero_errori, id_interazione) VALUES (9, 'Accuratezza Riconoscimento Vocale', 0.95432, 'Percentuale di riconoscimento corretto della voce', '2025-06-15 11:00:00', 'Accuratezza', 'Nessuno', 0, 4);
INSERT INTO public.metrica (id_metrica, nome, valore_metrica, descrizione, timestamp_metrica, tipo_metrica, errore, numero_errori, id_interazione) VALUES (10, 'Tempo Risposta Sistema', 1.23456, 'Tempo medio di risposta del sistema in secondi', '2025-06-15 16:07:03.425615', 'Tempo di Risposta', 'Time-Out', 3, 2);
INSERT INTO public.metrica (id_metrica, nome, valore_metrica, descrizione, timestamp_metrica, tipo_metrica, errore, numero_errori, id_interazione) VALUES (11, 'Tasso di Fallimento NLP', 0.04567, 'Percentuale di errori nel processamento NLP', '2025-06-15 11:15:00', 'Tasso di Fallimento', 'Errore NLP', 5, 3);
INSERT INTO public.metrica (id_metrica, nome, valore_metrica, descrizione, timestamp_metrica, tipo_metrica, errore, numero_errori, id_interazione) VALUES (12, 'Throughput Messaggi', 7.50000, 'Numero di messaggi processati al secondo (scala 0-10)', '2025-06-15 16:07:03.425615', 'Throughput', 'Nessuno', 0, 5);


--
-- TOC entry 5080 (class 0 OID 25298)
-- Dependencies: 239
-- Data for Name: policy_moderazione; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.policy_moderazione (id_policy, descrizione, azione, tipo_contenuto, attiva, data_creazione, ultima_modifica, id_messaggio) VALUES (1, 'Blocca l''uso di linguaggio offensivo o discriminatorio.', 'Blocco Messagio', 'Testo', true, '2025-06-15 08:00:00', '2025-06-15 08:00:00', 9);
INSERT INTO public.policy_moderazione (id_policy, descrizione, azione, tipo_contenuto, attiva, data_creazione, ultima_modifica, id_messaggio) VALUES (2, 'Segnala contenuti audio inappropriati per revisione tecnica.', 'Segnalazione Tecnica', 'Audio', true, '2025-06-15 16:12:07.496638', '2025-06-15 16:12:07.496638', 8);
INSERT INTO public.policy_moderazione (id_policy, descrizione, azione, tipo_contenuto, attiva, data_creazione, ultima_modifica, id_messaggio) VALUES (3, 'Modifica automaticamente messaggi con contenuti sensibili nelle immagini.', 'Modifica Messaggio', 'Immagine', true, '2025-06-10 10:30:00', '2025-06-15 09:00:00', 7);
INSERT INTO public.policy_moderazione (id_policy, descrizione, azione, tipo_contenuto, attiva, data_creazione, ultima_modifica, id_messaggio) VALUES (4, 'Avvisa l''utente in caso di comportamento non conforme alle policy.', 'Avviso Utente', 'Comportamento', true, '2025-06-01 12:00:00', '2025-06-10 14:00:00', 6);
INSERT INTO public.policy_moderazione (id_policy, descrizione, azione, tipo_contenuto, attiva, data_creazione, ultima_modifica, id_messaggio) VALUES (5, 'Politica universale di de-escalation per ridurre tensioni nelle conversazioni.', 'De-escalation', 'Tutto', true, '2025-06-15 16:12:07.496638', '2025-06-15 16:12:07.496638', 10);


--
-- TOC entry 5082 (class 0 OID 25310)
-- Dependencies: 241
-- Data for Name: profilo_utente; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.profilo_utente (id_profilo, tratti_personalita, stato_emotivo_prevalente, stile_comunicativo, nome_preferenza, attivazione_preferenza, descrizione_preferenza, ultima_modifica, id_utente, valore_preferenza) VALUES (4, 'Pragmatico, diretto, razionale', 'Neutrale', 'Chiaro e sintetico', 'Modalità dettagliata', true, 'Preferenza per spiegazioni dettagliate e precise.', '2025-06-15', 14, 3.7);
INSERT INTO public.profilo_utente (id_profilo, tratti_personalita, stato_emotivo_prevalente, stile_comunicativo, nome_preferenza, attivazione_preferenza, descrizione_preferenza, ultima_modifica, id_utente, valore_preferenza) VALUES (3, 'Stressato, impaurito', 'Stressato', 'Coinvolgente e dinamico', 'Risposte rapide', true, 'Preferenza per risposte concise e veloci.', '2025-06-15', 11, 10.0);
INSERT INTO public.profilo_utente (id_profilo, tratti_personalita, stato_emotivo_prevalente, stile_comunicativo, nome_preferenza, attivazione_preferenza, descrizione_preferenza, ultima_modifica, id_utente, valore_preferenza) VALUES (2, 'Estroverso, empatico, curioso', 'Calmo', 'Formale e preciso', 'Modalità silenziosa', false, 'Preferenza per interazioni con audio disattivato.', '2025-06-15', 7, 4.5);
INSERT INTO public.profilo_utente (id_profilo, tratti_personalita, stato_emotivo_prevalente, stile_comunicativo, nome_preferenza, attivazione_preferenza, descrizione_preferenza, ultima_modifica, id_utente, valore_preferenza) VALUES (1, 'Introverso, triste', 'Triste', 'Informale e uso di emoji', 'Preferenza linguistica', true, 'Preferenza per risposte in italiano e toni empatici.', '2025-06-15', 13, 1.2);
INSERT INTO public.profilo_utente (id_profilo, tratti_personalita, stato_emotivo_prevalente, stile_comunicativo, nome_preferenza, attivazione_preferenza, descrizione_preferenza, ultima_modifica, id_utente, valore_preferenza) VALUES (5, 'Arrabbiato, intimidatorio', 'Aggressivo', 'Impaziente, aggressivo', 'Tono rassicurante', true, 'Preferenza per risposte con tono rassicurante e lento.', '2025-06-15', 10, 7.4);


--
-- TOC entry 5088 (class 0 OID 25332)
-- Dependencies: 247
-- Data for Name: storico_emotivo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.storico_emotivo (id_storico, timestamp_storico, intensita_emozione, emozione_prevalente, id_utente, emozioni) VALUES (1, '2025-06-10 10:00:00', 0.85, 'Gioia', 7, 'Gioia, Fiducia');
INSERT INTO public.storico_emotivo (id_storico, timestamp_storico, intensita_emozione, emozione_prevalente, id_utente, emozioni) VALUES (2, '2025-06-11 14:15:00', 0.60, 'Tristezza', 13, 'Paura, Rabbia, Tristezza');
INSERT INTO public.storico_emotivo (id_storico, timestamp_storico, intensita_emozione, emozione_prevalente, id_utente, emozioni) VALUES (3, '2025-06-12 09:30:00', 0.40, 'Paura', 11, 'Rimorso, Disperazione, Paura');
INSERT INTO public.storico_emotivo (id_storico, timestamp_storico, intensita_emozione, emozione_prevalente, id_utente, emozioni) VALUES (4, '2025-06-13 18:45:00', 0.90, 'Fiducia', 14, 'Amore, Gioia, Fiducia');
INSERT INTO public.storico_emotivo (id_storico, timestamp_storico, intensita_emozione, emozione_prevalente, id_utente, emozioni) VALUES (5, '2025-06-15 16:31:13.868191', 0.75, 'Aggressivita', 10, 'Disperazione, Timore, Paura, Rabbia');


--
-- TOC entry 5094 (class 0 OID 25558)
-- Dependencies: 253
-- Data for Name: storico_emotivo_emozione; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.storico_emotivo_emozione (id_storico, id_emozione) VALUES (1, 11);
INSERT INTO public.storico_emotivo_emozione (id_storico, id_emozione) VALUES (2, 12);
INSERT INTO public.storico_emotivo_emozione (id_storico, id_emozione) VALUES (3, 13);
INSERT INTO public.storico_emotivo_emozione (id_storico, id_emozione) VALUES (4, 14);
INSERT INTO public.storico_emotivo_emozione (id_storico, id_emozione) VALUES (5, 15);


--
-- TOC entry 5090 (class 0 OID 25342)
-- Dependencies: 249
-- Data for Name: traduzione; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.traduzione (id_traduzione, timestamp_traduzione, testo_tradotto, lingua, id_messaggio) VALUES (1, '2025-06-15 09:00:00', 'Welcome! How are you feeling today?', 'ENG', 6);
INSERT INTO public.traduzione (id_traduzione, timestamp_traduzione, testo_tradotto, lingua, id_messaggio) VALUES (2, '2025-06-15 09:05:00', 'I''m sorry you''re feeling sad 😔', 'ENG', 7);
INSERT INTO public.traduzione (id_traduzione, timestamp_traduzione, testo_tradotto, lingua, id_messaggio) VALUES (4, '2025-06-15 18:30:00', '¡Has hecho un gran trabajo hoy!', 'SPA', 9);
INSERT INTO public.traduzione (id_traduzione, timestamp_traduzione, testo_tradotto, lingua, id_messaggio) VALUES (5, '2025-06-15 13:45:26', 'I recommend a short break. Would you like to start a guided breathing session?', 'ENG', 10);
INSERT INTO public.traduzione (id_traduzione, timestamp_traduzione, testo_tradotto, lingua, id_messaggio) VALUES (3, '2025-06-15 13:45:26', 'Starting your relaxing playlist.', 'ENG', 8);


--
-- TOC entry 5096 (class 0 OID 25588)
-- Dependencies: 255
-- Data for Name: utente_interfaccia; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.utente_interfaccia (id_utente, id_interfaccia) VALUES (7, 2);
INSERT INTO public.utente_interfaccia (id_utente, id_interfaccia) VALUES (13, 3);
INSERT INTO public.utente_interfaccia (id_utente, id_interfaccia) VALUES (11, 4);
INSERT INTO public.utente_interfaccia (id_utente, id_interfaccia) VALUES (14, 5);
INSERT INTO public.utente_interfaccia (id_utente, id_interfaccia) VALUES (10, 6);


--
-- TOC entry 5102 (class 0 OID 0)
-- Dependencies: 218
-- Name: accessibilita_id_accessibilita_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.accessibilita_id_accessibilita_seq', 5, true);


--
-- TOC entry 5103 (class 0 OID 0)
-- Dependencies: 220
-- Name: config_privacy_id_config_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.config_privacy_id_config_seq', 9, true);


--
-- TOC entry 5104 (class 0 OID 0)
-- Dependencies: 222
-- Name: configurazione_robot_id_configurazione_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.configurazione_robot_id_configurazione_seq', 10, true);


--
-- TOC entry 5105 (class 0 OID 0)
-- Dependencies: 224
-- Name: contenuto_multimediale_id_contenuto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.contenuto_multimediale_id_contenuto_seq', 5, true);


--
-- TOC entry 5106 (class 0 OID 0)
-- Dependencies: 226
-- Name: emozione_id_emozione_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.emozione_id_emozione_seq', 18, true);


--
-- TOC entry 5107 (class 0 OID 0)
-- Dependencies: 228
-- Name: feedback_id_feedback_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.feedback_id_feedback_seq', 10, true);


--
-- TOC entry 5108 (class 0 OID 0)
-- Dependencies: 230
-- Name: interazione_id_interazione_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interazione_id_interazione_seq', 6, true);


--
-- TOC entry 5109 (class 0 OID 0)
-- Dependencies: 232
-- Name: interfaccia_canale_id_interfaccia_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.interfaccia_canale_id_interfaccia_seq', 6, true);


--
-- TOC entry 5110 (class 0 OID 0)
-- Dependencies: 234
-- Name: log_errori_id_log_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.log_errori_id_log_seq', 4, true);


--
-- TOC entry 5111 (class 0 OID 0)
-- Dependencies: 236
-- Name: messaggio_id_messaggio_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.messaggio_id_messaggio_seq', 10, true);


--
-- TOC entry 5112 (class 0 OID 0)
-- Dependencies: 238
-- Name: metrica_id_metrica_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.metrica_id_metrica_seq', 13, true);


--
-- TOC entry 5113 (class 0 OID 0)
-- Dependencies: 240
-- Name: policy_moderazione_id_policy_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.policy_moderazione_id_policy_seq', 5, true);


--
-- TOC entry 5114 (class 0 OID 0)
-- Dependencies: 242
-- Name: profilo_utente_id_profilo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.profilo_utente_id_profilo_seq', 6, true);


--
-- TOC entry 5115 (class 0 OID 0)
-- Dependencies: 244
-- Name: robot_id_robot_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.robot_id_robot_seq', 61, true);


--
-- TOC entry 5116 (class 0 OID 0)
-- Dependencies: 246
-- Name: sessione_id_sessione_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sessione_id_sessione_seq', 14, true);


--
-- TOC entry 5117 (class 0 OID 0)
-- Dependencies: 248
-- Name: storico_emotivo_id_storico_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.storico_emotivo_id_storico_seq', 5, true);


--
-- TOC entry 5118 (class 0 OID 0)
-- Dependencies: 250
-- Name: traduzione_id_traduzione_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.traduzione_id_traduzione_seq', 6, true);


--
-- TOC entry 5119 (class 0 OID 0)
-- Dependencies: 252
-- Name: utente_id_utente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.utente_id_utente_seq', 20, true);


-- Completed on 2025-07-06 18:15:03

--
-- PostgreSQL database dump complete
--

