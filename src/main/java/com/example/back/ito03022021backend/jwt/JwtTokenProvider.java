package com.example.back.ito03022021backend.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class JwtTokenProvider {

    //add methods to generate token and external config

    private JwtConfig jwtConfig;

    @Autowired
    public JwtTokenProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(new HashMap<>())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getDurationInMS()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
    }




    public static void main(String[] args) {
        String jwt = Jwts.builder()
                .setSubject("test")
                .addClaims(new HashMap<>())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60*1000))
                .signWith(SignatureAlgorithm.HS512, "secret")
                .compact();
        System.out.println(jwt);
    }
}
