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
import br.com.petmooby.model.Estado;
import br.com.petmooby.role.EstadoRole;
import br.com.petmooby.util.PlaceHolderUtil;
import br.com.petmooby.web.util.RestUtilCall;

@FacesConverter(value ="estadoConverter")
public class EstadoConverter  implements Converter{


	private EstadoRole estadoRole;
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		
		Estado estado;
		try {
			if(!value.trim().equals("") && !value.trim().equals(PlaceHolderUtil.getSelEstado()) ){
				estado = RestUtilCall.getEntity("endereco/consulta/estado/"+value,Estado.class);
			}else{
				estado=null;
			}
		} catch (NumberFormatException | ExceptionValidation | ExceptionErroCallRest e) {
			 throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é um Estado valido."));
		}
		return estado;
		
		
	}

	
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null && !value.toString().trim().equals("")) {
            return String.valueOf(((Estado) value).getId());
        }
        else {
            return null;
        }
	}


	public EstadoRole getEstadoRole() {
		return estadoRole;
	}


	public void setEstadoRole(EstadoRole estadoRole) {
		this.estadoRole = estadoRole;
	}


	
	
	
	
	
	
}
