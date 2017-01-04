package br.com.petshow.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Anuncio;
import br.com.petshow.model.Usuario;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.web.util.CallAnuncioRest;
import br.com.petshow.web.util.ImagemUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class UsuarioBean {

	
	private Usuario usuario;
		

	private Part imagem;


	@PostConstruct
	public void init() {
		this.usuario= new Usuario();
		
		if(UsuarioRole.getUsuarioLogado() !=null){
			this.usuario= UsuarioRole.getUsuarioLogado();
		}else{
			this.usuario= new Usuario();
		}
	}
	
	public void solicitarCadastro(){
			try {
				
			//usuario.setFoto(ImagemUtil.transformBase64AsString(imagem));
			usuario = RestUtilCall.postEntity(usuario, "usuario/precadastro",Usuario.class);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Solicitação de cadastro foi efetivado com sucesso!Em até 1 dia util receberá um email com instruções!"));
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}

	public void salvar(){
		try {
			
		//usuario.setFoto(ImagemUtil.transformBase64AsString(imagem));
		usuario = RestUtilCall.postEntity(usuario, "usuario/precadastro",Usuario.class);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Solicitação de cadastro foi efetivado com sucesso!Em até 1 dia util receberá um email com instruções!"));
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

	public Part getImagem() {
		return imagem;
	}

	public void setImagem(Part imagem) {
		this.imagem = imagem;
	}
	
}
