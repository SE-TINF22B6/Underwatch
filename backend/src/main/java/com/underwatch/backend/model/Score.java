package com.underwatch.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String playerName;
    private int score;
    private int coins;
    private int kills;
    private int damageDealt;
    private int dps;
    private Timestamp timestamp;
    private Long game_time; // Assuming game_time is stored as milliseconds (you can adjust this based on your requirements)

    public Score() {
    }

    public Score(String playerName, int score, int coins, int kills, int damageDealt, int dps, Timestamp timestamp, Long game_time) {
        this.playerName = playerName;
        this.score = score;
        this.coins = coins;
        this.kills = kills;
        this.damageDealt = damageDealt;
        this.dps = dps;
        this.timestamp = timestamp;
        this.game_time = game_time;
    }

    public Score(int id, String playerName, int score, int coins, int kills, int damageDealt, int dps, Timestamp timestamp, Long game_time) {
        this.id = id;
        this.playerName = playerName;
        this.score = score;
        this.coins = coins;
        this.kills = kills;
        this.damageDealt = damageDealt;
        this.dps = dps;
        this.timestamp = timestamp;
        this.game_time = game_time;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the playername
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @param playername the playername to set
     */
    public void setPlayerName(String playername) {
        this.playerName = playername;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the coins
     */
    public int getCoins() {
        return coins;
    }

    /**
     * @param coins the coins to set
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * @return the kills
     */
    public int getKills() {
        return kills;
    }

    /**
     * @param kills the kills to set
     */
    public void setKills(int kills) {
        this.kills = kills;
    }

    /**
     * @return the damagedealt
     */
    public int getDamageDealt() {
        return damageDealt;
    }

    /**
     * @param damagedealt the damagedealt to set
     */
    public void setDamageDealt(int damagedealt) {
        this.damageDealt = damagedealt;
    }

    /**
     * @return the dps
     */
    public int getDps() {
        return dps;
    }

    /**
     * @param dps the dps to set
     */
    public void setDps(int dps) {
        this.dps = dps;
    }

    /**
     * @return the timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the game_time
     */
    public Long getGame_time() {
        return game_time;
    }

    /**
     * @param game_time the game_time to set
     */
    public void setGame_time(Long game_time) {
        this.game_time = game_time;
    }
}
