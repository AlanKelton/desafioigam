package com.capgemini.selecao.banco.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.capgemini.selecao.banco.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService 
{
	/**
	 * Jwt implementation, generation and validation. 
	 */
	
	@Value("${banco.jwt.expiration}")
	private String expiration;
	
	@Value("${banco.jwt.secret}")
	private String secret;

	public String generate(Authentication authentication) 
	{
		User logedUser = (User) authentication.getPrincipal();
		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("Banco Capegemini API")
				.setSubject(logedUser.getId().toString())
				.setIssuedAt(today)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();		
	}

	public boolean isValidToken(String token) 
	{
		try 
		{
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;			
		} 
		catch (Exception e) 
		{
			return false;
		}
	}

	public Long getUserId(String token) 
	{
		Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(body.getSubject());
	}
	
}
