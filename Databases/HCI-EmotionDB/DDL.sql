--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

-- Started on 2025-07-05 19:40:08

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 25179)
-- Name: accessibilita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accessibilita (
    id_accessibilita integer NOT NULL,
    contrasto_alto boolean DEFAULT false,
    dimensioni_testo character varying(10) DEFAULT 'normale'::character varying,
    data_configurazione date,
    riduzione_animazioni boolean DEFAULT false,
    navigazione_tastiera boolean DEFAULT false,
    sottotitoli_audio boolean DEFAULT false,
    lingua_segni boolean DEFAULT false,
    supporto_screen_reader boolean DEFAULT false,
    note_speciali text,
    id_utente integer NOT NULL,
    CONSTRAINT accessibilita_dimensioni_testo_check CHECK (((dimensioni_testo)::text = ANY (ARRAY[('piccolo'::character varying)::text, ('normale'::character varying)::text, ('grande'::character varying)::text])))
);


ALTER TABLE public.accessibilita OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 25192)
-- Name: accessibilita_id_accessibilita_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.accessibilita_id_accessibilita_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.accessibilita_id_accessibilita_seq OWNER TO postgres;

--
-- TOC entry 5157 (class 0 OID 0)
-- Dependencies: 218
-- Name: accessibilita_id_accessibilita_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.accessibilita_id_accessibilita_seq OWNED BY public.accessibilita.id_accessibilita;


--
-- TOC entry 219 (class 1259 OID 25193)
-- Name: config_privacy; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.config_privacy (
    id_config integer NOT NULL,
    consenso_dati_personali boolean DEFAULT false NOT NULL,
    consenso_marketing boolean DEFAULT false NOT NULL,
    consenso_profilazione boolean DEFAULT false NOT NULL,
    data_consenso date DEFAULT CURRENT_DATE NOT NULL,
    data_scadenza date NOT NULL,
    id_utente integer NOT NULL,
    CONSTRAINT config_privacy_check CHECK ((data_scadenza > data_consenso))
);


ALTER TABLE public.config_privacy OWNER TO postgres;

--
-- TOC entry 5158 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE config_privacy; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.config_privacy IS '
Tabella che rappresenta la configurazione privacy di ogni utente. 
Ogni utente ha una configurazione privacy obbligatoria al primo
accesso (vincolo logico gestito a livello applicativo).
';


--
-- TOC entry 220 (class 1259 OID 25201)
-- Name: config_privacy_id_config_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.config_privacy_id_config_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.config_privacy_id_config_seq OWNER TO postgres;

--
-- TOC entry 5159 (class 0 OID 0)
-- Dependencies: 220
-- Name: config_privacy_id_config_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.config_privacy_id_config_seq OWNED BY public.config_privacy.id_config;


--
-- TOC entry 221 (class 1259 OID 25202)
-- Name: configurazione_robot; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.configurazione_robot (
    id_configurazione integer NOT NULL,
    tipo_configurazione character varying(30),
    nome_parametro character varying(30),
    valore_parametro integer DEFAULT 1,
    modalita_conversazione character varying(30),
    priorita integer DEFAULT 1,
    attiva boolean DEFAULT false,
    data_applicazione timestamp without time zone,
    id_robot integer NOT NULL,
    id_utente integer NOT NULL,
    CONSTRAINT configurazione_robot_check CHECK ((((attiva = true) AND (data_applicazione IS NOT NULL)) OR (attiva = false))),
    CONSTRAINT configurazione_robot_modalita_conversazione_check CHECK (((modalita_conversazione)::text = ANY (ARRAY[('Conversazione'::character varying)::text, ('Compagnia'::character varying)::text, ('Empatia'::character varying)::text, ('Intrattenimento'::character varying)::text, ('Supporto'::character varying)::text, ('Educazione'::character varying)::text]))),
    CONSTRAINT configurazione_robot_priorita_check CHECK (((priorita >= 1) AND (priorita <= 10))),
    CONSTRAINT configurazione_robot_valore_parametro_check CHECK (((valore_parametro >= 1) AND (valore_parametro <= 10)))
);


ALTER TABLE public.configurazione_robot OWNER TO postgres;

--
-- TOC entry 5160 (class 0 OID 0)
-- Dependencies: 221
-- Name: TABLE configurazione_robot; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.configurazione_robot IS '
La tabella configurazione_robot rappresenta le tipologie di personalizzazioni
e configurazioni applicabili al sistema intelligente da parte dell''utente.
Tali configurazioni includono un parametro, un valore, una priorità e una modalità
conversazionale. L''utente può scegliere se attivarle e quando applicarle.
';


--
-- TOC entry 222 (class 1259 OID 25212)
-- Name: configurazione_robot_id_configurazione_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.configurazione_robot_id_configurazione_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.configurazione_robot_id_configurazione_seq OWNER TO postgres;

--
-- TOC entry 5161 (class 0 OID 0)
-- Dependencies: 222
-- Name: configurazione_robot_id_configurazione_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.configurazione_robot_id_configurazione_seq OWNED BY public.configurazione_robot.id_configurazione;


--
-- TOC entry 223 (class 1259 OID 25213)
-- Name: contenuto_multimediale; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contenuto_multimediale (
    id_contenuto integer NOT NULL,
    tipo_contenuto character varying(30),
    data_caricamento timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    percorso_file character varying(255),
    descrizione text,
    id_interazione integer NOT NULL,
    CONSTRAINT contenuto_multimediale_tipo_contenuto_check CHECK (((tipo_contenuto)::text = ANY (ARRAY[('Video'::character varying)::text, ('Immagine'::character varying)::text, ('Audio'::character varying)::text, ('Testo'::character varying)::text, ('Contenuto interattivo'::character varying)::text, ('Animazione'::character varying)::text, ('VR'::character varying)::text, ('Documento'::character varying)::text])))
);


ALTER TABLE public.contenuto_multimediale OWNER TO postgres;

--
-- TOC entry 5162 (class 0 OID 0)
-- Dependencies: 223
-- Name: TABLE contenuto_multimediale; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.contenuto_multimediale IS '
La tabella contenuto_multimediale rappresenta i possibili contenuti digitali
che un'' interazione può contenere (video, animazioni 2D/3D, immagini
testo ad esempio articoli, contenuti interattivi e realtà virtuale).
Ognuno contenuto caratterizzato da una descrizione, tipo, data di caricamento
e percorso file.
La tabella è strettamente correlata all''entità interazione attraverso la chiave
esterna e le clausole ON DELETE/UPDATE CASCADE.
';


--
-- TOC entry 224 (class 1259 OID 25220)
-- Name: contenuto_multimediale_id_contenuto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.contenuto_multimediale_id_contenuto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.contenuto_multimediale_id_contenuto_seq OWNER TO postgres;

--
-- TOC entry 5163 (class 0 OID 0)
-- Dependencies: 224
-- Name: contenuto_multimediale_id_contenuto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.contenuto_multimediale_id_contenuto_seq OWNED BY public.contenuto_multimediale.id_contenuto;


