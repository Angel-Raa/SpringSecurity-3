package org.caja.ideal.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * Clase de service para Json Token
 */
@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;
    @Value("${jwt.time.expiration}")
    private String TIME_EXPIRATION;

    /**
     * Genera token de Acceso
     * @param username
     * @return
     */
    public String generateAccessToken(@NonNull String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(TIME_EXPIRATION)))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Genera la  firma de token
     * @return
     */
    public Key getSignatureKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     *  Valida Token de acceso
     * @param token
     * @return
     */
    @SneakyThrows
    public  boolean isTokenValid(@NonNull String token){
        Jwts.parserBuilder()
                .setSigningKey(getSignatureKey()) // valida la firma
                .build()
                .parseClaimsJws(token)// valida toke
                .getBody();
        return true;
    }

    /**
     *  metodo que obtiene la caracteristica (Claims) de token
     * @param token
     * @return
     */
    public Claims extractAllClaims(@NonNull String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignatureKey()) // valida la firma de token
                .build()
                .parseClaimsJws(token)// valida toke
                .getBody();
    }

    /**
     * metodo que obtiene un solo  username (Claims) de token
     * @param token
     * @param claims
     * @return
     * @param <T>
     */
    public <T> T getClaims(@NonNull String token, Function<Claims, T> claims){
        Claims claim = extractAllClaims(token);
        return  claims.apply(claim);
    }

    /**
     * Obtener el username de token
     * @param token
     * @return
     */
    public String getUserUsername(@NonNull String token){
        return  getClaims(token, Claims::getSubject);
    }
}




