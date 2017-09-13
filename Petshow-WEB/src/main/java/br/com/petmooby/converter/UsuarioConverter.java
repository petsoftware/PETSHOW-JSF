package br.com.petmooby.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Usuario;
import br.com.petmooby.web.util.RestUtilCall;

@FacesConverter("usuarioConverter")
public class UsuarioConverter implements Converter{

	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		
		Usuario usuario;
		try {
			usuario =   RestUtilCall.getEntity("usuario/"+value,Usuario.class);;
		} catch (NumberFormatException | ExceptionValidation |ExceptionErroCallRest e) {
			 throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid user."));
		}
		return usuario;
	}

	
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
            return String.valueOf(((Usuario) value).getId());
        }
        else {
            return null;
        }

	}
	
}
