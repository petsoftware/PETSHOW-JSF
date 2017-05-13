package br.com.petshow.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

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
		if(processValidation()){
			return "sucessoLogin";
		}else{
			setAlert("Erro na validação do usuário. Por favor entrar em contato com o Suporte da PetShow");
			return null;
		}
		
	}
	
	public boolean processValidation() {
		
		if(seckey==null || seckey.trim().isEmpty()){
			MessagesBeanUtil.erroMessage("Erro de validação de chave de segurança:", "A chave de segurança não confere");
			return false;
		}
		return true;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
}
