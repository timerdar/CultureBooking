package ru.timerdar.CultureBooking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.timerdar.CultureBooking.service.AdminService;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends SecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AdminService adminService;

    @Autowired

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) ->
                        authz
                                .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                                .requestMatchers("/api/tickets/ban").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/events").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/events/*").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/events/*").hasRole("ADMIN")
                                .anyRequest().permitAll()
                                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
