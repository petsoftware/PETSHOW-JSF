package br.com.petshow.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class InicialBean {
	
	
	public InicialBean (){
		super();
	}
	
	

/*	private CallVendaRest restVenda;
	private CallAnimalRest restAnimal;
	private List<Venda> vendas;
	private List<Adocao> adocoes;
	private List<Perdido> perdidos;*/
	
	@ManagedProperty(value="#{consultaClassificadoBean}")
    private ConsultaClassificadoBean consultaClassificadoBean;
	
	@ManagedProperty(value="#{consultaAdocaoBean}")
    private ConsultaAdocaoBean consultaAdocaoBean;
	
	@ManagedProperty(value="#{consultaPerdidoBean}")
    private ConsultaPerdidoBean consultaPerdidoBean;
	
	@PostConstruct
	public void init() {
		
		
		
		
		
		/*this.perdidos = new ArrayList<Perdido>();
		this.adocoes = new ArrayList<Adocao>();
		this.vendas = new ArrayList<Venda>();
		restAnimal = new CallAnimalRest();
		restVenda = new CallVendaRest();
		getPerdidosBanco();
		getVendasBanco();
		getAdocaoBanco();*/
	}

	public ConsultaClassificadoBean getConsultaClassificadoBean() {
		return consultaClassificadoBean;
	}

	public void setConsultaClassificadoBean(ConsultaClassificadoBean consultaClassificadoBean) {
		this.consultaClassificadoBean = consultaClassificadoBean;
	}

	public ConsultaAdocaoBean getConsultaAdocaoBean() {
		return consultaAdocaoBean;
	}

	public void setConsultaAdocaoBean(ConsultaAdocaoBean consultaAdocaoBean) {
		this.consultaAdocaoBean = consultaAdocaoBean;
	}

	public ConsultaPerdidoBean getConsultaPerdidoBean() {
		return consultaPerdidoBean;
	}

	public void setConsultaPerdidoBean(ConsultaPerdidoBean consultaPerdidoBean) {
		this.consultaPerdidoBean = consultaPerdidoBean;
	}

	
	public String chamar(){
		System.out.println("chamou");
		return"teste";	 
	}
	
	
/*	public List<Perdido> getPerdidosBanco() {
		try {
			
			
			perdidos=restAnimal.getListAnimalPerdidoAchado(0, 0,0 ,null,null,12);

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		
		
		return perdidos;
	}
	
	public List<Venda> getVendasBanco() {
		try {
			
			
			vendas=restVenda.getListVenda(null, 0, 0,12);

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		
		
		return vendas;
	}

	public List<Adocao> getAdocaoBanco() {
		try {
			
			
			adocoes=restAnimal.getListAnimalAdocao(0l, 0l, null, null, null,12);

		} catch (ExceptionErroCallRest  e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ??:", e.getMessage()));

		} catch (ExceptionValidation e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado:", "Favor entrar em contato com o admistrador do sistema!"));
			e.printStackTrace();
		}
		
		
		return adocoes;
	}*/
	
	
}
