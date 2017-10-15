package br.com.petshow.beans.privates;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.beans.SuperBean;
import br.com.petshow.enums.EnumAssuntoNotificacao;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.model.Notificacao;
import br.com.petshow.web.util.CallAnimalRest;
import br.com.petshow.web.util.CallNotificacaoRest;
/**
 * Notificacoes do usuario
 * @author Rafael Rocha
 * @since 21/09/2017 as 23:08
 */
@ManagedBean
@ViewScoped
public class NotificacoesCountBean extends SuperBean<Notificacao> {

	private long qtMsgNaoLidas 	= 0;
	private long qtAdocoesPerfil = 0;
	private List<Notificacao> notificacoes;
	private String mensagemResposta;
	private Notificacao selectedNotificacao;
	private boolean renderNotificacao = false;
	
	@PostConstruct
	public void init() {
		System.out.println("@PostConstruct"+this.getClass().getName());
		getMessages();
	}
	
	public void getMessages() {
		try {
			
			setQtMsgNaoLidas(CallNotificacaoRest.getCountNotificacoesUsuario(getUsuarioLogado().getId()));
			setQtAdocoesPerfil(0);
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			e.printStackTrace();
		}
	}
	
	public void atualizarMensagens() {
		getMessages();
	}


	public List<Notificacao> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<Notificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}
	
	public void jaLiMensagem(Notificacao notificacao) {
		try {
			notificacao.setFlLida(true);
			notificacao.setFlExcluida(true);
			CallNotificacaoRest.postEntity(notificacao, "notificacao/salvar/", Notificacao.class);
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("OK , Você não verá mais esta mensagem"));
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			e.printStackTrace();
		}
	}
	
	
	

	
	public String getMensagemResposta() {
		return mensagemResposta;
	}

	public void setMensagemResposta(String mensagemResposta) {
		this.mensagemResposta = mensagemResposta;
	}

	public Notificacao getSelectedNotificacao() {
		return selectedNotificacao;
	}

	public void setSelectedNotificacao(Notificacao selectedNotificacao) {
		this.selectedNotificacao = selectedNotificacao;
	}

	
	

	public boolean isRenderNotificacao() {
		if(qtMsgNaoLidas > 0 || qtAdocoesPerfil > 0){
			renderNotificacao = true;
		}else{
			renderNotificacao = false;
		}
		return renderNotificacao;
	}

	public void setRenderNotificacao(boolean renderNotificacao) {
		this.renderNotificacao = renderNotificacao;
	}
	
	public boolean isTemMensagensNaoLidas() {
		 
		if(notificacoes != null &&  notificacoes.size() > 0){
			return true;
		}
		return false;
	}

	public long getQtMsgNaoLidas() {
		return qtMsgNaoLidas;
	}

	public void setQtMsgNaoLidas(long qtMsgNaoLidas) {
		this.qtMsgNaoLidas = qtMsgNaoLidas;
	}

	public long getQtAdocoesPerfil() {
		return qtAdocoesPerfil;
	}

	public void setQtAdocoesPerfil(long qtAdocoesPerfil) {
		this.qtAdocoesPerfil = qtAdocoesPerfil;
	}
	
}
