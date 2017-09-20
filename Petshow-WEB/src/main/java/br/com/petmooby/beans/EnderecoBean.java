package br.com.petmooby.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.petmooby.enums.EnumUF;
import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Cidade;
import br.com.petmooby.model.Endereco;
import br.com.petmooby.web.util.CallEnderecoRest;

@ManagedBean
@ViewScoped
public class EnderecoBean {
	
	private CallEnderecoRest callEnderecoRest;
	private EnumUF uf;
	private List<Cidade> cidades;
	private Endereco endereco;

	
	@PostConstruct
	public void ini() {
		callEnderecoRest = new CallEnderecoRest();
		
	}
	public EnumUF[] getUfs(){
		return EnumUF.values();
	}

	public List<Cidade> findCidadesByUF(EnumUF uf) {
		try {
			List<Cidade> cidades = callEnderecoRest.getListCidadeUF(uf);
			setCidades(cidades);
			return cidades;
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			return new ArrayList<>();
		}
	}
	public EnumUF getUf() {
		return uf;
	}
	public void setUf(EnumUF uf) {
		this.uf = uf;
	}
	public List<Cidade> getCidades() {
		return cidades;
	}
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public void preencherComboCidade(EnumUF uf) {
		if(uf != null && this.cidades == null){
			findCidadesByUF(uf);
		}
	}
	

}
