package com.capgemini.selecao.banco.controllers;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.selecao.banco.controllers.dto.AccountDTO;
import com.capgemini.selecao.banco.controllers.forms.DepositForm;
import com.capgemini.selecao.banco.controllers.forms.WithdrawForm;
import com.capgemini.selecao.banco.models.Account;
import com.capgemini.selecao.banco.models.User;
import com.capgemini.selecao.banco.repository.AccountRepository;
import com.capgemini.selecao.banco.repository.UserRepository;

@RestController
@RequestMapping("/account")
public class AccountController 
{
	/**
	 * Handles account transactions
	 */
	
	@Autowired
	private AccountRepository accRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<AccountDTO> account(@PathVariable(value = "id") Long id)
	{
		Optional<User> userOp = userRepository.findById(id);
		if(userOp.isPresent())
		{			
			User user = userOp.get();			
			
			return ResponseEntity.ok(new AccountDTO(user.getUserAccount()));
		}
		
		return ResponseEntity.notFound().build();		
	}
	
	@GetMapping("/balance/{id}")
	public ResponseEntity<String> balance(@PathVariable(value = "id") Long id)
	{
		Optional<User> userOp = userRepository.findById(id);
		if(userOp.isPresent())
		{			
			User user = userOp.get();			
			String balance = String.valueOf(user.getUserAccount().getBalance());
			
			return ResponseEntity.ok(balance);
		}		
		
		return ResponseEntity.notFound().build();		
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@PatchMapping("/deposit/{id}")
	@Transactional
	public ResponseEntity<AccountDTO> deposit(@PathVariable(value = "id") Long id, @RequestBody @Valid DepositForm form)
	{
		Optional<User> userOp = userRepository.findById(id);
		if(userOp.isPresent())
		{			
			User user = userOp.get();			
			Account acc = form.execute(user.getUserAccount().getId(), accRepository);		
			
			return ResponseEntity.ok(new AccountDTO(acc));
		}
		return ResponseEntity.notFound().build();
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@PatchMapping("/withdraw/{id}")
	@Transactional
	public ResponseEntity<AccountDTO> withdraw(@PathVariable(value = "id")  Long id, @RequestBody @Valid WithdrawForm form)
	{
		Optional<User> userOp = userRepository.findById(id);
		if(userOp.isPresent())
		{			
			User user = userOp.get();			
			Account acc = form.execute(user.getUserAccount().getId(), accRepository);		
			
			if(acc != null)
				return ResponseEntity.ok(new AccountDTO(acc));
			else
				return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.notFound().build();
	}
}
