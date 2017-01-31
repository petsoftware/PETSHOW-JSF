package br.com.petshow.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Cidade;

import br.com.petshow.role.CidadeRole;
import br.com.petshow.util.PlaceHolderUtil;

@FacesConverter("cidadeConverter")
public class CidadeConverter  implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		CidadeRole role = new ClassPathXmlApplicationContext("spring-context.xml").getBean(CidadeRole.class);
		Cidade cidade;
		try {
			if(!value.trim().equals("") && !value.trim().equals(PlaceHolderUtil.getSelCidade())){
				cidade = role.find(Long.parseLong(value));	
			}else{
				cidade=null;
			}
			
		} catch (NumberFormatException | ExceptionValidation e) {
			 throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é uma Cidade valida."));
		}
		return cidade;
		
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null && !value.toString().trim().equals("")) {
            return String.valueOf(((Cidade) value).getId()   );
        }
        else {
            return null;
        }
	}

	
	
	
	
	
	
}
