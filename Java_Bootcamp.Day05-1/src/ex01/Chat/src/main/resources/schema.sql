drop schema if exists chat cascade;
create schema if not exists chat;

create table if not exists chat.users
(
    id       serial primary key,
    login    varchar(20) not null unique,
    password varchar(20) not null
);

create table if not exists chat.chatrooms
(
    id    serial primary key,
    title varchar(20) not null unique,
    owner integer     not null,
    foreign key (owner) references chat.users (id)
);

create table if not exists chat.messages
(
    id     serial primary key,
    author integer not null,
    room   integer not null,
    text   text    not null,
    date   timestamp default CURRENT_TIMESTAMP,
    foreign key (author) references chat.users (id),
    foreign key (room) references chat.chatrooms (id)
);

create table if not exists chat.users_chatrooms
(
    user_id     integer not null,
    chatroom_id integer not null,
    foreign key (user_id) references chat.users (id),
    foreign key (chatroom_id) references chat.chatrooms (id)
);