CREATE TABLE scores (
    id SERIAL PRIMARY KEY,
    playername VARCHAR(255) NOT NULL,
    kills INT NOT NULL,
    damagedealt INT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    score INT NOT NULL,
    damage_dealt INT NOT NULL,
    coins INT NOT NULL,
    dps FLOAT NOT NULL
    game_time INTERVAL NOT NULL,
);