--
-- TOC entry 225 (class 1259 OID 25221)
-- Name: emozione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.emozione (
    id_emozione integer NOT NULL,
    nome character varying(20) NOT NULL,
    descrizione text NOT NULL,
    intensita numeric(3,2) NOT NULL,
    categoria_plutchik character varying(30) NOT NULL,
    emozione_opposta character varying(20) NOT NULL,
    id_messaggio integer NOT NULL,
    id_interazione integer NOT NULL,
    CONSTRAINT emozione_categoria_plutchik_check CHECK (((categoria_plutchik)::text = ANY (ARRAY[('emozione primaria'::character varying)::text, ('emozione secondaria'::character varying)::text]))),
    CONSTRAINT emozione_intensita_check CHECK (((intensita >= 0.00) AND (intensita <= 1.00))),
    CONSTRAINT emozione_nome_check CHECK (((nome)::text = ANY (ARRAY[('Gioia'::character varying)::text, ('Fiducia'::character varying)::text, ('Paura'::character varying)::text, ('Sorpresa'::character varying)::text, ('Tristezza'::character varying)::text, ('Disgusto'::character varying)::text, ('Rabbia'::character varying)::text, ('Anticipazione'::character varying)::text, ('Amore'::character varying)::text, ('Sottomissione'::character varying)::text, ('Timore'::character varying)::text, ('Delusione'::character varying)::text, ('Rimorso'::character varying)::text, ('Disprezzo'::character varying)::text, ('Aggressivita'::character varying)::text, ('Ottimismo'::character varying)::text])))
);


ALTER TABLE public.emozione OWNER TO postgres;

--
-- TOC entry 5164 (class 0 OID 0)
-- Dependencies: 225
-- Name: TABLE emozione; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.emozione IS '
La tabella emozione rappresenta l''emozione primaria/secondaria
appartenente al modello di Plutchik, rilevata durante
l''interazione tra l''utente e il sistema intelligente.
Si è scelto di mantenerla come tabella separata invece
di integrarla come attributo di interazione, in quanto l''emozione
è rilevata/elaborata dall''IA, quindi è un risultato dell''interazione.
Questa scelta consente:
- maggiore espressività e riuso in contesti diversi
- rispetto della 3NF
- gestione di intensità, categorie e opposizioni
- analisi e storicizzazione più flessibili.
';


--
-- TOC entry 226 (class 1259 OID 25229)
-- Name: emozione_id_emozione_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.emozione_id_emozione_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.emozione_id_emozione_seq OWNER TO postgres;

--
-- TOC entry 5165 (class 0 OID 0)
-- Dependencies: 226
-- Name: emozione_id_emozione_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.emozione_id_emozione_seq OWNED BY public.emozione.id_emozione;


--
-- TOC entry 227 (class 1259 OID 25230)
-- Name: feedback; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.feedback (
    id_feedback integer NOT NULL,
    valutazione numeric(3,1),
    commento text,
    timestamp_feedback timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    id_utente integer NOT NULL,
    id_interazione integer NOT NULL,
    CONSTRAINT feedback_valutazione_check CHECK (((valutazione >= 0.0) AND (valutazione <= (10)::numeric)))
);


ALTER TABLE public.feedback OWNER TO postgres;

--
-- TOC entry 5166 (class 0 OID 0)
-- Dependencies: 227
-- Name: TABLE feedback; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.feedback IS '
La tabella feedback rappresenta la valutazione e il commento che
un utente può lasciare riguardo a una interazione, in modo tale che
il sistema possa affinare e migliorare le proprie capacità nel tempo,
grazie all''apprendimento automatico.
';


--
-- TOC entry 228 (class 1259 OID 25237)
-- Name: feedback_id_feedback_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.feedback_id_feedback_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.feedback_id_feedback_seq OWNER TO postgres;

--
-- TOC entry 5167 (class 0 OID 0)
-- Dependencies: 228
-- Name: feedback_id_feedback_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.feedback_id_feedback_seq OWNED BY public.feedback.id_feedback;


--
-- TOC entry 229 (class 1259 OID 25238)
-- Name: interazione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.interazione (
    id_interazione integer NOT NULL,
    "timestamp" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    contenuto_input text NOT NULL,
    tipo_input character varying(200) NOT NULL,
    contesto_conversazione text NOT NULL,
    id_sessione integer NOT NULL,
    id_robot integer NOT NULL
);


ALTER TABLE public.interazione OWNER TO postgres;

--
-- TOC entry 5168 (class 0 OID 0)
-- Dependencies: 229
-- Name: TABLE interazione; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.interazione IS '
La tabella interazione registra gli scambi comunicativi tra l''utente
e il sistema intelligente. Essa registra il tipo di input ricevuto
dall''utente e il contenuto di esso.
';


--
-- TOC entry 230 (class 1259 OID 25245)
-- Name: interazione_id_interazione_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.interazione_id_interazione_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.interazione_id_interazione_seq OWNER TO postgres;

--
-- TOC entry 5169 (class 0 OID 0)
-- Dependencies: 230
-- Name: interazione_id_interazione_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interazione_id_interazione_seq OWNED BY public.interazione.id_interazione;


--
-- TOC entry 254 (class 1259 OID 25573)
-- Name: interazione_interfaccia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.interazione_interfaccia (
    id_interazione integer NOT NULL,
    id_interfaccia integer NOT NULL
);


ALTER TABLE public.interazione_interfaccia OWNER TO postgres;

--
-- TOC entry 5170 (class 0 OID 0)
-- Dependencies: 254
-- Name: TABLE interazione_interfaccia; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.interazione_interfaccia IS '
Tabella ponte per gestire la relazione N:N tra interazione e interfaccia canale.
';


--
-- TOC entry 231 (class 1259 OID 25249)
-- Name: interfaccia_canale; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.interfaccia_canale (
    id_interfaccia integer NOT NULL,
    tipo_canale character varying(20) NOT NULL,
    ultima_connessione timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modalita_output_supportate character varying(200) NOT NULL,
    modalita_input_supportate character varying(200) NOT NULL,
    parametri_autenticazione character varying(20) NOT NULL,
    configurazione_json json NOT NULL,
    nome_canale character varying(20) NOT NULL,
    url_endpoint character varying(255) NOT NULL,
    attivo boolean DEFAULT false NOT NULL,
    data_creazione timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT interfaccia_canale_parametri_autenticazione_check CHECK (((parametri_autenticazione)::text = ANY (ARRAY[('password'::character varying)::text, ('token'::character varying)::text, ('mfa'::character varying)::text, ('otp'::character varying)::text])))
);


ALTER TABLE public.interfaccia_canale OWNER TO postgres;

