package br.com.petmooby.beans;

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

import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Bairro;
import br.com.petmooby.model.Cidade;
import br.com.petmooby.model.Estado;
import br.com.petmooby.model.Usuario;
import br.com.petmooby.model.Venda;
import br.com.petmooby.role.UsuarioRole;
import br.com.petmooby.web.util.CallVendaRest;
import br.com.petmooby.web.util.ImagemUtil;
import br.com.petmooby.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class ConsultaClassificadoBean {
	
	private List<Venda> vendas;
	private CallVendaRest restVenda;
	@ManagedProperty(value="#{autoCompleteBean}")
    private AutoCompleteBean autoCompleteBean;
	private String palavraChave;
	private Estado estado;
	private Cidade cidade;
	
	
	@PostConstruct
	public void init() {
		this.vendas = new ArrayList<Venda>();
		restVenda = new CallVendaRest();
		
		getVendasBanco();
	}
	public ConsultaClassificadoBean (){
		super();
	}

	
		
	public String redirectDetalhe(long id){
		return "classificado-detalhe?id="+id;
	}
	
	public void selecionar(Venda venda){
	
		

	}
	public List<Venda> getVendasBanco() {
		try {
			long idEstado=0;
			long idCidade=0;
			if(estado!=null){
				idEstado=estado.getId();
			}
			if(cidade!=null){
				idCidade=cidade.getId();
			}
			vendas=restVenda.getListVenda(palavraChave, idCidade, idEstado);

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		
		
		return vendas;
	}


	
	public List<Venda> getVendas() {
		return vendas;
	}


	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}


	public CallVendaRest getRestVenda() {
		return restVenda;
	}


	public void setRestVenda(CallVendaRest restVenda) {
		this.restVenda = restVenda;
	}


	

	public AutoCompleteBean getAutoCompleteBean() {
		return autoCompleteBean;
	}


	public void setAutoCompleteBean(AutoCompleteBean autoCompleteBean) {
		this.autoCompleteBean = autoCompleteBean;
	}





	public String getPalavraChave() {
		return palavraChave;
	}





	public void setPalavraChave(String palavraChave) {
		this.palavraChave = palavraChave;
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
	
}