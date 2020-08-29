package com.capgemini.selecao.banco.controllers.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm 
{
	@NotEmpty @NotNull
	private String email;
	
	@NotEmpty @NotNull
	private String password;
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}

	public UsernamePasswordAuthenticationToken getLoginData() 
	{
		return new UsernamePasswordAuthenticationToken(email, password);
	}	
}
