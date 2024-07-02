-- This script create the user and database according to the k8s ConfigMap. 
-- This setup cannot be done bay any way unless script running
CREATE ROLE my_burger NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'password';
GRANT ALL ON SCHEMA public TO my_burger;


create database my_burger with owner my_burger;
GRANT ALL ON DATABASE my_burger TO my_burger;
