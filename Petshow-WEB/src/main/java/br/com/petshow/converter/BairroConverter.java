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
import br.com.petshow.model.Bairro;
import br.com.petshow.model.Cidade;
import br.com.petshow.role.BairroRole;
import br.com.petshow.util.PlaceHolderUtil;
import br.com.petshow.web.util.RestUtilCall;

@FacesConverter("bairroConverter")
public class BairroConverter  implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		
		Bairro bairro;
		try {
			
			
			if(!value.trim().equals("") && !value.trim().equals(PlaceHolderUtil.getSelBairro()) ){
				bairro = RestUtilCall.getEntity("endereco/consulta/bairro/"+value,Bairro.class);
			}else{
				bairro=null;
			}
		} catch (NumberFormatException | ExceptionValidation |ExceptionErroCallRest e) {
			 throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é um Bairro valido."));
		}
		return bairro;
		
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null && !value.toString().trim().equals("")) {
            return String.valueOf(((Bairro) value).getId());
        }
        else {
            return null;
        }
	}

	
	
	
	
	
	
}
