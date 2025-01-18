package com.ali.ecommerce.auth.config.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

//    @Value("${jwt.secret}")
//    the above (that is, the annotation) can't be used because the below data field is static
    private static final String SECRET_KEY;
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    static {
//        SECRET_KEY = System.getProperty("jwt.secret");
//      - the above won't work, because the property "jwt.secret" is not a JVM property
//        but rather an environment property
//      - or
        SECRET_KEY = System.getenv("JWT_SECRET_KEY");
    }

//    the below is wrong in my case, but left it for learning purposes:
//    public JwtService(@Value("${jwt.secret}") String jwtSecretKey) {
//        SECRET_KEY = jwtSecretKey;
//    }

//    (10) implement the generateAccessToken, extractUsername and isAccessTokenValid methods:
    public String generateAccessToken(UserDetails userDetails) {
        return generateAccessToken(new HashMap<>(), userDetails);
    }

    public String extractUsername(String jwtAccessToken) {
        return extractClaim(jwtAccessToken, Claims::getSubject);
//        calling the method extractClaim() dynamically, that is with passing
//        a method reference or lambda expression in place of a
//        Functional interface passed as a parameter to this method extractClaim()
    }

    public <R> R extractClaim(String jwtAccessToken, Function<Claims, R> claimsResolver) {
        final Claims claims = extractAllClaims(jwtAccessToken);
        return claimsResolver.apply(claims);
    }

    public boolean isAccessTokenValid(String jwtAccessToken, UserDetails userDetails) {
        String username = extractUsername(jwtAccessToken);
        return username.equals(userDetails.getUsername()) && !isAccessTokenExpired(jwtAccessToken);
    }

//    private methods:

    private String generateAccessToken(HashMap<String,Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) /* 15 min * 60 sec * 1000 milliseconds */
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSignInKey() {
//        log.info("secret key: {}", SECRET_KEY);
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String jwtAccessToken) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(jwtAccessToken)
                .getPayload();
    }

    private boolean isAccessTokenExpired(String jwtAccessToken) {
        return extractExpiration(jwtAccessToken);
    }

    private boolean extractExpiration(String jwtAccessToken) {
        return extractClaim(jwtAccessToken, Claims::getExpiration)
                .before(new Date(System.currentTimeMillis()));
    }

}
