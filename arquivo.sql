--
-- PostgreSQL database dump
--

-- Dumped from database version 10.23 (Ubuntu 10.23-1.pgdg20.04+1)
-- Dumped by pg_dump version 15.3

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
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

SET default_tablespace = '';

--
-- Name: ajusteestoque; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ajusteestoque (
    codigo integer NOT NULL,
    motivo character varying(255),
    dataajuste date,
    quantidade numeric(10,2),
    produto integer,
    tipo integer
);


ALTER TABLE public.ajusteestoque OWNER TO postgres;

--
-- Name: ajusteestoque_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.ajusteestoque ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ajusteestoque_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: caixa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.caixa (
    codigo integer NOT NULL,
    descricao character varying(255),
    datalancamento timestamp without time zone,
    valorbaixa numeric(10,3),
    tipo character varying(50),
    parcela integer,
    funcionario integer
);


ALTER TABLE public.caixa OWNER TO postgres;

--
-- Name: caixa_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.caixa ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.caixa_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: cidade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cidade (
    codigo integer NOT NULL,
    nome character varying(255),
    uf character(2)
);


ALTER TABLE public.cidade OWNER TO postgres;

--
-- Name: cidade_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.cidade ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.cidade_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    codigo integer NOT NULL,
    nome character varying(255),
    endereco character varying(255),
    bairro character varying(255),
    contato text,
    cpf character varying(25),
    credito numeric(10,2) DEFAULT 0.00,
    ativo character(1) DEFAULT 'T'::bpchar,
    datacadastro date,
    cidade integer
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- Name: cliente_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.cliente ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.cliente_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: condicaopagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.condicaopagamento (
    codigo integer NOT NULL,
    numeroparcelas integer,
    descricao character varying(255)
);


ALTER TABLE public.condicaopagamento OWNER TO postgres;

--
-- Name: condicaopagamento_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.condicaopagamento ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.condicaopagamento_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: estabelecimento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estabelecimento (
    codigo integer NOT NULL,
    nome character varying(255),
    contato text,
    endereco character varying(255),
    bairro character varying(255),
    cnpj character varying(25),
    cidade integer
);


ALTER TABLE public.estabelecimento OWNER TO postgres;

--
-- Name: estabelecimento_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.estabelecimento ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.estabelecimento_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: estoque_transiente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estoque_transiente (
    codigo integer NOT NULL,
    produto integer,
    quantidade_uso numeric(10,3),
    quantidade_acesso integer,
    horaisercao timestamp without time zone
);


ALTER TABLE public.estoque_transiente OWNER TO postgres;

--
-- Name: estoque_transiente_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.estoque_transiente ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.estoque_transiente_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: formapagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.formapagamento (
    codigo integer NOT NULL,
    aparecercaixa character(1) DEFAULT 'T'::bpchar,
    descricao character varying(255),
    compensado character(1) DEFAULT 'T'::bpchar
);


ALTER TABLE public.formapagamento OWNER TO postgres;

--
-- Name: formapagmento_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.formapagamento ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.formapagmento_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: fornecedor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fornecedor (
    codigo integer NOT NULL,
    nome character varying(255),
    cpfcnpj character varying(25),
    bairro character varying(255),
    endereco character varying(255),
    contato text,
    ativo character(1) DEFAULT 'T'::bpchar,
    cidade integer,
    fiscajuridica integer
);


ALTER TABLE public.fornecedor OWNER TO postgres;

--
-- Name: fornecedor_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.fornecedor ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.fornecedor_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: funcionario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.funcionario (
    codigo integer NOT NULL,
    nome character varying(255),
    cpf character varying(25),
    bairro character varying(255),
    endereco character varying(255),
    login character varying(255),
    senha character varying(255),
    ativo character(1) DEFAULT 'T'::bpchar,
    cidade integer
);


ALTER TABLE public.funcionario OWNER TO postgres;

--
-- Name: funcionario_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.funcionario ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.funcionario_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: funcionariopermissao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.funcionariopermissao (
    codigo integer NOT NULL,
    funcionario integer,
    permissao integer
);


ALTER TABLE public.funcionariopermissao OWNER TO postgres;

--
-- Name: funcionariopermissao_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.funcionariopermissao ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.funcionariopermissao_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: grupo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.grupo (
    codigo integer NOT NULL,
    descricao character varying(255)
);


