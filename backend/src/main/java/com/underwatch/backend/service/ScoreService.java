package com.underwatch.backend.service;

import com.underwatch.backend.model.Score;
import com.underwatch.backend.repository.ScoreRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        scoreRepository.saveAll(scores);
    }

    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    public void saveScore(Score score) {
        scoreRepository.save(score);
    }

    public Optional<Score> getScoreById(int id) {
        return scoreRepository.findById(id);
    }

}
