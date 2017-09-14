package br.com.petmooby.beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class PageHelperBean {

	private String mainTemplate 		= "../templates/main.xhtml";
	private String mainTemplatePrivate 	= "../private-area/main.xhtml";
	private String mainTemplateSite 	= "/templates/site/main.xhtml";

	public String getMainTemplate() {
		return mainTemplate;
	}

	public void setMainTemplate(String mainTemplate) {
		this.mainTemplate = mainTemplate;
	}

	public String getMainTemplateSite() {
		return mainTemplateSite;
	}

	public void setMainTemplateSite(String mainTemplateSite) {
		this.mainTemplateSite = mainTemplateSite;
	}

	public String getMainTemplatePrivate() {
		return mainTemplatePrivate;
	}

	public void setMainTemplatePrivate(String mainTemplatePrivate) {
		this.mainTemplatePrivate = mainTemplatePrivate;
	}
	
}
