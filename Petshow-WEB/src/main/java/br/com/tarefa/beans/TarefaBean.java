package br.com.tarefa.beans;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.primefaces.event.FileUploadEvent;

import br.com.petshow.enums.EnumPrioridade;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.util.CollectionUtil;
import br.com.petshow.web.util.ImagemUtil;
import br.com.petshow.web.util.RestUtilCall;
import br.com.tafera.enums.EnumDesenvolvedor;
import br.com.tafera.enums.EnumStatus;
import br.com.tafera.enums.EnumTipo;
import br.com.tarefa.model.Tarefa;

@ManagedBean
@ViewScoped
public class TarefaBean {

	private Tarefa tarefa;
	private Part imagem;
	private String observacao;
	
	@PostConstruct
	public void init() {
		tarefa = new Tarefa();
		 String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap() .get("id");
		 
		 if(id!=null && !id.trim().equals("")){
			 tarefa.setId(Long.parseLong(id));
			 consultarTarefa();
		 }
	}
	
	public void consultarTarefa(){
		try{

			
			
			tarefa = (Tarefa)RestUtilCall.getEntity("tarefa/"+tarefa.getId(),Tarefa.class);

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}


	}
	
	public void testar(){
		try{
			RestUtilCall.getEntity("tarefa/carga/",Tarefa.class);
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}


	}
	
	
	
	public void salvarTarefa(){
		try {
			
			//tarefa.setFoto(ImagemUtil.transformBase64AsString(imagem));
			
			if(tarefa.getId()==0){
				tarefa.setDataCriacao(new Date());
			}
			tarefa =  RestUtilCall.postEntity(tarefa, "tarefa/salvar",Tarefa.class);
			 
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Tarefa foi salva com sucesso!"));
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}

	}
	
	public void adicionarObservacao(){
		try {
			
			RestUtilCall.getHashMap("tarefa/obs/"+tarefa.getId()+"/"+observacao);
			consultarTarefa();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Observação foi salva com sucesso!"));
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}

	}
	public void cancelar(){
		alterarStatus(EnumStatus.CANCELADA);
	}
	public void reabrir(){
		alterarStatus(EnumStatus.ABERTA);
	}
	
	public void finalizar(){
		alterarStatus(EnumStatus.CONCLUIDA);
	}
	
	public void postergar(){
		alterarStatus(EnumStatus.POSTERGARDA);
	}
	
	public void novo(){
		tarefa= new Tarefa();
	}
	
	
	public void alterarStatus(EnumStatus item){
		try {
			tarefa.setStatus(item);
			tarefa = (Tarefa) RestUtilCall.postEntity(tarefa, "tarefa/status",Tarefa.class);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Tarefa foi salva com sucesso!"));
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}
	
	
	public EnumTipo[] getTipos(){
		return EnumTipo.values();
	}
	public EnumPrioridade[] getPrioridades(){
		return EnumPrioridade.values();
	}
	
	public EnumDesenvolvedor[] getCriadores(){
		return EnumDesenvolvedor.values();
	}
	

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Part getImagem() {
		return imagem;
	}

	public void setImagem(Part imagem) {
		this.imagem = imagem;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public void enviaImagem(FileUploadEvent event) {
		if(this.tarefa!=null){
			tarefa.getFotos().add(ImagemUtil.transformBase64AsString(event.getFile().getContents()));
		}
	}
	
	public void removePhoto(String photoInBase64) {
		if(this.tarefa != null){
			if(this.tarefa.getFotos().isEmpty() == false){
				this.tarefa.setFotos(CollectionUtil.removeItem(this.tarefa.getFotos(), photoInBase64));
			}
		}
	}
	
}
