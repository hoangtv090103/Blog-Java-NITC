package com.example.arniepanblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/h2-console",
                                "/h2-console/*"
                        )
                        .permitAll()
//                        .requestMatchers(HttpMethod.GET, "/h2-console/*").permitAll()
                        .anyRequest().authenticated()
                )
                .logout((logout) -> logout.permitAll());

        // Setting login handling and validation
        http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login").usernameParameter("email").passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .and()
                .httpBasic();


        //TODO: When you move away from h2-console you can remove theses
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
        return http.build();
    }
}