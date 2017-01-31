package br.com.petshow.beans;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
public class AnuncioBean  {
	public AnuncioBean (){
		super();
		System.out.println("criado o anuncio:"+ new Date().getTime());
	}

	private Anuncio anuncio;
	private List<Anuncio> anuncios;
	private CallAnuncioRest restAnuncio; 
	private Usuario usuarioLogado;

	private Part imagem;


	@PostConstruct
	public void init() {
		this.anuncio = new Anuncio();
		this.anuncios = new ArrayList<Anuncio>();
		restAnuncio = new CallAnuncioRest();
		usuarioLogado=UsuarioRole.getUsuarioLogado();
		anuncio.setUsuario(usuarioLogado);

		getAnunciosBanco(); 

	}
	/*
	@PreDestroy
	public void destroy() {
		System.out.println("Spring Container is destroy! Customer clean up");
		try {
			anuncios=restAnuncio.getListAnuncio(anuncio.getUsuario().getId());

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}
*/
	public void salvarAnuncio(){
		try {
			
			anuncio.setFoto(ImagemUtil.transformBase64AsString(imagem));
			anuncio.setDataCadastro(new Date());
			anuncio = (Anuncio) RestUtilCall.postEntity(anuncio, "anuncio/salvar",Anuncio.class);
			getAnunciosBanco(); 
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Anuncio foi salvo com sucesso!"));
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}





	}

	public void excluirAnuncio(){
		if(anuncio.getId() ==0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na Exclusão:", "Nenhum Anúncio foi selecionado !"));
		}else{
			excluirAnuncio(anuncio.getId());
		}
	}

	public void excluirAnuncio(long id){
		try{


			RestUtilCall.delete("anuncio/"+id);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Anuncio foi excluido com sucesso!"));
			getAnunciosBanco(); 
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}


	}

	public void consultarAnuncio(){
		try{

			anuncio = (Anuncio)RestUtilCall.getEntity("anuncio/"+anuncio.getId(),Anuncio.class);

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}


	}
	public void novo(){
		try{

			anuncio = new Anuncio();
			anuncio.setUsuario(UsuarioRole.getUsuarioLogado());


		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}


	}

	public void selecionar(Anuncio anuncio){
		this.anuncio=anuncio;

	}


	public List<Anuncio> getAnuncios() {
		
		
		
		return anuncios;
	}


	public void setAnuncios(List<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}

	public Anuncio getAnuncio() {
		return anuncio;
	}





	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}


	public Part getImagem() {
		return imagem;
	}


	public void setImagem(Part imagem) {
		this.imagem = imagem;
	}

	public List<Anuncio> getAnunciosBanco() {
		try {
			anuncios=restAnuncio.getListAnuncio(usuarioLogado.getId());

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		
		
		return anuncios;
	}

}
