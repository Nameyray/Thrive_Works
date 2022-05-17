CREATE DATABASE thrive;

\c thrive;

CREATE TABLE users(
id serial PRIMARY KEY,
name VARCHAR,
phone INT,
address VARCHAR,
email VARCHAR,
password VARCHAR,
specialization VARCHAR,
ratings INT,
is_vetted INT

);

CREATE TABLE appointments(
id serial PRIMARY KEY,
starttime DATE,
createdat TIMESTAMP,
endtime DATE,
patient INT,

);
