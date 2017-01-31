package br.com.petshow.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import br.com.petshow.enums.EnumFaseVida;
import br.com.petshow.enums.EnumSexo;
import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.model.Bairro;
import br.com.petshow.model.Cidade;
import br.com.petshow.model.Estado;
import br.com.petshow.model.Usuario;
import br.com.petshow.model.Venda;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.web.util.CallAnimalRest;
import br.com.petshow.web.util.CallVendaRest;
import br.com.petshow.web.util.ImagemUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class ConsultaAdocaoBean {
	
	private List<Adocao> adocoes;
	private CallAnimalRest restAnimal;
	@ManagedProperty(value="#{autoCompleteBean}")
    private AutoCompleteBean autoCompleteBean;

	private Estado estado;
	private Cidade cidade;
	private String animal;
	private String sexo;
	private String fase;
	
	
	@PostConstruct
	public void init() {
		this.adocoes = new ArrayList<Adocao>();
		restAnimal = new CallAnimalRest();
		
		getAdocaoBanco();
	}
	public ConsultaAdocaoBean (){
		super();
		System.out.println("criado o ConsultaClassificadoBean:"+ new Date().getTime());
	}

	
		
	
	public List<Adocao> getAdocaoBanco() {
		try {
			
			long idEstado=0;
			long idCidade=0;
			if(estado!=null){
				idEstado=estado.getId();
			}
			if(cidade!=null){
				idCidade=cidade.getId();
			}
			adocoes=restAnimal.getListAnimalAdocao(idEstado, idCidade, animal, fase, sexo);

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		
		
		return adocoes;
	}
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
	public String getAnimal() {
		return animal;
	}
	public void setAnimal(String animal) {
		this.animal = animal;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getFase() {
		return fase;
	}
	public void setFase(String fase) {
		this.fase = fase;
	}
	
}
