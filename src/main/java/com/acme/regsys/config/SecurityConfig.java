package com.acme.regsys.config;

import com.acme.regsys.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      // API style
      .csrf(csrf -> csrf.disable())
      .cors(Customizer.withDefaults())
      .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

      // Authorize requests
      .authorizeHttpRequests(auth -> auth
        // preflight
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

        // Swagger/OpenAPI (permit all)
        .requestMatchers(
          "/v3/api-docs",            // exact JSON
          "/v3/api-docs/**",         // subpaths incl. /swagger-config
          "/swagger-ui.html",
          "/swagger-ui/**"
        ).permitAll()

        // H2 console (dev only)
        .requestMatchers("/h2/**").permitAll()

        // Public app endpoints
        .requestMatchers("/api/health", "/api/auth/**").permitAll()

        // Everything else requires auth
        .anyRequest().authenticated()
      )

      // JWT filter
      .addFilterBefore(new JwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)

      // 401 instead of redirect
      .exceptionHandling(e ->
        e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
      )

      // no form login; keep basic for quick curl tests
      .formLogin(form -> form.disable())
      .httpBasic(Customizer.withDefaults())

      // allow H2 console frames (dev)
      .headers(h -> h.frameOptions(f -> f.disable()));

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