ALTER TABLE public.grupo OWNER TO postgres;

--
-- Name: grupo_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.grupo ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.grupo_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: itemvenda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.itemvenda (
    codigo integer NOT NULL,
    quantidade numeric(10,3),
    valorunitario numeric(10,3),
    totalunitario numeric(10,3),
    produto integer,
    venda integer
);


ALTER TABLE public.itemvenda OWNER TO postgres;

--
-- Name: itemvenda_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.itemvenda ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.itemvenda_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: parcela; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.parcela (
    codigo integer NOT NULL,
    valorparcela numeric(10,3),
    dataemissao date,
    datapagamento date,
    quitada character(1) DEFAULT 'T'::bpchar,
    numeroparcela character varying(50),
    recebimento integer
);


ALTER TABLE public.parcela OWNER TO postgres;

--
-- Name: parcela_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.parcela ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.parcela_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: permissao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.permissao (
    codigo integer NOT NULL,
    descricao character varying(255)
);


ALTER TABLE public.permissao OWNER TO postgres;

--
-- Name: permissao_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.permissao ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.permissao_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produto (
    codigo integer NOT NULL,
    descricao character varying(255),
    margem numeric(10,3),
    precocusto numeric(10,3),
    precovenda numeric(10,3),
    fornecedor integer,
    grupo integer,
    ativo character(1)
);


ALTER TABLE public.produto OWNER TO postgres;

--
-- Name: produto_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.produto ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.produto_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: recebimento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recebimento (
    codigo integer NOT NULL,
    valor numeric(10,3),
    dataemissao date,
    datavencimento date,
    quitada character(1) DEFAULT 'T'::bpchar,
    venda integer,
    condicaopagamento integer,
    formapagamento integer,
    cliente integer,
    quitado character(1) DEFAULT 'F'::bpchar
);


ALTER TABLE public.recebimento OWNER TO postgres;

--
-- Name: recebimento_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.recebimento ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.recebimento_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: venda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.venda (
    codigo integer NOT NULL,
    datavenda date,
    totalvenda numeric(10,2),
    cliente integer,
    desconto numeric(10,3),
    acrescimo numeric(10,3),
    funcionario integer
);


ALTER TABLE public.venda OWNER TO postgres;

--
-- Name: venda_codigo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.venda ALTER COLUMN codigo ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.venda_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: ajusteestoque; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ajusteestoque (codigo, motivo, dataajuste, quantidade, produto, tipo) FROM stdin;
1	teste	2023-08-16	5.00	1	2
4		2023-08-21	23.00	1	2
5	Em estoque	2023-09-12	100.00	2	2
6	Em estoque	2023-10-23	100.00	1	2
\.


