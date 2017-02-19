package br.com.petshow.beans;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.security.*;
import br.com.petshow.util.MD5EncriptUtil;

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
  private boolean isLogado = false;
  public boolean isLogado() {
	return isLogado;
}

public void setLogado(boolean isLogado) {
	this.isLogado = isLogado;
}

public String login() {
	  System.out.println("entrou login");
	boolean success = authenticationService.login(usuario, senha);
    
    if (!success) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha no login!", "Usuário ou senha inválidos!"));
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
	this.senha = MD5EncriptUtil.toMD5(senha) ;
}

public void chamar(){
	System.out.println("chamou");
}


  
}
