package br.com.petshow.beans;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Perdido;
import br.com.petshow.util.CollectionUtil;
import br.com.petshow.web.util.ImagemUtil;

@ManagedBean
@ViewScoped
public class PerdidoBean extends SuperBean<Perdido> {

	private Perdido perdido;
	
	@PostConstruct
	public void init() {
		novoPerdido();
	}

	private void novoPerdido() {
		perdido = new Perdido();
	}
	
	public void novo() {
		novoPerdido();
	}
	
	public String salvar() {
		try {
			perdido.setDataCadastro(new Date());
			perdido = postEntity(perdido, "animal/perdido/salvar/", Perdido.class);
			exibirInforMessage("Seu PET foi anunciado com sucesso!");
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			exibirErroMessage("Erro ao tentar salvar: " + e.getMessage());
		}
		return null;
	}

	public Perdido getPerdido() {
		return perdido;
	}

	public void setPerdido(Perdido perdido) {
		this.perdido = perdido;
	}
	
	public EnumTipoAnimal[] getTiposAnimais(){
		return EnumTipoAnimal.values();
	}
	
	public void enviaImagem(FileUploadEvent event) {

		if(this.perdido!=null){
			if(perdido.getFotos().size() >= 3){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Número de imagens excedido", "Só é permitido por até 5 imagens!"));
			}else{
				perdido.getFotos().add(ImagemUtil.transformBase64AsString(event.getFile().getContents()));
			}
		}
	}
	public void removePhoto(String photoInBase64) {
		if(this.perdido != null){
			if(this.perdido.getFotos().isEmpty() == false){
				this.perdido.setFotos(CollectionUtil.removeItem(this.perdido.getFotos(), photoInBase64));
			}
		}
	}
}
