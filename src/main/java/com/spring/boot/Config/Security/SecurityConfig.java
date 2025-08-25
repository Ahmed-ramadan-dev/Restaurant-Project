package com.spring.boot.Config.Security;

import com.spring.boot.Filters.AuthFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {
    @Autowired
    private AuthFilters authFilters;
/*
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        return userDetailsManager;
    }

دا القديم
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
      httpSecurity.authorizeRequests().requestMatchers(HttpMethod.GET,"/category/getAll-category-by-Name-asc").hasRole("ADMIN");
        httpSecurity.httpBasic(Customizer.withDefaults());
    return httpSecurity.build();
    }*/
    public static String[] PUBLIC_URLS = {
        "/auth/**",
        "/swagger-ui/**",
        "/v3/**"
};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws  Exception {
        httpSecurity.sessionManagement(httpSession->httpSession.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //لو انا عايز اوقف csrf عشان اعرض اضرب api post put delete في postman
        httpSecurity.csrf(httpSecurityCsrfConfigurer ->httpSecurityCsrfConfigurer.disable());
        //طريقه  lambda
        httpSecurity.authorizeRequests(api->
                api.requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated());
      //هضيف الفلتر الجديد قبل
        httpSecurity.addFilterBefore(authFilters, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    @Bean

    public UserDetailsService userDetailsService() {
        UserDetails User1 = User.withUsername("user").password("{noop}password").roles("USER").build();
        return new InMemoryUserDetailsManager(User1);
}*/
}