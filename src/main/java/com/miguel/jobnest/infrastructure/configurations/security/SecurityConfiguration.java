package com.miguel.jobnest.infrastructure.configurations.security;

import com.miguel.jobnest.infrastructure.configurations.security.authentication.JwtConverter;
import com.miguel.jobnest.infrastructure.configurations.security.authentication.handlers.CustomAccessDeniedHandler;
import com.miguel.jobnest.infrastructure.configurations.security.authentication.handlers.CustomAuthenticationEntryPointHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/api/v1/user-codes/**").permitAll()
                                .requestMatchers("/api/v1/users/verification/{code}").permitAll()
                                .requestMatchers("/api/v1/users/password-reset/{code}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/recruiter/job-vacancies").hasRole("RECRUITER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/recruiter/job-vacancies").hasRole("RECRUITER")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/recruiter/job-vacancies/{jobVacancyId}").hasRole("RECRUITER")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/recruiter/job-vacancies/{jobVacancyId}").hasRole("RECRUITER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/candidate/job-vacancies").hasRole("CANDIDATE")
                                .requestMatchers(HttpMethod.POST, "/api/v1/candidate/subscriptions").hasRole("CANDIDATE")
                                .requestMatchers(HttpMethod.GET, "/api/v1/candidate/subscriptions").hasRole("CANDIDATE")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/candidate/subscriptions/{subscriptionId}").hasRole("CANDIDATE")
                                .requestMatchers(HttpMethod.GET, "/api/v1/recruiter/subscriptions/{jobVacancyId}").hasRole("RECRUITER")
                                .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2ResourceServer -> {
                    oauth2ResourceServer.authenticationEntryPoint(new CustomAuthenticationEntryPointHandler());
                    oauth2ResourceServer.accessDeniedHandler(new CustomAccessDeniedHandler());
                    oauth2ResourceServer.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(new JwtConverter()));
                })
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "x-idempotency-key"));
        corsConfiguration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
