package br.com.petshow.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Racas;
import br.com.petshow.web.util.RestUtilCall;

@FacesConverter("racasConverter")
public class RacasConverter  implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Racas raca;
		try {
			if(value != null && !value.trim().equals("")){
				raca = RestUtilCall.getEntity("raca/"+value,Racas.class);
			}else{
				raca=null;
			}
		} catch (NumberFormatException | ExceptionValidation |ExceptionErroCallRest e) {
			 throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é uma Cidade valida."));
		} 
		return raca;		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null && !value.toString().trim().equals("")) {
            return String.valueOf(((Racas) value).getId()   );
        }
        else {
            return null;
        }
	}


	
}
