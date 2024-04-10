package org.example.ticketing.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {
    private static final String API_NAME = "Concert";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "콘서트 예약 서비스";

    @Bean
    public OpenAPI OpenAPIConfig() {
        return new OpenAPI().info(new Info().title(API_NAME).description(API_DESCRIPTION).version(API_VERSION));
    }

}
