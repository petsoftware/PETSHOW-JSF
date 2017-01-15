package br.com.petshow.validador;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator ( value = "numeroValidator")
public class NumeroValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		 
		try{
			
			String valor=value.toString().replace(",","").replace(".", "");
			if(!valor.trim().equals("")){
				Double.parseDouble(valor);
			}
		}catch (NumberFormatException e){
			 FacesMessage message = new FacesMessage();
             message.setSeverity(FacesMessage.SEVERITY_ERROR);
             message.setSummary("Campo aceita somente numeros!");
          
             throw new ValidatorException(message);
		}
		
		
		
		
	}
	
}
