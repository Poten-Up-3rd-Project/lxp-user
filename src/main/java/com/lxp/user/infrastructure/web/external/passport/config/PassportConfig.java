package com.lxp.user.infrastructure.web.external.passport.config;

import com.lxp.user.infrastructure.web.external.passport.filter.PassportAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class PassportConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, PassportAuthenticationFilter passportFilter) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(passportFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(user -> user
                .requestMatchers(HttpMethod.POST, "/internal/api-v1/users").permitAll()
                .requestMatchers("/internal/api-v1/users/*/role").permitAll()
                .anyRequest().authenticated()
            )
            .build();
    }

}
