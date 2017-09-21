package br.com.petshow.security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Usuario;
import br.com.petshow.web.util.CallUsuarioRest;



@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {//,AuthenticationManager {

	public UserDetails loadUserByUsername(String nmLogin) throws UsernameNotFoundException {
		try {
			return getUserByREST(nmLogin);
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			// TODO Auto-generated catch block
			throw new UsernameNotFoundException(e.getMessage());
		}
	}

	private Usuario getUserByREST(String nmLogin) throws ExceptionErroCallRest, ExceptionValidation  {
		return new CallUsuarioRest().getUserByLoginName(nmLogin);
	}
	
}
