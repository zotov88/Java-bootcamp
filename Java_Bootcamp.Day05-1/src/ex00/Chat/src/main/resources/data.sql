insert into chat.users(login, password)
values ('Semen', 'password'),
       ('Anna', 'password'),
       ('Anton', 'password'),
       ('Ivan', 'password'),
       ('Elena', 'password');

insert into chat.chatrooms(title, owner)
values ('room_1', 1),
       ('room_2', 2),
       ('room_3', 3),
       ('room_4', 4),
       ('room_5', 5);

insert into chat.messages (author, room, text)
values (1, 1, 'text_1'),
       (2, 2, 'text_2'),
       (3, 3, 'text_3'),
       (4, 4, 'text_4'),
       (5, 5, 'text_5'),
       (1, 5, 'text_6'),
       (2, 5, 'text_7');

insert into chat.users_chatrooms (user_id, chatroom_id)
values (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (1, 5),
       (2, 5);

