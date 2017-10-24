package br.com.petshow.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.enums.EnumFlTpEstabelecimento;
import br.com.petshow.model.Usuario;
import br.com.petshow.security.AuthenticationService;
import br.com.petshow.util.MD5EncriptUtil;
import br.com.petshow.web.util.MessagesBeanUtil;

@ManagedBean
@RequestScoped
public class LoginBean {
	public LoginBean (){
		super();
	}

	private String focusProperty = "";

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

	public String login() {
		System.out.println("entrou login");
		String validate = validate();
		if(validate.trim().isEmpty()){
			boolean success = authenticationService.login(usuario, senha);

			if (!success) {
				MessagesBeanUtil.erroMessage("Falha no login!", "Usuário ou senha inválidos! Ou o usuário pode não ter sido validado pelo E-mail");
				return "";
			}
			Usuario user = AuthenticationService.getUsuarioLogado();
			if(user != null){
				if(user.getFlTpEstabelecimento().equals(EnumFlTpEstabelecimento.USER)){
					return "sucessoLoginComumUser";
				}
			}
			return "sucessoLoginComumUser";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha no login!"+validate, null));
			return null;
		}
	}

	public String validate() {
		String result = "";
		if(getUsuario().trim().isEmpty()){
			result = "Por favor informar o Usuario para o login.";
			setFocusProperty("usuario");
		}else if(getSenha().trim().isEmpty()){
			result = "Por favor informar a senha para o login";
		}
		return result;
	}

	public String logout() {
		authenticationService.logout();
		return "logout";
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

	public String getFocusProperty() {
		return focusProperty;
	}

	public void setFocusProperty(String focusProperty) {
		this.focusProperty = focusProperty;
	}

	public void limparCampos() {
		setUsuario("");
		setSenha("");
	}


}
