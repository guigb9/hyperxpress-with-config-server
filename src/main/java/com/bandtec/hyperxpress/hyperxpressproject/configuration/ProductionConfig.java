package com.bandtec.hyperxpress.hyperxpressproject.configuration;


import com.bandtec.hyperxpress.hyperxpressproject.configuration.annotations.Production;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@Production
public class ProductionConfig {

    @Bean
    public CommandLineRunner mensagemAvisoAmbienteProducao(){
        return args -> {
            System.out.println("Hyperxpress-Project PORT 80 PRODUCTION");
        };
    }
}
