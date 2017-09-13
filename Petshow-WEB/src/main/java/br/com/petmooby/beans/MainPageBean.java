package br.com.petmooby.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Estatistica;
import br.com.petmooby.role.UsuarioRole;
import br.com.petmooby.web.util.CallEstatisticaRest;

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
