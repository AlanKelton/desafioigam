package com.capgemini.selecao.banco.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.selecao.banco.config.security.TokenService;
import com.capgemini.selecao.banco.controllers.dto.AuthenticationDTO;
import com.capgemini.selecao.banco.controllers.forms.LoginForm;

@RestController
@RequestMapping("/auth")
public class AuthenticationController 
{
	/**
	 * Handles Authentication transactions
	 */
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<AuthenticationDTO> authenticate(@RequestBody @Valid LoginForm form)
	{
		UsernamePasswordAuthenticationToken loginData = form.getLoginData();
		
		try 
		{
			Authentication authentication = authManager.authenticate(loginData);
			
			String token = tokenService.generate(authentication);
			
			 HttpHeaders responseHeaders = new HttpHeaders();
			    responseHeaders.set("Authorization", 
			      token);
			 
			    return ResponseEntity.ok()
			      .headers(responseHeaders)
			      .body(new AuthenticationDTO(token, "Bearer"));
			
		} 
		catch (AuthenticationException e) 
		{
			return ResponseEntity.badRequest().build();
		}
		
	}
	
}