--
-- Data for Name: caixa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.caixa (codigo, descricao, datalancamento, valorbaixa, tipo, parcela, funcionario) FROM stdin;
1	Recebimento: Daniel Ausech Esperandio - Parcela: 2	2023-10-21 22:49:59.130676	50.000	\N	4	1
2	Recebimento: Daniel Ausech Esperandio - Parcela: 2	2023-10-21 23:07:26.533946	14.670	\N	5	1
3	Recebimento: Daniel Ausech Esperandio - Parcela: 3	2023-10-21 23:07:27.489648	14.670	\N	6	1
4	Recebimento: Daniel Ausech Esperandio - Parcela: 4	2023-10-21 23:07:27.988273	14.670	\N	7	1
5	Recebimento: Daniel Ausech Esperandio - Parcela: 5	2023-10-21 23:07:28.47794	14.670	\N	8	1
6	Recebimento: Daniel Ausech Esperandio - Parcela: 6	2023-10-21 23:07:28.963264	14.670	\N	9	1
7	Recebimento: Daniel Ausech Esperandio - Parcela: 7	2023-10-21 23:07:29.760355	14.670	\N	10	1
8	Recebimento: Daniel Ausech Esperandio - Parcela: 2	2023-10-21 23:29:16.236034	40.000	\N	11	1
9	Recebimento: Bruno de Oliveira Alves - Parcela: 2	2023-10-21 23:36:26.991727	40.000	\N	12	1
10	Recebimento: Daniel Ausech Esperandio - Parcela: 2	2023-10-21 23:44:08.180205	50.000	\N	13	1
11	Recebimento: Daniel Ausech Esperandio - Parcela: 2	2023-10-22 01:24:24.744036	40.000	\N	14	1
12	Recebimento: Daniel Ausech Esperandio - Parcela: 2	2023-10-22 01:35:00.787054	40.000	\N	15	1
13	Recebimento: Daniel Ausech Esperandio - Parcela: 2	2023-10-23 16:23:50.527091	40.000	\N	16	1
14	Recebimento: Daniel Ausech Esperandio - Parcela: 2	2023-10-27 10:32:55.780039	50.000	\N	17	1
15	Recebimento: Bruno de Oliveira Alves - Parcela: 2	2023-10-27 10:33:29.326506	50.000	\N	18	1
16	Recebimento: Daniel Ausech Esperandio - Parcela: 2	2023-10-27 10:41:53.442953	40.000	\N	19	1
17	Recebimento: Daniel Ausech Esperandio - Parcela: 2	2023-10-27 10:45:52.204573	50.000	\N	20	1
18	Recebimento: Bruno de Oliveira Alves - Parcela: 2	2023-10-27 10:47:47.784629	50.000	\N	21	1
19	Recebimento: Daniel Ausech Esperandio - Parcela: 2	2023-10-27 10:51:41.571376	50.000	\N	22	1
20	Recebimento: Bruno de Oliveira Alves - Parcela: 2	2023-10-27 11:08:07.376569	50.000	\N	23	1
21	Recebimento: Bruno de Oliveira Alves - Parcela: 2	2023-10-27 11:09:53.353603	50.000	\N	24	1
22	Recebimento: Bruno de Oliveira Alves - Parcela: 2	2023-10-27 11:12:23.580795	50.000	\N	25	1
23	Recebimento: Bruno de Oliveira Alves - Parcela: 2	2023-10-27 11:23:01.153731	50.000	\N	26	1
24	Recebimento: Bruno de Oliveira Alves - Parcela: 2	2023-10-27 11:24:36.125002	50.000	\N	27	1
25	Recebimento: Bruno de Oliveira Alves - Parcela: 2	2023-10-27 11:27:45.56529	50.000	\N	28	1
26	Recebimento: Bruno de Oliveira Alves - Parcela: 2	2023-10-27 11:28:13.113575	50.000	\N	29	1
27	Recebimento: Bruno de Oliveira Alves - Parcela: 2	2023-10-27 11:29:14.479137	50.000	\N	30	1
28	Recebimento: Bruno de Oliveira Alves - Parcela: 2	2023-10-27 11:30:42.1518	50.000	\N	31	1
29	Recebimento: Bruno de Oliveira Alves - Parcela: 2	2023-10-27 11:35:03.754355	50.000	\N	32	1
30	Recebimento: Bruno de Oliveira Alves - Parcela: 2	2023-10-27 11:38:17.172515	50.000	\N	33	1
31	Recebimento: Daniel Ausech Esperandio - Parcela: 2	2023-10-27 11:40:45.922531	50.000	\N	34	1
\.


--
-- Data for Name: cidade; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cidade (codigo, nome, uf) FROM stdin;
1	Cândido Mota	SP
4	Antônio Conti	SP
6	Araçatuba	SP
2	Assis	SP
\.


--
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cliente (codigo, nome, endereco, bairro, contato, cpf, credito, ativo, datacadastro, cidade) FROM stdin;
2	Daniel Ausech Esperandio	Rua frederico Mossini 279	Jardim das Palmeiras	18997892286	45278457805	500.00	T	2023-08-14	2
3	Bruno de Oliveira Alves	Rua Altamiro Prado 56	Jardim Europa	Pai: 186653232	23504553006	500.00	T	2023-09-12	1
\.


--
-- Data for Name: condicaopagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.condicaopagamento (codigo, numeroparcelas, descricao) FROM stdin;
1	1	A vista
3	4	5 - 10 - 15 - 5
4	6	30 - 60 - 90 - 120 - 150 - 180
\.


--
-- Data for Name: estabelecimento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.estabelecimento (codigo, nome, contato, endereco, bairro, cnpj, cidade) FROM stdin;
1	LimaSoftware	teste	trsr	teste	56556565656655	1
\.


--
-- Data for Name: estoque_transiente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.estoque_transiente (codigo, produto, quantidade_uso, quantidade_acesso, horaisercao) FROM stdin;
37	2	15.000	15	2023-10-27 10:31:40.529517
\.


