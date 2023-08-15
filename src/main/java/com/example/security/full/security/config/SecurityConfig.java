package com.example.security.full.security.config;

import com.example.security.full.security.UserSecurity.dao.JpaUserDetailsService;
import com.example.security.full.security.auth.controller.CustomAuthenticationEntryPoint;
import com.example.security.full.security.auth.controller.CustomSuccessHandler;

import lombok.RequiredArgsConstructor;

import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    private final JpaUserDetailsService jpaUserDetailsService;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint = new CustomAuthenticationEntryPoint();

    @Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }

    @Bean
    public CustomSuccessHandler successHandler() {
        return new CustomSuccessHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(corsFilter(), SessionManagementFilter.class) // adds your custom CorsFilter
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                .formLogin()
                .successHandler(successHandler())
                .failureHandler(null)
                .loginProcessingUrl("/authentication")
                .passwordParameter("password")
                .usernameParameter("username")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .csrf().disable()
                .anonymous().disable()
                .authorizeRequests()
                .requestMatchers("/authentication").permitAll()
                .requestMatchers("/oauth/token").permitAll()
                .requestMatchers("/admin/*").access("hasRole('ROLE_ADMIN')")
                .requestMatchers("/user/*").access("hasRole('ROLE_USER')");
        return http.build();
    }
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    // Exception {

    // http
    // .cors(AbstractHttpConfigurer::disable)
    // .csrf(AbstractHttpConfigurer::disable)
    // .authorizeHttpRequests(auth -> auth
    // .requestMatchers("/api/v1/auth/**")
    // .permitAll()
    // .anyRequest()
    // .authenticated())
    // /*.authorizeHttpRequests(auth -> auth
    // .requestMatchers("/api/v1/**")
    // .authenticated())*/
    // .sessionManagement(session ->
    // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    // // .sessionManagement(session -> session.maximumSessions(1))
    // .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
    // .userDetailsService(jpaUserDetailsService)
    // .logout(logout -> logout
    // .logoutUrl("/api/v1/auth/logout")
    // .logoutSuccessUrl("/api/vi/auth/home")
    // //.logoutSuccessHandler(logoutSuccessHandler)
    // .invalidateHttpSession(true)
    // //.addLogoutHandler(logoutHandler)
    // .deleteCookies("jwt"))
    // ;
    // return http.build();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
