package br.com.petshow.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.petshow.beans.PlaceHolderBean;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Estado;
import br.com.petshow.role.EstadoRole;
import br.com.petshow.util.PlaceHolderUtil;

@FacesConverter(value ="estadoConverter")
public class EstadoConverter  implements Converter{


	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		EstadoRole role = new ClassPathXmlApplicationContext("spring-context.xml").getBean(EstadoRole.class);
		Estado estado;
		try {
			if(!value.trim().equals("") && !value.trim().equals(PlaceHolderUtil.getSelEstado()) ){
				estado = role.find(Long.parseLong(value));
			}else{
				estado=null;
			}
		} catch (NumberFormatException | ExceptionValidation e) {
			 throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é um Estado valido."));
		}
		return estado;
		
		
	}

	
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null && !value.toString().trim().equals("")) {
            return String.valueOf(((Estado) value).getId());
        }
        else {
            return null;
        }
	}

	
	
	
	
	
	
}
