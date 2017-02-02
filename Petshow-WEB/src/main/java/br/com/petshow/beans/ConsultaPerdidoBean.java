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
import br.com.petshow.model.Perdido;
import br.com.petshow.model.Usuario;
import br.com.petshow.model.Venda;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.web.util.CallAnimalRest;
import br.com.petshow.web.util.CallVendaRest;
import br.com.petshow.web.util.ImagemUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class ConsultaPerdidoBean {
	
	private List<Perdido> perdidos;
	private CallAnimalRest restAnimal;
	@ManagedProperty(value="#{autoCompleteBean}")
    private AutoCompleteBean autoCompleteBean;

	private Estado estado;
	private Cidade cidade;
	private Bairro bairro;
	private String animal;
	private String tpPerdidoAchado;
	
	
	
	@PostConstruct
	public void init() {
		this.perdidos = new ArrayList<Perdido>();
		restAnimal = new CallAnimalRest();
		
		getPerdidosBanco();
	}
	

	
		
	
	public List<Perdido> getPerdidosBanco() {
		try {
			
			long idEstado=0;
			long idCidade=0;
			long idBairro=0;
			if(estado!=null){
				idEstado=estado.getId();
			}
			if(cidade!=null){
				idCidade=cidade.getId();
			}
			if(bairro!=null){
				idBairro=bairro.getId();
			}
			perdidos=restAnimal.getListAnimalPerdidoAchado(idEstado, idCidade,idBairro ,animal,tpPerdidoAchado);

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		
		
		return perdidos;
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





	public List<Perdido> getPerdidos() {
		return perdidos;
	}





	public void setPerdidos(List<Perdido> perdidos) {
		this.perdidos = perdidos;
	}





	public Bairro getBairro() {
		return bairro;
	}





	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}





	public String getTpPerdidoAchado() {
		return tpPerdidoAchado;
	}





	public void setTpPerdidoAchado(String tpPerdidoAchado) {
		this.tpPerdidoAchado = tpPerdidoAchado;
	}
	
	public String getEstado(Estado estado) {
		if(estado !=null){
			return estado.getNome();
		}else{
			return "Não Informado";
		}
		
	}
	public String getCidade(Cidade cidade) {
		if(cidade !=null){
			return cidade.getNome();
		}else{
			return "Não Informado";
		}
	}
	public String getBairro(Bairro bairro) {
		if(bairro !=null){
			return bairro.getNome();
		}else{
			return "Não Informado";
		}
	}
	
}
