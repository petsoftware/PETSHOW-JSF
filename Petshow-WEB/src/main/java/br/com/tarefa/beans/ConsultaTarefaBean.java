package br.com.tarefa.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.enums.EnumPrioridade;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.tafera.enums.EnumDesenvolvedor;
import br.com.tafera.enums.EnumTipo;
import br.com.tarefa.model.Tarefa;
import br.com.tarefa.web.util.CallTarefaRest;

@ManagedBean
@ViewScoped
public class ConsultaTarefaBean  implements Serializable{
	
	private List<Tarefa> tarefas;
	private List<Tarefa> tarefasFiltradas;
	private CallTarefaRest restTarefa;
		
	
	@PostConstruct
	public void init() {
		restTarefa= new CallTarefaRest();
		getTarefasBanco();
	}
	
	
		
	
	public List<Tarefa> getTarefasBanco() {
		try {
			
			tarefas= restTarefa.getListTarefas();

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		
		
		return tarefas;
	}




	public List<Tarefa> getTarefas() {
		return tarefas;
	}




	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}




	public CallTarefaRest getRestTarefa() {
		return restTarefa;
	}




	public List<Tarefa> getTarefasFiltradas() {
		return tarefasFiltradas;
	}




	public void setTarefasFiltradas(List<Tarefa> tarefasFiltradas) {
		this.tarefasFiltradas = tarefasFiltradas;
	}




	public void setRestTarefa(CallTarefaRest restTarefa) {
		this.restTarefa = restTarefa;
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
	
	public List<String> getCriadores2(){
		EnumDesenvolvedor[] enums= EnumDesenvolvedor.values();
		List<String> retorno = new ArrayList<String>();
		for(int i =0;enums.length>i;++i){
			retorno.add(enums[i].toString());
		}
		return retorno;
	}
	
	public List<Long> getIds(){
		List<Long> retorno = new ArrayList<Long>();
		retorno.add(1l);
		retorno.add(3l);
		retorno.add(2l);
		return retorno;
		
	}
		
}
