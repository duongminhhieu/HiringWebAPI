package com.setqt.Hiring.Security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec;
import org.springframework.stereotype.Component;


import com.setqt.Hiring.Security.Model.CustomUserDetail;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenHelper {

	private final String JWT_SECRET = "HiringappForSEDSHCMUS";
	private String appName = "Hiring";

	
	private final long JWT_EXPIRATION = 604800000L;

	public String generateToken2(CustomUserDetail user) {
		Date now = new Date();
		Date expDate = new Date(now.getTime() + JWT_EXPIRATION);
		return Jwts.builder().setSubject(user.getUsername()).setIssuedAt(now).setExpiration(expDate)
				.signWith(SignatureAlgorithm.HS256, JWT_SECRET).compact();
	}


	 public String generateToken(String subject) {
	        long currentTimeMillis = System.currentTimeMillis();
	        long expirationTimeMillis = currentTimeMillis + 3600 * 1000; // 1 hour
	        Date issueDate = new Date(currentTimeMillis);
	        Date expirationDate = new Date(expirationTimeMillis);

	        return Jwts.builder()
//	                .setClaims(claims)
	                .setSubject(subject)
	                .setIssuedAt(issueDate)
	                .setExpiration(expirationDate)
	                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
	                .compact();
	    }
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();

		return Long.parseLong(claims.getSubject());
	}

	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}

}
