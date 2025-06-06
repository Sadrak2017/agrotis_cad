package org.agrotis.cad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(
        auth -> auth
          .requestMatchers("/cad/swagger-ui/index.html").permitAll()
          .requestMatchers("/cad/api-docs/**").permitAll()
          .anyRequest().permitAll())
        .csrf().disable()
      .httpBasic(Customizer.withDefaults());
    return http.build();
  }
}
