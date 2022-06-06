insert into user (user_id, email, name, role)
VALUES (1, 'test1@naver.com', 'test1', 'HOST'),
       (2, 'test2@naver.com', 'test2', 'HOST'),
       (3, 'test3@naver.com', 'test3', 'HOST'),
       (4, 'zbqmgldjfh@naver.com', 'shine', 'GUEST');

insert into house (comment_count, max_number, rate, room_introduction, type, name, point, price, host_id)
VALUES (3, 4, 4.5, '아늑한 집', '원룸1', 'house1', ST_GeomFromText('POINT(127.029371 37.495421)'), 75000, 1),
       (3, 4, 4.5, '아늑한 집', '원룸2', 'house2', ST_GeomFromText('POINT(127.029371 37.495422)'), 75000, 1),
       (3, 4, 4.5, '아늑한 집', '원룸3', 'house3', ST_GeomFromText('POINT(127.029371 37.495423)'), 75000, 2),
       (3, 4, 4.5, '아늑한 집', '원룸4', 'house4', ST_GeomFromText('POINT(127.029371 37.495424)'), 75000, 2),
       (3, 4, 4.5, '아늑한 집', '원룸5', 'house5', ST_GeomFromText('POINT(127.029371 37.495425)'), 75000, 3);

insert into wish (house_id, user_id)
VALUES (2, 4),
       (3, 4);
