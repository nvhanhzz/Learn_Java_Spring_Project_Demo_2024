package com.example.demo.configuration;

import com.example.demo.enumPackage.Role;
import com.example.demo.exception.CustomAccessDeniedHandler;
import com.example.demo.security.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(GET, "/v3/api-docs",
                                    "/v3/api-docs/**",
                                    "/swagger-resources",
                                    "/swagger-resources/**",
                                    "/configuration/ui",
                                    "/configuration/security",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/webjars/swagger-ui/**",
                                    "/swagger-ui/index.html",
                                    "/webjars/**",
                                    "/swagger-ui/api-docs/swagger-config"
                            ).permitAll()
                            .requestMatchers("/auth/**").permitAll()
                            .requestMatchers(POST, "/user").hasAnyRole(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER))
                            .requestMatchers(GET, "/user/{userId}").hasAnyRole(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER))
                            .requestMatchers(GET, "/user").hasAnyRole(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER))
                            .requestMatchers(PATCH, "/user/{userId}").hasAnyRole(String.valueOf(Role.MANAGER), String.valueOf(Role.ADMIN))
                            .requestMatchers(PATCH, "/user/{userId}/status/{status}").hasAnyRole(String.valueOf(Role.MANAGER), String.valueOf(Role.ADMIN))
                            .requestMatchers(POST, "/product").hasAnyRole(String.valueOf(Role.ADMIN), String.valueOf(Role.MANAGER))
                            .requestMatchers(PATCH, "/product/{productId}").hasAnyRole(String.valueOf(Role.MANAGER), String.valueOf(Role.ADMIN))
                            .requestMatchers(DELETE, "/product/{productId}").hasAnyRole(String.valueOf(Role.MANAGER), String.valueOf(Role.ADMIN))
                            .anyRequest().authenticated();
                })
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .csrf(AbstractHttpConfigurer::disable);

        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173"));
                corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                corsConfiguration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                corsConfiguration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", corsConfiguration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });

        return http.build();
    }
}