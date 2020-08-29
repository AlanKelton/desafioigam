package com.capgemini.selecao.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.selecao.banco.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long>
{
	
}
