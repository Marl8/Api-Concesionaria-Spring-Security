package com.example.pruebaJPA.security;

import com.example.pruebaJPA.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    AuthenticationProvider provider;

    JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(AuthenticationProvider provider, JwtAuthenticationFilter authenticationFilter) {
        this.provider = provider;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(provider)
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> {
            auth.requestMatchers("/error").permitAll();
            auth.requestMatchers("/api/login").permitAll();
            auth.requestMatchers("/api/user/save").hasRole("ADMIN");
            auth.requestMatchers("/api/user/update").hasRole("ADMIN");
            auth.requestMatchers("/api/user/delete").hasRole("ADMIN");
            auth.anyRequest().authenticated();
                }
            )
            .build();
    }
}
