package br.com.petshow.web.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessagesBeanUtil {
	
	public static void erroMessage(String erro, String details) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, erro+" "+details, null));
	}
	
	public static void erroMessage(String erro, String details, String clienteId) {
		FacesContext.getCurrentInstance().addMessage(clienteId, new FacesMessage(FacesMessage.SEVERITY_ERROR, erro+" "+details,null));
	}
	
	public static void erroMessage(String erro) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro do Sistema "+erro,null ));
	}
	
	public static void infor(String infor, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, infor+" "+detail,null));
	}
	
	public static void infor(String infor) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação do Sistema "+infor,null));
	}
	
	public static void alert(String alert) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ATENÇÃO! "+alert, null));
	}
	public static void exception(Exception e) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Falha grave: "+e.getMessage(),null));
	}
	
	public static void infor(String infor, String detail, String clientID) {
		FacesContext.getCurrentInstance().addMessage(clientID, new FacesMessage(FacesMessage.SEVERITY_INFO, infor+" "+detail,null));
	}
	
	public static void inforClient(String resumo, String infor,String clientID) {
		FacesContext.getCurrentInstance().addMessage(clientID, new FacesMessage(FacesMessage.SEVERITY_INFO, resumo+" "+infor,null));
	}
	

}
