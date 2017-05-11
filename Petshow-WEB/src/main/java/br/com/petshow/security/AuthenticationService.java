package br.com.petshow.security;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.petshow.model.Usuario;



@Component
public class AuthenticationService {

  @Autowired
  @Qualifier("authenticationManager")
  private AuthenticationManager authenticationManager;

  public boolean login(String username, String password) {
	  try {
		  UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		  Authentication authenticate = authenticationManager.authenticate(token);
		  if (authenticate.isAuthenticated()) {
			  SecurityContextHolder.getContext().setAuthentication(authenticate);
			  return true;
		  }
	  }
	  catch (AuthenticationException e) {e.printStackTrace();}
	  return false;
  }

  public void logout() {
    SecurityContextHolder.getContext().setAuthentication(null);
    invalidateSession();
  }

  public Usuario getUsuarioLogado() {
    
	  Object aut = SecurityContextHolder.getContext().getAuthentication();
	  if(aut == null){
		  return new Usuario();
	  }
	  Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	  Usuario user  = null;
	  if(object instanceof java.lang.String){
		  user = new Usuario();
		  user.setNmLogin((String)object);
	  }else if(object instanceof Usuario){
		  user = (Usuario)object;
	  }
	  
	  if(user.getNmLogin().equalsIgnoreCase(Usuario.ANONYMOUS_USER)){
		  //NOTE: O usuario nao esta logado
		  return new Usuario();
	  }
	  return user;
	  
  }

  private void invalidateSession() {
    FacesContext fc = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
    session.invalidate();
  }

}
