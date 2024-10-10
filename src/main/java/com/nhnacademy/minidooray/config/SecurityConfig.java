package com.nhnacademy.minidooray.config;

import com.nhnacademy.minidooray.service.CustomUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomUserDetailService userDetailService) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorizeRequests -> {
            authorizeRequests.anyRequest().permitAll();
//            authorizeRequests.requestMatchers("/admin/**").hasRole("ADMIN")
//                    .requestMatchers("/private-project/**").hasAnyAuthority("ROLE_MEMBER")
//                    .requestMatchers("/public-project/**").permitAll()
//                    .requestMatchers("/google-project/**").hasAnyAuthority("ROLE_GOOGLE")
//                    .requestMatchers("/auth/login").permitAll()
//                    .requestMatchers("/login/process").permitAll()
//                    .anyRequest().authenticated();
        });
//        http.exceptionHandling(handle -> handle.accessDeniedPage("/403"));

        // login
        http.formLogin(formLogin -> {
//            formLogin.disable()
            formLogin.loginPage("/login")
                    .usernameParameter("id")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login/process")
//                    .successHandler(new LoginSuccessHandler(redisTemplate))
//                    .failureHandler(new LoginFailureHandler())
            ;
        });
//        http.logout(logout -> {
//            logout.logoutSuccessHandler(new CustomLogoutSuccessHandler(redisTemplate));
//        });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
