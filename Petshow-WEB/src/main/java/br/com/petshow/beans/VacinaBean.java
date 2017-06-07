package br.com.petshow.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Usuario;
import br.com.petshow.model.Vermifugo;
import br.com.petshow.web.util.MessagesBeanUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
public class VacinaBean extends SuperBean {

	public Vermifugo vermifugo;
	public Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@PostConstruct
	public void init() {
		vermifugo = new Vermifugo();
	}
	
	public Vermifugo getVermifugo() {
		return vermifugo;
	}

	public void setVermifugo(Vermifugo vermifugo) {
		this.vermifugo = vermifugo;
	}
	
	public void salvar() {
		try {
			RestUtilCall.postEntity(vermifugo, "url", Vermifugo.class);
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			// TODO Auto-generated catch block
			MessagesBeanUtil.erroMessage("Erro ao tentar salvar o registro", e.getMessage());
		}
	}
	
}
