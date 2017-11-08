package br.com.petshow.beans;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.enums.EnumAssuntoNotificacao;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Venda;
import br.com.petshow.model.Notificacao;
import br.com.petshow.model.Usuario;
import br.com.petshow.util.FormatacaoUtil;
import br.com.petshow.web.util.CallNotificacaoRest;
import br.com.petshow.web.util.MessagesBeanUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class DetalheClassificadoBean extends SuperBean<Venda>{

	private Venda venda;
	private String id;
	private String nome;
	private String email;
	private long telefone;
	private String mensagem;
	private Usuario usuario= null;
	@PostConstruct
	public void init() {
		this.venda= new Venda();
		verificarSeUsuarioLogado();
		getVendaBanco();
	}
	
	private void verificarSeUsuarioLogado() {
		if(getUsuarioLogado() != null){
			usuario =  getUsuarioLogado();
		}
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
			Notificacao notificacao = new Notificacao();
			notificacao.setAdocao(null);
			notificacao.setPerdido(null);
			notificacao.setVenda(getVenda());
			notificacao.setContato(String.valueOf(getTelefone()));
			notificacao.setDtNotificacao(new Date());
			notificacao.setEmail(getEmail());
			notificacao.setFlExcluida(false);
			notificacao.setMsgNotificacao(getMensagem());
			notificacao.setNome(getNome());
			notificacao.setTpNotificacao("A");
			notificacao.setAssuntoNotificacao(EnumAssuntoNotificacao.ADOCAO);
			notificacao.setUsuarioDestinatario(getVenda().getUsuario());
			if(usuario!=null){
				notificacao.setUsuarioRemetente(usuario);
			}else{
				notificacao.setUsuarioRemetente(null);
			}
			CallNotificacaoRest.postEntity(notificacao, "notificacao/salvar/", Notificacao.class);
			MessagesBeanUtil.inforClient("","Enviado com sucesso!","msgEnviar");
			setMensagem("");
		} catch (ExceptionErroCallRest  e) {
			MessagesBeanUtil.exception(e);
		} catch (ExceptionValidation e) {
			MessagesBeanUtil.exception(e);
		} catch (Exception e) {
			MessagesBeanUtil.exception(e);
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
		if(naoInformouContato()){
			return "Não Informado";
		}else{
			return FormatacaoUtil.telefoneComDDD(venda.getUsuario().getDdd(), venda.getUsuario().getTelefone(), false);
			
		}
	}

	private boolean naoInformouContato() {
		return venda ==null || venda.getUsuario().getDdd() ==0 || venda.getUsuario().getTelefone()==0 ||  
			   venda.getUsuario().getDdd() !=null || venda.getUsuario().getTelefone() !=null;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getEstado() {
		if(getVenda().getEndereco() !=null){
			return getVenda().getEndereco().getUf().getLabel();
		}else{
			return "SEM Estado";
		}
		
	}
	public String getCidade() {
		if(getVenda().getEndereco() !=null){
			return getVenda().getEndereco().getCidade().getNome();
		}else{
			return "SEM Cidade";
		}
	}
	
	
}
