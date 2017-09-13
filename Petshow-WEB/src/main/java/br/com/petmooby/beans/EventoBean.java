package br.com.petmooby.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.core.Response;

import br.com.petmooby.constants.RestConstants;
import br.com.petmooby.constants.RestPathConstants;
import br.com.petmooby.model.Evento;

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

}
