package com.trashhcan.letter.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 1000L;
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000L;

    @Value("${spring.jwt.secret}")
    private String JWT_SECRET;

    public String generateAccessToken(Long memberId) {
        final Date now = new Date();

        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_TIME));

        // member id로 회원 구분하여 토큰 발급
        claims.put("memberId", memberId);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //header
                .setClaims(claims) //claim
                .signWith(getSigningKey()) //signature
                .compact();
    }

    public String generateRefreshToken(Long memberId) {
        final Date now = new Date();
        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME));

        claims.put("memberId", memberId);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(getSigningKey())
                .compact();
    }

    public JwtValidationType validateToken(String token){
        try {
            getBody(token);
            return JwtValidationType.VALID_JWT;
        } catch (MalformedJwtException e) {
            return JwtValidationType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException e) {
            return JwtValidationType.EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException e) {
            return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException e) {
            return JwtValidationType.EMPTY_JWT;
        }
    }

    public Long getMemberIdFromAccessToken(String token) {
        Claims claims = getBody(token);
        return (Long) claims.get("memberId");
    }

    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSigningKey() {
        //SecretKey 통해 signature 생성
        String encodedKey = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes());
        //일반적으로 HMAC (Hash-based Message Authentication Code) 알고리즘 사용
        return Keys.hmacShaKeyFor(encodedKey.getBytes());
    }
}
