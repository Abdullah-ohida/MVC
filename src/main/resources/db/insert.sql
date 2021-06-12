SET session_replication_role = 'replica';

TRUNCATE TABLE author CASCADE;
TRUNCATE TABLE post CASCADE;
TRUNCATE TABLE comment CASCADE;

INSERT INTO post(id, title, content, date_published)
VALUES (41, 'Title post 1', 'Post Content 1', '2021-06-03 11:33:06.000000'),
       (42, 'Title post 2', 'Post Content 2', '2021-06-03 11:30:06.000000'),
       (43, 'Title post 3', 'Post Content 3', '2021-06-03 11:28:06.000000');


INSERT INTO author(id, user_name, email)
VALUES(20, 'whale', 'whale@gmail.com'),
       (21, 'kunle', 'kunle@gmail.com'),
       (22, 'peter', 'peter@gmail.com');

SET session_replication_role = 'origin';