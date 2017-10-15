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

import br.com.petshow.enums.EnumFaseVida;
import br.com.petshow.enums.EnumPorteAnimal;
import br.com.petshow.enums.EnumSexo;
import br.com.petshow.enums.EnumSizePhoto;
import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.model.Racas;
import br.com.petshow.util.CollectionUtil;
import br.com.petshow.web.util.CallAdocaoRest;
import br.com.petshow.web.util.CallRacasRest;
import br.com.petshow.web.util.ImagemUtil;
import br.com.petshow.web.util.MessagesBeanUtil;
import br.com.petshow.web.util.RestUtilCall;
@ManagedBean
@ViewScoped
public class AdocaoBean extends SuperBean<Adocao>{

	private Adocao adocao;
	private List<Adocao> adocoes;
	private CallAdocaoRest callAdocaoRest;
	private List<Racas> racas;
	private CallRacasRest callRacasRest;

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
		callAdocaoRest  = new CallAdocaoRest();
		callRacasRest	= new CallRacasRest();
		obterAdocoesAnunciadasDoUsuario();
		System.out.println("@PostConstruct"+this.getClass().getName());
		novaAdocao();
	}
	
	public EnumTipoAnimal[] getTiposAnimais(){
		return EnumTipoAnimal.values();
	}
	
	public List<Adocao> obterAdocoesAnunciadasDoUsuario() {
		List<Adocao> adocoes = null;
		try {
			adocoes = callAdocaoRest.getMeusAnimaisAnunciadosAdocao(getUsuarioLogado().getId());
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			adocoes = new ArrayList<>();
		}
		setAdocoes(adocoes);
		return getAdocoes();
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
		//adocao.setDataAdocao(new Date());
		try{
			if(validar(adocao)){
				adocao = postEntity(adocao, "adocao/salvar", Adocao.class);
				MessagesBeanUtil.infor("Seu anúncio foi registrado com sucesso!");
				obterAdocoesAnunciadasDoUsuario();
				novaAdocao();
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessagesBeanUtil.erroMessage(e.getMessage());
		}
		return null;
	}
	
	public String salvar(Adocao adocao) {
		//adocao.setDataAdocao(new Date());
		try{
			if(validar(adocao)){
				adocao = postEntity(adocao, "adocao/salvar", Adocao.class);
				MessagesBeanUtil.infor("Seu anúncio foi alterado com sucesso!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessagesBeanUtil.erroMessage(e.getMessage());
		}
		return null;
	}

	public void enviaImagem(FileUploadEvent event) {

		if(this.adocao!=null){
			if(adocao.getFotos().size() >= 3){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Número de imagens excedido", "Só é permitido por até 3 imagens!"));
			}else{
				adocao.getFotos().add(ImagemUtil.transformBase64AsString(ImagemUtil.resizeImage(event.getFile(), EnumSizePhoto.ADOCAO)));
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
	
	public boolean validar(Adocao adocao) {
		String msg = "";
		if(adocao != null){
			if(adocao.getEndereco().getUf() == null){
				msg = "Informe o Estado";
			}else if(adocao.getEndereco().getCidade() == null){
				msg = "Favor informar a cidade";
			}else if(adocao.getTitulo().trim().isEmpty()){
				msg = "Por favor informe o título";
			}
		}else{
			msg = "";
		}
		if(msg.trim().isEmpty()){
			return true;
		}else{
			exibirErroMessage(msg);
			return false;
		}
	}
	
	public boolean isTemFotos() {
		if(adocao != null && adocao.getFotos() != null && adocao.getFotos().size() > 0){
			return true;
		}
		return false;
	}
	
	public void findRacasPorTipoAnimal(EnumTipoAnimal tipoAnimal) {
		try {
			List<Racas> racas = callRacasRest.getListRacasPorTipoDeAnimal(tipoAnimal);
			setRacas(racas);
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			setRacas(new ArrayList<>());
		}
	}

	public List<Racas> getRacas() {
		return racas;
	}

	public void setRacas(List<Racas> racas) {
		this.racas = racas;
	}
	
	public EnumFaseVida[] getFases(){
		return EnumFaseVida.values();
	}
	
	public EnumSexo[] getSexos(){
		return EnumSexo.values();
	}
	
	public EnumPorteAnimal[] getPortes() {
		return EnumPorteAnimal.values();
	}
}
