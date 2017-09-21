package br.com.petshow.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.springframework.web.jsf.FacesContextUtils;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Animal;
import br.com.petshow.model.Bairro;
import br.com.petshow.role.AnimalRole;
import br.com.petshow.web.util.RestUtilCall;

@FacesConverter("animalConverter")
public class AnimalConverter  implements Converter{
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		
		Animal animal;
		try {
			animal =  RestUtilCall.getEntity("animal/"+value,Animal.class);
		} catch (NumberFormatException | ExceptionValidation  |ExceptionErroCallRest e) {
			 throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Animal."));
		}
		return animal;
	}

	
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
            return String.valueOf(((Animal) value).getNome());
        }
        else {
            return null;
        }

	}
}
