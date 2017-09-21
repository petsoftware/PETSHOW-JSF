package br.com.petmooby.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petmooby.exceptions.ExceptionErroCallRest;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Servico;
import br.com.petmooby.model.Usuario;
import br.com.petmooby.role.UsuarioRole;
import br.com.petmooby.web.util.CallServicoRest;
import br.com.petmooby.web.util.RestUtilCall;


@ManagedBean
@ViewScoped
public class ServicoBean  {

	public ServicoBean (){
		super();
	}

	private Servico servico;
	private List<Servico> servicos;
	private CallServicoRest restServico; 
	private Usuario usuarioLogado;

	


	@PostConstruct
	public void init() {
		this.servico = new Servico();
		this.servicos = new ArrayList<Servico>();
		restServico = new CallServicoRest();
		usuarioLogado=UsuarioRole.getUsuarioLogado();
		servico.setUsuario(usuarioLogado);

		getServicosBanco(); 

	}
	
	public String salvarServico(){
	

		salvarServico(this.servico);
		return null;
	}
	
	
	public void salvarServico(Servico servico){
		try {
			servico = (Servico) RestUtilCall.postEntity(servico, "servico/salvar",Servico.class);
			getServicosBanco(); 
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Serviço foi salvo com sucesso!"));
			novo();
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));
		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}

	public String excluirServico(){
		if(servico.getId() ==0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na Exclusão:", "Nenhum Serviço foi selecionado !"));
		}else{
			excluirServico(servico.getId());
		}
		return null;
	}

	public void excluirServico(long id){
		try{
			RestUtilCall.delete("servico/"+id);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Anuncio foi excluido com sucesso!"));
			getServicosBanco(); 
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}


	}

	public String novo(){
		try{

			servico = new Servico();
			servico.setUsuario(UsuarioRole.getUsuarioLogado());


		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}

		return null;
	}

	public void selecionar(Servico servico){
		this.servico=servico;

	}



	public List<Servico> getServicosBanco() {
		try {
			servicos=restServico.getListServico(usuarioLogado.getId());

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		
		
		return servicos;
	}
	
	public void ativarServico(Servico servico){
		servico.setFlAtivo(true);
		salvarServico(servico);
	}
	public void inativarServico(Servico servico){
		servico.setFlAtivo(false);
		salvarServico(servico);

	}
	public Servico getServico() {
		return servico;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	public List<Servico> getServicos() {
		return servicos;
	}
	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}
	public CallServicoRest getRestServico() {
		return restServico;
	}
	public void setRestServico(CallServicoRest restServico) {
		this.restServico = restServico;
	}
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	
}
