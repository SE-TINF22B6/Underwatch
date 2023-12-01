package com.underwatch.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.underwatch.backend.model.Score;
import com.underwatch.backend.service.ScoreService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ScoreControllerTest {

    @Mock
    private ScoreService scoreService;

    @InjectMocks
    private ScoreController scoreController;

    @Test
    public void testGetUserById() {

        int scoreId = 5;
        Long date = getDateTime("8/19/2022");
        Score score = new Score(scoreId, "hans", 50, 200, 3, 100, 15, new Timestamp(date), 645456456456L);

        // Mock the Score
        Mockito.when(scoreService.getScoreById(scoreId)).thenReturn(Optional.of(score));

        // Result
        ResponseEntity<Score> response = scoreController.getScoreById(scoreId);

        // Asserts
        // Check the status code
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(score, response.getBody());

    }

    // Helper functions
    private static Long getDateTime(String dateString) {
        try {
            Long result = new SimpleDateFormat("M/d/yyyy").parse(dateString).getTime();
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
