package br.com.petshow.beans;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.ws.rs.core.Response;

import br.com.petshow.model.Adocao;
@ManagedBean
public class AdocaoBean extends SuperBean{

	public AdocaoBean (){
		super();
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
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(response!=null)response.close();
		}
		return null;
	}

}
