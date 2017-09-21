package br.com.petshow.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.petshow.enums.EnumSexo;
import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Animal;
import br.com.petshow.model.Tutor;
import br.com.petshow.web.util.CallAnimalRest;
import br.com.petshow.web.util.ImagemUtil;

@ManagedBean
@ViewScoped
public class MeusAnimaisBean extends SuperBean<Animal> {

	private List<Animal> meusAnimais;
	private Animal animal;
	private CallAnimalRest callAnimalRest;
	private boolean temAnimais = false;
		
	@PostConstruct
	public void init() {
		callAnimalRest = new CallAnimalRest();
		newAnimal();
		consultarAnimaisUsuarioLogado();
	}

	private void consultarAnimaisUsuarioLogado() {
		try {
			meusAnimais	   = callAnimalRest.getListAnimal(getUsuarioLogado().getId());
			if(meusAnimais != null && meusAnimais.size() > 0){
				setTemAnimais(true);
			}
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			setTemAnimais(false);
			meusAnimais = new ArrayList<>();
		}
	}

	private void newAnimal() {
		animal		   	= new Animal();
		Tutor tutor 	= new Tutor();
		tutor.setAnimal(null);
		tutor.setDonoAtual(true);
		tutor.setUsuario(getUsuarioLogado());
		animal.setTutor(tutor);
	}
	
	public List<Animal> getMeusAnimais() {
		consultarAnimaisUsuarioLogado();
		return meusAnimais;
	}

	
	public String salvar() {
		try {
			animal = CallAnimalRest.postEntity(animal, "animal/salvar/", Animal.class);
			String artigo 	= "O";
			String registra = "registrado";
			if(animal != null){
				if(animal.getFlSexo().equals(EnumSexo.FEMEA)){
					artigo 	= "A";
					registra= "registrada";
				}
			}
			exibirInforMessage(artigo + " " + animal.getNome() + " foi " + registra + " com sucesso!");
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			exibirErroMessage("Erro ao tentar registrar o animal : " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public void setMeusAnimais(List<Animal> meusAnimais) {
		this.meusAnimais = meusAnimais;
	}
	
	public void openCadAnimal() {
		
		Map<String,Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("width", 300);
		options.put("height", 300);
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("headerElement", "customheader");

		RequestContext.getCurrentInstance().openDialog("private-cad-animal", options, null);
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	
	public EnumTipoAnimal[] getTiposAnimais(){
		return EnumTipoAnimal.values();
	}
	
	public EnumSexo[] getSexos(){
		return EnumSexo.values();
	}

	public boolean isTemAnimais() {
		return temAnimais;
	}

	public void setTemAnimais(boolean temAnimais) {
		this.temAnimais = temAnimais;
	}
	
	public void enviaImagem(FileUploadEvent event) {
        if(this.animal != null){
        	this.animal.setFotoPerfil(ImagemUtil.transformBase64AsString(event.getFile().getContents()));
        }
	}
}
