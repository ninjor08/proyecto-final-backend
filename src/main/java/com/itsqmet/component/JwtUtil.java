package com.itsqmet.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "ProyectofinalFullstack2026!";
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    public String generarToken(String email, String rol) {
        return JWT.create()
                .withSubject(email)
                .withClaim("rol", rol)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                .sign(algorithm);
    }

    public String obtenerEmail(String token) {
        return decodificarToken(token).getSubject();
    }

    public String obtenerRol(String token) {
        return decodificarToken(token).getClaim("rol").asString();
    }

    public boolean esTokenValido(String token) {
        try {
            decodificarToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private DecodedJWT decodificarToken(String token) {
        return JWT.require(algorithm).build().verify(token);
    }
}

