package br.com.petshow.beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.petshow.enums.EnumCategoria;
import br.com.petshow.enums.EnumTipoClassificado;
import br.com.petshow.enums.EnumUF;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Bairro;
import br.com.petshow.model.Cidade;
import br.com.petshow.model.Estado;
import br.com.petshow.model.Venda;
import br.com.petshow.objects.query.VendasQuery;
import br.com.petshow.web.util.CallVendaRest;

@ManagedBean
@ViewScoped
public class ConsultaClassificadoBean {
	
	private List<Venda> vendas;
	private CallVendaRest restVenda;
	@ManagedProperty(value="#{autoCompleteBean}")
    private AutoCompleteBean autoCompleteBean;
	private String palavraChave;
	private Estado estado;
	private Cidade cidade;
	private EnumUF uf;
	private EnumCategoria categoria;
	private EnumTipoClassificado tpClassificado;
	private String descResumida;
	private Bairro bairro;
	private List<EnumCategoria> categorias;
	@PostConstruct
	public void init() {
		this.vendas = new ArrayList<Venda>();
		restVenda = new CallVendaRest();
		
		getVendasBanco();
	}
	public ConsultaClassificadoBean (){
		super();
	}
	
	public List<Venda> buscar() {
		VendasQuery query = new VendasQuery();
		try {
			query.setCategoria(getCategoria());
			query.setCidade(getCidade());
			query.setDescResumida(getDescResumida());
			query.setEnumTipoClassificado(getTpClassificado());
			query.setUf(getUf());
			setVendas(restVenda.buscarAnunciosClassificador(query));
			return getVendas();
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			return new ArrayList<>();
		}
	}
	
	public String redirectDetalhe(long id){
		return "classificado-detalhe?id="+id;
	}
	
	public void selecionar(Venda venda){

	}

	
	public List<Venda> getVendasBanco() {
		try {
			long idEstado=0;
			long idCidade=0;
			if(estado!=null){
				idEstado=estado.getId();
			}
			if(cidade!=null){
				idCidade=cidade.getId();
			}
			vendas=restVenda.getListVenda(palavraChave, idCidade, idEstado);
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
	
	public List<EnumCategoria> obterCategoriaByTtype(EnumTipoClassificado type) {
		setCategorias(EnumCategoria.getListEnum(type));
		return categorias;
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

	public AutoCompleteBean getAutoCompleteBean() {
		return autoCompleteBean;
	}


	public void setAutoCompleteBean(AutoCompleteBean autoCompleteBean) {
		this.autoCompleteBean = autoCompleteBean;
	}

	public String getPalavraChave() {
		return palavraChave;
	}

	public void setPalavraChave(String palavraChave) {
		this.palavraChave = palavraChave;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public EnumUF getUf() {
		return uf;
	}
	public void setUf(EnumUF uf) {
		this.uf = uf;
	}
	public Bairro getBairro() {
		return bairro;
	}
	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}
	public EnumCategoria getCategoria() {
		return categoria;
	}
	public void setCategoria(EnumCategoria categoria) {
		this.categoria = categoria;
	}
	public EnumTipoClassificado getTpClassificado() {
		return tpClassificado;
	}
	public void setTpClassificado(EnumTipoClassificado tpClassificado) {
		this.tpClassificado = tpClassificado;
	}
	public String getDescResumida() {
		return descResumida;
	}
	public void setDescResumida(String descResumida) {
		this.descResumida = descResumida;
	}
	public void setCategorias(List<EnumCategoria> categorias) {
		this.categorias = categorias;
	}
	
	public EnumTipoClassificado[] getTiposDeClassicados() {
		return EnumTipoClassificado.values();
	}
	public List<EnumCategoria> getCategorias() {
		return categorias;
	}
	
}
