package br.com.petshow.security;

//import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//import javax.persistence.PersistenceContext;

//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
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

//	static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
//	static{
//		AUTHORITIES.add(new GrantedAuthorityImpl("ADMIN"));
//	}
	
	public UserDetails loadUserByUsername(String nmLogin) throws UsernameNotFoundException {
		return getUserByREST(nmLogin);
		//return consultaPorNome(nmLogin);
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
	
//	@PersistenceContext
//	private EntityManager entityManager;
//	private Usuario consultaPorNome(String nmLogin) {
//	    try{
//	    	   	    	
//	    	
//	      return entityManager.createNamedQuery(Usuario.FIND_POR_NOME_LOGIN, Usuario.class).setParameter("nmLogin", nmLogin).getSingleResult();
//	      
//	    }catch (NoResultException e) {
//	      throw new UsernameNotFoundException("Usuario não encontrado!");
//	    }
//	  }

//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		// TODO Auto-generated method stub
//		if(authentication.getName().equals(authentication.getCredentials())) {
//			List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
//			AUTHORITIES.add(new GrantedAuthorityImpl("ADMIN"));
//			return new UsernamePasswordAuthenticationToken(authentication.getName(),
//							authentication.getCredentials(), AUTHORITIES);
//		}
//		throw new BadCredentialsException("Usuário ou senha inválidos!"	);
//	}

	
	
}
