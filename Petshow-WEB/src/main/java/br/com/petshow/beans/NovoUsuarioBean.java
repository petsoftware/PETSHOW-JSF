package br.com.petshow.beans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Usuario;
import br.com.petshow.util.MD5EncriptUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class NovoUsuarioBean {

	private Usuario usuario;
	private String senha;
	private String confSenha;
	
	@PostConstruct
	private void init() {
		this.usuario = new Usuario();
	}
	
	public void solicitarCadastro(){
		try {
			usuario.setNmLogin(usuario.getEmail());
			usuario.setNome("MUDAR O NOME POR FAVOR");
			usuario.setPassword(MD5EncriptUtil.toMD5(getSenha()));
			usuario = RestUtilCall.postEntity(usuario, "usuario/precadastro",Usuario.class);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Usuário Cadastrado com sucesso!"));
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfSenha() {
		return confSenha;
	}

	public void setConfSenha(String confSenha) {
		this.confSenha = confSenha;
	}
}
