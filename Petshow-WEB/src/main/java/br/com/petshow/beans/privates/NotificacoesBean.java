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
import br.com.petshow.web.util.CallPerfilAdocaoRest;
/**
 * Notificacoes do usuario
 * @author Rafael Rocha
 * @since 21/09/2017 as 23:08
 */
@ManagedBean
@ViewScoped
public class NotificacoesBean extends SuperBean<Notificacao> {

	private int qtMsgNaoLidas 	= 0;
	private int qtAdocoesPerfil = 0;
	private List<Notificacao> notificacoes;
	private String mensagemResposta;
	private Notificacao selectedNotificacao;
	private boolean renderNotificacao = false;
	
	@PostConstruct
	public void init() {
		
		getMessages();
	}
	
	public void getMessages() {
		try {
			List<Notificacao> list = CallNotificacaoRest.getListNotificacoesUsuario(getUsuarioLogado().getId());
			List<Adocao> listAdocao= new CallAnimalRest().getListAnimalDisponiveisAdocaoPorPerfil(getUsuarioLogado());
			setNotificacoes(list);
			setQtMsgNaoLidas(list.size());
			setQtAdocoesPerfil(listAdocao.size());
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
			notificacao.setFlLida(true);
			notificacao.setFlExcluida(true);
			CallNotificacaoRest.postEntity(notificacao, "notificacao/salvar/", Notificacao.class);
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("OK , Você não verá mais esta mensagem"));
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			e.printStackTrace();
		}
	}
	
	public void enviarResposta(Notificacao notificacao) {
		Notificacao notificacaoResp = new Notificacao();
		if(notificacao.getUsuarioRemetente() != null){
			try {
				notificacaoResp.setAssuntoNotificacao(EnumAssuntoNotificacao.PERDIDO);
				notificacaoResp.setContato(notificacao.getUsuarioDestinatario().getTelefone()+"");
				notificacaoResp.setDtNotificacao(new Date());
				notificacaoResp.setEmail(notificacao.getUsuarioDestinatario().getEmail());
				notificacaoResp.setFlEnviada(false);
				notificacaoResp.setFlExcluida(false);
				notificacaoResp.setFlLida(false);
				notificacaoResp.setMsgNotificacao(getMensagemResposta());
				notificacaoResp.setNome(notificacao.getUsuarioDestinatario().getNome());
				notificacaoResp.setUsuarioDestinatario(notificacao.getUsuarioRemetente());
				notificacaoResp.setUsuarioRemetente(notificacao.getUsuarioDestinatario());
				notificacaoResp.setMensagemRespondida(notificacao.getMsgNotificacao());
				CallNotificacaoRest.postEntity(notificacaoResp, "notificacao/salvar", Notificacao.class);
				notificacao.setFlRespondeu(true);
				notificacao.setResposta(getMensagemResposta());
				notificacao.setDtResposta(new Date());
				CallNotificacaoRest.postEntity(notificacao, "notificacao/salvar", Notificacao.class);
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviado com sucesso!",  null);
		        FacesContext.getCurrentInstance().addMessage("growl", message);
			} catch (ExceptionErroCallRest | ExceptionValidation e) {
				e.printStackTrace();
			}
		}else{
			enviarEmail(notificacao);
		}
	}
	
	public void enviarResposta(Long id) {
		Notificacao notificacaoResp = new Notificacao();
		Notificacao notificacao		= null;
		try {
			notificacao = CallNotificacaoRest.getEntity("notificacao/get/"+id, Notificacao.class);
		} catch (ExceptionErroCallRest | ExceptionValidation e1) {
			e1.printStackTrace();
			notificacao = null;
		}
		if(notificacao.getUsuarioRemetente() != null){
			try {
				notificacaoResp.setAssuntoNotificacao(EnumAssuntoNotificacao.PERDIDO);
				notificacaoResp.setContato(notificacao.getUsuarioDestinatario().getTelefone()+"");
				notificacaoResp.setDtNotificacao(new Date());
				notificacaoResp.setEmail(notificacao.getUsuarioDestinatario().getEmail());
				notificacaoResp.setFlEnviada(false);
				notificacaoResp.setFlExcluida(false);
				notificacaoResp.setFlLida(false);
				notificacaoResp.setMsgNotificacao(getMensagemResposta());
				notificacaoResp.setNome(notificacao.getUsuarioDestinatario().getNome());
				notificacaoResp.setUsuarioDestinatario(notificacao.getUsuarioRemetente());
				notificacaoResp.setUsuarioRemetente(notificacao.getUsuarioDestinatario());
				notificacaoResp.setMensagemRespondida(notificacao.getMsgNotificacao());
				CallNotificacaoRest.postEntity(notificacaoResp, "notificacao/salvar", Notificacao.class);
				notificacao.setFlRespondeu(true);
				CallNotificacaoRest.postEntity(notificacao, "notificacao/salvar", Notificacao.class);
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviado com sucesso!",  null);
		        FacesContext.getCurrentInstance().addMessage("growl", message);
			} catch (ExceptionErroCallRest | ExceptionValidation e) {
				e.printStackTrace();
			}
		}else{
			notificacao.setFlRespondeu(true);
			try {
				CallNotificacaoRest.postEntity(notificacao, "notificacao/salvar", Notificacao.class);
			} catch (ExceptionErroCallRest | ExceptionValidation e) {
				e.printStackTrace();
			}
			enviarEmail(notificacao);
		}
	}

	private void enviarEmail(Notificacao notificacao) {
		// TODO Auto-generated method stub
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

	public int getQtAdocoesPerfil() {
		return qtAdocoesPerfil;
	}

	public void setQtAdocoesPerfil(int qtAdocoesPerfil) {
		this.qtAdocoesPerfil = qtAdocoesPerfil;
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
	
}