--
-- TOC entry 5171 (class 0 OID 0)
-- Dependencies: 231
-- Name: TABLE interfaccia_canale; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.interfaccia_canale IS '
La tabella interfaccia_canale rappresenta il canale che utilizza
l''utente per interagire col sistema intelligente. L''utente
può scegliere diversi tipi di canale attraverso coi comunicare. 
Ogni canale di comunicazione ha un nome, un url attraverso il quale 
l''utente o il robot si collega comunicare con l''interfaccia, 
diverse modalità input/output supportate e una configurazione JSON 
(ad esempio: {
  "lingua": "it",
  "volume_voce": 80,
  "modalita_interazione": "empatica",
  "sensibilita_touch": "alta",
  "modalita_notturna": true
})
';


--
-- TOC entry 232 (class 1259 OID 25261)
-- Name: interfaccia_canale_id_interfaccia_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.interfaccia_canale_id_interfaccia_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.interfaccia_canale_id_interfaccia_seq OWNER TO postgres;

--
-- TOC entry 5172 (class 0 OID 0)
-- Dependencies: 232
-- Name: interfaccia_canale_id_interfaccia_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interfaccia_canale_id_interfaccia_seq OWNED BY public.interfaccia_canale.id_interfaccia;


--
-- TOC entry 233 (class 1259 OID 25262)
-- Name: log_errori; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.log_errori (
    id_log integer NOT NULL,
    codice_errore character varying(20) DEFAULT 'UNKNOWN'::character varying,
    livello_gravita numeric(2,1),
    azione_correttiva character varying(255),
    stack_trace text,
    messaggio_errore character varying(100),
    timestamp_errore timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    componente_sistema character varying(50),
    stato_risoluzione character varying(30),
    tecnico_assegnato character varying(50),
    id_robot integer,
    id_sessione integer,
    CONSTRAINT log_errori_codice_errore_check CHECK (((codice_errore)::text = ANY (ARRAY[('SYS_ERR_01'::character varying)::text, ('DB_CONN_02'::character varying)::text, ('API_FAIL_03'::character varying)::text, ('NLP_PROC_04'::character varying)::text, ('TTS_FAIL_05'::character varying)::text, ('ASR_FAIL_06'::character varying)::text, ('ROBOT_MOVE_07'::character varying)::text, ('AUTH_ERR_08'::character varying)::text, ('MOD_RULE_09'::character varying)::text, ('UNKNOWN'::character varying)::text]))),
    CONSTRAINT log_errori_componente_sistema_check CHECK (((componente_sistema)::text = ANY (ARRAY[('Database'::character varying)::text, ('API Gateway'::character varying)::text, ('NLP Engine'::character varying)::text, ('Speech Module'::character varying)::text, ('Core Logic'::character varying)::text, ('Moderation Engine'::character varying)::text, ('User Interface'::character varying)::text, ('Robot Hardware'::character varying)::text, ('Firmware'::character varying)::text]))),
    CONSTRAINT log_errori_livello_gravita_check CHECK (((livello_gravita >= 0.0) AND (livello_gravita <= (10)::numeric))),
    CONSTRAINT log_errori_messaggio_errore_check CHECK (((messaggio_errore)::text = ANY (ARRAY[('Connessione database fallita'::character varying)::text, ('Elaborazione input non riuscita'::character varying)::text, ('Risposta robot non generata'::character varying)::text, ('Errore autenticazione utente'::character varying)::text, ('Violazione policy moderazione'::character varying)::text, ('Servizio esterno non disponibile'::character varying)::text, ('Movimento robot bloccato'::character varying)::text, ('Errore generico di sistema'::character varying)::text, ('Anomalia non classificata'::character varying)::text]))),
    CONSTRAINT log_errori_stato_risoluzione_check CHECK (((stato_risoluzione)::text = ANY (ARRAY[('Aperto'::character varying)::text, ('In Lavorazione'::character varying)::text, ('Risolto'::character varying)::text, ('Chiuso'::character varying)::text, ('Non Riproducibile'::character varying)::text, ('In Attesa'::character varying)::text])))
);


ALTER TABLE public.log_errori OWNER TO postgres;

--
-- TOC entry 5173 (class 0 OID 0)
-- Dependencies: 233
-- Name: TABLE log_errori; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.log_errori IS '
La tabella log_errori tiene traccia degli errori del robot e 
delle sessioni in modo da faciliare il debugging e la manutenzione.
Essa traccia in dettaglio le anomalie e malfuinzionamenti che possono verificarsi
sia a livello del sistema robotico che nelle interazioni utente.
Ogni errore è caratterizzato da uno specifico messaggio, codice,
livello di gravità, timestamp, stack trace, stato di risoluzione e dalla corrispettiva
azione correttiva. Inoltre la tabella memorizza anche la
componente del sistema che ha causato l''errore e in caso
di necessità il relativo tecnico assegnato.
La tabella stabilisce anche un legame cruciale con il robot specifico che ha
generato l''errore e/o la sessione utente durante la quale l''errore si è 
manifestato.
Questi riferimenti sono vitali per contestualizzare gli errori, permettendo
agli sviluppatori di diagnosticare problemi in modo efficiente.
';


--
-- TOC entry 234 (class 1259 OID 25274)
-- Name: log_errori_id_log_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.log_errori_id_log_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.log_errori_id_log_seq OWNER TO postgres;

--
-- TOC entry 5174 (class 0 OID 0)
-- Dependencies: 234
-- Name: log_errori_id_log_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.log_errori_id_log_seq OWNED BY public.log_errori.id_log;


--
-- TOC entry 235 (class 1259 OID 25275)
-- Name: messaggio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.messaggio (
    id_messaggio integer NOT NULL,
    testo text NOT NULL,
    lingua character varying(10) DEFAULT 'ENG'::character varying NOT NULL,
    tono character varying(20) NOT NULL,
    timestamp_messaggio timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    contesto_conversazione text NOT NULL,
    intensita_emozione numeric(3,2) DEFAULT 0.00 NOT NULL,
    id_robot integer NOT NULL,
    tipo_messaggio character varying(20) DEFAULT 'Risposta'::character varying NOT NULL,
    tipo_output character varying(200) DEFAULT 'Testuale'::character varying NOT NULL,
    CONSTRAINT messaggio_tipo_messaggio_check CHECK (((tipo_messaggio)::text = ANY ((ARRAY['Risposta'::character varying, 'Suggerimento'::character varying])::text[]))),
    CONSTRAINT messaggio_tono_check CHECK (((tono)::text = ANY (ARRAY[('Empatico'::character varying)::text, ('Rassicurante'::character varying)::text, ('Incoraggiante'::character varying)::text, ('Informativo'::character varying)::text, ('Persuasivo'::character varying)::text, ('Rispettoso'::character varying)::text])))
);


ALTER TABLE public.messaggio OWNER TO postgres;

--
-- TOC entry 5175 (class 0 OID 0)
-- Dependencies: 235
-- Name: TABLE messaggio; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.messaggio IS '
La tabella messaggio rappresenta la risposta del robot all''utente.
Il messaggio si adatta all''emozione rilevata durante l''interazione
con l''utente, può avere diversi toni in base al contesto dell''
interazione. Il messaggio può assumere diverse forme (audio, visivo
aptico, testuale...ecc.)
';


