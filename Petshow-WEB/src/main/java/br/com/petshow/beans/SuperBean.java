package br.com.petshow.beans;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import br.com.petshow.model.Entidade;
import br.com.petshow.util.FileApplicationUtil;

public class SuperBean {
	
	protected static ResteasyClient client;
	protected static ResteasyWebTarget target;
	public static final String URL_BASE = FileApplicationUtil.getUrlBaseREST();
	
	static{
		client = new ResteasyClientBuilder().build();
		
	}
	
	protected Response post(String url, Entidade entidade) {
		target = client.target(URL_BASE+url);
		Response response = target.request().post(Entity.entity(entidade, MediaType.APPLICATION_JSON));
		return response;
	}

}
