package br.com.petshow.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Usuario;
import br.com.petshow.web.util.CallAutoComplete;

@ManagedBean
public class AutoCompleteBean {
	 private String caracteres;
	 private CallAutoComplete restCallAuto;
	 private Usuario usuario;
		@PostConstruct
		public void init() {
			restCallAuto = new CallAutoComplete();

		}
	 
	 
	 private CallAutoComplete restAutoComplete; 
	 
	 
	 public  List<Usuario> getUsuariosLike(String type){
		 List<Usuario> retorno = new ArrayList<Usuario>();
		
			 try {
				 
				 retorno =restCallAuto.getListUsuario(caracteres);
					
				} catch (ExceptionErroCallRest  e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

				} catch (ExceptionValidation e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
					e.printStackTrace();
				}
		
		 
		 return retorno;
	 }


	public String getCaracteres() {
		return caracteres;
	}


	public void setCaracteres(String caracteres) {
		this.caracteres = caracteres;
	}


	public CallAutoComplete getRestCallAuto() {
		return restCallAuto;
	}


	public void setRestCallAuto(CallAutoComplete restCallAuto) {
		this.restCallAuto = restCallAuto;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public CallAutoComplete getRestAutoComplete() {
		return restAutoComplete;
	}


	public void setRestAutoComplete(CallAutoComplete restAutoComplete) {
		this.restAutoComplete = restAutoComplete;
	}
	 
	 
	 
}
