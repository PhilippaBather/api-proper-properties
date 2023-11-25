package com.philippabather.properpropertiesapi;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig - la clase que define las configuraciones de la aplicación.
 *
 * @author Philippa Bather
 */

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper ModelMapper() {
        return new ModelMapper();
    }
}
