package com.bandtec.hyperxpress.hyperxpressproject.configuration;

import com.bandtec.hyperxpress.hyperxpressproject.configuration.annotations.Development;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@Development
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner mensagemAvisoAmbienteDesenvolvimento(){
        return args -> {
            System.out.println("Hyperxpress-Project PORT 8080 DEVELOPMENT");
        };
    }
}
