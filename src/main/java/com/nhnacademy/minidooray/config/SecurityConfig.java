package com.nhnacademy.minidooray.config;

import com.nhnacademy.minidooray.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomUserDetailService userDetailService) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorizeRequests -> {
            authorizeRequests
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/register").permitAll()
                    .anyRequest().authenticated();
        });

        // login
        http.formLogin(formLogin -> {
            formLogin.loginPage("/login")
                    .usernameParameter("id")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login/process")
            ;
        });

        http.logout(logout -> {
            logout.logoutUrl("/logout");
        });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
