package br.com.petshow.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.enums.EnumFaseVida;
import br.com.petshow.enums.EnumNotificationType;
import br.com.petshow.enums.EnumSexo;
import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.model.Cidade;
import br.com.petshow.model.Estado;
import br.com.petshow.objects.query.AdocaoQuery;
import br.com.petshow.web.util.CallAnimalRest;

@ManagedBean
@ViewScoped
public class ConsultaAdocaoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6287940244391936527L;
	private int totalRows = 0;
	private List<Adocao> adocoes;
	private List<Adocao> adocoesDisponiveis;
	private CallAnimalRest restAnimal;
	@ManagedProperty(value="#{autoCompleteBean}")
    private AutoCompleteBean autoCompleteBean;

	private Estado estado;
	private Cidade cidade;
	private EnumTipoAnimal animal;
	private EnumSexo sexo;
	private EnumFaseVida fase;
	
	
	
	
	@PostConstruct
	public void init() {
		this.adocoes = new ArrayList<Adocao>();
		restAnimal = new CallAnimalRest();
		obterAdocoesDisponiveis();
		setTotalRows(getAdocoesDisponiveis().size());
		//getAdocaoBanco();
	}
	public ConsultaAdocaoBean (){
		super();
	}

	public String chamarTelaDeCadastroDeAdocao() {
		return "anunciar-adocao-site";
	}
	
	public String obterAdocoesDisponiveis() {
		AdocaoQuery query = new AdocaoQuery();
		query.setFase(getFase() 		== null ? EnumFaseVida.FILHOTE 	  :getFase());
		query.setSexo(getSexo() 		== null ? EnumSexo.MACHO 		  : getSexo() );
		query.setTpAnimal(getAnimal()	== null ? EnumTipoAnimal.CACHORRO : getAnimal());
		try {
			setAdocoesDisponiveis(restAnimal.getListAnimalDisponiveisAdocao(query));
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			setAdocoesDisponiveis(new ArrayList<>());
		}
		return null;
	}	
	
//	public List<Adocao> getAdocaoBanco() {
//		try {
//			
//			long idEstado=0;
//			long idCidade=0;
//			if(estado!=null){
//				idEstado=estado.getId();
//			}
//			if(cidade!=null){
//				idCidade=cidade.getId();
//			}
//			adocoes=restAnimal.getListAnimalAdocao(idEstado, idCidade, animal, fase, sexo);
//
//		} catch (ExceptionErroCallRest  e) {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));
//
//		} catch (ExceptionValidation e) {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
//		} catch (Exception e) {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
//			e.printStackTrace();
//		}
//		
//		
//		return adocoes;
//	}
	public EnumTipoAnimal[] getTiposAnimais(){
		return EnumTipoAnimal.values();
	}
	
	public EnumFaseVida[] getFases(){
		return EnumFaseVida.values();
	}
	
	public EnumSexo[] getSexos(){
		return EnumSexo.values();
	}
	
	public List<Adocao> getAdocoes() {
		return adocoes;
	}
	public void setAdocoes(List<Adocao> adocoes) {
		this.adocoes = adocoes;
	}
	public CallAnimalRest getRestAnimal() {
		return restAnimal;
	}
	public void setRestAnimal(CallAnimalRest restAnimal) {
		this.restAnimal = restAnimal;
	}
	public AutoCompleteBean getAutoCompleteBean() {
		return autoCompleteBean;
	}
	public void setAutoCompleteBean(AutoCompleteBean autoCompleteBean) {
		this.autoCompleteBean = autoCompleteBean;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public List<Adocao> getAdocoesDisponiveis() {
		return adocoesDisponiveis;
	}
	public void setAdocoesDisponiveis(List<Adocao> adocoesDisponiveis) {
		this.adocoesDisponiveis = adocoesDisponiveis;
	}
	public EnumTipoAnimal getAnimal() {
		return animal;
	}
	public void setAnimal(EnumTipoAnimal animal) {
		this.animal = animal;
	}
	public EnumSexo getSexo() {
		return sexo;
	}
	public void setSexo(EnumSexo sexo) {
		this.sexo = sexo;
	}
	public EnumFaseVida getFase() {
		return fase;
	}
	public void setFase(EnumFaseVida fase) {
		this.fase = fase;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	
}
