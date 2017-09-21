package br.com.petshow.validador;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator ( value = "cepValidator")
public class CepValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		Pattern pattern = Pattern.compile("[0-9]{8}");
		Matcher m = pattern.matcher(value.toString());
		boolean valido=m.matches();
		    
		
		if (!valido) {
			
             FacesMessage message = new FacesMessage();
             message.setSeverity(FacesMessage.SEVERITY_ERROR);
             message.setSummary("CEP informado não possui 8 numeros!");
          
             throw new ValidatorException(message);
        }
		
	}
	
	  
}
