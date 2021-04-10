package com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * @program: SpringDemo
 * @description:
 * @author: 阙霞
 * @create: 2021-04-05 09:17
 */
@Configuration
@ComponentScan("com")
public class AppConfig {
    @Bean
    public Random r() {
        return new Random();
    }

}
