package br.com.petshow.beans;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.enums.EnumAssuntoNotificacao;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Notificacao;
import br.com.petshow.model.Perdido;
import br.com.petshow.model.Usuario;
import br.com.petshow.util.FormatacaoUtil;
import br.com.petshow.web.util.CallNotificacaoRest;
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
	private String tipo;
	private boolean encontrado;
	
	private Usuario usuario= null;
	
	@PostConstruct
	public void init() {
		this.perdido= new Perdido();
		id   = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap() .get("id");
		tipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap() .get("tipo");
		getAdocaoBanco();
		if(tipo != null){
			setEncontrado(true);
		}else{
			setEncontrado(false);
		}
		verificarSeUsuarioLogado();
	}
	
	private void preencherCampos(Usuario usuario) {
		nome = usuario.getNome();
		email = usuario.getEmail();
		if(usuario.getDdd() != null && usuario.getTelefone() !=null && usuario.getDdd() > 0 && usuario.getTelefone()>0){
			telefone = usuario.getDdd()+""+usuario.getTelefone();
		}
		
	}
	

	private void verificarSeUsuarioLogado() {
		if(getUsuarioLogado() != null){
			usuario =  getUsuarioLogado();
			preencherCampos( usuario);
		}
	}

	public void getAdocaoBanco(){
		try {
			perdido = RestUtilCall.getEntity("perdido/perdido/"+id,Perdido.class);
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
			Notificacao notificacao = new Notificacao();
			notificacao.setPerdido(getPerdido());
			notificacao.setAdocao(null);
			notificacao.setContato(getTelefone());
			notificacao.setDtNotificacao(new Date());
			notificacao.setEmail(getEmail());
			notificacao.setFlExcluida(false);
			notificacao.setMsgNotificacao(getMensagem());
			notificacao.setNome(getNome());
			notificacao.setTpNotificacao("P");
			notificacao.setAssuntoNotificacao(EnumAssuntoNotificacao.PERDIDO);
			notificacao.setUsuarioDestinatario(getPerdido().getUsuario());
			if(usuario!=null){
				notificacao.setUsuarioRemetente(usuario);
			}else{
				notificacao.setUsuarioRemetente(null);
			}
			CallNotificacaoRest.postEntity(notificacao, "notificacao/salvar/", Notificacao.class);
			MessagesBeanUtil.inforClient("","Enviado com sucesso!","msgEnviar");
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

	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isEncontrado() {
		return encontrado;
	}

	public void setEncontrado(boolean encontrado) {
		this.encontrado = encontrado;
	}
	public String racaInformado(){
		
		if(perdido.getRaca() == null){
			return "Não informada";
		}
	
		return perdido.getRaca().getDescricao();
		
		
	}
	public String corInformado(){
		
		if(perdido.getTpCorPrincipal() == null){
			return "Não informada";
		}
	
		return perdido.getTpCorPrincipal().getDesc();
		
		
	}
}
