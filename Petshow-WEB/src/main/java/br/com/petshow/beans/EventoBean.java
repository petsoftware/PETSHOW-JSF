package br.com.petshow.beans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response;

import org.primefaces.event.FileUploadEvent;

import br.com.petshow.constants.RestConstants;
import br.com.petshow.constants.RestPathConstants;
import br.com.petshow.model.Evento;
import br.com.petshow.web.util.ImagemUtil;

@ManagedBean
public class EventoBean extends SuperBean {
	
	private Evento evento;
	public EventoBean (){
		super();
	}

	
	
	@PostConstruct
	private void init() {
		evento = new Evento();
	}
	
	public String salvar() {
		Response response = null;
		response = post(RestPathConstants.PATH_EVENTO+"/"+RestConstants.REST_PATTERN_URL_INSERT, evento);
        
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
	                          + response.getStatus());
		}
//		System.out.println("Server response : \n");
//		System.out.println(response.readEntity(String.class));
		return null;
	}


	public Evento getEvento() {
		return evento;
	}


	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	public void enviaImagem(FileUploadEvent event) {
		if(this.evento!=null){
			evento.setBunner(ImagemUtil.transformBase64AsString(event.getFile().getContents()));
		}
	}

}
