package com.underwatch.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfiguration {
    // Configuration specific to the production environment
}
