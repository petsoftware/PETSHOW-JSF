package br.com.petshow.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.com.petshow.enums.EnumVacina;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Animal;
import br.com.petshow.model.Usuario;
import br.com.petshow.model.Vacina;
import br.com.petshow.util.WriteConsoleUtil;
import br.com.petshow.web.util.CallAnimalRest;
import br.com.petshow.web.util.MessagesBeanUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
public class VacinaBean extends SuperBean<Vacina> {

	public Vacina vacina;
	public Usuario usuario;
	private String vacinaURL = "animal/vacina/salvar";
	private List<Animal> animais;
	private List<Animal> animaisSelecionados;
	private Animal animalSelecionado;
	private CallAnimalRest callRestAnimal;


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@PostConstruct
	public void init() {
		vacina = new Vacina();
		callRestAnimal	= new CallAnimalRest();
	}

	public void eventoConsultaAnimais(SelectEvent event) {
		getAnimaisBanco(((Usuario) event.getObject()).getId());
	}


	public void getAnimaisBanco(long  id){
		try {
			animais=callRestAnimal.getListAnimal(id);
			if(animais.size()==1){
				animalSelecionado=animais.get(0);
			}else{
				animalSelecionado=null;
			}

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}

	}


	public EnumVacina[] getVacinas() {
		return EnumVacina.values();
	}

	public boolean mostrarGrid(){

		if(animais.size()>1){
			return true;
		}else{
			return false;
		}

	}

	public void salvar() {
		try {
			WriteConsoleUtil.write("Salvar vacina");
			RestUtilCall.postEntity(vacina, vacinaURL, Vacina.class);
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			// TODO Auto-generated catch block
			MessagesBeanUtil.erroMessage("Erro ao tentar salvar o registro", e.getMessage());
		}
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public List<Animal> getAnimaisSelecionados() {
		return animaisSelecionados;
	}

	public void setAnimaisSelecionados(List<Animal> animaisSelecionados) {
		this.animaisSelecionados = animaisSelecionados;
	}

	public List<Animal> getAnimais() {
		return animais;
	}

	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}

	public Animal getAnimalSelecionado() {
		return animalSelecionado;
	}

	public void setAnimalSelecionado(Animal animalSelecionado) {
		this.animalSelecionado = animalSelecionado;
	}

	public boolean mostrarlabel(){

		if(animais.size()==1){
			return true;
		}else{
			return false;
		}

	}
}
