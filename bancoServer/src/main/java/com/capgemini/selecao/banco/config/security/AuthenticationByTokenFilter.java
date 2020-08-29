package com.capgemini.selecao.banco.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.capgemini.selecao.banco.models.User;
import com.capgemini.selecao.banco.repository.UserRepository;

public class AuthenticationByTokenFilter extends OncePerRequestFilter
{
	/**
	 * Handles token authentication. 
	 */
	
	private TokenService tokenService;
	
	private UserRepository userRepository;	
	
	public AuthenticationByTokenFilter(TokenService tokenService, UserRepository userRepository) 
	{
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		String token = getToken(request);
		
		boolean valid = tokenService.isValidToken(token);

		if (valid) 
		{
			authenticateClient(token);
		}
		filterChain.doFilter(request, response);
	}

	// to apply the authentication to client
	private void authenticateClient(String token) 
	{
		Long user_id = tokenService.getUserId(token);
		User authenticatedUser = userRepository.findById(user_id).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);		
	}

	private String getToken(HttpServletRequest request) 
	{
		String headerAuthorization = request.getHeader("Authorization");
		
		// if there is no token or a non-bearer token its return null
		if(headerAuthorization == null || headerAuthorization.isEmpty() || !headerAuthorization.startsWith("Bearer "))
		{
			return null;
		}
		return headerAuthorization.substring(7, headerAuthorization.length());
	}

}
