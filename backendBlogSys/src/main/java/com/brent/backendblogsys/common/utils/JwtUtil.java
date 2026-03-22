package com.brent.backendblogsys.common.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    public static final long JWT_TTL = 60 * 60 * 1000L * 24 * 14;  // 有效期14天
    public static final String JWT_KEY = "woeiuroncoxjveonoODEGOjrewon3124lkdsjjnwfegf"; // 密钥

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }

        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .id(uuid)
                .subject(subject)
                .issuer("sg")
                .issuedAt(now)
                .signWith(generalKey())
                .expiration(expDate);
    }

    public static SecretKey generalKey() {
        byte[] encodeKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(encodeKey, "HmacSHA256");
    }

    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();

        // JJWT 0.13.0 版本的正确解析方式
        JwtParser parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        Jws<Claims> claimsJws = parser.parseSignedClaims(jwt);
        return claimsJws.getPayload();
    }
}