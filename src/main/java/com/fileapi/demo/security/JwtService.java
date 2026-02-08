package com.fileapi.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Generates a JWT-token for a user.
     *
     * @param username Username which will be included in token
     * @return JWT-token as a String
     */
    public String generateToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer("fileapi")
                .withSubject(username)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(5, ChronoUnit.MINUTES)) // 5 min
                .sign(algorithm);
    }

    /**
     * Validates a JWT-token and extracts username.
     *
     * @param token JWT-token to validate.
     * @return Username from token
     * @throws JWTVerificationException if token is invalid or expired
     */
    public String validateToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }
}
