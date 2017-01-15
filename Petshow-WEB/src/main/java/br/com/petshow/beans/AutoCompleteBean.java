package br.com.petshow.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Bairro;
import br.com.petshow.model.Cidade;
import br.com.petshow.model.Estado;
import br.com.petshow.model.Usuario;
import br.com.petshow.util.EnderecoUtil;
import br.com.petshow.util.StringUtil;
import br.com.petshow.web.util.CallAutoComplete;
import br.com.petshow.web.util.CallEnderecoRest;

@ManagedBean
@ViewScoped
public class AutoCompleteBean {
	private String caracteres;
	private CallAutoComplete restCallAuto;
	private Usuario usuario;
	

	private String estadoUltimaConsulta;

	private CallEnderecoRest restCallEndereco;
	private List<Estado> estados;
	private List<Cidade> cidades;
	private List<Bairro> bairros;




	


	@PostConstruct
	public void init() {
		System.out.println("chamou bean autocomplete");
		restCallAuto = new CallAutoComplete();
		restCallEndereco = new CallEnderecoRest();
		cidades = new ArrayList<Cidade>();
		bairros = new ArrayList<Bairro>();
		estados = new ArrayList<Estado>();
		consultaEstados();
	}


	private CallAutoComplete restAutoComplete; 


	public  List<Usuario> getUsuariosLike(String type){
		List<Usuario> retorno = new ArrayList<Usuario>();

		try {

			retorno =restCallAuto.getListUsuario(caracteres);

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}


		return retorno;
	}


	public List<String> completeTextEstado(String query) {
		List<String> estados = EnderecoUtil.getEstados();

		List<String> retorno =new ArrayList();

		for(String estado :estados){
			String estadoParaTeste=StringUtil.removerAcentos(estado).toUpperCase();
			String queryParaTeste=StringUtil.removerAcentos(query).toUpperCase();
			if(estadoParaTeste.contains(queryParaTeste)){
				retorno.add(estado);
			}
		}


		return retorno;
	}
	
	public void onEstadoChange(String id) {

		System.out.println("chamou estado trocado");


	}
	

	public void consultaCidades() {
		try {
			this.cidades=restCallEndereco.getListCidade(EnderecoUtil.getSigla(estadoUltimaConsulta));
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}

	}
	
	public void consultaEstados() {
		try {
			this.estados=restCallEndereco.getListEstado();
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}

	}


	public void consultaBairros(AjaxBehaviorEvent event) {
		try {
			
			
			String a = "";
			SelectEvent selectEvent=(SelectEvent)event;
			
			Object dueDate =   selectEvent.getObject();
			
			this.bairros=restCallEndereco.getListBairro("");
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}

	}

	public String getCaracteres() {
		return caracteres;
	}


	public void setCaracteres(String caracteres) {
		this.caracteres = caracteres;
	}


	public CallAutoComplete getRestCallAuto() {
		return restCallAuto;
	}


	public void setRestCallAuto(CallAutoComplete restCallAuto) {
		this.restCallAuto = restCallAuto;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public CallAutoComplete getRestAutoComplete() {
		return restAutoComplete;
	}


	public void setRestAutoComplete(CallAutoComplete restAutoComplete) {
		this.restAutoComplete = restAutoComplete;
	}


	public List<Cidade> getCidades() {
		return cidades;
	}


	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}


	public List<Bairro> getBairros() {
		return bairros;
	}


	public void setBairros(List<Bairro> bairros) {
		this.bairros = bairros;
	}

	public List<Estado> getEstado() {
		return estados;
	}


	public void setEstado(List<Estado> estados) {
		this.estados = estados;
	}


	public List<Estado> getEstados() {
		return estados;
	}


	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

}
