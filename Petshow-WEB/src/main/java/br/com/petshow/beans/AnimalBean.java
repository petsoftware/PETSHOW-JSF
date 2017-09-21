package br.com.petshow.beans;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import br.com.petshow.enums.EnumFrequenciaVermifugacao;
import br.com.petshow.enums.EnumSexo;
import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Animal;
import br.com.petshow.model.Tutor;
import br.com.petshow.model.Usuario;
import br.com.petshow.web.util.MessagesBeanUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
public class AnimalBean extends SuperBean<Animal> {
	
	private Animal animal;
	private Usuario cliente;
	private List<Animal> animais;
	RestUtilCall<Animal> rest;
	
	public List<Animal> getAnimais() {
		
		try {
			if(cliente != null){
				animais = rest.getEntityList("animal/consulta/usuario/"+cliente.getId(), Animal.class);
			}
		} catch (ExceptionErroCallRest e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionValidation e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return animais;
	}
	
	public void selecionar(Animal animal) {
		this.animal = animal;
	}
	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}
	public EnumTipoAnimal[] getTipoAnimal() {
		return EnumTipoAnimal.values();
	}
	@PostConstruct
	public void init() {
		animal = new Animal();
		rest = new RestUtilCall<>();
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}
	
	public String salvar() {
		try {
			animal.setDataNascimento(new Date());
			animal.setFlAdocao(false);
			animal.setFotoPerfil("");
			animal.setFrequenciaVermifugacao(EnumFrequenciaVermifugacao.ANUAL);
			animal.setTemReforco(false);
			animal.setFlSexo(EnumSexo.MACHO);
			if(cliente != null){
				Tutor tutor = new Tutor();
				tutor.setAnimal(null);
				tutor.setDonoAtual(true);
				tutor.setUsuario(cliente);
				animal.setTutor(tutor);
			}else{
				MessagesBeanUtil.erroMessage("Favor informar o cliente!");
				return null;
			}
			animal = RestUtilCall.postEntity(animal, "animal/salvar/", Animal.class);
			MessagesBeanUtil.infor("Animal Salvo com sucesso!");
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MessagesBeanUtil.exception(e);
		}
		return null;
	}

}