--
-- TOC entry 236 (class 1259 OID 25285)
-- Name: messaggio_id_messaggio_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.messaggio_id_messaggio_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.messaggio_id_messaggio_seq OWNER TO postgres;

--
-- TOC entry 5176 (class 0 OID 0)
-- Dependencies: 236
-- Name: messaggio_id_messaggio_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.messaggio_id_messaggio_seq OWNED BY public.messaggio.id_messaggio;


--
-- TOC entry 237 (class 1259 OID 25286)
-- Name: metrica; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.metrica (
    id_metrica integer NOT NULL,
    nome character varying(50) NOT NULL,
    valore_metrica numeric(10,5) NOT NULL,
    descrizione text NOT NULL,
    timestamp_metrica timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    tipo_metrica character varying(50) NOT NULL,
    errore character varying(50) DEFAULT 'None'::character varying NOT NULL,
    numero_errori integer DEFAULT 0 NOT NULL,
    id_interazione integer NOT NULL,
    CONSTRAINT metrica_errore_check CHECK (((errore)::text = ANY (ARRAY[('Nessuno'::character varying)::text, ('Erorre di Sistema'::character varying)::text, ('Errore NLP'::character varying)::text, ('Errore di Riconoscimento'::character varying)::text, ('Errore di Generazione'::character varying)::text, ('Time-Out'::character varying)::text, ('Violazione di Policy'::character varying)::text]))),
    CONSTRAINT metrica_numero_errori_check CHECK ((numero_errori >= 0)),
    CONSTRAINT metrica_tipo_metrica_check CHECK (((tipo_metrica)::text = ANY (ARRAY[('Accuratezza'::character varying)::text, ('Precisione'::character varying)::text, ('Sensibilità'::character varying)::text, ('Specificità'::character varying)::text, ('F1-Score'::character varying)::text, ('Tempo di Risposta'::character varying)::text, ('Tasso Completamento Task'::character varying)::text, ('Affidabilità'::character varying)::text, ('Latenza'::character varying)::text, ('Throughput'::character varying)::text, ('Tasso di Fallimento'::character varying)::text, ('Utilizzo Risorse'::character varying)::text]))),
    CONSTRAINT valore_metrica CHECK (((valore_metrica >= (0)::numeric) AND (valore_metrica <= (10)::numeric)))
);


ALTER TABLE public.metrica OWNER TO postgres;

--
-- TOC entry 5177 (class 0 OID 0)
-- Dependencies: 237
-- Name: TABLE metrica; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.metrica IS '
La tabella metrica è un componente fondamentlae per il monitoraggio continuo,
analisi delle prestazioni e la tracciabilià delle anomalie.
Essa cattura le informazioni relative a specifiche interazioni tra l''utente e 
il robot, fornendo dati essenziali per la valutazioni dell''efficienza del sistema.
Ogni record di metrica è univocamente identificato e include un nome descrittivo,
un valore, una descrizione, un timestmap, un tipo che classifica la natura della
misurazione, un errore che specifica se e quale tipo di errore è stato rilevato in 
concomitanza o in relazione alla misurazione di questa metrica e un numero di errori
che registra la quantità di erorri specifici associati a questa metrica.
La relazione tra l''entità Interazione assicura che ogni misurazione di performance
e il relativo stato di errore siano strettamente contestualizzati a uno specifico
scambio comunicativo.
Le clausole ON DELETE/UPDATE CASCADE garantiscono l''integrità referenziale, assicurando che
le metriche siano gestite correttamente in relazione al ciclo di vita delle interazioni.
Questa tabella è fondamentale per il data driven decision making, permettendo agli sviluppatori
di valutare l''impatto delle modifiche.
';


--
-- TOC entry 238 (class 1259 OID 25297)
-- Name: metrica_id_metrica_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.metrica_id_metrica_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.metrica_id_metrica_seq OWNER TO postgres;

--
-- TOC entry 5178 (class 0 OID 0)
-- Dependencies: 238
-- Name: metrica_id_metrica_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.metrica_id_metrica_seq OWNED BY public.metrica.id_metrica;


--
-- TOC entry 239 (class 1259 OID 25298)
-- Name: policy_moderazione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.policy_moderazione (
    id_policy integer NOT NULL,
    descrizione text NOT NULL,
    azione character varying(50) DEFAULT 'Nessuna Azione'::character varying NOT NULL,
    tipo_contenuto character varying(50) NOT NULL,
    attiva boolean DEFAULT true NOT NULL,
    data_creazione timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    ultima_modifica timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    id_messaggio integer,
    CONSTRAINT policy_moderazione_azione_check CHECK (((azione)::text = ANY (ARRAY[('Nessuna Azione'::character varying)::text, ('Blocco Messagio'::character varying)::text, ('Modifica Messaggio'::character varying)::text, ('Avviso Utente'::character varying)::text, ('Segnalazione Tecnica'::character varying)::text, ('De-escalation'::character varying)::text]))),
    CONSTRAINT policy_moderazione_tipo_contenuto_check CHECK (((tipo_contenuto)::text = ANY (ARRAY[('Testo'::character varying)::text, ('Audio'::character varying)::text, ('Immagine'::character varying)::text, ('Video'::character varying)::text, ('Comportamento'::character varying)::text, ('Tutto'::character varying)::text])))
);


ALTER TABLE public.policy_moderazione OWNER TO postgres;

--
-- TOC entry 5179 (class 0 OID 0)
-- Dependencies: 239
-- Name: TABLE policy_moderazione; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.policy_moderazione IS '
La tabella policy_moderazione è fondamentale per garantire che le
interazioni tra utente e robot siano sempre sicure ed eticamente corrette.
Essa definisce le regole e i criteri che il sistema deve applicare
per filtrare, modificare o segnalare contenuti o comportamenti
non conformi.
Ogni policy è caratterizzata da una descriione dettagliata che spiega
lo scopo e le condizioni dell apolicy, un''azione specificata che indica come
il sistema deve readire quando la policy viene attivata, un tipo contenuto che definisce
a quale categoria di media la policy si applica (il valore ''Tutto'' permette di definire policy universali),
Un flag "attiva" che indica se la policy è attualmente in vigore o meno, di default una policy è attiva appena viene creata,
la data di creazione della policy e di ultima modifica.
Questa tabella è progettata per contenere la definizione generale delle regole
di moderazione, con le singole applicazioni ad ogni singolo messaggio. 
Una singola policy (ad esmepio "Blocca linguaggio offensivo") può essere applicata a molti messaggi.
';


--
-- TOC entry 240 (class 1259 OID 25309)
-- Name: policy_moderazione_id_policy_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.policy_moderazione_id_policy_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.policy_moderazione_id_policy_seq OWNER TO postgres;

