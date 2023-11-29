package com.underwatch.backend.service;

import com.underwatch.backend.model.Score;
import com.underwatch.backend.repository.ScoreRepository;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ScoreService
 */
@Service
public class ScoreService {

    final private ScoreRepository scoreRepository;

    /**
     * @param scoreRepository
     */
    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public void saveAllScores(List<Score> scores) {
        for (Score score : scores) {
            scoreRepository.save(score);
        }
    }

    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    public void saveScore(Score score) {
        scoreRepository.save(score);
    }

}
