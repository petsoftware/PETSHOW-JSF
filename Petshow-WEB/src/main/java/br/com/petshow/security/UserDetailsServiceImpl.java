package br.com.petmooby.security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Usuario;
import br.com.petmooby.web.util.CallUsuarioRest;



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
