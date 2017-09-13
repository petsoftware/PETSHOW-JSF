package br.com.petmooby.web.util;



import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import br.com.petmooby.enums.EnumErrosSistema;
import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Entidade;
import br.com.petmooby.util.FileApplicationUtil;
import br.com.petmooby.util.JsonUtil;
import br.com.petmooby.util.MapErroRetornoRest;
import br.com.petmooby.util.WriteConsoleUtil;


public class RestUtilCall <T extends Entidade>{
	
	public static final String URL_BASE = FileApplicationUtil.getUrlBaseREST();
	
	public static ResteasyClient client;
	
	public static ResteasyWebTarget target;
	
	 
	public static <T> T postEntity(Entidade entidade, String url,Class<T> type) throws ExceptionErroCallRest,ExceptionValidation{

		ResteasyClient client = new ResteasyClientBuilder().build();
		
		ResteasyWebTarget target= client.target(URL_BASE+url);
		
		//WriteConsoleUtil.write("Enviado:"+JsonUtil.getJSON(entidade));
		
		Response response = target.request().post(Entity.entity(entidade, MediaType.APPLICATION_JSON));
		
		if(response.getStatus() != 200){
			MapErroRetornoRest erro=null;
			try{
				erro=response.readEntity(MapErroRetornoRest.class); // caso der problema de conversao é por que nao foi um erro previsto pelo proprio REST
			}catch(Throwable th){
				throw new ExceptionErroCallRest("Failed: HTTP error code:" +response.getStatus());
			}
			if(erro.getType()==EnumErrosSistema.ERRO_VALIDACAO){
				throw new ExceptionValidation(erro.getMessage());
			}
		}
		
		
		WriteConsoleUtil.write("Retornado:"+JsonUtil.getJSON(entidade));
		return  type.cast(response.readEntity(type));
	}
	
	
	public static void post( HashMap<String,String> map, String url) throws ExceptionErroCallRest,ExceptionValidation{
// ver melhor impliementacao futuramente
		ResteasyClient client = new ResteasyClientBuilder().build();
		
		ResteasyWebTarget target= client.target(URL_BASE+url);
						
		Response response = target.request().post(Entity.entity(map, MediaType.APPLICATION_JSON));
		
		if(response.getStatus() != 200){
			MapErroRetornoRest erro=null;
			try{
				erro=response.readEntity(MapErroRetornoRest.class); // caso der problema de conversao é por que nao foi um erro previsto pelo proprio REST
			}catch(Throwable th){
				throw new ExceptionErroCallRest("Failed: HTTP error code:" +response.getStatus());
			}
			if(erro.getType()==EnumErrosSistema.ERRO_VALIDACAO){
				throw new ExceptionValidation(erro.getMessage());
			}
		}
		
		
		
		
	}


	public static void delete(String url) throws ExceptionErroCallRest, ExceptionValidation{

		ResteasyClient client = new ResteasyClientBuilder().build();
		
		ResteasyWebTarget target= client.target(URL_BASE+url);
		
		Response response =target.request().delete();
		
		if(response.getStatus() != 200){
			MapErroRetornoRest erro=null;
			try{
				erro=response.readEntity(MapErroRetornoRest.class); // caso der problema de conversao é por que nao foi um erro previsto pelo proprio REST
			}catch(Throwable th){
				throw new ExceptionErroCallRest("Failed: HTTP error code:" +response.getStatus());
			}
			
			if(erro.getType()==EnumErrosSistema.ERRO_VALIDACAO){
				throw new ExceptionValidation(erro.getMessage());
			}
		}
		
		
	
	}
	
	
	public static <T>  T getEntity(String url,Class<T> type) throws ExceptionErroCallRest, ExceptionValidation{

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target= client.target(URL_BASE+url);
		Object entidade=null;
		try{
			Response response = target.request().get();
			if(response.getStatus() == HttpServletResponse.SC_OK){
				entidade = response.readEntity(type);
			}else if(response.getStatus() == HttpServletResponse.SC_NO_CONTENT){
				entidade = response.readEntity(String.class);
			}else if(response.getStatus() == HttpServletResponse.SC_BAD_REQUEST){
				System.out.println(response.toString());
				entidade = response.readEntity(MapErroRetornoRest.class);
			}
				
			
		}catch(Exception ex){
			throw new ExceptionErroCallRest("Failed: HTTP error code:"+ex.getMessage());
		}
		if(entidade instanceof MapErroRetornoRest){// caso seja um objeto do tipo MapErroRetornoRest ocorreu um erro/validacao previsto no REST
			MapErroRetornoRest erro=(MapErroRetornoRest) entidade;
			if(erro.getType()==EnumErrosSistema.ERRO_VALIDACAO){
				throw new ExceptionValidation(erro.getMessage());
			}
		}
		WriteConsoleUtil.write("Retornado:"+JsonUtil.getJSON(entidade));
		return type.cast(entidade);
	}	
	
	public static HashMap<String,String> getHashMap(String url) throws ExceptionErroCallRest, ExceptionValidation{

		ResteasyClient client = new ResteasyClientBuilder().build();
		
		ResteasyWebTarget target= client.target(URL_BASE+url);
		
		
		Object entidade=null;
		try{
			Response response = target.request().get();
			entidade = response.readEntity(HashMap.class);
		}catch(Exception ex){
			throw new ExceptionErroCallRest("Failed: HTTP error code:"+ex.getMessage());
		
		}

		if(entidade instanceof MapErroRetornoRest){// caso seja um objeto do tipo MapErroRetornoRest ocorreu um erro/validacao previsto no REST
			MapErroRetornoRest erro=(MapErroRetornoRest) entidade;
			if(erro.getType()==EnumErrosSistema.ERRO_VALIDACAO){
				throw new ExceptionValidation(erro.getMessage());
			}
		}	
		WriteConsoleUtil.write("Retornado:"+JsonUtil.getJSON(entidade));
		return (HashMap<String,String>)entidade;
	}
	
	/**
	 * Retorna uma lista de objetos do Tipo T
	 * @param url
	 * @param type
	 * @return
	 * @throws ExceptionErroCallRest
	 * @throws ExceptionValidation
	 */
	public List<T> getEntityList(String url, Class<T> type) throws ExceptionErroCallRest, ExceptionValidation{
		buildClientREST();
		target = client.target(URL_BASE+url);
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<T>>() {});
//			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<TypeToken<T>>>() {});
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
		return (List<T>)entidades;
	
	}


	private static void buildClientREST() {
		if(client==null){
			client = new ResteasyClientBuilder().build();
		}
	}

	
}
