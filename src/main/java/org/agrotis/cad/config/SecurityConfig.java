package org.agrotis.cad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
          .requestMatchers("/release-notes.html").permitAll()
          .requestMatchers("/cad/swagger-ui/index.html").permitAll()
          .requestMatchers("/cad/v1/actuator/**").permitAll()
          .requestMatchers("/cad/v1/recursos/**").permitAll()
          .requestMatchers("/cad/v3/**").permitAll()
          .requestMatchers("/cad/v3/swagger-ui/**").permitAll()
          .requestMatchers("/cad/api-docs/**").permitAll()
          .anyRequest().permitAll())
      .httpBasic(Customizer.withDefaults());
    return http.build();
  }
}
