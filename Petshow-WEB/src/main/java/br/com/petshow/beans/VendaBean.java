package br.com.petshow.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.primefaces.event.FileUploadEvent;

import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Bairro;
import br.com.petshow.model.Cidade;
import br.com.petshow.model.Usuario;
import br.com.petshow.model.Venda;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.util.CollectionUtil;
import br.com.petshow.web.util.CallVendaRest;
import br.com.petshow.web.util.ImagemUtil;
import br.com.petshow.web.util.RestUtilCall;

@ManagedBean
@ViewScoped
public class VendaBean {
	public VendaBean (){
		super();
		
	}
	
	private Venda venda;
	private List<Venda> vendas;
	private CallVendaRest restVenda;
	private Usuario usuarioLogado;
	@ManagedProperty(value="#{autoCompleteBean}")
    private AutoCompleteBean autoCompleteBean;

	private Part imagem1;
	private Part imagem2;
	private Part imagem3;
	private List<String> tempList = new ArrayList<>();

	public List<String> getTempList() {
		return tempList;
	}


	public void setTempList(List<String> tempList) {
		this.tempList = tempList;
	}


	@PostConstruct
	public void init() {
		this.venda	= new Venda();
		this.vendas = new ArrayList<Venda>();
		restVenda 	= new CallVendaRest();
		usuarioLogado=UsuarioRole.getUsuarioLogado();
		venda.setUsuario(usuarioLogado);
		
		getVendasBanco();
	}
	
	
	public void salvarVenda(){
		try {
			
			List<String> fotos = venda.getFotos();
			if(fotos ==null){
				fotos = new ArrayList<String>();
			}
			
			if(imagem1 !=null && !ImagemUtil.transformBase64AsString(imagem1).trim().equals("")){
				fotos.add(ImagemUtil.transformBase64AsString(imagem1));
			}
			if(imagem2 !=null && !ImagemUtil.transformBase64AsString(imagem2).trim().equals("")){
				fotos.add(ImagemUtil.transformBase64AsString(imagem2));
			}
			if(imagem3 !=null && !ImagemUtil.transformBase64AsString(imagem3).trim().equals("")){
				fotos.add(ImagemUtil.transformBase64AsString(imagem3));
			}
			venda.setFotos(fotos);
			
			venda =  RestUtilCall.postEntity(venda, "venda/salvar",Venda.class);
			getVendasBanco(); 
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Classificado foi salvo com sucesso!"));
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}

	}
	
	public void excluirVenda(){
		if(venda.getId() ==0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na Exclusão:", "Nenhum classificado foi selecionado !"));
		}else{
			excluirVenda(venda.getId());
		}
	}
	
	public void excluirVenda(long id){
		try{


			RestUtilCall.delete("venda/"+id);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK:","Classificado foi excluido com sucesso!"));
			getVendasBanco(); 
		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
	}
	
	public void enviaImagem(FileUploadEvent event) {
            
            if(this.venda!=null){
            	if(venda.getFotos().size() >= 5){
            		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Número de imagens excedido", "Só é permitido por até 5 imagens!"));
            	}else{
            		venda.getFotos().add(ImagemUtil.transformBase64AsString(event.getFile().getContents()));
            	}
            }
    }
	
	public void novo(){
		try{

			venda = new Venda();
			venda.setUsuario(UsuarioRole.getUsuarioLogado());
			getAutoCompleteBean().setCidades(new ArrayList<Cidade>());
			getAutoCompleteBean().setBairros(new ArrayList<Bairro>());
			
			

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}


	}

	public void selecionar(Venda venda){
		this.venda=venda;
//		
//		if(venda.getEstado()!=null){
//			getAutoCompleteBean().consultaCidades(venda.getEstado());
//			if(venda.getCidade()!=null){
//				getAutoCompleteBean().consultaBairros(venda.getCidade());;
//			}
//		}
		

	}
	public List<Venda> getVendasBanco() {
		try {
			vendas=restVenda.getListVenda(usuarioLogado.getId());

		} catch (ExceptionErroCallRest  e) {
			// erro: nao está mostrando a mensavem
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		
		
		return vendas;
	}


	public Venda getVenda() {
		return venda;
	}


	public void setVenda(Venda venda) {
		this.venda = venda;
	}


	public List<Venda> getVendas() {
		return vendas;
	}


	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}


	public CallVendaRest getRestVenda() {
		return restVenda;
	}


	public void setRestVenda(CallVendaRest restVenda) {
		this.restVenda = restVenda;
	}


	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}


	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}


	public Part getImagem1() {
		return imagem1;
	}


	public void setImagem1(Part imagem1) {
		this.imagem1 = imagem1;
	}


	public Part getImagem2() {
		return imagem2;
	}


	public void setImagem2(Part imagem2) {
		this.imagem2 = imagem2;
	}


	public Part getImagem3() {
		return imagem3;
	}


	public void setImagem3(Part imagem3) {
		this.imagem3 = imagem3;
	}


	public AutoCompleteBean getAutoCompleteBean() {
		return autoCompleteBean;
	}


	public void setAutoCompleteBean(AutoCompleteBean autoCompleteBean) {
		this.autoCompleteBean = autoCompleteBean;
	}
	
	public void removePhoto(String photoInBase64) {
		if(this.venda != null){
			if(this.venda.getFotos().isEmpty() == false){
				this.venda.setFotos(CollectionUtil.removeItem(this.venda.getFotos(), photoInBase64));
			}
		}
	}
	
	public boolean isComFoto() {
		if(this.venda != null){
			if(this.venda.getFotos().isEmpty()){
				return false;
			}else{
				return true;
			}
		}
		return false;
	}
	
}
