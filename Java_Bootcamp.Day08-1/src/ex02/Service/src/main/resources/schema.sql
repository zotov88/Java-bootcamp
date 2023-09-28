drop table if exists users;

create table if not exists users
(
    id       serial primary key,
    password varchar(80),
    email    varchar(30) not null unique
);