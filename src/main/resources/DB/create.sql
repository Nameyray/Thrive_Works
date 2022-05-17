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
starttime TIMESTAMP,
createdat TIMESTAMP,
endtime TIMESTAMP,
patient INT,
therapist INT,
link VARCHAR
);

CREATE TABLE reviews(
  id serial PRIMARY KEY,
  appointmentid INT,
  feedback VARCHAR
);

CREATE TABLE payments(
id serial PRIMARY KEY,
appointmentid INT,
amount DOUBLE PRECISION
);

CREATE DATABASE thrive_test WITH TEMPLATE thrive;