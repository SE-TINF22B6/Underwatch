package com.underwatch.backend;

import com.underwatch.backend.model.Score;
import com.underwatch.backend.repository.ScoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.sql.Timestamp;
import java.util.Date;

import static org.springframework.test.util.AssertionErrors.assertEquals;

//@RunWith(SpringRunner.class)
@DataJpaTest
public class ScoreFilterTest {

    @Autowired
    private ScoreRepository repository;


    @BeforeTestClass
    public void beforeTest() {
        repository.save(new Score("Player",
                5, 5, 5, 5, 5,
                new Timestamp(new Date().getTime()), 5L));
    }

    @Test
    @DisplayName("Filtering works with a timestamp")
    public void filterWithTimestampTest() {
        Date date = new Date();

        repository.save(new Score("Player",
                5, 5, 5, 5, 5,
                new Timestamp(date.getTime()), 5L));

        Page<Score> result2 = repository.filterQuery(0, null,
                new Timestamp(date.getTime()),
                new Timestamp(date.getTime()),
                null);

        result2.get().forEach(score -> System.out.println(score.getPlayerName()));

        assertEquals("Total Elements in Query", result2.getTotalElements(), 1L);
    }


}