--
-- Data for Name: formapagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.formapagamento (codigo, aparecercaixa, descricao, compensado) FROM stdin;
1	T	Dinheiro	T
6	T	Boleto Bancário	T
5	T	Cartão de crédito	T
\.


--
-- Data for Name: fornecedor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fornecedor (codigo, nome, cpfcnpj, bairro, endereco, contato, ativo, cidade, fiscajuridica) FROM stdin;
3	Energisa	47240926000118	Centro	Rua Altamiro Prado 23	Fernando 3341-5628	T	2	1
\.


--
-- Data for Name: funcionario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.funcionario (codigo, nome, cpf, bairro, endereco, login, senha, ativo, cidade) FROM stdin;
3	Fernando Cesar de Lima	57823304066	teste	Teste	2	2	T	1
1	Daniel	45278457805	Teste	Rua teste	1	1	T	1
\.


--
-- Data for Name: funcionariopermissao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.funcionariopermissao (codigo, funcionario, permissao) FROM stdin;
619	3	1
620	3	10
621	3	11
622	3	12
623	3	13
624	3	14
625	3	15
626	3	16
627	3	17
628	3	18
629	3	19
630	3	20
631	3	21
632	3	22
633	3	23
634	3	24
635	3	25
636	3	26
637	3	27
638	3	28
639	3	29
640	3	30
641	3	31
642	3	32
643	3	33
644	3	34
645	3	35
646	3	36
647	3	37
648	3	38
649	3	39
650	3	40
651	3	41
652	3	42
653	3	43
688	1	1
689	1	10
690	1	11
691	1	12
692	1	13
693	1	14
694	1	15
695	1	16
696	1	17
697	1	18
698	1	19
699	1	20
700	1	21
701	1	22
702	1	23
703	1	24
704	1	25
705	1	26
706	1	27
707	1	28
708	1	29
709	1	30
710	1	31
711	1	32
712	1	33
713	1	34
714	1	35
715	1	36
716	1	37
717	1	38
718	1	39
719	1	40
720	1	41
721	1	42
722	1	43
\.


--
-- Data for Name: grupo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.grupo (codigo, descricao) FROM stdin;
2	Roupas
\.


--
-- Data for Name: itemvenda; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.itemvenda (codigo, quantidade, valorunitario, totalunitario, produto, venda) FROM stdin;
1	1.000	40.000	40.000	1	8
2	1.000	40.000	40.000	1	9
3	1.000	50.000	50.000	2	10
4	1.000	40.000	40.000	1	11
5	1.000	40.000	40.000	1	12
6	1.000	40.000	40.000	1	13
7	1.000	50.000	50.000	2	14
8	1.000	50.000	50.000	2	15
9	1.000	40.000	40.000	1	16
10	1.000	50.000	50.000	2	17
11	1.000	50.000	50.000	2	18
12	1.000	50.000	50.000	2	19
13	1.000	50.000	50.000	2	20
14	1.000	50.000	50.000	2	21
15	1.000	50.000	50.000	2	22
16	1.000	50.000	50.000	2	23
17	1.000	50.000	50.000	2	24
18	1.000	50.000	50.000	2	25
19	1.000	50.000	50.000	2	26
20	1.000	50.000	50.000	2	27
21	1.000	50.000	50.000	2	28
22	1.000	50.000	50.000	2	29
23	1.000	50.000	50.000	2	30
24	1.000	50.000	50.000	2	31
\.


