create table if not exists users (
                                     user_id bigserial not null,
                                         username varchar not null,
                                         surname varchar not null,
                                         country varchar not null,
                                         date_of_birdth date not null,
                                         relationship_status varchar,
                                         deleted boolean,
                                         primary key (user_id)
    );
