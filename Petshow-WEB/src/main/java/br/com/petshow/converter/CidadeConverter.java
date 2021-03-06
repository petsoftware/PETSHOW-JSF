package br.com.petshow.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Cidade;
import br.com.petshow.util.PlaceHolderUtil;
import br.com.petshow.web.util.RestUtilCall;

@FacesConverter("cidadeConverter")
public class CidadeConverter  implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		Cidade cidade;
		try {
			if(!value.trim().equals("") && !value.trim().equals(PlaceHolderUtil.getSelCidade())){
				cidade = RestUtilCall.getEntity("endereco/consulta/cidade/"+value,Cidade.class);
			}else{
				cidade=null;
			}
			
		} catch (NumberFormatException | ExceptionValidation |ExceptionErroCallRest e) {
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