--
-- Data for Name: parcela; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.parcela (codigo, valorparcela, dataemissao, datapagamento, quitada, numeroparcela, recebimento) FROM stdin;
4	50.000	2023-10-21	2023-10-21	T	2	6
5	14.670	2023-10-21	2023-11-20	T	2	7
6	14.670	2023-10-21	2023-12-20	T	3	8
7	14.670	2023-10-21	2024-01-19	T	4	9
8	14.670	2023-10-21	2024-02-18	T	5	10
9	14.670	2023-10-21	2024-03-19	T	6	11
10	14.670	2023-10-21	2024-04-18	T	7	12
11	40.000	2023-10-21	2023-10-21	T	2	13
12	40.000	2023-10-21	2023-10-21	T	2	14
13	50.000	2023-10-21	2023-10-22	T	2	15
14	40.000	2023-10-22	2023-10-22	T	2	16
15	40.000	2023-10-22	2023-10-22	T	2	17
16	40.000	2023-10-23	2023-10-23	T	2	18
17	50.000	2023-10-27	2023-10-27	T	2	19
18	50.000	2023-10-27	2023-10-27	T	2	20
19	40.000	2023-10-27	2023-10-27	T	2	21
20	50.000	2023-10-27	2023-10-27	T	2	22
21	50.000	2023-10-27	2023-10-27	T	2	23
22	50.000	2023-10-27	2023-10-27	T	2	24
23	50.000	2023-10-27	2023-10-27	T	2	25
24	50.000	2023-10-27	2023-10-27	T	2	26
25	50.000	2023-10-27	2023-10-27	T	2	27
26	50.000	2023-10-27	2023-10-27	T	2	28
27	50.000	2023-10-27	2023-10-27	T	2	29
28	50.000	2023-10-27	2023-10-27	T	2	30
29	50.000	2023-10-27	2023-10-27	T	2	31
30	50.000	2023-10-27	2023-10-27	T	2	32
31	50.000	2023-10-27	2023-10-27	T	2	33
32	50.000	2023-10-27	2023-10-27	T	2	34
33	50.000	2023-10-03	2023-10-27	T	2	35
34	50.000	2023-10-27	2023-10-27	T	2	36
\.


--
-- Data for Name: permissao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.permissao (codigo, descricao) FROM stdin;
1	Alterar Estabelecimento
10	Pesquisar cidade
11	Cadastrar cidade
12	Alterar cidade
13	Excluir cidade
14	Pesquisar funcionario
15	Alterar funcionario
16	Cadastrar funcionario
17	Excluir funcionario
18	Consultar fornecedor
19	Cadastrar fornecedor
20	Alterar fornecedor
21	Excluir fornecedor
22	Consultar cliente
23	Cadastrar cliente
24	Alterar cliente
25	Excluir cliente
26	Consultar grupo
27	Cadastrar grupo
28	Alterar grupo
29	Excluir grupo
30	Consultar produto
31	Cadastrar produto
32	Alterar produto
33	Cadastrar ajuste estoque
34	Excluir ajuste estoque
35	Pesquisar forma de pagamento
36	Alterar forma de pagamento
37	Excluir forma de pagamento
38	Cadastrar forma de pagamento
39	Consultar condicao de pagamento
40	Alterar condicao de pagamento
41	Cadastrar condicao de pagamento
42	Excluir condicao de pagamento
43	Iniciar venda
\.


--
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.produto (codigo, descricao, margem, precocusto, precovenda, fornecedor, grupo, ativo) FROM stdin;
1	Traje de banho feminino da Barbie	100.000	20.000	40.000	3	2	T
2	Chave de fenda Philips Amarela	100.000	25.000	50.000	3	2	T
\.


--
-- Data for Name: recebimento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.recebimento (codigo, valor, dataemissao, datavencimento, quitada, venda, condicaopagamento, formapagamento, cliente, quitado) FROM stdin;
1	65.000	2023-10-21	2023-10-21	T	1	1	1	3	T
6	50.000	2023-10-21	2023-10-21	T	6	1	1	2	T
7	14.670	2023-10-21	2023-11-20	T	7	4	5	2	T
8	14.670	2023-10-21	2023-12-20	T	7	4	5	2	T
9	14.670	2023-10-21	2024-01-19	T	7	4	5	2	T
10	14.670	2023-10-21	2024-02-18	T	7	4	5	2	T
11	14.670	2023-10-21	2024-03-19	T	7	4	5	2	T
12	14.670	2023-10-21	2024-04-18	T	7	4	5	2	T
13	40.000	2023-10-21	2023-10-21	T	8	1	1	2	T
14	40.000	2023-10-21	2023-10-21	T	9	1	1	3	T
15	50.000	2023-10-21	2023-10-22	T	10	1	5	2	T
16	40.000	2023-10-22	2023-10-22	T	11	1	1	2	T
17	40.000	2023-10-22	2023-10-22	T	12	1	1	2	T
18	40.000	2023-10-23	2023-10-23	T	13	1	1	2	T
19	50.000	2023-10-27	2023-10-27	T	14	1	1	2	T
20	50.000	2023-10-27	2023-10-27	T	15	1	1	3	T
21	40.000	2023-10-27	2023-10-27	T	16	1	1	2	T
22	50.000	2023-10-27	2023-10-27	T	17	1	1	2	T
23	50.000	2023-10-27	2023-10-27	T	18	1	1	3	T
24	50.000	2023-10-27	2023-10-27	T	19	1	1	2	T
25	50.000	2023-10-27	2023-10-27	T	20	1	1	3	T
26	50.000	2023-10-27	2023-10-27	T	21	1	1	3	T
27	50.000	2023-10-27	2023-10-27	T	22	1	1	3	T
28	50.000	2023-10-27	2023-10-27	T	23	1	1	3	T
29	50.000	2023-10-27	2023-10-27	T	24	1	1	3	T
30	50.000	2023-10-27	2023-10-27	T	25	1	1	3	T
31	50.000	2023-10-27	2023-10-27	T	26	1	1	3	T
32	50.000	2023-10-27	2023-10-27	T	27	1	1	3	T
33	50.000	2023-10-27	2023-10-27	T	28	1	1	3	T
34	50.000	2023-10-27	2023-10-27	T	29	1	1	3	T
35	50.000	2023-10-27	2023-10-27	T	30	1	1	3	T
36	50.000	2023-10-27	2023-10-27	T	31	1	1	2	T
\.


