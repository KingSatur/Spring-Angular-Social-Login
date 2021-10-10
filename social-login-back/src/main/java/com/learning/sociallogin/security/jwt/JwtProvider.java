package com.learning.sociallogin.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import com.learning.sociallogin.security.UserMain;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtProvider {

	private final static Logger logger = LoggerFactory
			.getLogger(JwtProvider.class);

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private int expiration;

	public String generateToken(Authentication authentication) {
		UserMain userMain = (UserMain) authentication.getPrincipal();
		return Jwts.builder().setSubject(userMain.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(
						new Date(System.currentTimeMillis() + this.expiration))
				.signWith(SignatureAlgorithm.HS256, this.secret).compact();

	}

	public String getEmailFromToken(String token) {
		return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token)
				.getBody().getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException | UnsupportedJwtException
				| ExpiredJwtException | IllegalArgumentException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

}
