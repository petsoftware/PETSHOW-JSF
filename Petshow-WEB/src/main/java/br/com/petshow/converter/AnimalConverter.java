package br.com.petshow.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Animal;
import br.com.petshow.model.Usuario;
import br.com.petshow.role.AnimalRole;
import br.com.petshow.role.UsuarioRole;

@FacesConverter("animalConverter")
public class AnimalConverter  implements Converter{
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		AnimalRole role =new ClassPathXmlApplicationContext("spring-context.xml").getBean(AnimalRole.class);
		Animal animal;
		try {
			animal = role.find(Long.parseLong(value));
		} catch (NumberFormatException | ExceptionValidation e) {
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
