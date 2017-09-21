package br.com.petshow.web.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import br.com.petshow.enums.EnumErrosSistema;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Usuario;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.util.MapErroRetornoRest;


public class CallAutoComplete  extends RestUtilCall {
	
	
	

	
	public  List<Usuario> getListUsuario(String nome) throws ExceptionErroCallRest, ExceptionValidation{

		client = new ResteasyClientBuilder().build();
		
		target= client.target(URL_BASE+"usuario/consulta/like/nome/"+UsuarioRole.getUsuarioLogado().getId()+"/"+nome+"");
		
		
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<Usuario>>() {});
			
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
		
		
		
		return (List<Usuario>)entidades;
	
	}
	
	 
}
