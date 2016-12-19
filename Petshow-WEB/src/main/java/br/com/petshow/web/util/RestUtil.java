package br.com.petshow.web.util;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.ClientResponse;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.model.Anuncio;
import br.com.petshow.model.Entidade;
import br.com.petshow.util.FileApplicationUtil;
import br.com.petshow.util.JsonUtil;
import br.com.petshow.util.WriteConsoleUtil;
import javassist.bytecode.SignatureAttribute.ClassType;

public class RestUtil {
	
	public static final String URL_BASE = FileApplicationUtil.getUrlBaseREST();
	
	public ResteasyClient client;
	
	public ResteasyWebTarget target;
	
	
	public static <T> T postEntity(Entidade entidade, String url,Class<T> type) throws ExceptionErroCallRest{

		ResteasyClient client = new ResteasyClientBuilder().build();
		
		ResteasyWebTarget target= client.target(URL_BASE+url);
		
		WriteConsoleUtil.write("Enviado:"+JsonUtil.getJSON(entidade));
		
		Response response = target.request().post(Entity.entity(entidade, MediaType.APPLICATION_JSON));
		
		if(response.getStatus() != 200){
			throw new ExceptionErroCallRest("Failed: HTTP error code:" +response.getStatus());
		}
		
		WriteConsoleUtil.write("Retornado:"+JsonUtil.getJSON(entidade));
		return  type.cast(response.readEntity(type));
	}


	public static void delete(String url) throws ExceptionErroCallRest{

		ResteasyClient client = new ResteasyClientBuilder().build();
		
		ResteasyWebTarget target= client.target(URL_BASE+url);
		
		Response response =target.request().delete();
		if(response.getStatus() != 200){
			throw new ExceptionErroCallRest("Failed: HTTP error code:" +response.getStatus());
		}
		
		
	
	}
	
	
	public static <T>  T getEntity(String url,Class<T> type) throws ExceptionErroCallRest{

		ResteasyClient client = new ResteasyClientBuilder().build();
		
		ResteasyWebTarget target= client.target(URL_BASE+url);
		
		
		
		Object entidade=null;
		try{
			Response response = target.request().get();
			entidade = response.readEntity(type);
		}catch(Exception ex){
			throw new ExceptionErroCallRest("Failed: HTTP error code:"+ex.getMessage());
		
		}
		
		
		WriteConsoleUtil.write("Retornado:"+JsonUtil.getJSON(entidade));
		return type.cast(entidade);
	}
	
	
	
	
	/*
	public static <T> T getList(String url,Class<T> type) throws ExceptionErroCallRest{

		ResteasyClient client = new ResteasyClientBuilder().build();
		
		ResteasyWebTarget target= client.target(URL_BASE+url);
		
		
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<T>() {});
			WriteConsoleUtil.write("Enviado:"+JsonUtil.getJSON(entidades));
		}catch(Exception ex){
			throw new ExceptionErroCallRest("Failed: HTTP error code:"+ex.getMessage());
			
		}
		return type.cast(entidades);
	
	}
	*/
		
	
}
