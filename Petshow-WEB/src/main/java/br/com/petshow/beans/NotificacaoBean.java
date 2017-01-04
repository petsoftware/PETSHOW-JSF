package br.com.petshow.beans;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.Part;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Animal;
import br.com.petshow.model.Anuncio;
import br.com.petshow.model.Servico;
import br.com.petshow.model.Usuario;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.web.util.CallAnuncioRest;
import br.com.petshow.web.util.CallRestAnimal;
import br.com.petshow.web.util.ImagemUtil;
import br.com.petshow.web.util.RestUtilCall;




@ManagedBean
@ViewScoped
public class NotificacaoBean  {

	private String autoCompleteUsuario;
	private Usuario usuario;
	private CallRestAnimal callRestAnimal;
	private List<Animal> animais;
	private Animal animalSelecionado;
	private String usuarioSel;
	private Servico servico;
	private boolean mostrarComboBoxAnimal;
	private boolean mostrarLabelAnimal;


	public void setMostrarComboBoxAnimal(boolean mostrarComboBoxAnimal) {
		this.mostrarComboBoxAnimal = mostrarComboBoxAnimal;
	}

	public void setMostrarLabelAnimal(boolean mostrarLabelAnimal) {
		this.mostrarLabelAnimal = mostrarLabelAnimal;
	}

	@PostConstruct
	public void init() {
		this.autoCompleteUsuario="";
		usuario = new Usuario();
		callRestAnimal= new CallRestAnimal();
		//teste
		//usuarioSel="1";
		//getAnimaisBanco();
	}

	public void eventoConsultaAnimais () {
	
		getAnimaisBanco("1");
	 
	 
	 }


	public void getAnimaisBanco(String  id){
		try {
			animais=callRestAnimal.getListAnimal(Long.parseLong(id));
			if(animais.size()==1){
				animalSelecionado=animais.get(0);

			}

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}

	}

	public void enviarNotificacao(){
		try {
			HashMap<String, String> parametros = new HashMap<String,String>();
			parametros.put("idUsuario", "1");
			parametros.put("idAnimal", "1");
			parametros.put("idServico", "1");
			RestUtilCall.post(parametros, "notificacao/entrega"); 

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}

	}

	public String getAutoCompleteUsuario() {
		return autoCompleteUsuario;
	}

	public void setAutoCompleteUsuario(String autoCompleteUsuario) {
		this.autoCompleteUsuario = autoCompleteUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public CallRestAnimal getCallRestAnimal() {
		return callRestAnimal;
	}

	public void setCallRestAnimal(CallRestAnimal callRestAnimal) {
		this.callRestAnimal = callRestAnimal;
	}

	public Animal getAnimalSelecionado() {
		return animalSelecionado;
	}

	public void setAnimalSelecionado(Animal animalSelecionado) {
		this.animalSelecionado = animalSelecionado;
	}

	public String getUsuarioSel() {
		return usuarioSel;
	}

	public void setUsuarioSel(String usuarioSel) {
		this.usuarioSel = usuarioSel;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}

	public List<Animal> getAnimais() {
		return animais;
	}
   

	public boolean isMostrarComboBoxAnimal() {
		if(this.animais == null){
    		return false;
    	}
    	return this.animais.size()>1;
	}

	public boolean isMostrarLabelAnimal() {
		if(this.animais == null){
    		return false;
    	}
    	return this.animais.size()==1;
	}
    
}