--
-- TOC entry 5180 (class 0 OID 0)
-- Dependencies: 240
-- Name: policy_moderazione_id_policy_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.policy_moderazione_id_policy_seq OWNED BY public.policy_moderazione.id_policy;


--
-- TOC entry 241 (class 1259 OID 25310)
-- Name: profilo_utente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.profilo_utente (
    id_profilo integer NOT NULL,
    tratti_personalita text NOT NULL,
    stato_emotivo_prevalente character varying(100) NOT NULL,
    stile_comunicativo character varying(100) NOT NULL,
    nome_preferenza character varying(50),
    attivazione_preferenza boolean DEFAULT false,
    descrizione_preferenza text,
    ultima_modifica date DEFAULT CURRENT_DATE NOT NULL,
    id_utente integer NOT NULL,
    valore_preferenza numeric(3,1),
    CONSTRAINT profilo_utente_valore_preferenza_check CHECK (((valore_preferenza >= 0.0) AND (valore_preferenza <= 10.0)))
);


ALTER TABLE public.profilo_utente OWNER TO postgres;

--
-- TOC entry 5181 (class 0 OID 0)
-- Dependencies: 241
-- Name: TABLE profilo_utente; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.profilo_utente IS '
Tabella che contiene le preferenze, emozione prevalente e 
lo stile comunicativo di ciascun utente';


--
-- TOC entry 242 (class 1259 OID 25317)
-- Name: profilo_utente_id_profilo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.profilo_utente_id_profilo_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.profilo_utente_id_profilo_seq OWNER TO postgres;

--
-- TOC entry 5182 (class 0 OID 0)
-- Dependencies: 242
-- Name: profilo_utente_id_profilo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.profilo_utente_id_profilo_seq OWNED BY public.profilo_utente.id_profilo;


--
-- TOC entry 243 (class 1259 OID 25318)
-- Name: robot; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.robot (
    id_robot integer NOT NULL,
    nome_robot character varying(30) NOT NULL,
    modello character varying(30) NOT NULL,
    versione_software character varying(30) NOT NULL,
    data_attivazione timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    livello_autonomia character varying(40) NOT NULL,
    localizzazione character varying(30) NOT NULL,
    ultimo_update date NOT NULL,
    ore_operative integer NOT NULL,
    stato_operativo character varying(30) NOT NULL,
    capacita_multimodale character varying(100),
    CONSTRAINT robot_livello_autonomia_check CHECK (((livello_autonomia)::text = ANY (ARRAY[('0 - nessuna autonomia'::character varying)::text, ('1 - supporto umano'::character varying)::text, ('2 - assistenza parziale'::character varying)::text, ('3 - autonomia condizionata'::character varying)::text, ('4 - alta autonomia'::character varying)::text, ('5 - autonomia completa'::character varying)::text]))),
    CONSTRAINT robot_stato_operativo_check CHECK (((stato_operativo)::text = ANY (ARRAY[('On'::character varying)::text, ('Off'::character varying)::text, ('Standby'::character varying)::text, ('Paused'::character varying)::text, ('Error'::character varying)::text, ('Manutenzione'::character varying)::text, ('Emergenza'::character varying)::text])))
);


ALTER TABLE public.robot OWNER TO postgres;

--
-- TOC entry 5183 (class 0 OID 0)
-- Dependencies: 243
-- Name: TABLE robot; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.robot IS '
La tabella robot rappresenta il sistema intelligente con cui interagisce
l''utente. Esso è caratterizzato da nome, modello, versione, 
capacità multimodale e livello di autonomia. Inoltre può trovarsi
in diservi stati operativi a seconda della situazione.
';


--
-- TOC entry 244 (class 1259 OID 25325)
-- Name: robot_id_robot_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.robot_id_robot_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.robot_id_robot_seq OWNER TO postgres;

--
-- TOC entry 5184 (class 0 OID 0)
-- Dependencies: 244
-- Name: robot_id_robot_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.robot_id_robot_seq OWNED BY public.robot.id_robot;


--
-- TOC entry 245 (class 1259 OID 25326)
-- Name: sessione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sessione (
    id_sessione integer NOT NULL,
    token character varying(30) NOT NULL,
    data_inizio timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    data_fine timestamp without time zone,
    id_utente integer NOT NULL,
    CONSTRAINT sessione_check CHECK (((data_fine IS NULL) OR (data_fine > data_inizio)))
);


ALTER TABLE public.sessione OWNER TO postgres;

--
-- TOC entry 5185 (class 0 OID 0)
-- Dependencies: 245
-- Name: TABLE sessione; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.sessione IS '
Tabella che rappresenta la sessione avviata dall'' utente, 
ogni sessione ha un token univoco, una data di inizio e di fine.
';


--
-- TOC entry 246 (class 1259 OID 25331)
-- Name: sessione_id_sessione_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sessione_id_sessione_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sessione_id_sessione_seq OWNER TO postgres;

--
-- TOC entry 5186 (class 0 OID 0)
-- Dependencies: 246
-- Name: sessione_id_sessione_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sessione_id_sessione_seq OWNED BY public.sessione.id_sessione;


--
-- TOC entry 247 (class 1259 OID 25332)
-- Name: storico_emotivo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.storico_emotivo (
    id_storico integer NOT NULL,
    timestamp_storico timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    intensita_emozione numeric(3,2) DEFAULT 0.00 NOT NULL,
    emozione_prevalente character varying(20) NOT NULL,
    id_utente integer NOT NULL,
    emozioni text,
    CONSTRAINT intensita_emozione CHECK (((intensita_emozione >= 0.00) AND (intensita_emozione <= 1.00))),
    CONSTRAINT storico_emotivo_emozione_prevalente_check CHECK (((emozione_prevalente)::text = ANY (ARRAY[('Gioia'::character varying)::text, ('Fiducia'::character varying)::text, ('Paura'::character varying)::text, ('Sorpresa'::character varying)::text, ('Tristezza'::character varying)::text, ('Disgusto'::character varying)::text, ('Rabbia'::character varying)::text, ('Anticipazione'::character varying)::text, ('Amore'::character varying)::text, ('Sottomissione'::character varying)::text, ('Timore'::character varying)::text, ('Delusione'::character varying)::text, ('Rimorso'::character varying)::text, ('Disprezzo'::character varying)::text, ('Aggressivita'::character varying)::text, ('Ottimismo'::character varying)::text])))
);


ALTER TABLE public.storico_emotivo OWNER TO postgres;

--
-- TOC entry 5187 (class 0 OID 0)
-- Dependencies: 247
-- Name: TABLE storico_emotivo; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.storico_emotivo IS '
Tabella che memorizza la cronologia degli stati emotivi 
prevalenti dell''utente nel tempo. Ogni riga registra l''intensità
e la prevalenza di una specifica emozione, in un determinato istante,
in modo da personalizzare e rendere il piu coinvolgente possibile
l''esperienza dell''utente.
';


