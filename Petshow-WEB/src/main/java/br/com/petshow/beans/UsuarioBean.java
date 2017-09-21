package br.com.petshow.beans;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.primefaces.event.FileUploadEvent;

import br.com.petshow.enums.EnumFlTpEstabelecimento;
import br.com.petshow.enums.EnumTipoUser;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Endereco;
import br.com.petshow.model.Usuario;
import br.com.petshow.model.UsuarioCliente;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.web.util.ImagemUtil;
import br.com.petshow.web.util.MessagesBeanUtil;
import br.com.petshow.web.util.RestUtilCall;


@ManagedBean
@ViewScoped
public class UsuarioBean extends SuperBean<Usuario>{

	public UsuarioBean (){
		super();
		
	}
	private Usuario usuario;
	private Part imagem;
	private String cb;	
	private boolean temFoto = false;
	public String getCb() {
		return cb;
	}

	public void setCb(String cb) {
		this.cb = cb;
	}
	
	public EnumFlTpEstabelecimento[] getTipos() {
		return EnumFlTpEstabelecimento.values();
	}

	@PostConstruct
	public void init() {
		this.usuario= new Usuario();

		if(UsuarioRole.getUsuarioLogado() !=null){
			try {
				this.usuario= 	RestUtilCall.getEntity("usuario/"+UsuarioRole.getUsuarioLogado().getId(),Usuario.class);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
				e.printStackTrace();
			};
		}else{
			this.usuario= new Usuario();
		}
	}

	public void solicitarCadastro(){
		try {
			usuario = RestUtilCall.postEntity(usuario, "usuario/precadastro",Usuario.class);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Solicitação de cadastro foi efetivado com sucesso!Em até 1 dia util receberá um email com instruções!"));
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}

	public void salvarUsuarioEstabelecimento(){
		try {
			usuario.setTipoUser(EnumTipoUser.USER);
			usuario.setFlTpEstabelecimento(EnumFlTpEstabelecimento.USER);
			usuario = RestUtilCall.postEntity(usuario, "usuario/salvar",Usuario.class);
			UsuarioCliente userCli = new UsuarioCliente();
			userCli.setCliente(usuario);
			userCli.setEstabelecimento(getUsuarioLogado());
			userCli.setDataCadastro(new Date());
			String result = RestUtilCall.postEntity(userCli, "usuario/salvar/usercli",String.class);
			MessagesBeanUtil.infor("Usuário salvo com sucesso", result);
		} catch (ExceptionErroCallRest  e) {
			MessagesBeanUtil.exception(e);
		} catch (ExceptionValidation e) {
			MessagesBeanUtil.exception(e);
		} catch (Exception e) {
			MessagesBeanUtil.exception(e);
		}
	}

	public void salvarUsuario(){
		try {
			usuario = RestUtilCall.postEntity(usuario, "usuario/salvar",Usuario.class);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Alteração efetuada com sucesso!"));
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}

	public void consultaCEP(){
		try {
				HashMap<String,String> retorno = RestUtilCall.getHashMap("endereco/consulta/Cep/"+usuario.getNrCep());
				usuario.setCidade(retorno.get("cidade"));
				usuario.setEstado(retorno.get("estado"));
		
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}

	public String getLblNmLogin(){
		if(usuario==null){
			return "Login:";
		}else{
			return "Login:"+usuario.getNmLogin();
		}
	}
	public String getLblCNPJ(){
		if(usuario==null){
			return "CNPJ:";
		}else{
			String cnpjFormatado=usuario.getCnpjCpf();
			if(cnpjFormatado!=null){	
				for(int i =usuario.getCnpjCpf().length();i<14;++i){
					cnpjFormatado="0"+cnpjFormatado;
				}
				return "CNPJ:"+cnpjFormatado.substring(0, 2) + "." + cnpjFormatado.substring(2, 5) + "." + cnpjFormatado.substring(5, 8 ) + "/" + cnpjFormatado.substring(8, 12) + "-" + cnpjFormatado.substring(12, 14);
			}else{
				return "CNPJ:";
			}
			
			
		}
	}

	public String getLblEmail(){
		if(usuario==null){
			return "E-mail";
		}else{
			return "E-mail:"+usuario.getEmail();
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Part getImagem() {
		return imagem;
	}

	public void setImagem(Part imagem) {
		this.imagem = imagem;
	}
	
	public void enviaImagem(FileUploadEvent event) {
        
        if(this.usuario!=null){
        	this.usuario.setFoto(ImagemUtil.transformBase64AsString(event.getFile().getContents()));
        }
	}

	public boolean isTemFoto() {
		if(usuario.getFoto() != null){
			temFoto = !usuario.getFoto().isEmpty();
		}else{
			temFoto = false;
		}
		return temFoto;
	}

	public void setTemFoto(boolean temFoto) {
		this.temFoto = temFoto;
	}

}
