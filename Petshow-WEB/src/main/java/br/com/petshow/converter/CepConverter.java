package br.com.petshow.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("cepConverter")
public class CepConverter  implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		String cep = value;
		if (value!= null && !value.equals(""))
			cep = value.replaceAll("\\-", "");

		return cep;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value)  {

		String cep = value.toString();

		if (cep != null ){
			for(int i =cep.length();i<8;++i){
				cep="0"+cep;
			}
			cep = cep.substring(0, 5)+"-"+cep.substring(5,8);
		}


		return cep;
	}


}
