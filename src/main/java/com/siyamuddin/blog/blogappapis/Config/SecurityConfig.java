package com.siyamuddin.blog.blogappapis.Config;

import com.siyamuddin.blog.blogappapis.Security.JwtAuthenticationEntryPoint;
import com.siyamuddin.blog.blogappapis.Security.JwtAuthenticationFilter;
import com.siyamuddin.blog.blogappapis.Services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    public static final String[] PUBLIC_URLS={
            "/auth/login/**",
            "/auth/register/**",
            "/v3/api-docs/**",
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/api/posts/**"
    };
    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;
    private CustomUserDetailService customUserDetailService;
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeRequests()
//                .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**","/auth/login/**","/auth/register/**").permitAll()
                .requestMatchers(PUBLIC_URLS).permitAll()
                .anyRequest()
                .authenticated()
                .and().exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Allow specific origins (replace with your frontend URLs)
        configuration.setAllowedOriginPatterns(Arrays.asList(
            "http://localhost:3000",     // React dev server
            "http://localhost:4200",     // Angular dev server
            "http://localhost:8080",     // Vue dev server
            "http://localhost:5173",     // Vite dev server
            "https://yourdomain.com",    // Production domain
            "https://www.yourdomain.com" // Production domain with www
        ));
        
        // Allow specific HTTP methods
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));
        
        // Allow specific headers
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization", "Content-Type", "X-Requested-With", 
            "Accept", "Origin", "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
        ));
        
        // Allow credentials (cookies, authorization headers)
        configuration.setAllowCredentials(true);
        
        // Set max age for preflight requests
        configuration.setMaxAge(3600L); // 1 hour
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }


protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
}
public PasswordEncoder passwordEncoder()
{
    return new BCryptPasswordEncoder();
}
}
