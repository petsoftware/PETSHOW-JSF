package br.com.petshow.beans;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.com.petshow.model.Adocao;
import br.com.petshow.util.CollectionUtil;
import br.com.petshow.web.util.ImagemUtil;
import br.com.petshow.web.util.MessagesBeanUtil;
@ManagedBean
@ViewScoped
public class AdocaoBean extends SuperBean<Adocao>{

	private Adocao adocao;
	private List<Adocao> adocoes;

	public List<Adocao> getAdocoes() {
		return adocoes;
	}

	public void setAdocoes(List<Adocao> adocoes) {
		this.adocoes = adocoes;
	}

	public AdocaoBean (){
		super();
	}

	public Adocao getAdocao() {
		return adocao;
	}

	public void setAdocao(Adocao adocao) {
		this.adocao = adocao;
	}

	@PostConstruct
	public void init() {
		novaAdocao();
	}

	private void novaAdocao() {
		adocao = new Adocao();
		adocao.setUsuario(getUsuarioLogado());
	}
	
	public String novo() {
		novaAdocao();
		return null;
	}

	public String salvar() {
		send();
		return null;
	}
	
	public String send() {
		adocao.setDataAdocao(new Date());
		try{
			adocao = postEntity(adocao, "adocao/salvar", Adocao.class);
			MessagesBeanUtil.infor("Seu anúncio foi registrado com sucesso!");;
		} catch (Exception e) {
			e.printStackTrace();
			MessagesBeanUtil.erroMessage(e.getMessage());
		}
		return null;
	}

	public void enviaImagem(FileUploadEvent event) {

		if(this.adocao!=null){
			if(adocao.getFotos().size() >= 3){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Número de imagens excedido", "Só é permitido por até 5 imagens!"));
			}else{
				adocao.getFotos().add(ImagemUtil.transformBase64AsString(event.getFile().getContents()));
			}
		}
	}
	public void removePhoto(String photoInBase64) {
		if(this.adocao != null){
			if(this.adocao.getFotos().isEmpty() == false){
				this.adocao.setFotos(CollectionUtil.removeItem(this.adocao.getFotos(), photoInBase64));
			}
		}
	}

}
