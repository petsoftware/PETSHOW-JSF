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
public class UserDetailsServiceImpl implements UserDetailsService {


	public UserDetails loadUserByUsername(String nmLogin) throws UsernameNotFoundException {
		return getUserByREST(nmLogin);
	}

	private Usuario getUserByREST(String nmLogin) {
		try {
			return new CallUsuarioRest().getUserByLoginName(nmLogin);
		} catch (ExceptionErroCallRest e) {
			// TODO Auto-generated catch block
			return null;
		} catch (ExceptionValidation e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
