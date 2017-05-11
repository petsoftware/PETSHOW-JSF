package br.com.petshow.beans;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.core.Response;

import br.com.petshow.model.Adocao;
@ManagedBean
public class AdocaoBean extends SuperBean{

	private Adocao adocao;
	private List<Adocao> adocoes;
	
	public List<Adocao> getAdocoes() {
		return adocoes;
	}

	public void setAdocoes(List<Adocao> adocoes) {
		this.adocoes = adocoes;
	}

	public AdocaoBean (){
		super();
	}
	
	public Adocao getAdocao() {
		return adocao;
	}

	public void setAdocao(Adocao adocao) {
		this.adocao = adocao;
	}

	@PostConstruct
	public void init() {
		if(adocao == null){
			adocao = new Adocao();
		}
	}
	
	public String send() {

		Adocao adocao = new Adocao();
		adocao.setDataAdocao(new Date());
		Response response=null;
		try{
			response = post("Adocao/post", adocao);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
		                          + response.getStatus());
			}
//			System.out.println("Server response : \n");
//			System.out.println(response.readEntity(String.class));
			System.out.println(Adocao.class);;
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(response!=null)response.close();
		}
		return null;
	}

}
