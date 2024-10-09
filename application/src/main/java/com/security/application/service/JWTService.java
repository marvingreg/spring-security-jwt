package com.security.application.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.security.application.model.Users;
import com.security.application.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	String secretKey;
	
	

	public JWTService() throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
		SecretKey sk = keyGen.generateKey();
		this.secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
	}
	
	public String generateToken(Users user) {
		
		Map<String, Object> claims = new HashMap<String, Object>();
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))
				.add("role", user.getRole())
				.add("id",user.getId())
				.and()
				.signWith(getKey())
				.compact();
				
	}

	private SecretKey getKey() {
		byte [] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public boolean validateToken(String token, UserDetails userDetails, String globalId) {
		final String userName = extractUsername(token);
		final String id = extractId(token);
		return (userName.equals(userDetails.getUsername()) 
				&& !isTokenExpired(token)
				&& id.equalsIgnoreCase(globalId)); //validate using id attached in the request uri
	}
	
	private String extractId(String token) {
		Claims claims = extractAllClaims(token);
		return (String) claims.get("id");
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}
