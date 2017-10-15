package br.com.petshow.web.util;

import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import br.com.petshow.constants.RestPathConstants;
import br.com.petshow.enums.EnumErrosSistema;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.model.PerfilAdocao;
import br.com.petshow.model.Usuario;
import br.com.petshow.util.MapErroRetornoRest;

public class CallPerfilAdocaoRest extends RestUtilCall<PerfilAdocao> {
	
	public PerfilAdocao salvarNaAPIRest(PerfilAdocao perfilAdocao) throws Exception {
		try {
			return postEntity(perfilAdocao, RestPathConstants.PATH_PERFIL_ADOCAO+"/"+RestPathConstants.SALVAR, PerfilAdocao.class);
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			throw e;
		}
	}

	public PerfilAdocao getSingleResult(Usuario usuario) throws ExceptionErroCallRest, ExceptionValidation{
		try {
			if(usuario != null){
				long id = usuario.getId();
				return getEntity(RestPathConstants.PATH_PERFIL_ADOCAO+"/"+RestPathConstants.GET+"/"+id, PerfilAdocao.class);
			}else{
				return new PerfilAdocao();
			}
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			throw e;
		}
	}
	public static long getCountAnimalDisponiveisAdocaoPorPerfil(Usuario usuario) throws ExceptionErroCallRest, ExceptionValidation{
		client = new ResteasyClientBuilder().build();
		target= client.target(URL_BASE+RestPathConstants.PATH_PERFIL_ADOCAO+"/"+RestPathConstants.GET+"/count/"+usuario.getId());
		long count = 0;
		try{
			count =  target.request().get(Long.class);
		}catch(Exception ex){
			throw new ExceptionErroCallRest("Failed: HTTP error code:"+ex.getMessage());
		}
		return count;
	}
	
}
