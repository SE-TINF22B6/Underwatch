package com.underwatch.backend.controller;

import com.underwatch.backend.model.Score;
import com.underwatch.backend.repository.ScoreRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * ScoreController
 */
@RestController
@RequestMapping("/api/scores")
public class ScoreController {


    private final ScoreRepository scoreRepository;



    /**
     * @param scoreRepository
     */
    public ScoreController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }



    @GetMapping
    public List<Score> findAll() {
        return scoreRepository.findAll();
    }
}
