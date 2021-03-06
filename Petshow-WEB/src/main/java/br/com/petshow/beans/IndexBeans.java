package br.com.petshow.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.objects.EnviarMensagem;
import br.com.petshow.web.util.MessagesBeanUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
@RequestScoped
public class IndexBeans extends SuperBean<EnviarMensagem> {

	private EnviarMensagem enviarMensagem;
	@PostConstruct
	public void init() {
		enviarMensagem = new EnviarMensagem();
		System.out.println("@PostConstruct"+this.getClass().getName());
	}
	public void enviar() {
		try {
			StringBuilder message = new StringBuilder();
			message.append("Mensagem recebida do site - área de contato.").append("\n")
			.append("Remetente: ").append(getEnviarMensagem().getEmail()).append("\n")
			.append("Contato: ").append(getEnviarMensagem().getContato()).append("\n")
			.append("Mensagem enviada:").append("\n")
			.append(getEnviarMensagem().getMensagem());
			
			getEnviarMensagem().setMensagem(message.toString());
			RestUtilCall.postEntity(enviarMensagem, "email/enviar/", EnviarMensagem.class);
			MessagesBeanUtil.inforClient("E-mail enviado com sucesso." ,"Obrigado por entrar em contato.", "growl");
			enviarMensagem = new EnviarMensagem();
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			MessagesBeanUtil.erroMessage(e.getMessage());
		}
	}
	public EnviarMensagem getEnviarMensagem() {
		return enviarMensagem;
	}
	public void setEnviarMensagem(EnviarMensagem enviarMensagem) {
		this.enviarMensagem = enviarMensagem;
	}
	
}


