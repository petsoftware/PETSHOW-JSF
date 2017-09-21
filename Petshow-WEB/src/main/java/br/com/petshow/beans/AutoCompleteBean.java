package br.com.petmooby.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Bairro;
import br.com.petmooby.model.Cidade;
import br.com.petmooby.model.Estado;
import br.com.petmooby.model.Usuario;
import br.com.petmooby.util.EnderecoUtil;
import br.com.petmooby.util.StringUtil;
import br.com.petmooby.web.util.CallAutoComplete;
import br.com.petmooby.web.util.CallEnderecoRest;

@ManagedBean
@ViewScoped
public class AutoCompleteBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6337290547223375763L;
	private String caracteres;
	private CallAutoComplete restCallAuto;
	private Usuario usuario;
	

	public String getEstadoUltimaConsulta() {
		return estadoUltimaConsulta;
	}





	public void setEstadoUltimaConsulta(String estadoUltimaConsulta) {
		this.estadoUltimaConsulta = estadoUltimaConsulta;
	}





	public CallEnderecoRest getRestCallEndereco() {
		return restCallEndereco;
	}





	public void setRestCallEndereco(CallEnderecoRest restCallEndereco) {
		this.restCallEndereco = restCallEndereco;
	}


	private String estadoUltimaConsulta;

	private CallEnderecoRest restCallEndereco;
	private List<Estado> estados;
	private List<Cidade> cidades;
	private List<Bairro> bairros;

	public AutoCompleteBean (){
		super();
	}


	


	@PostConstruct
	public void init() {
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
	
	public void onEstadoChange(Estado estado) {
		consultaCidades(estado);

	}
	
	public void onCidadeChange(Cidade cidade) {
		consultaBairros(cidade);
	}
	

	public void consultaCidades(Estado estado) {
		try {
			if(estado!=null){
				this.cidades=restCallEndereco.getListCidadeIDEstado(estado.getId()+"");
			}else{
				cidades = new ArrayList<Cidade>();
				bairros = new ArrayList<Bairro>();
				
			}
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


	public void consultaBairros(Cidade cidade) {
		try {
			if(cidade !=null){
				this.bairros=restCallEndereco.getListBairro(cidade.getId()+"");
			}else{
				this.bairros= new ArrayList<Bairro>();
			}
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
