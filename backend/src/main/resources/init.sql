CREATE TABLE scores (       --create a table for datastorage
    id SERIAL PRIMARY KEY,
    playername VARCHAR(255) NOT NULL,
    kills INT NOT NULL,
    damagedealt INT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    score INT NOT NULL,
    coins INT NOT NULL,
    dps FLOAT NOT NULL,
    game_time INT NOT NULL
);


INSERT INTO scores (id, playername, score, coins, kills, damagedealt, dps, timestamp, game_time)
VALUES
    (1, 'ndeangelo0', 529, 9384, 49, 5980, 932, '2022-08-18T22:00:00.000+00:00', 2981),
    (2, 'lclaeskens1', 989, 6449, 96, 3949, 659, '2022-03-05T23:00:00.000+00:00', 2121),
    (3, 'owilkisson2', 839, 3371, 60, 8608, 80, '2022-01-11T23:00:00.000+00:00', 2116),
    (4, 'bcastanyer3', 546, 8889, 63, 115, 870, '2022-07-24T22:00:00.000+00:00', 581),
    (5, 'bhughson4', 840, 108, 35, 3672, 33, '2022-03-26T23:00:00.000+00:00', 2416),
    (6, 'efernao5', 823, 1743, 25, 8491, 492, '2022-07-12T22:00:00.000+00:00', 1433),
    (7, 'lollivierre6', 157, 9680, 61, 5601, 242, '2022-05-11T22:00:00.000+00:00', 3162),
    (8, 'lplayhill7', 999, 5876, 51, 9087, 694, '2022-06-26T22:00:00.000+00:00', 1084),
    (9, 'bciciura8', 324, 4965, 97, 1983, 564, '2022-05-29T22:00:00.000+00:00', 279),
    (10, 'ckippling9', 895, 2333, 8, 7853, 345, '2022-02-28T23:00:00.000+00:00', 1502);