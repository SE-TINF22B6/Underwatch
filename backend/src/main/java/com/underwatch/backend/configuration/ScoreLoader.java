package com.underwatch.backend.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.underwatch.backend.model.Score;
import com.underwatch.backend.service.ScoreService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ScoreLoader
 */
@Component
public class ScoreLoader implements CommandLineRunner {

    private final ScoreService scoreService;

    /**
     * @param scoreService
     */
    public ScoreLoader(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Override
    public void run(String... args) throws Exception {
        loadScores();
    }

    private void loadScores() {
        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("Mock_Data.csv");
            if (resourceAsStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));

                // Skip the header line
                reader.readLine();

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");

                    // int id = Integer.parseInt(data[0].trim());
                    String playername = data[1].trim();
                    int score = Integer.parseInt(data[2].trim());
                    int coins = Integer.parseInt(data[3].trim());
                    int kills = Integer.parseInt(data[4].trim());
                    int damagedealt = Integer.parseInt(data[5].trim());
                    int dps = Integer.parseInt(data[6].trim());
                    // Parse the date string from CSV
                    String dateString = data[7].trim();
                    Timestamp timestamp = parseTimestamp(dateString);

                    Long gameTime = Long.parseLong(data[8].trim());

                    // Create a new Score object and save it to the database
                    Score scoreObject = new Score(playername, score, coins, kills, damagedealt, dps, timestamp,
                            gameTime);
                    scoreService.saveScore(scoreObject);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private Timestamp parseTimestamp(String dateString) {
        try {
            // Parse the date string in the CSV format
            SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
            Date parsedDate = dateFormat.parse(dateString);

            // Convert the parsed date to a Timestamp
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error parsing timestamp from CSV", e);
        }
    }
}
