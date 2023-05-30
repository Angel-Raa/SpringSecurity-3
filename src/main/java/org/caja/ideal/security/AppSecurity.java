package org.caja.ideal.security;

import org.caja.ideal.config.UserDetailsServiceConfig;
import org.caja.ideal.jwt.JwtAuthenticationFilter;
import org.caja.ideal.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class AppSecurity {
    @Autowired
    private JwtService service;


    @Autowired
    private BCryptPasswordEncoder encoder;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager manager) throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(service);
        filter.setAuthenticationManager(manager);
        return http
                .cors().disable()
                .authorizeHttpRequests( (auth) -> {
                    auth.requestMatchers("/api/hello").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement((session) -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(filter)
                .build();
    }


}
