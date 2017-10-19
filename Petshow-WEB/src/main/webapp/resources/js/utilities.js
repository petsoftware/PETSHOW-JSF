/**
 * 
 */

jQuery(function($){
			
    		
    	   $(".onlyTelephone").mask("(99) - 999999999");
    	   
    	   $( ".email-validation" ).blur(function validarEmail(){
			
    		      var email = $(".email-validation").val();
    		      if(email != "")
    		      {
    		    	
    		    		var filtro = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
      					if(filtro.test(email) == false){
      						 alert("Formato de e-mail inválido!")
    		        		 
    		        	 	 $(".email-validation").val("")
    		        	 	 
    		        	 	 setTimeout(function() { $('.email-validation').focus() }, 55);
    		        	 	 $(".email-validation").focus()
      					}
    		      }
    		 
    		})
    	

    	});