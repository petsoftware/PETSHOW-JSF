package br.com.petshow.beans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.enums.EnumFlTpEstabelecimento;
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
	
	public EnumFlTpEstabelecimento[] getTipos() {
		return EnumFlTpEstabelecimento.values();
	}
	public String solicitarCadastro(){
		try {
			String validate = validate();
			if(validate.trim().isEmpty()){
				usuario.setNmLogin(usuario.getEmail());
				usuario.setPassword(MD5EncriptUtil.toMD5(getSenha()));
				usuario.setFlPreCadastro(true);
				usuario = RestUtilCall.postEntity(usuario, "usuario/precadastro",Usuario.class);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Usuário Cadastrado com sucesso!"));
				return null;
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informações incorretas:", validate));
				return null;
			}
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		return null;
	}
	
	private String validate() {
		String result = "";
		if(usuario!=null){
			if(usuario.getEmail().trim().isEmpty()){
				result = "O nome do usuário não pode ser em branco.";
			}else if(getSenha().trim().isEmpty()){
				result = "A senha de cadastro não pode ser em branco.";
			}else if(getConfSenha().trim().isEmpty()){
				result = "A confirmação da senha não pode ser em branco";
			}else if(!getSenha().equals(getConfSenha())){
				result = "As senhas não conferem. Coloque a senha depois confirme com a mesma senha.";
			}else if(usuario.getCnpjCpf() != null && usuario.getCnpjCpf().trim().isEmpty()){
				result = "CNPJ deve ser informado.";
			}
		}else{
			result = "Usuário não informado ou nulo";
		}
		return result;
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
