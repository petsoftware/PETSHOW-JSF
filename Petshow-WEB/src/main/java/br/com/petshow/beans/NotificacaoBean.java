package br.com.petshow.beans;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Animal;
import br.com.petshow.model.Notificacao;
import br.com.petshow.model.Servico;
import br.com.petshow.model.Usuario;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.web.util.CallAnimalRest;
import br.com.petshow.web.util.CallServicoRest;
import br.com.petshow.web.util.CallUsuarioRest;
import br.com.petshow.web.util.MessagesBeanUtil;
import br.com.petshow.web.util.RestUtilCall;
import br.com.tafera.enums.EnumFlTpEstabelecimento;




@ManagedBean
@ViewScoped
public class NotificacaoBean  extends SuperBean<Notificacao>{

	public NotificacaoBean (){
		super();
	}

	private int situacao;
	private String autoCompleteUsuario;
	private Usuario usuario;
	private Usuario userLogado;
	private CallAnimalRest callRestAnimal;
	private CallUsuarioRest callRestUsuariol;
	private CallServicoRest restServico;
	private List<Animal> animais;
	private List<Animal> animaisSelecionados;
	private List<Usuario> usuarios;
	private List<Servico> servicos;
	private Animal animalSelecionado;
	private String usuarioSel;
	private String fieldFocusId = "autoUsuario";
	private Servico servico;
	private boolean mostrarComboBoxAnimal;
	private boolean mostrarLabelAnimal;
    private Date horaSituacao;
    


	public Date getHoraSituacao() {
		return horaSituacao;
	}

