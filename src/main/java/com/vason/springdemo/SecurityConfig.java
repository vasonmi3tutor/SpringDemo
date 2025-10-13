package com.vason.springdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        //Allow public request in login page
                        .requestMatchers("/login").permitAll()
                        //Allow public request in signup page
                        .requestMatchers("/signup").permitAll()
                        // All other request require authentication
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        // Specify the custom login page URL
                        .loginPage("/login")
                        // Redirect to home once success
                        .defaultSuccessUrl("/",true)
                        // Allow access login page for all
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
