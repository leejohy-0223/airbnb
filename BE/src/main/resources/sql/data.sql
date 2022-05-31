insert into user (email, name, role)
VALUES ('test1@naver.com', 'test1', 'HOST'),
       ('test2@naver.com', 'test2', 'HOST'),
       ('test3@naver.com', 'test3', 'HOST');

insert into house (comment_count, max_number, rate, room_introduction, type, name, point, price, host_id)
VALUES (3, 4, 4.5, '아늑한 집', '원룸1', 'house1', ST_GeomFromText('POINT(37.495428 127.029371)'), 75000, 1),
       (3, 4, 4.5, '아늑한 집', '원룸2', 'house2', ST_GeomFromText('POINT(37.496428 127.029371)'), 75000, 1),
       (3, 4, 4.5, '아늑한 집', '원룸3', 'house3', ST_GeomFromText('POINT(37.497428 127.029371)'), 75000, 2),
       (3, 4, 4.5, '아늑한 집', '원룸4', 'house4', ST_GeomFromText('POINT(37.498428 127.029371)'), 75000, 2),
       (3, 4, 4.5, '아늑한 집', '원룸5', 'house5', ST_GeomFromText('POINT(37.499428 127.029371)'), 75000, 3);
