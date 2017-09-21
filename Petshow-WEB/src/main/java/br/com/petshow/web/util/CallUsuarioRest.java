package br.com.petmooby.web.util;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import br.com.petmooby.enums.EnumErrosSistema;
import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.SecurityLogin;
import br.com.petmooby.model.Usuario;
import br.com.petmooby.util.MapErroRetornoRest;

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
	/**
	 * Realiza o REST para obter o usuario do login
	 * @param nmLogin
	 * @return
	 * @throws ExceptionErroCallRest
	 * @throws ExceptionValidation
	 */
	public  Usuario getUserByLoginName(String nmLogin) throws ExceptionErroCallRest, ExceptionValidation{
		return getEntity("usuario/consulta/login/"+nmLogin, Usuario.class);
	}
	
	/**
	 * Realiza a validação do usuário para saber se o mesmo se autenticou pelo email.
	 * @param key
	 * @return
	 */
	public Usuario validateUser(String key){
		Usuario usuario = new Usuario();
		try{
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
