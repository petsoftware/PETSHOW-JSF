package br.com.petmooby.web.util;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import br.com.petmooby.enums.EnumErrosSistema;
import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Adocao;
import br.com.petmooby.model.Animal;
import br.com.petmooby.model.Perdido;
import br.com.petmooby.objects.query.AdocaoQuery;
import br.com.petmooby.util.MapErroRetornoRest;

public class CallAnimalRest  extends RestUtilCall<Animal> {

	public  List<Animal> getListAnimal(long usuarioId) throws ExceptionErroCallRest, ExceptionValidation{

		client = new ResteasyClientBuilder().build();
		
		target= client.target(URL_BASE+"animal/consulta/usuario/"+usuarioId);
		
		
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<Animal>>() {});
			
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
		
		
		
		return (List<Animal>)entidades;
	
	}
	
	public  List<Adocao> getListAnimalAdocao(long estado,long cidade,String tpAnimal,String fase,String sexo) throws ExceptionErroCallRest, ExceptionValidation{
		return  getListAnimalAdocao( estado, cidade, tpAnimal, fase, sexo, 300);
	}
	public  List<Adocao> getListAnimalAdocao(long estado,long cidade,String tpAnimal,String fase,String sexo,int limiteRegistros) throws ExceptionErroCallRest, ExceptionValidation{

		client = new ResteasyClientBuilder().build();
		
		if(tpAnimal==null || tpAnimal.trim().equals("")){
			tpAnimal="null";
		}
		if(fase==null || fase.trim().equals("")){
			fase="null";
		}
		if(sexo==null || sexo.trim().equals("")){
			sexo="null";
		}
		
		target= client.target(URL_BASE+"animal/consulta/adocao/"+estado+"/"+cidade+"/"+tpAnimal+"/"+fase+"/"+sexo+"/"+limiteRegistros);
		
		
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<Adocao>>() {});
			
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
		
		
		
		return (List<Adocao>)entidades;
	
	}
	
	public  List<Adocao> getListAnimalDisponiveisAdocao(AdocaoQuery query) throws ExceptionErroCallRest, ExceptionValidation{

		client = new ResteasyClientBuilder().build();
		target= client.target(URL_BASE+"animal/consulta/adocao/");
		Object entidades = null;
		try{
			Response response = target.request().post(Entity.entity(query, MediaType.APPLICATION_JSON));
			entidades = response.readEntity(new javax.ws.rs.core.GenericType<List<Adocao>>() {});
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
		return (List<Adocao>)entidades;
	}
	
	
	public  List<Perdido> getListAnimalPerdidoAchado(long estado,long cidade,long bairro,String tpAnimal,String tpAchadoPerdido) throws ExceptionErroCallRest, ExceptionValidation{
		 return getListAnimalPerdidoAchado( estado, cidade, bairro, tpAnimal, tpAchadoPerdido, 300); 
	}
	public  List<Perdido> getListAnimalPerdidoAchado(long estado,long cidade,long bairro,String tpAnimal,String tpAchadoPerdido, int limiteRegistros) throws ExceptionErroCallRest, ExceptionValidation{

		client = new ResteasyClientBuilder().build();
		
		if(tpAnimal==null || tpAnimal.trim().equals("")){
			tpAnimal="null";
		}
		if(tpAchadoPerdido==null || tpAchadoPerdido.trim().equals("")){
			tpAchadoPerdido="null";
		}
		
		
		target= client.target(URL_BASE+"animal/consulta/perdido/"+estado+"/"+cidade+"/"+bairro+"/"+tpAnimal+"/"+tpAchadoPerdido+"/"+limiteRegistros);
		
		
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
