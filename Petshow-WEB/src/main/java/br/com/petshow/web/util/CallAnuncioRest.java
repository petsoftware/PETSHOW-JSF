package br.com.petshow.web.util;

import java.util.List;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import br.com.petshow.enums.EnumErrosSistema;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Anuncio;
import br.com.petshow.util.MapErroRetornoRest;

public class CallAnuncioRest extends RestUtilCall {
	
	public  List<Anuncio> getListAnuncio(long usuarioId) throws ExceptionErroCallRest, ExceptionValidation{

		client = new ResteasyClientBuilder().build();
		
		target= client.target(URL_BASE+"anuncio/consulta/usuario/"+usuarioId);
		
		
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<Anuncio>>() {});
			
		}catch(Exception ex){
 
			throw new ExceptionErroCallRest("Failed: HTTP error code:"+ex.getMessage());
			
		}
		if(entidades instanceof MapErroRetornoRest){// caso seja um objeto do tipo MapErroRetornoRest ocorreu um erro/validacao previsto no REST
			MapErroRetornoRest erro=(MapErroRetornoRest) entidades;
			if(erro.getType()==EnumErrosSistema.ERRO_VALIDACAO){
				throw new ExceptionValidation(erro.getMessage());
			}else{
				throw new ExceptionErroCallRest("Failed: HTTP error code:"+erro.getMessage());
			}
		}
		
		
		
		return (List<Anuncio>)entidades;
	
	}
	
	
	public  List<Anuncio> getListAnuncio2(long usuarioId) throws ExceptionErroCallRest, ExceptionValidation{

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

}
