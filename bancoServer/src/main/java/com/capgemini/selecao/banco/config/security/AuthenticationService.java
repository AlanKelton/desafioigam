package com.capgemini.selecao.banco.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.capgemini.selecao.banco.models.User;
import com.capgemini.selecao.banco.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService
{
	/**
	 * Handles user authentication. 
	 */
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		// if you change the username field change repository method
		Optional<User> userOp = userRepository.findByEmail(username);
		
		if(userOp.isPresent())
		{
			return userOp.get();
		}
		
		throw new UsernameNotFoundException("Dados inválidos!");
	}
}
