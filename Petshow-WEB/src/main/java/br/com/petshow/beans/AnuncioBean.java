package br.com.petshow.beans;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import javax.ws.rs.core.Response;

import org.bouncycastle.util.encoders.Base64;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.model.Anuncio;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.web.util.CallAnuncioRest;
import br.com.petshow.web.util.ImagemUtil;
import br.com.petshow.web.util.RestUtil;




@ManagedBean
public class AnuncioBean  {

	
	private Anuncio anuncio;
	private List<Anuncio> anuncios;
	private CallAnuncioRest restAnuncio;

	private Part imagem;
	
	
	@PostConstruct
	public void init() {
		this.anuncio = new Anuncio();
		this.anuncios = new ArrayList<Anuncio>();
		restAnuncio = new CallAnuncioRest();
		anuncio.setUsuario(UsuarioRole.getUsuarioLogado());
		try {
			anuncios=restAnuncio.getListAnuncio(anuncio.getUsuario().getId());
			
		} catch (ExceptionErroCallRest e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void salvarAnuncio(){
		try{
		    anuncio.setFoto(ImagemUtil.transformBase64AsString(imagem));
			anuncio.setDataCadastro(new Date());
			anuncio = (Anuncio) RestUtil.postEntity(anuncio, "anuncio/salvar",Anuncio.class);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}catch(Throwable ex){
			ex.printStackTrace();
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
		
		
		RestUtil.delete("anuncio/"+id);

		}catch(Exception ex){
			ex.printStackTrace();
		}catch(Throwable ex){
			ex.printStackTrace();
		}
		

	}
	
	public void consultarAnuncio(){
		try{
				
		anuncio = (Anuncio)RestUtil.getEntity("anuncio/"+anuncio.getId(),Anuncio.class);

		}catch(Exception ex){
			ex.printStackTrace();
		}catch(Throwable ex){
			ex.printStackTrace();
		}
		

	}
	public void novo(){
		try{
				
		anuncio = new Anuncio();
		anuncio.setUsuario(UsuarioRole.getUsuarioLogado());

		}catch(Exception ex){
			ex.printStackTrace();
		}catch(Throwable ex){
			ex.printStackTrace();
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
	
}
;