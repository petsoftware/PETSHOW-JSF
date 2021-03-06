package br.com.petshow.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.com.petshow.enums.EnumAchadoPerdido;
import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Perdido;
import br.com.petshow.util.CollectionUtil;
import br.com.petshow.web.util.CallPerdidoRest;
import br.com.petshow.web.util.ImagemUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class EncontradoBean extends SuperBean<Perdido> {
	private Perdido perdido;
	private List<Perdido> perdidos;
	private CallPerdidoRest callPerdidoRest;
	@PostConstruct
	public void init() {
		callPerdidoRest = new CallPerdidoRest();
		obterAnimaisEncontrados();
		novoPerdido();
	}

	private void novoPerdido() {
		perdido = new Perdido();
		perdido.setUsuario(getUsuarioLogado());
	}
	
	public void novo() {
		novoPerdido();
	}
	public void obterAnimaisEncontrados() {
		List<Perdido> perdidos = null;
		try {
			perdidos = callPerdidoRest.getMeusAnimaisEncontrados(getUsuarioLogado().getId());
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			perdidos = new ArrayList<>();
		}
		setPerdidos(perdidos);
	}
	
	public String salvar() {
		try {
			perdido.setDataCadastro(new Date());
			perdido.setFlAcontecimento(EnumAchadoPerdido.ACHADO);
			perdido = postEntity(perdido, "animal/perdido/salvar/", Perdido.class);
			exibirInforMessage("PET foi anunciado com sucesso!");
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			exibirErroMessage("Erro ao tentar salvar: " + e.getMessage());
		}
		return null;
	}
	
	public Perdido salvar(Perdido perdido) {
		try {
			perdido.setDataCadastro(new Date());
			perdido = postEntity(perdido, "animal/perdido/salvar/", Perdido.class);
			exibirInforMessage("Seu PET foi anunciado com sucesso!");
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			exibirErroMessage("Erro ao tentar salvar: " + e.getMessage());
		}
		return perdido;
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

	public List<Perdido> getPerdidos() {
		return perdidos;
	}

	public void setPerdidos(List<Perdido> perdidos) {
		this.perdidos = perdidos;
	}
	
	public void foiEncontrado(Perdido perdido) {
		perdido.setFlEncontrado(true);
		perdido.setDtEncontrado(new Date());
		perdido = salvar(perdido);
	}
	
	public void naoFoiEncontrado(Perdido perdido) {
		perdido.setFlEncontrado(false);
		perdido.setDtEncontrado(null);
		perdido = salvar(perdido);
	}
	
	public void selecionar(Perdido perdido) {
		setPerdido(perdido);
	}
	
	public void excluir(long idPerdido){
		try{
			RestUtilCall.delete("perdido/"+idPerdido);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Anuncio de Adoção excluido com sucesso!"));
			obterAnimaisEncontrados(); 
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));
		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}
	
	public void excluisaoLogica(Perdido perdido) {
		if(perdido != null){
			perdido.setFlAtivo(false);
			perdido.setDtDesativacao(new Date());
			perdido = salvar(perdido);
		}
	}
}
