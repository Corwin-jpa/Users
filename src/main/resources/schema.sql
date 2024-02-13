CREATE TABLE users (
user_id serial PRIMARY KEY,
username VARCHAR(50) not null,
surname VARCHAR(50) not null,
country VARCHAR(50) not null,
date_of_birdth date not null,
relationship_status VARCHAR(50),
deleted boolean not null default false
);
CREATE TABLE subscriptions (
publisher_id serial NOT NULL,
subscriber_id serial NOT NULL,
primary key(publisher_id,subscriber_id),
FOREIGN KEY (publisher_id) references users(user_id) on delete cascade,
FOREIGN KEY (subscriber_id) references users(user_id),
CONSTRAINT unique_subscription_users CHECK (publisher_id != subscriber_id)
);