--
-- Data for Name: venda; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.venda (codigo, datavenda, totalvenda, cliente, desconto, acrescimo, funcionario) FROM stdin;
1	2023-10-21	40.00	3	0.000	25.000	1
6	2023-10-21	50.00	2	0.000	0.000	3
7	2023-10-21	80.00	2	0.000	16.000	3
8	2023-10-21	40.00	2	0.000	0.000	3
9	2023-10-21	40.00	3	0.000	0.000	1
10	2023-10-21	50.00	2	0.000	0.000	3
11	2023-10-22	40.00	2	0.000	0.000	1
12	2023-10-22	40.00	2	0.000	0.000	1
13	2023-10-23	40.00	2	0.000	0.000	3
14	2023-10-27	50.00	2	0.000	0.000	3
15	2023-10-27	50.00	3	0.000	0.000	3
16	2023-10-27	40.00	2	0.000	0.000	3
17	2023-10-27	50.00	2	0.000	0.000	3
18	2023-10-27	50.00	3	0.000	0.000	3
19	2023-10-27	50.00	2	0.000	0.000	3
20	2023-10-27	50.00	3	0.000	0.000	3
21	2023-10-27	50.00	3	0.000	0.000	3
22	2023-10-27	50.00	3	0.000	0.000	3
23	2023-10-27	50.00	3	0.000	0.000	3
24	2023-10-27	50.00	3	0.000	0.000	3
25	2023-10-27	50.00	3	0.000	0.000	3
26	2023-10-27	50.00	3	0.000	0.000	3
27	2023-10-27	50.00	3	0.000	0.000	3
28	2023-10-27	50.00	3	0.000	0.000	3
29	2023-10-27	50.00	3	0.000	0.000	3
30	2023-10-03	50.00	3	0.000	0.000	3
31	2023-10-27	50.00	2	0.000	0.000	3
\.


--
-- Name: ajusteestoque_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ajusteestoque_codigo_seq', 6, true);


--
-- Name: caixa_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.caixa_codigo_seq', 31, true);


--
-- Name: cidade_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cidade_codigo_seq', 7, true);


--
-- Name: cliente_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cliente_codigo_seq', 3, true);


--
-- Name: condicaopagamento_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.condicaopagamento_codigo_seq', 4, true);


--
-- Name: estabelecimento_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.estabelecimento_codigo_seq', 1, false);


--
-- Name: estoque_transiente_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.estoque_transiente_codigo_seq', 37, true);


--
-- Name: formapagmento_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.formapagmento_codigo_seq', 6, true);


--
-- Name: fornecedor_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fornecedor_codigo_seq', 3, true);


--
-- Name: funcionario_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.funcionario_codigo_seq', 3, true);


--
-- Name: funcionariopermissao_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.funcionariopermissao_codigo_seq', 722, true);


--
-- Name: grupo_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.grupo_codigo_seq', 2, true);


--
-- Name: itemvenda_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.itemvenda_codigo_seq', 24, true);


--
-- Name: parcela_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.parcela_codigo_seq', 34, true);


