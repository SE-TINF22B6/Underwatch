package com.underwatch.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfiguration {
    // Configuration specific to the development environment
}
