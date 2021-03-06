package br.com.petshow.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import br.com.petshow.enums.EnumFlTpEstabelecimento;
import br.com.petshow.enums.EnumTipoUser;
import br.com.petshow.model.Usuario;
import br.com.petshow.security.AuthenticationService;
import br.com.petshow.web.util.CallUsuarioRest;
import br.com.petshow.web.util.MessagesBeanUtil;

@ManagedBean
@ViewScoped
public class BeanValidateNewUser {

	/**
	 * Chave de segurancao para validar o cliente
	 */
	private String seckey;
	/**
	 * Variavel que contem o parametro com o valor do email criptografado
	 */
	private String lg;
	
	private String alert;
	@ManagedProperty(value = "#{authenticationService}")
	private AuthenticationService authenticationService;
	public String getSeckey() {
		return seckey;
	}
	public void setSeckey(String seckey) {
		this.seckey = seckey;
	}
	public String getLg() {
		return lg;
	}
	public void setLg(String lg) {
		this.lg = lg;
	}
	public String onLoad() {
		return processValidation();
		
	}
	/**
	 * 
	 * @return outcome
	 */
	public String processValidation() {
		Usuario user = null;
		if(seckey==null || seckey.trim().isEmpty()){
			MessagesBeanUtil.erroMessage("Erro de validação de chave de segurança:", "A chave de segurança não confere");
			return null;
		}else{
			user = new CallUsuarioRest().validateUser(seckey);
			if(user!=null && !user.getNmLogin().trim().isEmpty() && !user.getPassword().trim().isEmpty()){
				System.out.println("Efetuar login pelo " + this.getClass().getName());
					boolean success = authenticationService.login(user.getNmLogin(),user.getPassword());
					if (!success) {
						MessagesBeanUtil.erroMessage("Erro ao validar usuario:", "Usuário ou senha incorretos!");
						return "";
					}
					if(user.getFlTpEstabelecimento().equals(EnumFlTpEstabelecimento.USER)){
						return "private-inicio";
					}else{
						return "sucessoLogin";
					}
			}else{
				MessagesBeanUtil.erroMessage("Erro ao validar usuário", "Credenciais não existem");
			}
		}
		return null;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public AuthenticationService getAuthenticationService() {
		return authenticationService;
	}
	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
}
