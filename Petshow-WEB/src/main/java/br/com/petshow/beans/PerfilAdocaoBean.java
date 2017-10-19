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
	private boolean flNaoReceberNotificacao = false;
	private boolean flTemPerfil				= false;
	private String estado = "Não informado";
	private String cidade = "Não informado";
	private String sexo   = "Não informado";
	private String tipo   = "Não informado";
	private String fase   = "Não informado";
	private String porte  = "Não informado";
	
	@PostConstruct
	public void init() { 
		
		perfilAdocaoRest = new CallPerfilAdocaoRest();
		try {
			perfilAdocao	 = perfilAdocaoRest.getSingleResult(getUsuarioLogado());
			if(perfilAdocao != null){
				setFlNaoReceberNotificacao(!perfilAdocao.isFlAtivo());
				if(perfilAdocao.getId() > 0){
					setFlTemPerfil(true);
					preencherQuadroDoPerfil();
				}
			}
			System.out.println("@PostConstruct"+this.getClass().getName());
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			MessagesBeanUtil.erroMessage("erro ao tentar carregar o perfil de adoção da base da dados.", e.getMessage());
			perfilAdocao = null;
		}
		if(perfilAdocao == null){
			perfilAdocao 	 = new PerfilAdocao(getUsuarioLogado());
		}else if(perfilAdocao.getId() == 0){
			perfilAdocao 	 = new PerfilAdocao(getUsuarioLogado());
		}
		
	}
	
	public void preencherQuadroDoPerfil() {
		if(this.perfilAdocao != null){
			if(perfilAdocao.getCidade() != null){
				setCidade(perfilAdocao.getCidade().getNome());
			}
			if(perfilAdocao.getFaseVida() != null){
				setFase(perfilAdocao.getFaseVida().name());
			}
			if(perfilAdocao.getPorteAnimal() != null){
				setPorte(perfilAdocao.getPorteAnimal().name());
			}
			if(perfilAdocao.getSexo() != null){
				setSexo(perfilAdocao.getSexo().name());
			}
			if(perfilAdocao.getTipoAnimal() != null){
				setTipo(perfilAdocao.getTipoAnimal().name());
			}
			if(perfilAdocao.getUf() != null){
				setEstado(perfilAdocao.getUf().getLabel());
			}
		}
	}
	
	public void salvar() {
		try {
			perfilAdocao.setFlAtivo(!isFlNaoReceberNotificacao());
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

	public boolean isFlNaoReceberNotificacao() {
		return flNaoReceberNotificacao;
	}

	public void setFlNaoReceberNotificacao(boolean flNaoReceberNotificacao) {
		this.flNaoReceberNotificacao = flNaoReceberNotificacao;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public String getPorte() {
		return porte;
	}

	public void setPorte(String porte) {
		this.porte = porte;
	}



	public boolean isFlTemPerfil() {
		return flTemPerfil;
	}



	public void setFlTemPerfil(boolean flTemPerfil) {
		this.flTemPerfil = flTemPerfil;
	}
}
