package br.com.petshow.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.petshow.model.Adocao;

@ManagedBean
@RequestScoped
public class ViewAdocaoDetalheBean {
	
	private List<String> fotos;
	private List<String> idAdocao = new ArrayList<>();
	private String srtIdAdoca = "";
	private Adocao adocao;
	
	public void onload() {
		System.out.println("onload");
	}
	
	
	@PostConstruct
	public void init() {
		System.out.println("init view fotos adocao");
//		try {
//			if(getIdAdocao() != null && getIdAdocao().size() > 0){
//				String id = getIdAdocao().get(0);
//				adocao = RestUtilCall.getEntity("adocao/"+id, Adocao.class);
//			}
//		} catch (ExceptionErroCallRest | ExceptionValidation e) {
//			adocao = new Adocao();
//		}
	}

	public List<String> getFotos() {
//		try {
//			if(getIdAdocao() != null && getIdAdocao().size() > 0){
//				String id = getIdAdocao().get(0);
//				adocao = RestUtilCall.getEntity("adocao/"+id, Adocao.class);
//			}else{
//				adocao = new Adocao();
//			}
//		} catch (ExceptionErroCallRest | ExceptionValidation e) {
//			adocao = new Adocao();
//		}
//		return adocao.getFotos();
		return fotos;
	}

	public void setFotos(List<String> fotos) {
		this.fotos = fotos;
	}


	public Adocao getAdocao() {
		return adocao;
	}

	public void setAdocao(Adocao adocao) {
		this.adocao = adocao;
	}

	public List<String> getIdAdocao() {
		return idAdocao;
	}

	public void setIdAdocao(List<String> idAdocao) {
		this.idAdocao = idAdocao;
	}


	public String getSrtIdAdoca() {
		return srtIdAdoca;
	}


	public void setSrtIdAdoca(String srtIdAdoca) {
		this.srtIdAdoca = srtIdAdoca;
	}

}
