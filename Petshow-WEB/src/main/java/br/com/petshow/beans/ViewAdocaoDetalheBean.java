package br.com.petshow.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
@RequestScoped
public class ViewAdocaoDetalheBean {
	
	private List<String> fotos;
	private List<String> idAdocao;
	private Adocao adocao;
	
	@PostConstruct
	public void init() {
		try {
			if(getIdAdocao() != null && getIdAdocao().size() > 0){
				String id = getIdAdocao().get(0);
				adocao = RestUtilCall.getEntity("adocao/"+id, Adocao.class);
			}
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			adocao = new Adocao();
		}
	}

	public List<String> getFotos() {
		try {
			if(getIdAdocao() != null && getIdAdocao().size() > 0){
				String id = getIdAdocao().get(0);
				adocao = RestUtilCall.getEntity("adocao/"+id, Adocao.class);
			}else{
				adocao = new Adocao();
			}
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			adocao = new Adocao();
		}
		return adocao.getFotos();
	}

	public void setFotos(List<String> fotos) {
		this.fotos = fotos;
	}


	public Adocao getAdocao() {
		return adocao;
	}

	public void setAdocao(Adocao adocao) {
		this.adocao = adocao;
	}

	public List<String> getIdAdocao() {
		return idAdocao;
	}

	public void setIdAdocao(List<String> idAdocao) {
		this.idAdocao = idAdocao;
	}

}
