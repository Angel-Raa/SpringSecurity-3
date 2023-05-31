package org.caja.ideal.security;

import org.caja.ideal.jwt.JwtAuthenticationFilter;
import org.caja.ideal.jwt.JwtAuthorizationFilter;
import org.caja.ideal.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity // Metodo alternativo a EnableGlobalMethodSecurity
@Configuration
public class AppSecurity {
    @Autowired
    private JwtService service;
    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager manager) throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(service);
        filter.setAuthenticationManager(manager);
        return http
                .cors().disable()
                .authorizeHttpRequests( (auth) -> {
                    auth.requestMatchers("/api/add").permitAll();
                    auth.requestMatchers("/api/hello").permitAll();
                 //   auth.requestMatchers("/test/admin").hasRole("ADMIN"); // Rol de Admin
                    auth.anyRequest().authenticated();
                })
                .sessionManagement((session) -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(filter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
