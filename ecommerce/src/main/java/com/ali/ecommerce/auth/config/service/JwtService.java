package com.ali.ecommerce.auth.config.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

    // @Value("${jwt.secret}")
    private static final String SECRET_KEY;

    static {
        // SECRET_KEY = System.getProperty("jwt.secret");
        SECRET_KEY = System.getenv("JWT_SECRET_KEY");
    }

    // implement the generateAccessToken, extractUsername and isAccessTokenValid methods:
    public String generateAccessToken(UserDetails userDetails) {
        return generateAccessToken(new HashMap<>(), userDetails);
    }

    public String extractUsername(String jwtAccessToken) {
        return extractClaim(jwtAccessToken, Claims::getSubject);
    }

    public <R> R extractClaim(
            String jwtAccessToken,
            Function<Claims, R> claimsResolver
    ) throws JwtException, IllegalArgumentException {
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
//                .expiration(new Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000)) /* 15 min * 60 sec * 1000 milliseconds */
                .expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) /* 15 min * 60 sec * 1000 milliseconds */
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSignInKey() {
//        log.info("secret key: {}", SECRET_KEY);
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(
            String jwtAccessToken
    ) throws JwtException, IllegalArgumentException {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(jwtAccessToken)
                .getPayload();
    }

    // changed this method from private to public
    public boolean isAccessTokenExpired(String jwtAccessToken) {
//        try {
            return extractExpiration(jwtAccessToken);
//        } catch (ExpiredJwtException e) {
//            return true; // If token is expired, return true instead of throwing an error
//        }
    }

    // the below method might throw an ExpiredJwtException
    private boolean extractExpiration(String jwtAccessToken) throws ExpiredJwtException{
        return extractClaim(jwtAccessToken, Claims::getExpiration)
                .before(new Date(System.currentTimeMillis()));
    }
}
