package com.lxp.user.infrastructure.web.external.passport.config;

import com.lxp.user.infrastructure.web.external.passport.filter.PassportAuthenticationEntryPoint;
import com.lxp.user.infrastructure.web.external.passport.filter.PassportAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;

@Configuration
@RequiredArgsConstructor
public class PassportConfig {

    private final PassportAuthenticationEntryPoint passportAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, PassportAuthenticationFilter passportFilter) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .requestCache(RequestCacheConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .securityContext(c -> c.securityContextRepository(new NullSecurityContextRepository()))
            .logout(AbstractHttpConfigurer::disable)
            .exceptionHandling(configurer ->
                configurer.authenticationEntryPoint(passportAuthenticationEntryPoint)
            )
            .addFilterBefore(passportFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(user -> user
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/internal/api-v1/users").permitAll()
                .requestMatchers("/internal/api-v1/users/*/role").permitAll()
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated()
            )
            .build();
    }

}
