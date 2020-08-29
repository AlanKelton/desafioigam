package com.capgemini.selecao.banco.controllers.dto;

public class AuthenticationDTO 
{
	/**
	 * Autentication DTO pattern
	 */
	
	private String token;
	private String type;

	public AuthenticationDTO(String token, String type) 
	{
		this.token = token;
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public String getType() {
		return type;
	}

}
