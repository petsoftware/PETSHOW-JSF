package br.com.petshow.beans;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import br.com.petshow.enums.EnumFrequenciaVermifugacao;
import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Animal;
import br.com.petshow.model.Usuario;
import br.com.petshow.web.util.MessagesBeanUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
public class AnimalBean extends SuperBean<Animal> {
	
	private Animal animal;
	private Usuario cliente;
	
	public EnumTipoAnimal[] getTipoAnimal() {
		return EnumTipoAnimal.values();
	}
	@PostConstruct
	public void init() {
		animal = new Animal();
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
			animal.setFlSexo("M");
			
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
