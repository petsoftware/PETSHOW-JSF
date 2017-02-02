package br.com.petshow.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.model.Anuncio;
import br.com.petshow.model.Perdido;
import br.com.petshow.model.Venda;
import br.com.petshow.util.FormatacaoUtil;
import br.com.petshow.util.ValidationUtil;
import br.com.petshow.web.util.CallVendaRest;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class DetalhePerdidoBean {

	private Perdido perdido;
		
	private String nome;
	
	
	
	private String email;
	
	private long telefone;
	
	private String mensagem;
	
	private String id;
	
	@PostConstruct
	public void init() {
		this.perdido= new Perdido();
		 id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap() .get("id");
		getAdocaoBanco();
	
	}
	
	public void getAdocaoBanco(){
		try {
			
			
			perdido = RestUtilCall.getEntity("animal/perdido/"+id,Perdido.class);

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
			map.put("idPerdido", id );
			map.put("mensagem", mensagem);
			map.put("telefone", telefone+"");
			map.put("email", email+"");
			
			RestUtilCall.post( map, "notificacao/perdido/msganuncio");

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
		if(perdido ==null || perdido.getDddResidencial() ==null || perdido.getTelefoneResidencial()==null){
			return "Não Informado";
			
		}else{
			return FormatacaoUtil.telefoneComDDD(perdido.getDddResidencial(), perdido.getTelefoneResidencial(), true);
		}
	}
	public String getTelCelFormatado(){
		if(perdido ==null || perdido.getDddCelular() ==null || perdido.getTelefoneCelular()==null){
			return "Não Informado";
		}else{
			return FormatacaoUtil.telefoneComDDD(perdido.getDddCelular(), perdido.getTelefoneCelular(), false);
			
		}
	}
	public String getTelCelVendedor(){
		//if(venda ==null || adocao.getUsuario().getDdd() ==0 || adocao.getUsuario().getTelefone()==0){
			return "Não Informado";
			/*}else{
			return FormatacaoUtil.telefoneComDDD(adocao.getUsuario().getDdd(), adocao.getUsuario().getTelefone(), false);
			
		}*/
		
	}
	public String getDataAnunciada(){
		if(perdido !=null ){
			return FormatacaoUtil.dataPorExtenso(perdido.getDataCadastro());
		}else{
			return "Não Informado";
		}
	}
	public String getDtAcontecimentoExt(){
		
		
		
		if(perdido !=null && perdido.getDtPerdidoAchado() !=null  ){
			return FormatacaoUtil.dataPorExtenso(perdido.getDtPerdidoAchado());
		}else{
			return "Não Informado";
		}
		
	}
	
	public String getAcontecimento(){
		String retorno="";
		if(perdido.getFlAcontecimento().equals("A")){
			retorno="Encontrado:";
		}else{
			retorno="Perdido:";
		}
		return retorno;
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
		if(perdido.getEstado() !=null){
			return perdido.getEstado().getNome();
		}else{
			return "Não Informado";
		}
		
	}
	public String getCidade() {
		if(perdido.getCidade() !=null){
			return perdido.getCidade().getNome();
		}else{
			return "Não Informado";
		}
	}
	public String getBairro() {
		if(perdido.getBairro() !=null){
			return perdido.getBairro().getNome();
		}else{
			return "Não Informado";
		}
	}

	public Perdido getPerdido() {
		return perdido;
	}

	public void setPerdido(Perdido perdido) {
		this.perdido = perdido;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	
}
