-- create database
CREATE DATABASE authorization_token_server_database;

-- user configuration
CREATE USER authorization_token_server_user WITH PASSWORD 'authorization_token_server_user_password';
GRANT CONNECT ON DATABASE authorization_token_server_database TO authorization_token_server_user;

-- connect to created database
\connect authorization_token_server_database

-----------------------------------
---			TOKEN STORE			---
-----------------------------------
CREATE TABLE oauth_access_token (
	token_id character varying(256) NOT NULL,
	token bytea NOT NULL,
	authentication_id character varying(256) NOT NULL,
	user_name character varying(256) NOT NULL,
	client_id character varying(256) NOT NULL,
	authentication bytea NOT NULL,
	refresh_token character varying(256) NOT NULL
);

ALTER TABLE ONLY oauth_access_token
    ADD CONSTRAINT oauth_access_token_pkey PRIMARY KEY (token_id);

GRANT SELECT, INSERT, UPDATE, DELETE
	ON oauth_access_token TO authorization_token_server_user;

-----------------------------------
---		  REFRESH TOKENS		---
-----------------------------------
CREATE TABLE oauth_refresh_token (
	token_id character varying(256) NOT NULL,
	token bytea NOT NULL,
	authentication bytea NOT NULL
);

ALTER TABLE ONLY oauth_refresh_token
    ADD CONSTRAINT oauth_refresh_token_pkey PRIMARY KEY (token_id);

GRANT SELECT, INSERT, UPDATE, DELETE
ON oauth_refresh_token TO authorization_token_server_user;
