package com.assistencia.tecnica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configuração principal de segurança
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // API REST não usa CSRF
                .csrf(csrf -> csrf.disable())

                // Regras de autorização
                .authorizeHttpRequests(auth -> auth
                        // Endpoint público (teste da API)
                        .requestMatchers("/").permitAll()

                        // Tudo que for /api precisa estar autenticado
                        .requestMatchers("/api/**").authenticated()

                        // Qualquer outra rota exige autenticação
                        .anyRequest().authenticated()
                )

                // Autenticação simples (Basic Auth)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Usuário padrão do sistema (admin/admin)
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles("ADMIN")
                        .build()
        );
    }

    /**
     * Encoder de senha (BCrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
