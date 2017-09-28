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

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.util.CollectionUtil;
import br.com.petshow.web.util.CallAdocaoRest;
import br.com.petshow.web.util.ImagemUtil;
import br.com.petshow.web.util.MessagesBeanUtil;
import br.com.petshow.web.util.RestUtilCall;
@ManagedBean
@ViewScoped
public class AdocaoBean extends SuperBean<Adocao>{

	private Adocao adocao;
	private List<Adocao> adocoes;
	private CallAdocaoRest callAdocaoRest;

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
		callAdocaoRest = new CallAdocaoRest();
		obterAdocoesAnunciadasDoUsuario();
		novaAdocao();
	}

	public void obterAdocoesAnunciadasDoUsuario() {
		List<Adocao> adocoes = null;
		try {
			adocoes = callAdocaoRest.getMeusAnimaisAnunciadosAdocao(getUsuarioLogado().getId());
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			adocoes = new ArrayList<>();
		}
		setAdocoes(adocoes);
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
	
	public String salvar(Adocao adocao) {
		adocao.setDataAdocao(new Date());
		try{
			adocao = postEntity(adocao, "adocao/salvar", Adocao.class);
			MessagesBeanUtil.infor("Seu anúncio foi alterado com sucesso!");;
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
	
	public void selecionar(Adocao adocao) {
		setAdocao(adocao);
	}
	
	public void excluirAdocao(long idAdocao){
		try{
			RestUtilCall.delete("adocao/"+idAdocao);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Anuncio de Adoção excluido com sucesso!"));
			obterAdocoesAnunciadasDoUsuario(); 
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));
		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}
	
	public void marcarComoAdotado(Adocao adocao) {
		adocao.setFlAdotado(true);
		adocao.setDataAdocao(new Date());
		salvar(adocao);
	}
	
	public void marcarComoNAOAdotado(Adocao adocao) {
		adocao.setFlAdotado(false);
		adocao.setDataAdocao(null);
		salvar(adocao);
	}

}
