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
import br.com.petshow.model.Servico;
import br.com.petshow.role.EstadoRole;
import br.com.petshow.role.ServicoRole;
import br.com.petshow.util.PlaceHolderUtil;

@FacesConverter(value ="servicoConverter")
public class ServicoConverter  implements Converter{


	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		ServicoRole role = new ClassPathXmlApplicationContext("spring-context.xml").getBean(ServicoRole.class);
		Servico servico;
		try {
			if(!value.trim().equals("") && !value.trim().equals(PlaceHolderUtil.getSelServico()) ){
				servico = role.find(Long.parseLong(value));
			}else{
				servico=null;
			}
		} catch (NumberFormatException | ExceptionValidation e) {
			 throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é um Serviço valido."));
		}
		return servico;
		
		
	}

	
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null && !value.toString().trim().equals("")) {
            return String.valueOf(((Servico) value).getId());
        }
        else {
            return null;
        }
	}

	
	
	
	
	
	
}
