package br.com.petshow.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

	public UsuarioBean (){
		super();
		System.out.println("criado o UsuarioBean:"+ new Date().getTime());
	}
	private Usuario usuario;


	private Part imagem;

	private String cb;	

	public String getCb() {
		return cb;
	}

	public void setCb(String cb) {
		this.cb = cb;
	}

	@PostConstruct
	public void init() {
		this.usuario= new Usuario();

		if(UsuarioRole.getUsuarioLogado() !=null){
			try {
				this.usuario= 	RestUtilCall.getEntity("usuario/"+UsuarioRole.getUsuarioLogado().getId(),Usuario.class);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
				e.printStackTrace();
			};
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

	public void salvarUsuario(){
		try {

			//usuario.setFoto(ImagemUtil.transformBase64AsString(imagem));
			usuario.setFoto(ImagemUtil.transformBase64AsString(imagem));
			usuario = RestUtilCall.postEntity(usuario, "usuario/salvar",Usuario.class);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Alteração efetuada com sucesso!"));
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}

	public void consultaCEP(){
		try {
			
				HashMap<String,String> retorno = RestUtilCall.getHashMap("endereco/consulta/Cep/"+usuario.getNrCep());
				usuario.setCidade(retorno.get("cidade"));
				usuario.setRua(retorno.get("rua"));
				usuario.setBairro(retorno.get("bairro"));
				usuario.setEstado(retorno.get("estado"));
		
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}


	public String getLblNmLogin(){
		if(usuario==null){
			return "Nome Login:";
		}else{
			return "Nome Login:"+usuario.getNmLogin();
		}
	}
	public String getLblCNPJ(){
		if(usuario==null){
			return "CNPJ:";
		}else{
			String cnpjFormatado=usuario.getCnpjCpf();
			for(int i =usuario.getCnpjCpf().length();i<14;++i){
				cnpjFormatado="0"+cnpjFormatado;
			}
			
			return "CNPJ:"+cnpjFormatado.substring(0, 2) + "." + cnpjFormatado.substring(2, 5) + "." + cnpjFormatado.substring(5, 8 ) + "/" + cnpjFormatado.substring(8, 12) + "-" + cnpjFormatado.substring(12, 14);
		}
	}

	public String getLblEmail(){
		if(usuario==null){
			return "E-mail";
		}else{
			return "E-mail:"+usuario.getEmail();
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
