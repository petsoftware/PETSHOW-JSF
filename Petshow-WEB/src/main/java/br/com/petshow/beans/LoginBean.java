package br.com.petshow.beans;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.security.*;

@ManagedBean
@RequestScoped
public class LoginBean {
	public LoginBean (){
		super();
		System.out.println("criado o LoginBean:"+ new Date().getTime());
	}

	
	
  @ManagedProperty(value = "#{authenticationService}")
  private AuthenticationService authenticationService;

  private String usuario;
  private String senha;

  public String login() {
	  System.out.println("entrou login");
	boolean success = authenticationService.login(usuario, senha);
    
    if (!success) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha no login!", "Usu�rio ou senha inv�lidos!"));
      return "";
    }
    
    return "sucessoLogin";
  }

  public String logout() {
    authenticationService.logout();
    return "login";
  }
  
  public String getUsuarioLogado(){
    return authenticationService.getUsuarioLogado().getUsername();
  }

  
  public void setAuthenticationService(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

public String getUsuario() {
	return usuario;
}

public void setUsuario(String usuario) {
	this.usuario = usuario;
}

public String getSenha() {
	return senha;
}

public void setSenha(String senha) {
	this.senha = senha;
}
  
  
}