--
-- TOC entry 253 (class 1259 OID 25558)
-- Name: storico_emotivo_emozione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.storico_emotivo_emozione (
    id_storico integer NOT NULL,
    id_emozione integer NOT NULL
);


ALTER TABLE public.storico_emotivo_emozione OWNER TO postgres;

--
-- TOC entry 5188 (class 0 OID 0)
-- Dependencies: 253
-- Name: TABLE storico_emotivo_emozione; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.storico_emotivo_emozione IS '
Tabella ponte per la relazione N:N tra emozione e storico emotivo.
Permette di associare più emozioni a uno stesso evento storico e viceversa.
';


--
-- TOC entry 248 (class 1259 OID 25341)
-- Name: storico_emotivo_id_storico_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.storico_emotivo_id_storico_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.storico_emotivo_id_storico_seq OWNER TO postgres;

--
-- TOC entry 5189 (class 0 OID 0)
-- Dependencies: 248
-- Name: storico_emotivo_id_storico_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.storico_emotivo_id_storico_seq OWNED BY public.storico_emotivo.id_storico;


--
-- TOC entry 249 (class 1259 OID 25342)
-- Name: traduzione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.traduzione (
    id_traduzione integer NOT NULL,
    timestamp_traduzione timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    testo_tradotto text NOT NULL,
    lingua character varying(10) DEFAULT 'ENG'::character varying NOT NULL,
    id_messaggio integer NOT NULL
);


ALTER TABLE public.traduzione OWNER TO postgres;

--
-- TOC entry 5190 (class 0 OID 0)
-- Dependencies: 249
-- Name: TABLE traduzione; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.traduzione IS '
La tabella traduzione contiene la versione tradotta del messaggio 
originario, in una lingua specificata. Supporta il multilinguismo 
nel sistema.
';


--
-- TOC entry 250 (class 1259 OID 25349)
-- Name: traduzione_id_traduzione_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.traduzione_id_traduzione_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.traduzione_id_traduzione_seq OWNER TO postgres;

--
-- TOC entry 5191 (class 0 OID 0)
-- Dependencies: 250
-- Name: traduzione_id_traduzione_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.traduzione_id_traduzione_seq OWNED BY public.traduzione.id_traduzione;


--
-- TOC entry 251 (class 1259 OID 25350)
-- Name: utente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utente (
    id_utente integer NOT NULL,
    nome character varying(50) NOT NULL,
    cognome character varying(50) NOT NULL,
    email character varying(70) NOT NULL,
    password character varying(100) NOT NULL,
    sesso character(1) NOT NULL,
    data_nascita date NOT NULL,
    lingua character varying(30) NOT NULL,
    data_registrazione date DEFAULT CURRENT_DATE,
    CONSTRAINT utente_password_check CHECK ((char_length((password)::text) >= 8)),
    CONSTRAINT utente_sesso_check CHECK ((sesso = ANY (ARRAY['M'::bpchar, 'F'::bpchar, 'O'::bpchar])))
);


ALTER TABLE public.utente OWNER TO postgres;

--
-- TOC entry 5192 (class 0 OID 0)
-- Dependencies: 251
-- Name: TABLE utente; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.utente IS '
Tabella che rappresenta la persona che interagisce col sistema,
contiene le sue credenziali, dati anagrafici, email, password e lingua
';


--
-- TOC entry 252 (class 1259 OID 25356)
-- Name: utente_id_utente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.utente_id_utente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.utente_id_utente_seq OWNER TO postgres;

--
-- TOC entry 5193 (class 0 OID 0)
-- Dependencies: 252
-- Name: utente_id_utente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.utente_id_utente_seq OWNED BY public.utente.id_utente;


--
-- TOC entry 255 (class 1259 OID 25588)
-- Name: utente_interfaccia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utente_interfaccia (
    id_utente integer NOT NULL,
    id_interfaccia integer NOT NULL
);


ALTER TABLE public.utente_interfaccia OWNER TO postgres;

--
-- TOC entry 5194 (class 0 OID 0)
-- Dependencies: 255
-- Name: TABLE utente_interfaccia; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.utente_interfaccia IS '
Tabella ponte per gestire la relazione N:N tra utente e interfaccia canale.
';


--
-- TOC entry 4839 (class 2604 OID 25360)
-- Name: accessibilita id_accessibilita; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accessibilita ALTER COLUMN id_accessibilita SET DEFAULT nextval('public.accessibilita_id_accessibilita_seq'::regclass);


--
-- TOC entry 4847 (class 2604 OID 25361)
-- Name: config_privacy id_config; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.config_privacy ALTER COLUMN id_config SET DEFAULT nextval('public.config_privacy_id_config_seq'::regclass);


--
-- TOC entry 4852 (class 2604 OID 25362)
-- Name: configurazione_robot id_configurazione; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configurazione_robot ALTER COLUMN id_configurazione SET DEFAULT nextval('public.configurazione_robot_id_configurazione_seq'::regclass);


--
-- TOC entry 4856 (class 2604 OID 25363)
-- Name: contenuto_multimediale id_contenuto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contenuto_multimediale ALTER COLUMN id_contenuto SET DEFAULT nextval('public.contenuto_multimediale_id_contenuto_seq'::regclass);


--
-- TOC entry 4858 (class 2604 OID 25364)
-- Name: emozione id_emozione; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emozione ALTER COLUMN id_emozione SET DEFAULT nextval('public.emozione_id_emozione_seq'::regclass);


--
-- TOC entry 4859 (class 2604 OID 25365)
-- Name: feedback id_feedback; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.feedback ALTER COLUMN id_feedback SET DEFAULT nextval('public.feedback_id_feedback_seq'::regclass);


--
-- TOC entry 4861 (class 2604 OID 25366)
-- Name: interazione id_interazione; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interazione ALTER COLUMN id_interazione SET DEFAULT nextval('public.interazione_id_interazione_seq'::regclass);


--
-- TOC entry 4863 (class 2604 OID 25367)
-- Name: interfaccia_canale id_interfaccia; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interfaccia_canale ALTER COLUMN id_interfaccia SET DEFAULT nextval('public.interfaccia_canale_id_interfaccia_seq'::regclass);


--
-- TOC entry 4867 (class 2604 OID 25368)
-- Name: log_errori id_log; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_errori ALTER COLUMN id_log SET DEFAULT nextval('public.log_errori_id_log_seq'::regclass);


--
-- TOC entry 4870 (class 2604 OID 25369)
-- Name: messaggio id_messaggio; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messaggio ALTER COLUMN id_messaggio SET DEFAULT nextval('public.messaggio_id_messaggio_seq'::regclass);


--
-- TOC entry 4876 (class 2604 OID 25370)
-- Name: metrica id_metrica; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.metrica ALTER COLUMN id_metrica SET DEFAULT nextval('public.metrica_id_metrica_seq'::regclass);


