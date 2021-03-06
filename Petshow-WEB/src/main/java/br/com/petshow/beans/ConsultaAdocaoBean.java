package br.com.petshow.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.petshow.enums.EnumFaseVida;
import br.com.petshow.enums.EnumPorteAnimal;
import br.com.petshow.enums.EnumSexo;
import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.enums.EnumUF;
import br.com.petshow.exceptions.ExceptionErroCallRest;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.model.Cidade;
import br.com.petshow.model.Estado;
import br.com.petshow.model.Racas;
import br.com.petshow.objects.query.AdocaoQuery;
import br.com.petshow.web.util.CallAnimalRest;
import br.com.petshow.web.util.CallRacasRest;

@ManagedBean
@ViewScoped
public class ConsultaAdocaoBean extends SuperBean<Adocao> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6287940244391936527L;
	private int totalRows = 0;
	private int totalRowsAdocaoPerfil = 0;
	private List<Adocao> adocoes;
	private Adocao selectedAdocao;
	private List<Adocao> adocoesDisponiveis;
	private List<Adocao> adocoesDisponiveisPorPerfil;
	private CallAnimalRest restAnimal;
	private CallRacasRest callRacasRest;
	@ManagedProperty(value="#{autoCompleteBean}")
    private AutoCompleteBean autoCompleteBean;
	private List<String> fotos;
	private Estado estado;
	private EnumUF uf;
	private Cidade cidade;
	private EnumTipoAnimal animal;
	private EnumSexo sexo;
	private EnumFaseVida fase;
	private List<Racas> racas;
	
	
	
	@PostConstruct
	public void init() {
		this.adocoes 	= new ArrayList<Adocao>();
		restAnimal 		= new CallAnimalRest();
		callRacasRest	= new CallRacasRest();
		obterAdocoesDisponiveis();
		obterAdocoesPorPerfil();
		setTotalRows(getAdocoesDisponiveis().size());
		System.out.println("@PostConstruct"+this.getClass().getName());
	}
	public ConsultaAdocaoBean (){
		super();
	}

	public String chamarTelaDeCadastroDeAdocao() {
		if(getAuthenticationService().isAuthenticated()){
			return "anunciar-adocao-site";
		}else{
			return "do-login";
		}
	}
	
	public String obterAdocoesDisponiveis() {
		AdocaoQuery query = new AdocaoQuery();
		query.setFase(getFase());
		query.setSexo(getSexo() );
		query.setTpAnimal(getAnimal());
		query.setUf(getUf());
		query.setCidade(getCidade());
		try {
			setAdocoesDisponiveis(restAnimal.getListAnimalDisponiveisAdocao(query));
		} catch (ExceptionErroCallRest | ExceptionValidation e) {
			setAdocoesDisponiveis(new ArrayList<>());
		}
		return null;
	}	
	
	public EnumTipoAnimal[] getTiposAnimais(){
		return EnumTipoAnimal.values();
	}
	
	public EnumFaseVida[] getFases(){
		return EnumFaseVida.values();
	}
	
	public EnumSexo[] getSexos(){
		return EnumSexo.values();
	}
	
	public List<Adocao> getAdocoes() {
		return adocoes;
	}
	public void setAdocoes(List<Adocao> adocoes) {
		this.adocoes = adocoes;
	}
	public CallAnimalRest getRestAnimal() {
		return restAnimal;
	}
	public void setRestAnimal(CallAnimalRest restAnimal) {
		this.restAnimal = restAnimal;
	}
	public AutoCompleteBean getAutoCompleteBean() {
		return autoCompleteBean;
	}
	public void setAutoCompleteBean(AutoCompleteBean autoCompleteBean) {
		this.autoCompleteBean = autoCompleteBean;
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
	
	public List<Adocao> getAdocoesDisponiveis() {
		return adocoesDisponiveis;
	}
	public void setAdocoesDisponiveis(List<Adocao> adocoesDisponiveis) {
		this.adocoesDisponiveis = adocoesDisponiveis;
	}
	public EnumTipoAnimal getAnimal() {
		return animal;
	}
	public void setAnimal(EnumTipoAnimal animal) {
		this.animal = animal;
	}
	public EnumSexo getSexo() {
		return sexo;
	}
	public void setSexo(EnumSexo sexo) {
		this.sexo = sexo;
	}
	public EnumFaseVida getFase() {
		return fase;
	}
	public void setFase(EnumFaseVida fase) {
		this.fase = fase;
	}
	public int getTotalRows() {
		totalRows = 0;
		if(getAdocoesDisponiveis() != null){
			totalRows = getAdocoesDisponiveis().size();
		}
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	
	public void openGalleryDialog(Adocao adocao) {
		Map<String, List<String>> params = montarParametros(adocao);
		Map<String,Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("width", 300);
		options.put("height", 300);
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("headerElement", "customheader");

		RequestContext.getCurrentInstance().openDialog("view-adocao-detalhe", options, null);
	}
	
	private Map<String, List<String>> montarParametros(Adocao adocao) {
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		List<String> id = Arrays.asList(String.valueOf(adocao.getId()));
	    params.put("idAdocao", id);  
	    return params;
	}
	
	public Adocao getSelectedAdocao() {
		return selectedAdocao;
	}
	public void setSelectedAdocao(Adocao selectedAdocao) {
		this.selectedAdocao = selectedAdocao;
	}
	public List<String> getFotos() {
		return fotos;
	}
	public void setFotos(List<String> fotos) {
		this.fotos = fotos;
	}
	public EnumUF getUf() {
		return uf;
	}
	public void setUf(EnumUF uf) {
		this.uf = uf;
	}
	
	public List<Adocao> getAdocoesDisponiveisPorPerfil() {
		return adocoesDisponiveisPorPerfil;
	}
	private void obterAdocoesPorPerfil() {
		try {
			if(getUsuarioLogado() != null){	
				adocoesDisponiveisPorPerfil = restAnimal.getListAnimalDisponiveisAdocaoPorPerfil(getUsuarioLogado());
			}
		} catch (ExceptionErroCallRest | ExceptionValidation e) {

			e.printStackTrace();
		}
	}
	public void setAdocoesDisponiveisPorPerfil(List<Adocao> adocoesDisponiveisPorPerfil) {
		this.adocoesDisponiveisPorPerfil = adocoesDisponiveisPorPerfil;
	}
	public int getTotalRowsAdocaoPerfil() {
		totalRowsAdocaoPerfil = 0;
		if(getAdocoesDisponiveisPorPerfil() != null){
			totalRowsAdocaoPerfil = getAdocoesDisponiveisPorPerfil().size();
		}
		return totalRowsAdocaoPerfil;
	}
	public void setTotalRowsAdocaoPerfil(int totalRowsAdocaoPerfil) {
		this.totalRowsAdocaoPerfil = totalRowsAdocaoPerfil;
	}
	
	public EnumPorteAnimal[] getPortes() {
		return EnumPorteAnimal.values();
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
}
