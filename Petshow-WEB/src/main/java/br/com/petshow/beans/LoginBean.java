package br.com.petshow.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.model.Usuario;
import br.com.petshow.security.AuthenticationService;
import br.com.petshow.util.MD5EncriptUtil;

@ManagedBean
@RequestScoped
public class LoginBean {
	public LoginBean (){
		super();
	}

	
	
  @ManagedProperty(value = "#{authenticationService}")
  private AuthenticationService authenticationService;

  private String usuario;
  private String senha;
  
  	public String getUserName() {
  		return getUsuarioLogado().getNome();
	}

public boolean isLogado() {
	  String userName = getUsuarioLogado().getUsername();
		if(userName!=null && !userName.isEmpty()){
			return true;
		}else{
			return false;
		}
  }

//public void setLogado(boolean isLogado) {
//	LoginBean.isLogado = isLogado;
//}

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
  
  public Usuario getUsuarioLogado(){
    Usuario user = authenticationService.getUsuarioLogado();
    if(user!=null){
    	return user;
    }else{
    	return new Usuario();
    }
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