--
-- TOC entry 4880 (class 2604 OID 25371)
-- Name: policy_moderazione id_policy; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.policy_moderazione ALTER COLUMN id_policy SET DEFAULT nextval('public.policy_moderazione_id_policy_seq'::regclass);


--
-- TOC entry 4885 (class 2604 OID 25372)
-- Name: profilo_utente id_profilo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profilo_utente ALTER COLUMN id_profilo SET DEFAULT nextval('public.profilo_utente_id_profilo_seq'::regclass);


--
-- TOC entry 4888 (class 2604 OID 25373)
-- Name: robot id_robot; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.robot ALTER COLUMN id_robot SET DEFAULT nextval('public.robot_id_robot_seq'::regclass);


--
-- TOC entry 4890 (class 2604 OID 25374)
-- Name: sessione id_sessione; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione ALTER COLUMN id_sessione SET DEFAULT nextval('public.sessione_id_sessione_seq'::regclass);


--
-- TOC entry 4892 (class 2604 OID 25375)
-- Name: storico_emotivo id_storico; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico_emotivo ALTER COLUMN id_storico SET DEFAULT nextval('public.storico_emotivo_id_storico_seq'::regclass);


--
-- TOC entry 4895 (class 2604 OID 25376)
-- Name: traduzione id_traduzione; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.traduzione ALTER COLUMN id_traduzione SET DEFAULT nextval('public.traduzione_id_traduzione_seq'::regclass);


--
-- TOC entry 4898 (class 2604 OID 25377)
-- Name: utente id_utente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente ALTER COLUMN id_utente SET DEFAULT nextval('public.utente_id_utente_seq'::regclass);


--
-- TOC entry 4934 (class 2606 OID 25379)
-- Name: accessibilita accessibilita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accessibilita
    ADD CONSTRAINT accessibilita_pkey PRIMARY KEY (id_accessibilita);


--
-- TOC entry 4936 (class 2606 OID 25381)
-- Name: config_privacy config_privacy_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.config_privacy
    ADD CONSTRAINT config_privacy_pkey PRIMARY KEY (id_config);


--
-- TOC entry 4938 (class 2606 OID 25383)
-- Name: configurazione_robot configurazione_robot_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configurazione_robot
    ADD CONSTRAINT configurazione_robot_pkey PRIMARY KEY (id_configurazione);


--
-- TOC entry 4940 (class 2606 OID 25385)
-- Name: contenuto_multimediale contenuto_multimediale_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contenuto_multimediale
    ADD CONSTRAINT contenuto_multimediale_pkey PRIMARY KEY (id_contenuto);


--
-- TOC entry 4942 (class 2606 OID 25387)
-- Name: emozione emozione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emozione
    ADD CONSTRAINT emozione_pkey PRIMARY KEY (id_emozione);


--
-- TOC entry 4944 (class 2606 OID 25389)
-- Name: feedback feedback_id_utente_id_interazione_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.feedback
    ADD CONSTRAINT feedback_id_utente_id_interazione_key UNIQUE (id_utente, id_interazione);


--
-- TOC entry 4946 (class 2606 OID 25391)
-- Name: feedback feedback_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.feedback
    ADD CONSTRAINT feedback_pkey PRIMARY KEY (id_feedback);


--
-- TOC entry 4978 (class 2606 OID 25577)
-- Name: interazione_interfaccia interazione_interfaccia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interazione_interfaccia
    ADD CONSTRAINT interazione_interfaccia_pkey PRIMARY KEY (id_interazione, id_interfaccia);


--
-- TOC entry 4948 (class 2606 OID 25395)
-- Name: interazione interazione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interazione
    ADD CONSTRAINT interazione_pkey PRIMARY KEY (id_interazione);


--
-- TOC entry 4950 (class 2606 OID 25397)
-- Name: interfaccia_canale interfaccia_canale_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interfaccia_canale
    ADD CONSTRAINT interfaccia_canale_pkey PRIMARY KEY (id_interfaccia);


--
-- TOC entry 4952 (class 2606 OID 25399)
-- Name: log_errori log_errori_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_errori
    ADD CONSTRAINT log_errori_pkey PRIMARY KEY (id_log);


--
-- TOC entry 4954 (class 2606 OID 25401)
-- Name: messaggio messaggio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messaggio
    ADD CONSTRAINT messaggio_pkey PRIMARY KEY (id_messaggio);


--
-- TOC entry 4956 (class 2606 OID 25403)
-- Name: metrica metrica_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.metrica
    ADD CONSTRAINT metrica_pkey PRIMARY KEY (id_metrica);


--
-- TOC entry 4958 (class 2606 OID 25405)
-- Name: policy_moderazione policy_moderazione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.policy_moderazione
    ADD CONSTRAINT policy_moderazione_pkey PRIMARY KEY (id_policy);


--
-- TOC entry 4960 (class 2606 OID 25407)
-- Name: profilo_utente profilo_utente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profilo_utente
    ADD CONSTRAINT profilo_utente_pkey PRIMARY KEY (id_profilo);


--
-- TOC entry 4962 (class 2606 OID 25409)
-- Name: robot robot_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.robot
    ADD CONSTRAINT robot_pkey PRIMARY KEY (id_robot);


--
-- TOC entry 4964 (class 2606 OID 25411)
-- Name: sessione sessione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione
    ADD CONSTRAINT sessione_pkey PRIMARY KEY (id_sessione);


--
-- TOC entry 4966 (class 2606 OID 25413)
-- Name: sessione sessione_token_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione
    ADD CONSTRAINT sessione_token_key UNIQUE (token);


--
-- TOC entry 4976 (class 2606 OID 25562)
-- Name: storico_emotivo_emozione storico_emotivo_emozione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico_emotivo_emozione
    ADD CONSTRAINT storico_emotivo_emozione_pkey PRIMARY KEY (id_storico, id_emozione);


--
-- TOC entry 4968 (class 2606 OID 25417)
-- Name: storico_emotivo storico_emotivo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico_emotivo
    ADD CONSTRAINT storico_emotivo_pkey PRIMARY KEY (id_storico);


--
-- TOC entry 4970 (class 2606 OID 25419)
-- Name: traduzione traduzione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.traduzione
    ADD CONSTRAINT traduzione_pkey PRIMARY KEY (id_traduzione);


--
-- TOC entry 4972 (class 2606 OID 25421)
-- Name: utente utente_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_email_key UNIQUE (email);


--
-- TOC entry 4980 (class 2606 OID 25592)
-- Name: utente_interfaccia utente_interfaccia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente_interfaccia
    ADD CONSTRAINT utente_interfaccia_pkey PRIMARY KEY (id_utente, id_interfaccia);


--
-- TOC entry 4974 (class 2606 OID 25425)
-- Name: utente utente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (id_utente);


--
-- TOC entry 4981 (class 2606 OID 25426)
-- Name: accessibilita accessibilita_id_utente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accessibilita
    ADD CONSTRAINT accessibilita_id_utente_fkey FOREIGN KEY (id_utente) REFERENCES public.utente(id_utente) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4982 (class 2606 OID 25431)
