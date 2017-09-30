package br.com.petshow.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.enums.EnumAchadoPerdido;
import br.com.petshow.enums.EnumFaseVida;
import br.com.petshow.enums.EnumSexo;
import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.enums.EnumUF;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Bairro;
import br.com.petshow.model.Cidade;
import br.com.petshow.model.Estado;
import br.com.petshow.model.Perdido;
import br.com.petshow.objects.query.PerdidoQuery;
import br.com.petshow.web.util.CallPerdidoRest;

@ManagedBean
@ViewScoped
public class ConsultaEncontradoBean extends SuperBean<Perdido>{
	
	private List<Perdido> perdidos;
	private CallPerdidoRest callPerdidoRest;
	@ManagedProperty(value="#{autoCompleteBean}")
    private AutoCompleteBean autoCompleteBean;
	private Estado estado;
	private EnumUF uf;
	private Cidade cidade;
	private Bairro bairro;
	private EnumTipoAnimal animal;
	private EnumAchadoPerdido tpPerdidoAchado;
	private EnumSexo sexo;
	private int totalRows = 0;
	
	@PostConstruct
	public void init() {
		this.perdidos 	= new ArrayList<Perdido>();
		callPerdidoRest = new CallPerdidoRest();
		obterAnimaisEncontrados();
	}

	public List<Perdido> getPerdidosBanco() {
		return perdidos;
	}

	public String obterAnimaisEncontrados() {
		
		PerdidoQuery query = new PerdidoQuery();
		try {
			
			query.setCidade(getCidade());
			query.setSexo(getSexo());
			query.setUf(getUf());
			query.setTpAnimal(getAnimal());
			perdidos=callPerdidoRest.getListAnimaisEncontrados(query);
			
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		return null;
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

	public EnumSexo getSexo() {
		return sexo;
	}

	public void setSexo(EnumSexo sexo) {
		this.sexo = sexo;
	}
	
	public String chamarTelaDeCadastroDePerdido() {
		if(getAuthenticationService().isAuthenticated()){
			return "anunciar-encontrado-site";
		}else{
			return "do-login";
		}
	}

	public int getTotalRows() {
		totalRows = 0;
		if(getPerdidosBanco() != null){
			totalRows = getPerdidosBanco().size();
		}
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public EnumAchadoPerdido getTpPerdidoAchado() {
		return tpPerdidoAchado;
	}

	public void setTpPerdidoAchado(EnumAchadoPerdido tpPerdidoAchado) {
		this.tpPerdidoAchado = tpPerdidoAchado;
	}

	public EnumUF getUf() {
		return uf;
	}

	public void setUf(EnumUF uf) {
		this.uf = uf;
	}

	public EnumTipoAnimal getAnimal() {
		return animal;
	}

	public void setAnimal(EnumTipoAnimal animal) {
		this.animal = animal;
	}
	
}
