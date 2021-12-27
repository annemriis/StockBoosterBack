package com.example.back.ito03022021backend.security;

import com.example.back.ito03022021backend.security.jwt.JwtRequestFilter;
import com.example.back.ito03022021backend.security.jwt.RestAuthenticationEntryPoint;
import com.example.back.ito03022021backend.services.api.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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

    private final JwtRequestFilter jwtRequestFilter;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public SecurityConfiguration(JwtRequestFilter jwtRequestFilter,
                                 RestAuthenticationEntryPoint authenticationEntryPoint, MyUserDetailsService myUserDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/skateboards/**").permitAll()
                .antMatchers("/hats/**").permitAll()
                .antMatchers("/boardgames/**").permitAll()
                .antMatchers("/coins/**").permitAll()
                .antMatchers(HttpMethod.POST ,"/users/login").permitAll()
                .antMatchers(HttpMethod.POST ,"/users/register").permitAll()
                .antMatchers(HttpMethod.POST ,"/skateboards/add").permitAll()
                .antMatchers(HttpMethod.POST ,"/hats/save").permitAll()
                .antMatchers(HttpMethod.POST, "/boardgames").permitAll()
                .antMatchers(HttpMethod.POST ,"/coins/add").permitAll()
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
        auth.userDetailsService(this.myUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(
                "/api/**",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**",
                "/users/**",
                "/skateboards/**",
                "/hats/**",
                "/boardgames/**",
                "/coins/**"
        );
    }

}
