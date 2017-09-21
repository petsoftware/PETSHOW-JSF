package br.com.petshow.web.util;

import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import br.com.petshow.enums.EnumErrosSistema;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Vacina;
import br.com.petshow.util.MapErroRetornoRest;

public class CallVacinaRest extends RestUtilCall{

	public static List<Vacina> getListVacinasPorAnimal(long idAnimal) throws ExceptionErroCallRest, ExceptionValidation{
		String url = "animal/vacina/animal/"+idAnimal;
		client = new ResteasyClientBuilder().build();
		target= client.target(URL_BASE+url);
		
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<Vacina>>() {});
			
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
		
		
		
		return (List<Vacina>)entidades;
	
	}

}
