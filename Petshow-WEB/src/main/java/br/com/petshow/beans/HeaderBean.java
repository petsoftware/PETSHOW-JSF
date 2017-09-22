package br.com.petshow.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Notificacao;
import br.com.petshow.model.Usuario;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class HeaderBean extends SuperBean<Usuario> {

	private int qtMsgNaoLidas = 0;
	private List<Notificacao> notificacoes;
	private RestUtilCall restUtilCall;
	
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
}