--
-- Name: permissao_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.permissao_codigo_seq', 43, true);


--
-- Name: produto_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produto_codigo_seq', 2, true);


--
-- Name: recebimento_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recebimento_codigo_seq', 36, true);


--
-- Name: venda_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.venda_codigo_seq', 31, true);


--
-- Name: estabelecimento cnpj_estabelecimento_un; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estabelecimento
    ADD CONSTRAINT cnpj_estabelecimento_un UNIQUE (cnpj);


--
-- Name: ajusteestoque codigo_ajusteestoque_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ajusteestoque
    ADD CONSTRAINT codigo_ajusteestoque_pk PRIMARY KEY (codigo);


--
-- Name: caixa codigo_caixa_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caixa
    ADD CONSTRAINT codigo_caixa_pk PRIMARY KEY (codigo);


--
-- Name: cliente codigo_cliente_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT codigo_cliente_pk PRIMARY KEY (codigo);


--
-- Name: condicaopagamento codigo_condicaopagamento_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condicaopagamento
    ADD CONSTRAINT codigo_condicaopagamento_pk PRIMARY KEY (codigo);


--
-- Name: estabelecimento codigo_estabelecimento_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estabelecimento
    ADD CONSTRAINT codigo_estabelecimento_pk PRIMARY KEY (codigo);


--
-- Name: estoque_transiente codigo_estoque_transiente_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estoque_transiente
    ADD CONSTRAINT codigo_estoque_transiente_pk PRIMARY KEY (codigo);


--
-- Name: formapagamento codigo_formapagmento_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formapagamento
    ADD CONSTRAINT codigo_formapagmento_pk PRIMARY KEY (codigo);


--
-- Name: fornecedor codigo_fornecedor_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fornecedor
    ADD CONSTRAINT codigo_fornecedor_pk PRIMARY KEY (codigo);


--
-- Name: funcionario codigo_funcionario_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT codigo_funcionario_pk PRIMARY KEY (codigo);


--
-- Name: funcionariopermissao codigo_funcionariopermissao_fk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcionariopermissao
    ADD CONSTRAINT codigo_funcionariopermissao_fk PRIMARY KEY (codigo);


--
-- Name: grupo codigo_grupo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grupo
    ADD CONSTRAINT codigo_grupo_pk PRIMARY KEY (codigo);


--
-- Name: itemvenda codigo_itemvenda_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itemvenda
    ADD CONSTRAINT codigo_itemvenda_pk PRIMARY KEY (codigo);


--
-- Name: parcela codigo_parcela_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parcela
    ADD CONSTRAINT codigo_parcela_pk PRIMARY KEY (codigo);


--
-- Name: permissao codigo_permissao_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permissao
    ADD CONSTRAINT codigo_permissao_pk PRIMARY KEY (codigo);


--
-- Name: produto codigo_produto_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT codigo_produto_pk PRIMARY KEY (codigo);


--
-- Name: recebimento codigo_recebimento_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recebimento
    ADD CONSTRAINT codigo_recebimento_pk PRIMARY KEY (codigo);


--
-- Name: venda codigo_venda_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda
    ADD CONSTRAINT codigo_venda_pk PRIMARY KEY (codigo);


--
-- Name: cliente cpf_nome_cliente_un; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cpf_nome_cliente_un UNIQUE (cpf, nome);


--
-- Name: fornecedor cpf_nome_fornecedor_un; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fornecedor
    ADD CONSTRAINT cpf_nome_fornecedor_un UNIQUE (cpfcnpj, nome);


--
-- Name: funcionario cpf_nome_funcionario_un; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT cpf_nome_funcionario_un UNIQUE (cpf, nome);


--
-- Name: condicaopagamento descricao_condicaopagamento_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.condicaopagamento
    ADD CONSTRAINT descricao_condicaopagamento_pk UNIQUE (descricao);


--
-- Name: grupo descricao_grupo_un; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grupo
    ADD CONSTRAINT descricao_grupo_un UNIQUE (descricao);


--
-- Name: permissao descricao_permissao_un; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permissao
    ADD CONSTRAINT descricao_permissao_un UNIQUE (descricao);


--
-- Name: produto descricao_produto_un; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT descricao_produto_un UNIQUE (descricao);


--
-- Name: cidade id_cidade_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cidade
    ADD CONSTRAINT id_cidade_pk PRIMARY KEY (codigo);


