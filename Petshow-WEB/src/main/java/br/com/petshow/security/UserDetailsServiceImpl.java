package br.com.petshow.security;



import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.petshow.model.Usuario;



@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  @PersistenceContext
  private EntityManager entityManager;

  public UserDetails loadUserByUsername(String nmLogin) throws UsernameNotFoundException {
    return consultaPorNome(nmLogin);
  }
  
  private Usuario consultaPorNome(String nmLogin) {
    try{
    	   	    	
    	
      return entityManager.createNamedQuery(Usuario.FIND_POR_NOME_LOGIN, Usuario.class).setParameter("nmLogin", nmLogin).getSingleResult();
      
    }catch (NoResultException e) {
      throw new UsernameNotFoundException("Usuario não encontrado!");
    }
  }
}
