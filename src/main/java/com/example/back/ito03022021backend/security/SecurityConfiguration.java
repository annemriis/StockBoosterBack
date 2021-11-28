package com.example.back.ito03022021backend.security;

import com.example.back.ito03022021backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.back.ito03022021backend.security.ApplicationRoles.ADMIN;
import static com.example.back.ito03022021backend.security.ApplicationRoles.USER;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // secureEnabled make spring use @Secured
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserConfig userConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
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
                .headers().httpStrictTransportSecurity().disable();
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

}
