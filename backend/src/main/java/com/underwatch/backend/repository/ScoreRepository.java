package com.underwatch.backend.repository;

import com.underwatch.backend.model.Score;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends PagingAndSortingRepository<Score, Integer>, ListCrudRepository<Score, Integer> {

}