	public void setHoraSituacao(Date horaSituacao) {
		this.horaSituacao = horaSituacao;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public void setMostrarComboBoxAnimal(boolean mostrarComboBoxAnimal) {
		this.mostrarComboBoxAnimal = mostrarComboBoxAnimal;
	}

	public void setMostrarLabelAnimal(boolean mostrarLabelAnimal) {
		this.mostrarLabelAnimal = mostrarLabelAnimal;
	}

	@PostConstruct
	public void init() {
		this.autoCompleteUsuario="";
		userLogado 		= getUsuarioLogado(); 
		usuario 		= new Usuario();
		callRestAnimal	= new CallAnimalRest();
		callRestUsuariol= new CallUsuarioRest();
		restServico 	= new CallServicoRest();
		animais 		= new ArrayList<Animal>();
		getServicosBanco();
	}

	public void eventoConsultaAnimais(SelectEvent event) {
    	getAnimaisBanco(((Usuario) event.getObject()).getId());
	 }


	public void getAnimaisBanco(long  id){
		try {
			animais=callRestAnimal.getListAnimal(id);
			if(animais.size()==1){
				animalSelecionado=animais.get(0);
			}else{
				animalSelecionado=null;
			}

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}

	}
	
	public List<Usuario> getclientes(String parteNome){
		try {
			usuarios= callRestUsuariol.getListClienteAutoComplete(userLogado.getId(), parteNome);
		} catch (ExceptionErroCallRest  e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		return usuarios;
	}

	private boolean validate() {
		if(usuario!=null && usuario.getId() == 0){
			MessagesBeanUtil.erroMessage("O dono do animal não foi informado");
			setFieldFocusId("autoUsuario");
			return false;
		}else if(userLogado!=null && userLogado.getId() == 0){
			MessagesBeanUtil.erroMessage("Usuário não está logado!");
			return false;
		}if(animalSelecionado!=null && animalSelecionado.getId() == 0){
			MessagesBeanUtil.erroMessage("Por favor selecione um animal");
			return false;
		}if(servico!=null && servico.getId() == 0){
			MessagesBeanUtil.erroMessage("Por favor selecione um serviço");
			setFieldFocusId("cbServico");
			return false;
		}
		return true;
	}
	
	public String enviarNotificacao(){
	 try {
		 	if(validate()){
		 		enviarEntrega();
		 	}
		} catch (ExceptionErroCallRest  e) {
			MessagesBeanUtil.exception(e);
		} catch (ExceptionValidation e) {
			MessagesBeanUtil.exception(e);
		} catch (Exception e) {
			MessagesBeanUtil.exception(e);
		}
		return null;
	}

	private void enviarEntrega() throws ExceptionErroCallRest, ExceptionValidation {
		HashMap<String, String> parametros = new HashMap<String,String>();
		parametros.put("idUsuarioDestinatario"	, ""+usuario.getId());
		parametros.put("idUsuarioRemetente"		, ""+userLogado.getId());
		parametros.put("idAnimal"				, ""+animalSelecionado.getId());
		parametros.put("idServico"				, ""+servico.getId());
		RestUtilCall.post(parametros, "notificacao/entrega");
		MessagesBeanUtil.infor("Notificação enviada!");
	}
	
	
	public List<Servico> getServicosBanco() {
		try {
			servicos=restServico.getListServico(UsuarioRole.getUsuarioLogado().getId());

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		
		
		return servicos;
	}

	public String getAutoCompleteUsuario() {
		return autoCompleteUsuario;
	}

	public void setAutoCompleteUsuario(String autoCompleteUsuario) {
		this.autoCompleteUsuario = autoCompleteUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public CallAnimalRest getCallRestAnimal() {
		return callRestAnimal;
	}

	public void setCallRestAnimal(CallAnimalRest callRestAnimal) {
		this.callRestAnimal = callRestAnimal;
	}

	public Animal getAnimalSelecionado() {
		return animalSelecionado;
	}

	public void setAnimalSelecionado(Animal animalSelecionado) {
		this.animalSelecionado = animalSelecionado;
	}

	public String getUsuarioSel() {
		return usuarioSel;
	}

	public void setUsuarioSel(String usuarioSel) {
		this.usuarioSel = usuarioSel;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}

	public List<Animal> getAnimais() {
		return animais;
	}
   

	public boolean isMostrarComboBoxAnimal() {
		if(this.animais == null){
    		return false;
    	}
    	return this.animais.size()>1;
	}

	public boolean isMostrarLabelAnimal() {
		if(this.animais == null){
    		return false;
    	}
    	return this.animais.size()==1;
	}

	public CallUsuarioRest getCallRestUsuariol() {
		return callRestUsuariol;
	}

	public void setCallRestUsuariol(CallUsuarioRest callRestUsuariol) {
		this.callRestUsuariol = callRestUsuariol;
	}

	public List<Animal> getAnimaisSelecionados() {
		return animaisSelecionados;
	}

	public void setAnimaisSelecionados(List<Animal> animaisSelecionados) {
		this.animaisSelecionados = animaisSelecionados;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
    public boolean mostrarGrid(){
    	
    	if(animais.size()>1){
    		return true;
    	}else{
    		return false;
    	}
    	
    }
    public boolean mostrarlabel(){
    	
    	if(animais.size()==1){
    		return true;
    	}else{
    		return false;
    	}
    	
    }

	public CallServicoRest getRestServico() {
		return restServico;
	}

	public void setRestServico(CallServicoRest restServico) {
		this.restServico = restServico;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}
	
	public boolean mostrarTxtHora(){
		if(situacao==3 ||situacao==4){
			return true;
		}
		return false;
	}
	
	public String getDescricao() {
		String result = "Não foi possível determinar";
		if(userLogado!=null){
			EnumFlTpEstabelecimento tipo = userLogado.getFlTpEstabelecimento();
			String userName = userLogado.getNome();
			if(tipo == EnumFlTpEstabelecimento.PETSHOP){
				result = "PETSHOP " + userName;
			}else if(tipo == EnumFlTpEstabelecimento.ONG){
				result = "ONG " + userName;
			}else if (tipo == EnumFlTpEstabelecimento.USER){
				result = "Usuário " + userName;
			}
		}
		return result;
	}

	public Usuario getUserLogado() {
		return userLogado;
	}

	public void setUserLogado(Usuario userLogado) {
		this.userLogado = userLogado;
	}

	public String getFieldFocusId() {
		return fieldFocusId;
	}

	public void setFieldFocusId(String fieldFocusId) {
		this.fieldFocusId = fieldFocusId;
	}
}
