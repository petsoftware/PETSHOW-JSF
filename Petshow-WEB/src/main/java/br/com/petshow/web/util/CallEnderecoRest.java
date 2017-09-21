package br.com.petshow.web.util;

import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import br.com.petshow.enums.EnumErrosSistema;
import br.com.petshow.enums.EnumUF;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Bairro;
import br.com.petshow.model.Cidade;
import br.com.petshow.model.Estado;
import br.com.petshow.util.MapErroRetornoRest;

public class CallEnderecoRest extends RestUtilCall{
	
	
	public  List<Cidade> getListCidadeUF(String uf) throws ExceptionErroCallRest, ExceptionValidation{

		client = new ResteasyClientBuilder().build();
		
		target= client.target(URL_BASE+"endereco/consulta/cidade/estado/uf/"+uf);
		
		
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<Cidade>>() {});
			
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
		
		
		
		return (List<Cidade>)entidades;
	
	}
	public  List<Cidade> getListCidadeIDEstado(String id) throws ExceptionErroCallRest, ExceptionValidation{
		client = new ResteasyClientBuilder().build();
		
		target= client.target(URL_BASE+"endereco/consulta/cidade/estado/id/"+id);
		
		
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<Cidade>>() {});
			
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
		
		return (List<Cidade>)entidades;
	
	}
	
	
	
	public  List<Bairro> getListBairro(String idCidade) throws ExceptionErroCallRest, ExceptionValidation{

		client = new ResteasyClientBuilder().build();
		
		target= client.target(URL_BASE+"endereco/consulta/bairro/cidade/"+idCidade);
		
		
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<Bairro>>() {});
			
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
		
		
		
		return (List<Bairro>)entidades;
	
	}
	
	public  List<Estado> getListEstado() throws ExceptionErroCallRest, ExceptionValidation{

		client = new ResteasyClientBuilder().build();
		
		target= client.target(URL_BASE+"endereco/consulta/estados");
		
		
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<Estado>>() {});
			
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
		
		
		
		return (List<Estado>)entidades;
	
	}
	
	public  List<Cidade> getListCidadeUF(EnumUF uf) throws ExceptionErroCallRest, ExceptionValidation{
		client = new ResteasyClientBuilder().build();
		target= client.target(URL_BASE+"endereco/consulta/cidade/uf/"+uf);
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<Cidade>>() {});
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
		return (List<Cidade>)entidades;
	}
}