--
-- Name: cidade nome_cidade_un; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cidade
    ADD CONSTRAINT nome_cidade_un UNIQUE (nome);


--
-- Name: caixa caixa_funcionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caixa
    ADD CONSTRAINT caixa_funcionario_fk FOREIGN KEY (funcionario) REFERENCES public.funcionario(codigo);


--
-- Name: caixa caixa_parcela_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.caixa
    ADD CONSTRAINT caixa_parcela_fk FOREIGN KEY (parcela) REFERENCES public.parcela(codigo);


--
-- Name: cliente cidade_cliente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cidade_cliente_fk FOREIGN KEY (cidade) REFERENCES public.cidade(codigo);


--
-- Name: estabelecimento cidade_estabelecimento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estabelecimento
    ADD CONSTRAINT cidade_estabelecimento_fk FOREIGN KEY (cidade) REFERENCES public.cidade(codigo);


--
-- Name: fornecedor cidade_fornecedor_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fornecedor
    ADD CONSTRAINT cidade_fornecedor_fk FOREIGN KEY (cidade) REFERENCES public.cidade(codigo);


--
-- Name: funcionario cidade_funcionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT cidade_funcionario_fk FOREIGN KEY (cidade) REFERENCES public.cidade(codigo);


--
-- Name: venda cliente_venda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda
    ADD CONSTRAINT cliente_venda_fk FOREIGN KEY (cliente) REFERENCES public.cliente(codigo);


--
-- Name: recebimento condicaopagamento_recebimento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recebimento
    ADD CONSTRAINT condicaopagamento_recebimento_fk FOREIGN KEY (condicaopagamento) REFERENCES public.condicaopagamento(codigo);


--
-- Name: recebimento formapagamento_recebimento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recebimento
    ADD CONSTRAINT formapagamento_recebimento_fk FOREIGN KEY (formapagamento) REFERENCES public.formapagamento(codigo);


--
-- Name: produto fornecedor_produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT fornecedor_produto_fk FOREIGN KEY (fornecedor) REFERENCES public.fornecedor(codigo);


--
-- Name: funcionariopermissao funcionario_funcionariopermissao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcionariopermissao
    ADD CONSTRAINT funcionario_funcionariopermissao_fk FOREIGN KEY (funcionario) REFERENCES public.funcionario(codigo);


--
-- Name: produto grupo_produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT grupo_produto_fk FOREIGN KEY (grupo) REFERENCES public.grupo(codigo);


--
-- Name: funcionariopermissao permissao_funcionariopermissao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.funcionariopermissao
    ADD CONSTRAINT permissao_funcionariopermissao_fk FOREIGN KEY (permissao) REFERENCES public.permissao(codigo);


--
-- Name: ajusteestoque produto_ajusteestoque_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ajusteestoque
    ADD CONSTRAINT produto_ajusteestoque_fk FOREIGN KEY (produto) REFERENCES public.produto(codigo);


--
-- Name: estoque_transiente produto_estoque_transiente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estoque_transiente
    ADD CONSTRAINT produto_estoque_transiente_fk FOREIGN KEY (produto) REFERENCES public.produto(codigo);


--
-- Name: itemvenda produto_itemvenda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itemvenda
    ADD CONSTRAINT produto_itemvenda_fk FOREIGN KEY (produto) REFERENCES public.produto(codigo);


--
-- Name: recebimento recebimento_cliente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recebimento
    ADD CONSTRAINT recebimento_cliente_fk FOREIGN KEY (cliente) REFERENCES public.cliente(codigo);


--
-- Name: parcela recebimento_parcela_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parcela
    ADD CONSTRAINT recebimento_parcela_fk FOREIGN KEY (recebimento) REFERENCES public.recebimento(codigo);


--
-- Name: venda venda_funcionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.venda
    ADD CONSTRAINT venda_funcionario_fk FOREIGN KEY (funcionario) REFERENCES public.funcionario(codigo);


--
-- Name: itemvenda venda_itemvenda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itemvenda
    ADD CONSTRAINT venda_itemvenda_fk FOREIGN KEY (venda) REFERENCES public.venda(codigo);


--
-- Name: recebimento venda_recebimento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recebimento
    ADD CONSTRAINT venda_recebimento_fk FOREIGN KEY (venda) REFERENCES public.venda(codigo);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

