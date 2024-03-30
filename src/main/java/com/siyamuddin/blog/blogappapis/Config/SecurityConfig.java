package com.siyamuddin.blog.blogappapis.Config;

import com.siyamuddin.blog.blogappapis.Security.JwtAuthenticationEntryPoint;
import com.siyamuddin.blog.blogappapis.Security.JwtAuthenticationFilter;
import com.siyamuddin.blog.blogappapis.Services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider()
//    {
//        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//        provider.setUserDetailsService(customUserDetailService);
//        provider.setPasswordEncoder(passwordEncoder);
//        return provider;
//    }
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
