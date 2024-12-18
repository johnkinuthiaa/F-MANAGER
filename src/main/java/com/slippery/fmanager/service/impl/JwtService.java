package com.slippery.fmanager.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final Long EXPITATION_TIME =864000L;
    private final String SECRET_KEY ="91519ab348be52130bc1c7208fe16992e8ff794a5c56b122a965a87eb9f50d1a95221ddc778b82276949f014de0f2978548fdfd5df1295b4e3e9ac73e232f91a236fd62e665bb3fd5ab972801fd35b4855c7d8fecc87239de1db75018ecb9fd05cd2df3ddce417637161b62f061de9d15dceb5bfe6c3f697ca5e49efba20324ec038f96ee9b537e6157f1a67cb71477e526920ad517a8ff881fd824cffa0793c94b24cc54e761c9085d5e323a0ad4e8e465bc254e4c5fff2cb9c0c2c188f7f008223cc075b169881ea83da0fcd6db38614a80425ac01345d1dec1608cd594d385b9e750639de59c65e105344db202b0f6b7222354660c0c7128f9058b4baa550";

    private SecretKey generateSecretKey(){
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateJwtToken(String username){
        Map<String,Object> claims =new HashMap<>();
        return Jwts.
                builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+EXPITATION_TIME))
                .and()
                .signWith(generateSecretKey())
                .compact();
    }
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(generateSecretKey()).build().parseSignedClaims(token).getPayload());

    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
