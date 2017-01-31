package br.com.petshow.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Servico;
import br.com.petshow.model.Usuario;
import br.com.petshow.role.UsuarioRole;

import br.com.petshow.web.util.CallServicoRest;
import br.com.petshow.web.util.RestUtilCall;


@ManagedBean
@ViewScoped
public class ServicoBean  {

	public ServicoBean (){
		super();
		System.out.println("criado o ServicoBean:"+ new Date().getTime());
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
	/*
	@PreDestroy
	public void destroy() {
		System.out.println("Spring Container is destroy! Customer clean up");
		try {
			anuncios=restAnuncio.getListAnuncio(anuncio.getUsuario().getId());

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}
*/
	
	public void salvarServico(){
	

		salvarServico(this.servico);

	}
	
	
	public void salvarServico(Servico servico){
		try {
			
			
			
			servico = (Servico) RestUtilCall.postEntity(servico, "servico/salvar",Servico.class);
			getServicosBanco(); 
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Serviço foi salvo com sucesso!"));
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}





	}

	public void excluirServico(){
		if(servico.getId() ==0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na Exclusão:", "Nenhum Serviço foi selecionado !"));
		}else{
			excluirServico(servico.getId());
		}
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

	public void novo(){
		try{

			servico = new Servico();
			servico.setUsuario(UsuarioRole.getUsuarioLogado());


		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}


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
