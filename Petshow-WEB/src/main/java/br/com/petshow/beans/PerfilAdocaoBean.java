package br.com.petshow.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.petshow.enums.EnumFaseVida;
import br.com.petshow.enums.EnumPorteAnimal;
import br.com.petshow.enums.EnumSexo;
import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.PerfilAdocao;
import br.com.petshow.web.util.CallPerfilAdocaoRest;
import br.com.petshow.web.util.MessagesBeanUtil;

@ManagedBean
@ViewScoped
public class PerfilAdocaoBean extends SuperBean<PerfilAdocao> {

	
	private PerfilAdocao perfilAdocao;
	private CallPerfilAdocaoRest perfilAdocaoRest;
	
	@PostConstruct
	public void init() { 
		
		perfilAdocaoRest = new CallPerfilAdocaoRest();
		try {
			perfilAdocao	 = perfilAdocaoRest.getSingleResult(getUsuarioLogado());
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			MessagesBeanUtil.erroMessage("erro ao tentar carregar o perfil de adoção da base da dados.", e.getMessage());
			perfilAdocao = null;
		}
		if(perfilAdocao == null){
			perfilAdocao 	 = new PerfilAdocao(getUsuarioLogado());
		}
		
	}
	
	public void salvar() {
		try {
			perfilAdocao = perfilAdocaoRest.salvarNaAPIRest(getPerfilAdocao());
		} catch (Exception e) {
			MessagesBeanUtil.erroMessage("Erro ao tentar salvar: ", e.getMessage());
		}
	}
	
	public EnumFaseVida[] getFasesVida(){
		return EnumFaseVida.values();
	}
	
	public EnumTipoAnimal[] getTiposDeAnimal(){
		return EnumTipoAnimal.values();
	}

	public EnumPorteAnimal[] getPortesDosAnimais(){
		return EnumPorteAnimal.values();
	}
	public PerfilAdocao getPerfilAdocao() {
		return perfilAdocao;
	}

	public void setPerfilAdocao(PerfilAdocao perfilAdocao) {
		this.perfilAdocao = perfilAdocao;
	}
	
	public EnumSexo[] getSexoAnimal() {
		return EnumSexo.values();
	}
}
