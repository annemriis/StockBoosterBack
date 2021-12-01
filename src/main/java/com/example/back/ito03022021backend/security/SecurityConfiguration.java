package com.example.back.ito03022021backend.security;

import com.example.back.ito03022021backend.jwt.JwtRequestFilter;
import com.example.back.ito03022021backend.jwt.RestAuthenticationEntryPoint;
import com.example.back.ito03022021backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.back.ito03022021backend.security.ApplicationRoles.ADMIN;
import static com.example.back.ito03022021backend.security.ApplicationRoles.USER;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // secureEnabled make spring use @Secured
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserConfig userConfig;
    private JwtRequestFilter jwtRequestFilter;
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public SecurityConfiguration(UserConfig userConfig, JwtRequestFilter jwtRequestFilter, RestAuthenticationEntryPoint authenticationEntryPoint) {
        this.userConfig = userConfig;
        this.jwtRequestFilter = jwtRequestFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                //.antMatchers("/user").hasAnyRole("USER")
                //.antMatchers("/admin").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .headers().httpStrictTransportSecurity().disable()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(userConfig.getUserName())
                .password(passwordEncoder().encode(userConfig.getUserPassword())) // Spring Security 5 requires specifying the password storage format
                .authorities(USER)
                .and()
                .withUser(userConfig.getAdminName())
                .password(passwordEncoder().encode(userConfig.getAdminPassword())) // Spring Security 5 requires specifying the password storage format
                .authorities(USER, ADMIN);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers(

                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**"
        );
    }

}
