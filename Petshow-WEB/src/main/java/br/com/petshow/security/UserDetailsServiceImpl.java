package br.com.petshow.security;



import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;


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

	@PersistenceContext
	private EntityManager entityManager;

	public UserDetails loadUserByUsername(String nmLogin) throws UsernameNotFoundException {
//		return consultaPorNome(nmLogin);
		return getUserByREST(nmLogin);
	}

	private Usuario consultaPorNome(String nmLogin) {
		try{
			return entityManager.createNamedQuery(Usuario.FIND_POR_NOME_LOGIN, Usuario.class).setParameter("nmLogin", nmLogin).getSingleResult();
		}catch (NoResultException e) {
			throw new UsernameNotFoundException("Usuario não encontrado!");
		}
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
