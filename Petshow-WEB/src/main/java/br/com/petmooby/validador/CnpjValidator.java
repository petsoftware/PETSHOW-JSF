package br.com.petmooby.validador;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator ( value = "br.com.petshow.validator.CnpjValidator")
public class CnpjValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		 if (!validaCNPJ(String.valueOf(value))) {
			
             FacesMessage message = new FacesMessage();
             message.setSeverity(FacesMessage.SEVERITY_ERROR);
             message.setSummary("CNPJ informado não é valido!");
          
             throw new ValidatorException(message);
        }
		
	}
	
	  public static boolean validaCNPJ(String cnpj) {
          if(cnpj == null || cnpj.length() != 14)
               return false;
 
          try {
               Long.parseLong(cnpj);
          } catch (NumberFormatException e) { // CNPJ não possui somente números
               return false;
          }
 
          int soma = 0;
          String cnpj_calc = cnpj.substring(0, 12);
 
          char chr_cnpj[] = cnpj.toCharArray();
          for(int i = 0; i < 4; i++)
               if(chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                    soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
 
         for(int i = 0; i < 8; i++)
              if(chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)
                    soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
 
         int dig = 11 - soma % 11;
         cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();
         soma = 0;
         for(int i = 0; i < 5; i++)
              if(chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                   soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
 
         for(int i = 0; i < 8; i++)
              if(chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)
                   soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
 
         dig = 11 - soma % 11;
         cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();
 
         return cnpj.equals(cnpj_calc);
     }

}
