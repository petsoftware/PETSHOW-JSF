package br.com.petmooby.beans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petmooby.enums.EnumFlTpEstabelecimento;
import br.com.petmooby.enums.EnumTipoUser;
import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Usuario;
import br.com.petmooby.util.MD5EncriptUtil;
import br.com.petmooby.web.util.RestUtilCall;


@ManagedBean
@ViewScoped
public class NovoUsuarioBean {

	private Usuario usuario;
	private String senha;
	private String confSenha;
	private String textUsuario = "";
	private boolean renderizarNome = false;
	private boolean renderizarEmail= false;
	private boolean renderizarCNPJ = false;
	private String tipoUsuario = "";
	
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
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Em instante você receberá um e-mail para completar o seu cadastro!"));
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
				if(!tipoUsuario.equalsIgnoreCase("U")){
					result = "CNPJ deve ser informado.";
				}
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

	public String getTextUsuario() {
		return textUsuario;
	}

	public void setTextUsuario(String textUsuario) {
		this.textUsuario = textUsuario;
	}

	public boolean isRenderizarNome() {
		return renderizarNome;
	}

	public void setRenderizarNome(boolean renderizarNome) {
		this.renderizarNome = renderizarNome;
	}

	public boolean isRenderizarEmail() {
		return renderizarEmail;
	}

	public void setRenderizarEmail(boolean renderizarEmail) {
		this.renderizarEmail = renderizarEmail;
	}

	public boolean isRenderizarCNPJ() {
		return renderizarCNPJ;
	}

	public void setRenderizarCNPJ(boolean renderizarCNPJ) {
		this.renderizarCNPJ = renderizarCNPJ;
	}
	
	public void renderComponenets() {
		if(tipoUsuario.equalsIgnoreCase("P") || tipoUsuario.equalsIgnoreCase("O")){
			setRenderizarCNPJ(true);
			setRenderizarEmail(true);
			setRenderizarNome(true);
			setTextUsuario("NOME DA SUA EMPRESA");
			if(tipoUsuario.equalsIgnoreCase("P")){
				usuario.setFlTpEstabelecimento(EnumFlTpEstabelecimento.PETSHOP);
			}else{
				usuario.setFlTpEstabelecimento(EnumFlTpEstabelecimento.ONG);
			}
		}else{
			setRenderizarCNPJ(false);
			setRenderizarEmail(true);
			setRenderizarNome(true);
			setTextUsuario("SEU NOME");
			usuario.setFlTpEstabelecimento(EnumFlTpEstabelecimento.USER);
		}
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
}
