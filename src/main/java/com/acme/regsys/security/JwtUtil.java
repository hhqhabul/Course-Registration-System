package com.acme.regsys.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
  private static final Key KEY = Keys.hmacShaKeyFor("change-this-secret-change-this-secret-123".getBytes());
  private static final long EXP_MS = 1000 * 60 * 60; // 1h

  public static String generate(String email) {
    return Jwts.builder()
      .setSubject(email)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + EXP_MS))
      .signWith(KEY, SignatureAlgorithm.HS256)
      .compact();
  }

  public static String validateAndGetEmail(String token) {
    return Jwts.parserBuilder().setSigningKey(KEY).build()
      .parseClaimsJws(token).getBody().getSubject();
  }
}
