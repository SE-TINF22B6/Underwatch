package com.underwatch.backend.controller;

import com.underwatch.backend.model.Score;
import com.underwatch.backend.service.ScoreService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ScoreController
 */
@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    private final ScoreService scoreService;

    /**
     * @param scoreRepository
     */
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    public List<Score> findAll() {
        return scoreService.getAllScores();
    }

    @GetMapping("/{scoreId}")
    public ResponseEntity<Score> getScoreById(@PathVariable int scoreId) {
        // wrap the score in a response entity
        return ResponseEntity.of(scoreService.getScoreById(scoreId));

    }
}
