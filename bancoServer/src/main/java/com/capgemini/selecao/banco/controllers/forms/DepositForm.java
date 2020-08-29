package com.capgemini.selecao.banco.controllers.forms;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.capgemini.selecao.banco.models.Account;
import com.capgemini.selecao.banco.repository.AccountRepository;

public class DepositForm 
{
	/**
	 * Dto pattern for input data, deposit.
	 */
	
	@NotNull @DecimalMin("1.00")
	private BigDecimal amount;
	
	public BigDecimal getAmount() 
	{
		return amount;
	}

	public void setAmount(BigDecimal amount) 
	{
		this.amount = amount;
	}

	public Account execute(Long id, AccountRepository repository)
	{
		Account acc = repository.getOne(id);
		
		float balance = acc.getBalance();		
		
		balance += amount.floatValue();
		acc.setBalance(balance);
		
		return acc;
	}
}
