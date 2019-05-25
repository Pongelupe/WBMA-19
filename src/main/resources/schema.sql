--
-- PostgreSQL database dump
--

-- Dumped from database version 11.3 (Debian 11.3-1.pgdg90+1)
-- Dumped by pg_dump version 11.3 (Debian 11.3-1.pgdg90+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: wbma; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE wbma WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8';


ALTER DATABASE wbma OWNER TO postgres;

\connect wbma

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: commit; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.commit (
    id integer NOT NULL,
    commited_at timestamp without time zone,
    author character varying(60),
    message text,
    contributor_id integer NOT NULL,
    repository_id integer NOT NULL
);


ALTER TABLE public.commit OWNER TO postgres;

--
-- Name: commit_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.commit_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.commit_id_seq OWNER TO postgres;

--
-- Name: commit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.commit_id_seq OWNED BY public.commit.id;


--
-- Name: contributor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contributor (
    id integer NOT NULL,
    username character varying(60)
);


ALTER TABLE public.contributor OWNER TO postgres;

--
-- Name: contributor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.contributor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.contributor_id_seq OWNER TO postgres;

--
-- Name: contributor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.contributor_id_seq OWNED BY public.contributor.id;


--
-- Name: course; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.course (
    id integer NOT NULL,
    name character varying(60),
    semester timestamp without time zone
);


ALTER TABLE public.course OWNER TO postgres;

--
-- Name: course_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.course_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.course_id_seq OWNER TO postgres;

--
-- Name: course_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.course_id_seq OWNED BY public.course.id;


--
-- Name: coursexcontributor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.coursexcontributor (
    contributor_id integer NOT NULL,
    course_id integer NOT NULL,
    key_contributor boolean DEFAULT false
);


ALTER TABLE public.coursexcontributor OWNER TO postgres;

--
-- Name: repository; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.repository (
    id integer NOT NULL,
    name_tool character varying(60),
    name character varying(60),
    url character varying(120),
    truck_factor integer,
    course_id integer NOT NULL
);


ALTER TABLE public.repository OWNER TO postgres;

--
-- Name: repository_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.repository_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.repository_id_seq OWNER TO postgres;

--
-- Name: repository_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.repository_id_seq OWNED BY public.repository.id;


--
-- Name: sprint; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sprint (
    id integer NOT NULL,
    name character varying(60),
    initialdate timestamp without time zone,
    enddate timestamp without time zone,
    course_id integer NOT NULL
);


ALTER TABLE public.sprint OWNER TO postgres;

--
-- Name: sprint_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sprint_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sprint_id_seq OWNER TO postgres;

--
-- Name: sprint_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sprint_id_seq OWNED BY public.sprint.id;


--
-- Name: commit id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commit ALTER COLUMN id SET DEFAULT nextval('public.commit_id_seq'::regclass);


--
-- Name: contributor id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contributor ALTER COLUMN id SET DEFAULT nextval('public.contributor_id_seq'::regclass);


--
-- Name: course id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course ALTER COLUMN id SET DEFAULT nextval('public.course_id_seq'::regclass);


--
-- Name: repository id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repository ALTER COLUMN id SET DEFAULT nextval('public.repository_id_seq'::regclass);


--
-- Name: sprint id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sprint ALTER COLUMN id SET DEFAULT nextval('public.sprint_id_seq'::regclass);


--
-- Data for Name: commit; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.commit (id, commited_at, author, message, contributor_id, repository_id) FROM stdin;
\.


--
-- Data for Name: contributor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.contributor (id, username) FROM stdin;
\.


--
-- Data for Name: course; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.course (id, name, semester) FROM stdin;
\.


--
-- Data for Name: coursexcontributor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.coursexcontributor (contributor_id, course_id, key_contributor) FROM stdin;
\.


--
-- Data for Name: repository; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.repository (id, name_tool, name, url, truck_factor, course_id) FROM stdin;
\.


--
-- Data for Name: sprint; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sprint (id, name, initialdate, enddate, course_id) FROM stdin;
\.


--
-- Name: commit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.commit_id_seq', 1, false);


--
-- Name: contributor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.contributor_id_seq', 1, false);


--
-- Name: course_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.course_id_seq', 1, false);


--
-- Name: repository_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.repository_id_seq', 1, false);


--
-- Name: sprint_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sprint_id_seq', 1, false);


--
-- Name: commit commit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commit
    ADD CONSTRAINT commit_pkey PRIMARY KEY (id);


--
-- Name: contributor contributor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contributor
    ADD CONSTRAINT contributor_pkey PRIMARY KEY (id);


--
-- Name: course course_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course
    ADD CONSTRAINT course_pkey PRIMARY KEY (id);


--
-- Name: coursexcontributor coursexcontributor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.coursexcontributor
    ADD CONSTRAINT coursexcontributor_pkey PRIMARY KEY (contributor_id, course_id);


--
-- Name: repository repository_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repository
    ADD CONSTRAINT repository_pkey PRIMARY KEY (id);


--
-- Name: sprint sprint_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sprint
    ADD CONSTRAINT sprint_pkey PRIMARY KEY (id);


--
-- Name: commit commit_contributor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commit
    ADD CONSTRAINT commit_contributor_id_fkey FOREIGN KEY (contributor_id) REFERENCES public.contributor(id);


--
-- Name: commit commit_repository_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commit
    ADD CONSTRAINT commit_repository_id_fkey FOREIGN KEY (repository_id) REFERENCES public.repository(id);


--
-- Name: repository repository_course_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repository
    ADD CONSTRAINT repository_course_id_fkey FOREIGN KEY (course_id) REFERENCES public.course(id);


--
-- Name: sprint sprint_course_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sprint
    ADD CONSTRAINT sprint_course_id_fkey FOREIGN KEY (course_id) REFERENCES public.course(id);


--
-- PostgreSQL database dump complete
--

