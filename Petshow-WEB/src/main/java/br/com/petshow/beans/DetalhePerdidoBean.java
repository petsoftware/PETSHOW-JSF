package br.com.petshow.beans;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Perdido;
import br.com.petshow.util.FormatacaoUtil;
import br.com.petshow.web.util.MessagesBeanUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class DetalhePerdidoBean extends SuperBean<Perdido>{

	private Perdido perdido;
	private String nome;
	private String email;
	private String telefone;
	private String mensagem;
	private String id;
	private boolean temUsuarioLogado;
	
	@PostConstruct
	public void init() {
		this.perdido= new Perdido();
		id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap() .get("id");
		getAdocaoBanco();
		verificarSeUsuarioLogado();
	}

	private void verificarSeUsuarioLogado() {
		if(getUsuarioLogado() != null){
			if(getUsuarioLogado().getId() > 0){
				setTemUsuarioLogado(true);
			}else{
				setTemUsuarioLogado(false);
			}
		}
	}

	public void getAdocaoBanco(){
		try {
			perdido = RestUtilCall.getEntity("animal/perdido/"+id,Perdido.class);
		} catch (ExceptionErroCallRest  e) {
			// erro: nao está mostrando a mensavem
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}
	
	public void enviar(){
		try {			
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("idPerdido", id );
			map.put("mensagem", mensagem);
			map.put("telefone", telefone+"");
			map.put("email", email+"");
			map.put("nome", getNome());
			if(isTemUsuarioLogado()){
				map.put("idUserRemet", getUsuarioLogado().getId()+"");
			}else{
				map.put("idUserRemet", "0");
			}
			RestUtilCall.post( map, "notificacao/perdido/msganuncio");
			MessagesBeanUtil.inforClient("Enviado com sucesso!","msgEnviar");
			setMensagem("");
		} catch (ExceptionErroCallRest  e) {
			// erro: nao está mostrando a mensavem
			FacesContext.getCurrentInstance().addMessage("msgEnviar", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage("msgEnviar", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("msgEnviar", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}

	public String getTelResFormatado(){
		if(perdido ==null || perdido.getDddResidencial() ==null || perdido.getTelefoneResidencial()==null){
			return "Tel. Fixo";
			
		}else{
			return FormatacaoUtil.telefoneComDDD(perdido.getDddResidencial(), perdido.getTelefoneResidencial(), true);
		}
	}
	public String getTelCelFormatado(){
		if(perdido ==null || perdido.getDddCelular() ==null || perdido.getTelefoneCelular()==null){
			return "Não Informado";
		}else{
			return FormatacaoUtil.telefoneComDDD(perdido.getDddCelular(), perdido.getTelefoneCelular(), false);
			
		}
	}
	public String getTelCelVendedor(){
			return "Não Informado";
	}
	public String getDataAnunciada(){
		if(perdido !=null ){
			return FormatacaoUtil.dataPorExtenso(perdido.getDataCadastro());
		}else{
			return "Não Informado";
		}
	}
	public String getDtAcontecimentoExt(){
		if(perdido !=null && perdido.getDtPerdidoAchado() !=null  ){
			return FormatacaoUtil.dataPorExtenso(perdido.getDtPerdidoAchado());
		}else{
			return "Não Informado";
		}
		
	}
	
	public String getAcontecimento(){
		String retorno="";
		if(perdido.getFlAcontecimento().equals("A")){
			retorno="Encontrado:";
		}else{
			retorno="Perdido:";
		}
		return retorno;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getEstado() {
		if(perdido.getEndereco() !=null){
			return perdido.getEndereco().getUf().getLabel();
		}else{
			return "";
		}
		
	}
	public String getCidade() {
		if(perdido.getEndereco()!=null){
			return perdido.getEndereco().getCidade().getNome();
		}else{
			return "";
		}
	}
	public String getBairro() {
		if(perdido.getEndereco() !=null){
			return perdido.getEndereco().getBairro();
		}else{
			return "";
		}
	}

	public Perdido getPerdido() {
		return perdido;
	}

	public void setPerdido(Perdido perdido) {
		this.perdido = perdido;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isTemUsuarioLogado() {
		return temUsuarioLogado;
	}

	public void setTemUsuarioLogado(boolean temUsuarioLogado) {
		this.temUsuarioLogado = temUsuarioLogado;
	}

}
