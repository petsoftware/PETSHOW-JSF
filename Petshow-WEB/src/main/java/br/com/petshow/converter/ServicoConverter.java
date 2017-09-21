package br.com.petmooby.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.springframework.web.jsf.FacesContextUtils;

import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Anuncio;
import br.com.petmooby.model.Servico;
import br.com.petmooby.role.ServicoRole;
import br.com.petmooby.util.PlaceHolderUtil;
import br.com.petmooby.web.util.RestUtilCall;

@FacesConverter(value ="servicoConverter")
public class ServicoConverter  implements Converter{


	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		ServicoRole role = FacesContextUtils.getWebApplicationContext(context).getBean(ServicoRole.class);
		Servico servico;
		try {
			if(!value.trim().equals("") && !value.trim().equals(PlaceHolderUtil.getSelServico()) ){
				servico = RestUtilCall.getEntity("servico/"+value,Servico.class);
							
			}else{
				servico=null;
			}
		} catch (NumberFormatException | ExceptionValidation |ExceptionErroCallRest e ) {
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
