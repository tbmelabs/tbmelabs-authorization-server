-- create database
CREATE DATABASE authorization_server_integration;

-- user configuration
CREATE USER authorization_server_integration_user WITH PASSWORD 'authorization_server_integration_user_password';
GRANT ALL PRIVILEGES ON DATABASE authorization_server_integration TO authorization_server_integration_user;
