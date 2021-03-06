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
import br.com.petshow.enums.EnumCor;
import br.com.petshow.enums.EnumSexo;
import br.com.petshow.enums.EnumSizePhoto;
import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Perdido;
import br.com.petshow.model.Racas;
import br.com.petshow.util.CollectionUtil;
import br.com.petshow.web.util.CallPerdidoRest;
import br.com.petshow.web.util.CallRacasRest;
import br.com.petshow.web.util.ImagemUtil;
import br.com.petshow.web.util.MessagesBeanUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class PerdidoBean extends SuperBean<Perdido> {

	private Perdido perdido;
	private List<Perdido> perdidos;
	private CallPerdidoRest callPerdidoRest;
	private CallRacasRest callRacasRest;
	private boolean redenrizarCampos = false;
	private String descricao 		 = "";
	private List<Racas> racas;
	@PostConstruct
	public void init() {
		callPerdidoRest = new CallPerdidoRest();
		callRacasRest   = new CallRacasRest();
		obterAnimaisPerdidosDoUsuario();
		novoPerdido();
		System.out.println("@PostConstruct"+this.getClass().getName());
	}

	private void novoPerdido() {
		perdido = new Perdido();
		perdido.setUsuario(getUsuarioLogado());
	}
	
	public void novo() {
		novoPerdido();
	}
	public void obterAnimaisPerdidosDoUsuario() {
		List<Perdido> perdidos = null;
		try {
			perdidos = callPerdidoRest.getMeusAnimaisPerdidos(getUsuarioLogado().getId());
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			perdidos = new ArrayList<>();
		}
		setPerdidos(perdidos);
	}
	
	public String salvar() {
		try {
			if(validar(perdido)){
				if(perdido.getId() == 0){
					perdido.setDataCadastro(new Date());
				}
				perdido = postEntity(perdido, "animal/perdido/salvar/", Perdido.class);
				exibirInforMessage("Seu PET foi anunciado com sucesso!");
				novoPerdido();
				obterAnimaisPerdidosDoUsuario();
			}
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			exibirErroMessage("Erro ao tentar salvar: " + e.getMessage());
		}
		return null;
	}
	
	private boolean validar(Perdido perdido) {
		String msg = "";
		if(perdido != null){
			if(perdido.getEndereco().getUf() == null){
				msg = "Por favor selecione o estado!";
			}else if(perdido.getEndereco().getCidade() == null){
				msg = "Por favor informe a cidade!";
			}else if(perdido.getNome().trim().isEmpty() ){
				msg = "Por favor informe o nome!";
			}else if(perdido.getDtPerdidoAchado() == null ){
				msg = "Por favor informe a data do acontecimento!";
			}else if(perdido.getFlAcontecimento() == null ){
				msg = "Por favor diga se ele foi achado ou perdido!";
			}else if(perdido.getDddCelular() == null || perdido.getDddCelular() ==0){
				msg = "Por favor informe seu DDD!";
			}else if(perdido.getTelefoneCelular() == null || perdido.getTelefoneCelular() ==0 ){
				msg = "Por favor informe seu número para contato!";
			}else if(perdido.getTelefoneCelular() < 9999999 ){
				msg = "Por favor informe um número com 8 ou 9 dígitos! ";
			}else if(perdido.getDddCelular() < 9 ){
				msg = "Por favor informe um DDD com  2 dígitos! ";
			}else if(perdido.getTpAnimal() == null){
				msg = "Por favor informe o tipo de animal! ";
			}else if(perdido.getDescAcontecimento() == null){
				msg = "Por favor fale sobre o acontecimento! ";
			}else if(perdido.getDescAcontecimento().length() < 20){
				msg = "Por favor fale um pouco mais sobre como aconteceu! ";
			}else if(perdido.getFotos().size() == 0){
				msg = "Por favor coloque pelo menos uma foto do animal! ";
			}else if(perdido.getDtPerdidoAchado().getTime() > new Date().getTime()){
				msg = "A data do acontecimento não pode ser maior que hoje! ";
			}
			
			if(perdido.getFlAcontecimento() != null &&
				perdido.getFlAcontecimento() == EnumAchadoPerdido.PERDIDO &&
				perdido.getTpAnimal() != null &&
				(perdido.getTpAnimal() == EnumTipoAnimal.CACHORRO || perdido.getTpAnimal() == EnumTipoAnimal.GATO)){
				if(perdido.getRaca()== null)
					msg = "Por favor informe a raça do animal! ";
				if(perdido.getTpCorPrincipal() == null)
					msg = "Por favor informe a cor predominante do animal! ";
			}
			
			if(perdido.getTpAnimal() != null &&
			   perdido.getTpAnimal() == EnumTipoAnimal.CACHORRO && 
			   perdido.getFlSexo() == null){
					msg = "Por favor informe o sexo do animal! ";
				}
			
		}else{
			msg = "Objeto perdido veio nulo";
		}
		if(msg.trim().isEmpty()){
			return true;
		}else{
			exibirErroMessage(msg);
			return false;
		}
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
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Número de imagens ultrapassado!", "Só é permitido por até 3 imagens!"));
			}else{
				perdido.getFotos().add(ImagemUtil.transformBase64AsString(ImagemUtil.resizeImage(event.getFile(), EnumSizePhoto.PERDIDO)));
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
			obterAnimaisPerdidosDoUsuario(); 
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
	
	public EnumCor[] getCores() {
		return EnumCor.values();
	}
	
	public EnumAchadoPerdido[] getAchadoPerdido() {
		return EnumAchadoPerdido.values();
	}

	public boolean isRedenrizarCampos() {
		return redenrizarCampos;
	}

	public void setRedenrizarCampos(boolean redenrizarCampos) {
		this.redenrizarCampos = redenrizarCampos;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void adequarForm() {
		if(perdido != null){
			if(perdido.getFlAcontecimento() != null){
				if(perdido.getFlAcontecimento().equals(EnumAchadoPerdido.PERDIDO)){
					setDescricao("Qual é o nome do seu PET?");
					perdido.setNome("");
				}else{
					setDescricao("Sugira um nome ou deixe como estar.");
					perdido.setNome("PET encontrado");
				}
				setRedenrizarCampos(true);
			}else{
				perdido.setNome("");
				setRedenrizarCampos(false);
			}
		}
	}
	public boolean isTemFotos() {
		if(perdido != null && perdido.getFotos() != null && perdido.getFotos().size() > 0){
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
	
	public EnumSexo[] getSexos(){
		return EnumSexo.values();
	}
	
	
	
	
	

}
