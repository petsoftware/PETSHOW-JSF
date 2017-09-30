package br.com.petshow.beans;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.enums.EnumAssuntoNotificacao;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.model.Notificacao;
import br.com.petshow.util.FormatacaoUtil;
import br.com.petshow.web.util.CallNotificacaoRest;
import br.com.petshow.web.util.MessagesBeanUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class DetalheAdocaoBean extends SuperBean<Adocao>{

	private Adocao adocao;
	private String nome;
	private String email;
	private String telefone;
	private String mensagem;
	private String id;
	private boolean temUsuarioLogado;
	
	@PostConstruct
	public void init() {
		this.adocao= new Adocao();
		getAdocaoBanco();
		verificarSeUsuarioLogado();
	}
	
	private void verificarSeUsuarioLogado() {
		if(getUsuarioLogado() != null){
			if(getUsuarioLogado().getId() > 0){
				setTemUsuarioLogado(true);
			}else{
				setTemUsuarioLogado(false);
			}
		}
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
			Notificacao notificacao = new Notificacao();
			notificacao.setAdocao(getAdocao());
			notificacao.setPerdido(null);
			notificacao.setContato(getTelefone());
			notificacao.setDtNotificacao(new Date());
			notificacao.setEmail(getEmail());
			notificacao.setFlExcluida(false);
			notificacao.setMsgNotificacao(getMensagem());
			notificacao.setNome(getNome());
			notificacao.setTpNotificacao("A");
			notificacao.setAssuntoNotificacao(EnumAssuntoNotificacao.ADOCAO);
			notificacao.setUsuarioDestinatario(getAdocao().getUsuario());
			if(isTemUsuarioLogado()){
				notificacao.setUsuarioRemetente(getUsuarioLogado());
			}else{
				notificacao.setUsuarioRemetente(null);
			}
			CallNotificacaoRest.postEntity(notificacao, "notificacao/salvar/", Notificacao.class);
			MessagesBeanUtil.inforClient("Enviado com sucesso!","msgEnviar");
			setMensagem("");

		} catch (ExceptionErroCallRest  e) {
			// erro: nao está mostrando a mensavem
			FacesContext.getCurrentInstance().addMessage("msgEnviar", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage("msgEnviar", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("msgEnviar", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}

	public String getTelResFormatado(){
		if(adocao ==null || adocao.getDddResidencial() ==null || adocao.getTelefoneResidencial()==null){
			return "Tel. Fixo";
			
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
		return "Não Informado";
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

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getEstado() {
		if(adocao.getEndereco() !=null){
			return adocao.getEndereco().getUf().getLabel();
		}else{
			return "";
		}
		
	}
	public String getCidade() {
		if(adocao.getEndereco() !=null){
			return adocao.getEndereco().getCidade().getNome();
		}else{
			return "";
		}
	}
	public String getBairro() {
		if(adocao.getEndereco() !=null){
			return "Bairro: " + adocao.getEndereco().getBairro();
		}else{
			return "";
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
	
	public boolean isTemUsuarioLogado() {
		return temUsuarioLogado;
	}

	public void setTemUsuarioLogado(boolean temUsuarioLogado) {
		this.temUsuarioLogado = temUsuarioLogado;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
}