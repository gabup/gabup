package com.gabup.gabup.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .mvcMatchers(HttpMethod.GET,"/favicon.ico").permitAll()
                                .mvcMatchers(HttpMethod.POST,"/upload").permitAll()
                                .mvcMatchers(HttpMethod.GET,"/script/main").permitAll()
                                .mvcMatchers(HttpMethod.GET,"/script/scripts").permitAll()
                                .anyRequest().authenticated()
                ).logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                .and().formLogin().defaultSuccessUrl("/dashboard").and().cors().disable().csrf().disable();
        return http.build();
    }

    @Bean
    UserDetailsService users() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123456")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
