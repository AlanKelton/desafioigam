package com.capgemini.selecao.banco.controllers.dto;

import com.capgemini.selecao.banco.models.Account;

public class AccountDTO 
{
	/**
	 * Account DTO pattern 
	 */
	
	private Long number;
	private float balance;
	
	public AccountDTO(Account acc)
	{
		this.number = acc.getNumber();
		this.balance = acc.getBalance();
	}
	
	public Long getNumber() {
		return number;
	}
	public float getBalance() {
		return balance;
	}
	
	

}
