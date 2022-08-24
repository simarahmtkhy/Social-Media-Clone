package com.socialmediaclone.socialmediaclone.configurations;

import com.socialmediaclone.socialmediaclone.security.UserDetailsServicePrincipal;
import com.socialmediaclone.socialmediaclone.security.filters.CustomAuthenticationFilter;
import com.socialmediaclone.socialmediaclone.security.filters.CustomAuthorizationFilter;
import com.socialmediaclone.socialmediaclone.security.filters.RefreshTokenFilter;
import com.socialmediaclone.socialmediaclone.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final UserDetailsServicePrincipal userDetailsServicePrincipal;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    public SecurityConfig(UserDetailsServicePrincipal userDetailsServicePrincipal, PasswordEncoder passwordEncoder, UserService userService) {
        this.userDetailsServicePrincipal = userDetailsServicePrincipal;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new CustomAuthenticationFilter(authenticationProvider(), userService))
                .addFilterBefore(new CustomAuthorizationFilter(userDetailsServicePrincipal), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new RefreshTokenFilter(userDetailsServicePrincipal), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/app/**").authenticated()
                .antMatchers("/refresh").authenticated();

        return http.build();
    }


    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsServicePrincipal);
        return authenticationProvider;
    }

}
