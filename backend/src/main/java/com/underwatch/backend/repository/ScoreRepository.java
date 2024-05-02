package com.underwatch.backend.repository;

import com.underwatch.backend.model.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface ScoreRepository extends PagingAndSortingRepository<Score, Integer>, ListCrudRepository<Score, Integer> {
    @Query("SELECT c FROM Score c " +
            "WHERE (:score is null or c.score >= :score) " +
            "and (:playerName is null or c.playerName like :playerName)" +
            "and (cast(:timeStampA as timestamp) is null or c.timestamp >= :timeStampA)" +
            "and (cast(:timeStampB as timestamp) is null or c.timestamp <= :timeStampB)")
    Page<Score> filterQuery(@Param("score") int score, @Param("playerName") String playerName, @Param("timeStampA") Timestamp timestampA, @Param("timeStampB") Timestamp timestampB, Pageable pageable);
}
