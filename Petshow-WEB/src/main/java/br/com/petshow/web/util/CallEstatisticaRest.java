package br.com.petshow.web.util;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Estatistica;
import br.com.petshow.model.Usuario;

public class CallEstatisticaRest extends RestUtilCall<Estatistica>{

	public  Estatistica getEstatisTicaDoUsuario(Usuario usuario) throws ExceptionErroCallRest, ExceptionValidation{
		client = new ResteasyClientBuilder().build();
		target= client.target(URL_BASE+"estatistica/usuario/"+usuario.getId());
		Estatistica entidade = null;
		try{
			entidade =  target.request().get(Estatistica.class);
		}catch(Exception ex){
			throw new ExceptionErroCallRest("Failed: HTTP error code:"+ex.getMessage());
		}
		return entidade;
	}
}
