package br.com.petshow.web.util;

import java.util.Date;
import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import br.com.petshow.enums.EnumErrosSistema;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Venda;
import br.com.petshow.util.MapErroRetornoRest;

public class CallVendaRest extends RestUtilCall {
	
	public  List<Venda> getListVenda(long usuarioId) throws ExceptionErroCallRest, ExceptionValidation{
		
		client = new ResteasyClientBuilder().build();
		
		target= client.target(URL_BASE+"venda/consulta/usuario/"+usuarioId);
		
		
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<Venda>>() {});
			
		}catch(Exception ex){
			ex.printStackTrace();
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
		
		
		
		return (List<Venda>)entidades;
	
	}
	
	public  List<Venda> getListVenda(String palavraChave ,long idCidade,long idEstado) throws ExceptionErroCallRest, ExceptionValidation{
		return getListVenda( palavraChave , idCidade, idEstado,300);
	}
	
	
	public  List<Venda> getListVenda(String palavraChave ,long idCidade,long idEstado,int limiteRegistros) throws ExceptionErroCallRest, ExceptionValidation{
		
		client = new ResteasyClientBuilder().build();
		
		target= client.target(URL_BASE+"venda/consulta/"+palavraChave+"/"+idCidade+"/"+idEstado+"/"+limiteRegistros);
		
		
		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<List<Venda>>() {});
			
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
		
		
		
		return (List<Venda>)entidades;
	
	}
	


}
