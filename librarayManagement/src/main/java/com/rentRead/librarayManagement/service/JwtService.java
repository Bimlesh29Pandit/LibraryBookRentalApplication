package com.rentRead.librarayManagement.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.rentRead.librarayManagement.enumAll.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

//	private static final String SECRET = "";
	private static String secretKey = "";
	public JwtService() {
		secretKey = generateSecretKey();
	}
	
	public String generateSecretKey() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey secretKey = keyGen.generateKey();
			System.out.println("Secret Key : "+ secretKey.toString());
			return Base64.getEncoder().encodeToString(secretKey.getEncoded());
		}catch(NoSuchAlgorithmException e) {
			throw new RuntimeException("Error generating secret key", e);
		}
	}
	
	public String generateToken(String username,Role role) { 
		// TODO Auto-generated method stub
		
		Map<String, Object> claims = new HashMap();
		claims.put("Role","ROLE_"+ role.name());
		
		
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
				.signWith(getKey(),SignatureAlgorithm.HS256).compact();
	}

	private Key getKey() {
		// TODO Auto-generated method stub
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//		System.out.println(Keys.hmacShaKeyFor(keyBytes));
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUserName(String token) {
//		System.out.println("ExtractUserName");
		
		return extractClaim(token,Claims::getSubject);
	}
	public String extractRole(String token) {
//		System.out.println("extract role");
		Claims claims = extractAllClaims(token);
		return claims.get("Role",String.class);
	}
	public <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
//		System.out.println("extractClaim ");
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	private Claims extractAllClaims(String token) {
//		System.out.println("extractAllClaims ");
		Claims claims =  Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build().parseClaimsJws(token).getBody();
		System.out.println("extractAllClaims2 ");
		return claims;
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		// TODO Auto-generated method stub
		final String userName  = extractUserName(token);
//		System.out.println(userName);
		return (userName.equals(userDetails.getUsername())&& !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		 return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token,Claims::getExpiration);
	}

}
