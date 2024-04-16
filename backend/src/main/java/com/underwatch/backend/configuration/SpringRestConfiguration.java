package com.underwatch.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class SpringRestConfiguration implements RepositoryRestConfigurer {

    /**
     * We want to use the benefits spring-boot-starter-rest brings, however, we don't want to expose
     * DELETE, PUT or PATCH to the end user (especially since we don't have authentication yet).
     * <br>
     * Therefore, we just disable those methods in the RestConfiguration.
     *
     * @param config injected by SpringBoot
     * @param cors   injected by SpringBoot
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        ExposureConfiguration c = config.getExposureConfiguration();
        c.withItemExposure((metadata, httpMethods) ->
                httpMethods.disable(HttpMethod.PATCH)
                        .disable(HttpMethod.PUT)
                        .disable(HttpMethod.DELETE));
    }
}