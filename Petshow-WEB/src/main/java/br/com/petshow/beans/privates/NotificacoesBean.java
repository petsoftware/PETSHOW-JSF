package br.com.petshow.beans.privates;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.beans.SuperBean;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.model.Notificacao;
import br.com.petshow.web.util.RestUtilCall;
/**
 * Notificacoes do usuario
 * @author Rafael Rocha
 * @since 21/09/2017 as 23:08
 */
@ManagedBean
@ViewScoped
public class NotificacoesBean extends SuperBean<Notificacao> {

	private int qtMsgNaoLidas = 0;
	private List<Notificacao> notificacoes;
	private RestUtilCall<Notificacao> restUtilCall;
	
	@PostConstruct
	public void init() {
		restUtilCall = new RestUtilCall<>();
		getMessages();
	}
	
	public void getMessages() {
		try {
			List<Notificacao> list = restUtilCall.getEntityList("notificacao/usuario/"+getUsuarioLogado().getId(), Notificacao.class);
			setNotificacoes(list);
			setQtMsgNaoLidas(list.size());
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			e.printStackTrace();
		}
	}

	public int getQtMsgNaoLidas() {
		return qtMsgNaoLidas;
	}

	public void setQtMsgNaoLidas(int qtMsgNaoLidas) {
		this.qtMsgNaoLidas = qtMsgNaoLidas;
	}

	public List<Notificacao> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<Notificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}
	
	public void jaLiMensagem(Notificacao notificacao) {
		try {
			RestUtilCall.postEntity(notificacao, "url modificar que ja leu", Notificacao.class);
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("OK , Você não verá mais esta mensagem"));
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			e.printStackTrace();
		}
	}
}
