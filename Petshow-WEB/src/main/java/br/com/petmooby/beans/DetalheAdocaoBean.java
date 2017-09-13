package br.com.petmooby.beans;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Adocao;
import br.com.petmooby.util.FormatacaoUtil;
import br.com.petmooby.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class DetalheAdocaoBean {

	private Adocao adocao;
	
	
	private String nome;
	
	private String email;
	
	private long telefone;
	
	private String mensagem;
	
	private String id;
	
	@PostConstruct
	public void init() {
		this.adocao= new Adocao();
		getAdocaoBanco();
	
	}
	
	public void getAdocaoBanco(){
		try {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap(); 
			 id = params.get("id");
			if(id != null){
				adocao = RestUtilCall.getEntity("adocao/"+id,Adocao.class);
			}else{
				adocao = new Adocao();
			}

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
			map.put("idAdocao", id );
			map.put("mensagem", mensagem);
			map.put("telefone", telefone+"");
			map.put("email", email);
			
			RestUtilCall.post( map, "notificacao/adocao/msganuncio");

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
		if(adocao ==null || adocao.getDddResidencial() ==null || adocao.getTelefoneResidencial()==null){
			return "Não Informado";
			
		}else{
			return FormatacaoUtil.telefoneComDDD(adocao.getDddResidencial(), adocao.getTelefoneResidencial(), true);
		}
	}
	public String getTelCelFormatado(){
		if(adocao ==null || adocao.getDddCelular() ==null || adocao.getTelefoneCelular()==null){
			return "Não Informado";
		}else{
			return FormatacaoUtil.telefoneComDDD(adocao.getDddCelular(), adocao.getTelefoneCelular(), false);
			
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
		if(adocao !=null ){
			return FormatacaoUtil.dataPorExtenso(adocao.getDataCadastro());
		}else{
			return "Não Informado";
		}
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
		if(adocao.getEstado() !=null){
			return adocao.getEstado().getNome();
		}else{
			return "Não Informado";
		}
		
	}
	public String getCidade() {
		if(adocao.getCidade() !=null){
			return adocao.getCidade().getNome();
		}else{
			return "Não Informado";
		}
	}
	public String getBairro() {
		if(adocao.getBairro() !=null){
			return adocao.getBairro().getNome();
		}else{
			return "Não Informado";
		}
	}



	public Adocao getAdocao() {
		return adocao;
	}



	public void setAdocao(Adocao adocao) {
		this.adocao = adocao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
