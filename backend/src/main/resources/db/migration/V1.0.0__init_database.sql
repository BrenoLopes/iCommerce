create table authority
(
    id   serial,
    name varchar(50),
    primary key (id)
);

create table users
(
    id       serial,
    name     varchar(255),
    username varchar(255),
    password varchar(255),
    enabled  boolean
);

create table user_authority
(
    user_id      bigint,
    authority_id bigint,
    primary key (user_id, authority_id)
);
