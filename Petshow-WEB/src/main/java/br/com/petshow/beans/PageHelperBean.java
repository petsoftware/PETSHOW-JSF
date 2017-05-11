package br.com.petshow.beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class PageHelperBean {

	private String mainTemplate = "../templates/main.xhtml";

	public String getMainTemplate() {
		return mainTemplate;
	}

	public void setMainTemplate(String mainTemplate) {
		this.mainTemplate = mainTemplate;
	}
	
}
