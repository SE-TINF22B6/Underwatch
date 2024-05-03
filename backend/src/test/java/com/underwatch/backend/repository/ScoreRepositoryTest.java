package com.underwatch.backend.repository;

import com.underwatch.backend.model.Score;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ScoreRepositoryTest {

    @Autowired
    private ScoreRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(new Score("Schlongus Longus", 999, 10, 5, 5523, 24, new Timestamp(new Date().getTime()), 100L));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Name only matches with Full Name")
    void filterQuery() {
        assertEquals(1, repository.filterQuery(0, "s", null, null, null).getTotalElements());
        assertEquals(1, repository.filterQuery(0, "S", null, null, null).getTotalElements());
        assertEquals(1, repository.filterQuery(0, "Schlongus Longus", null, null, null).getTotalElements());
    }

    @Test
    @DisplayName("Matching for Date Range")
    void filterQueryRange() {
        Date date = new Date();
        Page<Score> result = repository.filterQuery(0, null,
                new Timestamp(date.getTime() - 1000 * 60 * 60 * 24),
                new Timestamp(date.getTime() + 1000 * 60 * 60 * 24),
                null);
        assertEquals(1, result.getTotalElements());
    }
}