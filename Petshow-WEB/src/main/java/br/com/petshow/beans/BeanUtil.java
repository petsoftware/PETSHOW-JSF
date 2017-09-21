package br.com.petmooby.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

@ManagedBean
@RequestScoped
public class BeanUtil {
	
	
	public String getBooleanoPT(boolean bol){
		
		if(bol){
			return "SIM";
		}else{
			return "NÃO";
		}
	}
}
