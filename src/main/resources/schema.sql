CREATE TABLE users (
user_id serial PRIMARY KEY,
username VARCHAR(50) not null,
surname VARCHAR(50) not null,
country VARCHAR(50) not null,
date_of_birdth date not null,
relationship_status VARCHAR(50),
deleted boolean not null default false
);