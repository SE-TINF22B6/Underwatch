package com.underwatch.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.underwatch.backend.model.Score;
import com.underwatch.backend.repository.ScoreRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ScoreServiceTest {
    @Mock
    private ScoreRepository scoreRepository;
    @InjectMocks
    private ScoreService scoreService;

    @Test
    public void testGetUserById() {
        int scoreId = 5;
        Long date = getDateTime("8/19/2022");
        Score score = new Score(scoreId, "hans", 50, 200, 3, 100, 15, new Timestamp(date), 645456456456L);

        // Mock the Score
        Mockito.when(scoreService.getScoreById(scoreId)).thenReturn(Optional.of(score));

        // Result
        Score response = scoreService.getScoreById(scoreId).orElseThrow();

        // Asserts
        // Check the status code
        assertEquals(score, response);

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
