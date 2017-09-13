package br.com.petmooby.web.util;

import br.com.petmooby.constants.RestPathConstants;
import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.PerfilAdocao;
import br.com.petmooby.model.Usuario;

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
}