-- Name: config_privacy config_privacy_id_utente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.config_privacy
    ADD CONSTRAINT config_privacy_id_utente_fkey FOREIGN KEY (id_utente) REFERENCES public.utente(id_utente) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4983 (class 2606 OID 25436)
-- Name: configurazione_robot configurazione_robot_id_robot_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configurazione_robot
    ADD CONSTRAINT configurazione_robot_id_robot_fkey FOREIGN KEY (id_robot) REFERENCES public.robot(id_robot) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4984 (class 2606 OID 25441)
-- Name: configurazione_robot configurazione_robot_id_utente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configurazione_robot
    ADD CONSTRAINT configurazione_robot_id_utente_fkey FOREIGN KEY (id_utente) REFERENCES public.utente(id_utente) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4985 (class 2606 OID 25446)
-- Name: contenuto_multimediale contenuto_multimediale_id_interazione_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contenuto_multimediale
    ADD CONSTRAINT contenuto_multimediale_id_interazione_fkey FOREIGN KEY (id_interazione) REFERENCES public.interazione(id_interazione) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4986 (class 2606 OID 25451)
-- Name: emozione emozione_id_interazione_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emozione
    ADD CONSTRAINT emozione_id_interazione_fkey FOREIGN KEY (id_interazione) REFERENCES public.interazione(id_interazione) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4987 (class 2606 OID 25456)
-- Name: emozione emozione_id_messaggio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.emozione
    ADD CONSTRAINT emozione_id_messaggio_fkey FOREIGN KEY (id_messaggio) REFERENCES public.messaggio(id_messaggio) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4988 (class 2606 OID 25461)
-- Name: feedback feedback_id_interazione_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.feedback
    ADD CONSTRAINT feedback_id_interazione_fkey FOREIGN KEY (id_interazione) REFERENCES public.interazione(id_interazione) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4989 (class 2606 OID 25466)
-- Name: feedback feedback_id_utente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.feedback
    ADD CONSTRAINT feedback_id_utente_fkey FOREIGN KEY (id_utente) REFERENCES public.utente(id_utente) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4990 (class 2606 OID 25471)
-- Name: interazione interazione_id_robot_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interazione
    ADD CONSTRAINT interazione_id_robot_fkey FOREIGN KEY (id_robot) REFERENCES public.robot(id_robot) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4991 (class 2606 OID 25476)
-- Name: interazione interazione_id_sessione_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interazione
    ADD CONSTRAINT interazione_id_sessione_fkey FOREIGN KEY (id_sessione) REFERENCES public.sessione(id_sessione) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5003 (class 2606 OID 25578)
-- Name: interazione_interfaccia interazione_interfaccia_id_interazione_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interazione_interfaccia
    ADD CONSTRAINT interazione_interfaccia_id_interazione_fkey FOREIGN KEY (id_interazione) REFERENCES public.interazione(id_interazione) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5004 (class 2606 OID 25583)
-- Name: interazione_interfaccia interazione_interfaccia_id_interfaccia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interazione_interfaccia
    ADD CONSTRAINT interazione_interfaccia_id_interfaccia_fkey FOREIGN KEY (id_interfaccia) REFERENCES public.interfaccia_canale(id_interfaccia) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4992 (class 2606 OID 25491)
-- Name: log_errori log_errori_id_robot_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_errori
    ADD CONSTRAINT log_errori_id_robot_fkey FOREIGN KEY (id_robot) REFERENCES public.robot(id_robot) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 4993 (class 2606 OID 25496)
-- Name: log_errori log_errori_id_sessione_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_errori
    ADD CONSTRAINT log_errori_id_sessione_fkey FOREIGN KEY (id_sessione) REFERENCES public.sessione(id_sessione) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 4994 (class 2606 OID 25501)
-- Name: messaggio messaggio_id_robot_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messaggio
    ADD CONSTRAINT messaggio_id_robot_fkey FOREIGN KEY (id_robot) REFERENCES public.robot(id_robot) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4995 (class 2606 OID 25506)
-- Name: metrica metrica_id_interazione_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.metrica
    ADD CONSTRAINT metrica_id_interazione_fkey FOREIGN KEY (id_interazione) REFERENCES public.interazione(id_interazione) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4996 (class 2606 OID 25511)
-- Name: policy_moderazione policy_moderazione_id_messaggio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.policy_moderazione
    ADD CONSTRAINT policy_moderazione_id_messaggio_fkey FOREIGN KEY (id_messaggio) REFERENCES public.messaggio(id_messaggio) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4997 (class 2606 OID 25516)
-- Name: profilo_utente profilo_utente_id_utente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profilo_utente
    ADD CONSTRAINT profilo_utente_id_utente_fkey FOREIGN KEY (id_utente) REFERENCES public.utente(id_utente) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4998 (class 2606 OID 25521)
-- Name: sessione sessione_id_utente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione
    ADD CONSTRAINT sessione_id_utente_fkey FOREIGN KEY (id_utente) REFERENCES public.utente(id_utente) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5001 (class 2606 OID 25568)
-- Name: storico_emotivo_emozione storico_emotivo_emozione_id_emozione_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico_emotivo_emozione
    ADD CONSTRAINT storico_emotivo_emozione_id_emozione_fkey FOREIGN KEY (id_emozione) REFERENCES public.emozione(id_emozione) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5002 (class 2606 OID 25563)
-- Name: storico_emotivo_emozione storico_emotivo_emozione_id_storico_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico_emotivo_emozione
    ADD CONSTRAINT storico_emotivo_emozione_id_storico_fkey FOREIGN KEY (id_storico) REFERENCES public.storico_emotivo(id_storico) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4999 (class 2606 OID 25536)
-- Name: storico_emotivo storico_emotivo_id_utente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico_emotivo
    ADD CONSTRAINT storico_emotivo_id_utente_fkey FOREIGN KEY (id_utente) REFERENCES public.utente(id_utente) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5000 (class 2606 OID 25541)
-- Name: traduzione traduzione_id_messaggio_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.traduzione
    ADD CONSTRAINT traduzione_id_messaggio_fkey FOREIGN KEY (id_messaggio) REFERENCES public.messaggio(id_messaggio) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5005 (class 2606 OID 25598)
-- Name: utente_interfaccia utente_interfaccia_id_interfaccia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente_interfaccia
    ADD CONSTRAINT utente_interfaccia_id_interfaccia_fkey FOREIGN KEY (id_interfaccia) REFERENCES public.interfaccia_canale(id_interfaccia) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5006 (class 2606 OID 25593)
-- Name: utente_interfaccia utente_interfaccia_id_utente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente_interfaccia
    ADD CONSTRAINT utente_interfaccia_id_utente_fkey FOREIGN KEY (id_utente) REFERENCES public.utente(id_utente) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2025-07-05 19:40:08

--
-- PostgreSQL database dump complete
--

