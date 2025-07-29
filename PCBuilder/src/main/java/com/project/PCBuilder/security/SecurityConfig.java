package com.project.PCBuilder.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {
private final CustomUserDetailsService uds;

public SecurityConfig(CustomUserDetailsService uds) {
 this.uds = uds;
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
   http
     .csrf().disable()             // for API testing; re-enable later
     .authorizeHttpRequests()
       .requestMatchers("/api/v1/accounts/signup", "/api/v1/accounts/verify/**").permitAll()
       .anyRequest().permitAll() //authenticated()
     .and()
     .sessionManagement()
       .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
     .and()
     .formLogin().disable()        // we’ll expose our own login endpoint
     .httpBasic().disable();
   return http.build();
}

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}


}
