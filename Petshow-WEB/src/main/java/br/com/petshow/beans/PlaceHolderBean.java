package br.com.petshow.beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import br.com.petshow.util.PlaceHolderUtil;

@ManagedBean
@RequestScoped
public class PlaceHolderBean {

	
	public  String getSelEstado() {
		return PlaceHolderUtil.getSelEstado();
	}



	public  String getSelCidade() {
		return PlaceHolderUtil.getSelCidade();
	}



	public  String getSelBairro() {
		return PlaceHolderUtil.getSelBairro();
	}



	public  String getSelUsuario() {
		return PlaceHolderUtil.getSelUsuario();
	}
	
	public  String getSelSexo() {
		return PlaceHolderUtil.getSelSexo();
	}
	
	public  String getSelAnimal() {
		return PlaceHolderUtil.getSelAnimal();
	}
	
	public  String getSelFase() {
		return PlaceHolderUtil.getSelFase();
	}
	public  String getSelServico() {
		return PlaceHolderUtil.getSelServico();
	}
}
