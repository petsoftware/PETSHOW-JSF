package br.com.petshow.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Estatistica;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.web.util.CallEstatisticaRest;

@ManagedBean
@ViewScoped
public class MainPageBean {

	private Estatistica estatistica;
	public Estatistica getEstatistica() {
		return estatistica;
	}

	public void setEstatistica(Estatistica estatistica) {
		this.estatistica = estatistica;
	}

	private CallEstatisticaRest callEstatisticaRest;
	
	@PostConstruct
	public void init() {
		callEstatisticaRest = new CallEstatisticaRest();
		try {
			estatistica = callEstatisticaRest.getEstatisTicaDoUsuario(UsuarioRole.getUsuarioLogado());
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			estatistica = new Estatistica();
		}
	}
	
	
}
