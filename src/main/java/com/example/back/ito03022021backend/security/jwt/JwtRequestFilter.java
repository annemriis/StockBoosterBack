package com.example.back.ito03022021backend.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Lazy
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_ = "Bearer ";
    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetails;

    @Lazy
    @Autowired
    public JwtRequestFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetails) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetails = userDetails;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Get token form request
        Optional<String> token = getToken(request);
        System.out.println(token);
        if (token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        // Get username out of token
        String username = getUsername(token);
        if (username == null) {
            filterChain.doFilter(request, response);
            return;
        }
        //Is token valid??
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            // get spring context by spring user
            UserDetails userDetails =  this.userDetails.loadUserByUsername(username);
            if (jwtTokenProvider.isValid(token.get(), userDetails.getUsername())) {
                // If token is valid, tell security that everything is ok
                UsernamePasswordAuthenticationToken authenticationToken = buildAuthToken(userDetails, request);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken buildAuthToken(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }

    private String getUsername(Optional<String> token) {
        try {
            return jwtTokenProvider.getUsernameFormToken(token.get());

        } catch (RuntimeException e) {
            return null;
        }
    }

    public Optional<String> getToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null || !header.startsWith(BEARER_)) {
            return Optional.empty();
        }
        return Optional.of(header.substring(BEARER_.length()));
    }
}
