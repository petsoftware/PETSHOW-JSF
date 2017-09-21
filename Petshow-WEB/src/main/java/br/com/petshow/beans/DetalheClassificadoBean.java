package br.com.petmooby.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Anuncio;
import br.com.petmooby.model.Venda;
import br.com.petmooby.util.FormatacaoUtil;
import br.com.petmooby.util.ValidationUtil;
import br.com.petmooby.web.util.CallVendaRest;
import br.com.petmooby.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class DetalheClassificadoBean {

	private Venda venda;
	
	private String id;
	
	private String nome;
	
	private String email;
	
	private long telefone;
	
	private String mensagem;
	
	@PostConstruct
	public void init() {
		this.venda= new Venda();
	
	
		getVendaBanco();
	}
	
	public void getVendaBanco(){
		try {
			if(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap() .get("id") !=null){
				id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap() .get("id");
			}
			venda = RestUtilCall.getEntity("venda/"+id,Venda.class);

		} catch (ExceptionErroCallRest  e) {
			// erro: nao está mostrando a mensavem
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		
		
		
	}
	
	public void enviar(){
		try {
			
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("destinatario", venda.getUsuario().getEmail());
			map.put("mensagem", mensagem);
			map.put("telefone", telefone+"");
			map.put("email", email);
			
			RestUtilCall.post( map, "email/enviar");

		} catch (ExceptionErroCallRest  e) {
			// erro: nao está mostrando a mensavem
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}

	public String getTelResFormatado(){
		if(venda ==null || venda.getDddResidencial() ==0 || venda.getTelefoneResidencial()==0){
			return "Não Informado";
			
		}else{
			return FormatacaoUtil.telefoneComDDD(venda.getDddResidencial(), venda.getTelefoneResidencial(), true);
		}
	}
	public String getTelCelFormatado(){
		if(venda ==null || venda.getDddCelular() ==0 || venda.getTelefoneCelular()==0){
			return "Não Informad!";
		}else{
			return FormatacaoUtil.telefoneComDDD(venda.getDddCelular(), venda.getTelefoneCelular(), false);
			
		}
	}
	public String getTelCelVendedor(){
		if(venda ==null || venda.getUsuario().getDdd() ==0 || venda.getUsuario().getTelefone()==0){
			return "Não Informado";
		}else{
			return FormatacaoUtil.telefoneComDDD(venda.getUsuario().getDdd(), venda.getUsuario().getTelefone(), false);
			
		}
	}
	public String getDataAnunciada(){
		if(venda !=null ){
			return FormatacaoUtil.dataPorExtenso(venda.getDataCadastro());
		}else{
			return "Não Informado";
		}
	}
	

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	public Venda getVenda() {
		return venda;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getTelefone() {
		return telefone;
	}

	public void setTelefone(long telefone) {
		this.telefone = telefone;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getEstado() {
		if(venda.getEstado() !=null){
			return venda.getEstado().getNome();
		}else{
			return "Não Informado";
		}
		
	}
	public String getCidade() {
		if(venda.getCidade() !=null){
			return venda.getCidade().getNome();
		}else{
			return "Não Informado";
		}
	}
	public String getBairro() {
		if(venda.getBairro() !=null){
			return venda.getBairro().getNome();
		}else{
			return "Não Informado";
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
