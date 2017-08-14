package br.com.petshow.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.petshow.model.PerfilAdocao;

@ManagedBean
@ViewScoped
public class PerfilAdocaoBean extends SuperBean<PerfilAdocao> {

	
	private PerfilAdocao perfilAdocao;
	
	@PostConstruct
	public void init() {
		perfilAdocao = new PerfilAdocao(getUsuarioLogado());
	}
	
	public void salvar() {
		
	}
	
}
