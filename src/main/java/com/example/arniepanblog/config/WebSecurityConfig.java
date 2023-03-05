package com.example.arniepanblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private static final String[] ALLOWLIST = {
            "/",
            "/login",
            "/register",
            "/h2-console/*",
            "/css/**",
            "/post-images",
            "/post-images/**",
            "/reset-password",
    };

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(ALLOWLIST).permitAll();
                    requests.anyRequest().authenticated();
                })
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login-failure")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .httpBasic();
        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
        //TODO: When you move away from h2-console you can remove theses
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
    }
}