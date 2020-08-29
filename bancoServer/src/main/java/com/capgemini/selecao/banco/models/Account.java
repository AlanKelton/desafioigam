package com.capgemini.selecao.banco.models;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.capgemini.selecao.banco.controllers.dto.AccountDTO;

@Entity
public class Account 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long number;
	private float balance;
	
	public Long getNumber() 
	{
		return number;
	}
	
	public void setNumber(Long number) 
	{
		this.number = number;
	}
	
	public float getBalance() 
	{
		return balance;
	}	
	
	public Long getId() 
	{
		return id;
	}

	public void setBalance(float balance) 
	{
		this.balance = balance;
	}
	
	public static List<AccountDTO> accountListToDTO(List<Account> accounts) 
	{
		return accounts.stream().map(AccountDTO::new).collect(Collectors.toList());
	}
}
