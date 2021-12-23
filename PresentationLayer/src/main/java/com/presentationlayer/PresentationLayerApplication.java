package com.presentationlayer;

import com.dataaccesslayer.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class PresentationLayerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PresentationLayerApplication.class, args);

        Database.Create("localhost", "5432", "problem_reporter",
                "postgres", "postgres").Connect();
    }

    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/user/register").permitAll()
                    .anyRequest().authenticated();
        }
    }
}
