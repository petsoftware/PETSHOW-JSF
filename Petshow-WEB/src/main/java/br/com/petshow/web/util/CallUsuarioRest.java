package br.com.petshow.web.util;

import java.net.URLEncoder;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import br.com.petshow.enums.EnumErrosSistema;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.SecurityLogin;
import br.com.petshow.model.Usuario;
import br.com.petshow.util.MapErroRetornoRest;

public class CallUsuarioRest  extends RestUtilCall {

	public  List<Usuario> getListClienteAutoComplete(long idEstabelecimento,String parteNome) throws ExceptionErroCallRest, ExceptionValidation{

		client = new ResteasyClientBuilder().build();

		target= client.target(URL_BASE+"usuario/consulta/clientes/"+idEstabelecimento+"/"+parteNome);


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

	public  List<Usuario> getListCliente(long idEstabelecimento) throws ExceptionErroCallRest, ExceptionValidation{

		client = new ResteasyClientBuilder().build();

		target= client.target(URL_BASE+"usuario/consulta/clientes/"+idEstabelecimento);


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

	public  Usuario getUserByLoginName(String nmLogin) throws ExceptionErroCallRest, ExceptionValidation{

		client = new ResteasyClientBuilder().build();

		target= client.target(URL_BASE+"usuario/consulta/login/"+nmLogin);


		Object entidades = null;
		try{
			entidades =  target.request().get(new javax.ws.rs.core.GenericType<Usuario>() {});

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

		return (Usuario)entidades;

	}
	
	public Usuario validateUser(String key){
		Usuario usuario = new Usuario();
		try{
//			usuario = (Usuario) RestUtilCall.getEntity("usuario/validate/key/"+URLEncoder.encode(key, "UTF-8") ,Usuario.class);
			SecurityLogin secLogin = new SecurityLogin();
			secLogin.setKey(key);
			usuario = (Usuario) RestUtilCall.postEntity(secLogin, "usuario/validate/", Usuario.class);
			if(usuario!=null){
				System.out.println(usuario.getNome());
			}
			
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		return usuario;

	}


}
