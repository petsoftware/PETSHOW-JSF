package br.com.petshow.web.util;

import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.model.Anuncio;

public class CallAnuncioRest extends RestUtil {
	
	public  List<Anuncio> getListAnuncio(long usuarioId) throws ExceptionErroCallRest{

		client = new ResteasyClientBuilder().build();
		
		target= client.target(URL_BASE+"anuncio/consulta/usuario/"+usuarioId);
		
		
		List<Anuncio> entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<Anuncio>>() {});
			
		}catch(Exception ex){
			throw new ExceptionErroCallRest("Failed: HTTP error code:"+ex.getMessage());
			
		}
		return entidades;
	
	}
	

	public  void excluirAnuncio(long anuncioId) throws ExceptionErroCallRest{

		client = new ResteasyClientBuilder().build();
		
		target= client.target(URL_BASE+"anuncio/"+anuncioId);
			
		try{
			target.request().delete();
			
		}catch(Exception ex){
			throw new ExceptionErroCallRest("Failed: HTTP error code:"+ex.getMessage());
			
		}
		
	
	}

}
