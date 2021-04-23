INSERT INTO `users` (id, email, is_moderator , name, password, reg_time)
VALUES
(1, "u1@mail.ru", 0, "Маша С", "11111", '2021-01-20 19:04:18'),
(2, "u2@mail.ru", 0, "Илья П", "11111", '2021-02-21 18:04:18'),
(3, "u3@mail.ru", 0, "Дима С", "11111", '2021-03-22 11:04:18'),
(4, "u4@mail.ru", 0, "Соня У", "11111", '2021-04-2 9:04:18'),
(5, "u5@mail.ru", 0, "Алина Ч", "11111", '2021-04-3 8:04:18');

INSERT INTO `posts` (id, is_active, moderation_status , text, time, title, view_count, user_id)
VALUES
(1, 1, 'ACCEPTED', "hello world from Masha", '2021-01-20 20:04:18', "hey hey", 1, 1),
(2, 1, 'ACCEPTED', "wats up", '2021-01-22 20:04:18', "bon jour", 1, 1),
(3, 1, 'ACCEPTED', "hello world from Илья", '2021-02-20 21:04:18', "guten morgen", 1, 2),
(4, 1, 'ACCEPTED', "hello world from Дима", '2021-03-22 22:04:18', "oi", 1, 3),
(5, 1, 'ACCEPTED', "hello world from Соня", '2021-04-2 20:04:18', "buenas dias", 1, 4),
(6, 1, 'ACCEPTED', "hello world from Алина", '2021-04-3 20:04:18', "ola", 1, 5),
(7, 1, 'ACCEPTED', "lets talk guys", '2021-04-4 20:04:18', "attention", 1, 5);

INSERT INTO `post_comments` (id, text, time, post_id, user_id)
VALUES
(1, "nice to see you here", '2021-02-20 20:04:18', 1, 2),
(2, "hi", '2021-02-23 21:04:18', 3, 1),
(3, "а где это?", '2021-04-10 21:04:18', 1, 5),
(4, "wats up?", '2021-05-10 21:04:18', 7, 4),
(5, "que paso?", '2021-04-10 21:04:18', 7, 3);

INSERT INTO `post_votes` (id, time, value, post_id, user_id)
VALUES
(1, '2021-02-20 20:04:19', 1,  1, 2),
(2, '2021-04-11 20:04:19', 1,  1, 5),
(3, '2021-04-6 20:04:19', 1,  7, 1),
(4, '2021-04-6 20:04:19', 1,  7, 2),
(5, '2021-04-6 20:04:19', 1,  7, 3);

INSERT INTO `tags` (id, name)
VALUES
(1, "lol"),
(2, "xoxo"),
(3, "ddd"),
(4, "sos");

INSERT INTO `tag2post` (post_id, tag_id)
VALUES
(1, 1),
(1, 2),
(2, 3),
(7, 4);

