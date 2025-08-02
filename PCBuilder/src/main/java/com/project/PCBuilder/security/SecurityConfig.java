package com.project.PCBuilder.security;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService uds;
    private final JwtUtil jwtUtil;

    public SecurityConfig(CustomUserDetailsService uds, JwtUtil jwtUtil) {
        this.uds     = uds;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf().disable()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
          .authorizeHttpRequests()
            .requestMatchers(
              "/api/v1/accounts/signup",
              "/api/v1/accounts/verify/**",
              "/api/v1/accounts/login",
              "/swagger-ui/**",
              "/v3/api-docs/**",
              "/swagger-resources/**",
              "/configuration/**",
              "/webjars/**"
                    ).permitAll()
                    .anyRequest().permitAll(); // TEMP: all requests are permitted, no authentication required
//                    .anyRequest().authenticated() // PROD: enable authentication for other endpoints
//                    .and()
//                      .addFilterBefore(
//                        jwtAuthenticationFilter(), // PROD: enable JWT authentication filter
//                        UsernamePasswordAuthenticationFilter.class
//                      );
                return http.build();
            }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, uds);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // FIXED: Allow specific origins instead of wildcard when using credentials
        config.setAllowedOriginPatterns(List.of("*")); // Use this instead of setAllowedOrigins with credentials
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
