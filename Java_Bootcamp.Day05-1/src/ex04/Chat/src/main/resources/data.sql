insert into chat.users(login, password)
values ('Semen', 'password'),
       ('Anna', 'password'),
       ('Anton', 'password'),
       ('Ivan', 'password'),
       ('Elena', 'password'),
       ('Alexey', 'password'),
       ('Kris', 'password'),
       ('Viktor', 'password'),
       ('Olga', 'password');

insert into chat.chatrooms(title, owner_id)
values ('room_1', 1),
       ('room_2', 1),
       ('room_3', 3),
       ('room_4', 4),
       ('room_5', 5);

insert into chat.messages (author_id, room_id, text)
values (1, 1, 'text_1'),
       (1, 2, 'text_2'),
       (1, 3, 'text_9'),
       (1, 5, 'text_7'),
       (2, 2, 'text_3'),
       (2, 5, 'text_8'),
       (3, 3, 'text_4'),
       (4, 1, 'text_10'),
       (4, 4, 'text_5'),
       (5, 5, 'text_6'),
       (6, 2, 'text_11'),
       (6, 4, 'text_12'),
       (7, 3, 'text_13'),
       (8, 1, 'text_14'),
       (8, 5, 'text_15'),
       (9, 4, 'text_16'),
       (9, 1, 'text_17'),
       (9, 3, 'text_18');

insert into chat.users_chatrooms (user_id, chatroom_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 5),
       (2, 2),
       (2, 5),
       (3, 3),
       (4, 1),
       (4, 4),
       (5, 5),
       (6, 2),
       (6, 4),
       (7, 3),
       (8, 1),
       (8, 5),
       (9, 4),
       (9, 1),
       (9, 3);