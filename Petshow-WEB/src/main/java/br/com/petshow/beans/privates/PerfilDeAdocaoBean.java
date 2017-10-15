package br.com.petshow.beans.privates;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.petshow.beans.SuperBean;
import br.com.petshow.model.Adocao;

@ManagedBean
@ViewScoped
public class PerfilDeAdocaoBean extends SuperBean<Adocao> {

	@PostConstruct
	public void init() {
		
		getAdocoesNoPerfil();
		System.out.println("@PostConstruct"+this.getClass().getName());
	}

	private void getAdocoesNoPerfil() {
		// TODO Auto-generated method stub
		
	}
}
