package com.underwatch.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

@org.springframework.boot.test.context.SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class SpringBootTests {
    @Test
    @DisplayName("Sanity check Spring-Boot if the Context loads")
    void contextLoads() {
    }
}
