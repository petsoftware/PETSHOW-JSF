package br.com.petshow.web.util;

import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import br.com.petshow.enums.EnumErrosSistema;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Perdido;
import br.com.petshow.util.MapErroRetornoRest;

public class CallPerdidoRest extends RestUtilCall<Perdido> {
	
	public  List<Perdido> getMeusAnimaisPerdidos(long idUsuario) throws ExceptionErroCallRest, ExceptionValidation{
		client = new ResteasyClientBuilder().build();
		target= client.target(URL_BASE+"perdido/consulta/perdido/usuario/"+idUsuario);
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<Perdido>>() {});
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
		return (List<Perdido>)entidades;
	}
}
