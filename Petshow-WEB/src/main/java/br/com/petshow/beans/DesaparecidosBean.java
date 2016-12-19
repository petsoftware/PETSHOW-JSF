package br.com.petshow.beans;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.servlet.http.Part;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bouncycastle.util.encoders.Base64;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import br.com.petshow.dao.DesaparecidosDAO;
import br.com.petshow.model.Desaparecidos;
import br.com.petshow.model.Entidade;
import br.com.petshow.role.DesaparecidosRole;
import br.com.petshow.util.FileApplicationUtil;

@ManagedBean
public class DesaparecidosBean {//extends SuperBean{

	@ManagedProperty("#{desaparecidosRole}")
	private DesaparecidosRole desaparecidosRole;
	@ManagedProperty("#{desaparecidosDAO}")
	private DesaparecidosDAO desaparecidosDAO;
	private Desaparecidos desaparecidos;
	private BufferedImage image; 
	private Part imagem;
	protected static ResteasyClient client;
	protected static ResteasyWebTarget target;
	//public static final String URL_BASE = FileApplicationUtil.getUrlBaseREST();
	
	protected Response post(String url, Entidade entidade) {
		target = client.target("URL_BASE"+url);
		Response response = target.request().post(Entity.entity(entidade, MediaType.APPLICATION_JSON));
		return response;
	}
	@PostConstruct
	public void init() {
		this.desaparecidos = new Desaparecidos();
		client = new ResteasyClientBuilder().build();
	}

	public Desaparecidos getDesaparecidos() {
		return desaparecidos;
	}

	public void setDesaparecidos(Desaparecidos desaparecidos) {
		this.desaparecidos = desaparecidos;
	}
	
	 public String salvar() throws Exception{
         byte[] imageAsByte = new byte[(int) imagem.getSize()];
         Response response = null;
         try {
                imagem.getInputStream().read(imageAsByte);
                String base64AsString = new String(Base64.encode(imageAsByte));
                desaparecidos.setFoto(base64AsString);
                response = post("desaparecidos/insert", desaparecidos);
                if (response.getStatus() != 200) {
    				throw new RuntimeException("Failed : HTTP error code : "
    		                          + response.getStatus());
    			}
    			System.out.println("Server response : \n");
    			System.out.println(response.readEntity(String.class));
    			//response.close();
                //System.out.println(base64AsString);
                //desaparecidosRole.insert(desaparecidos);
         } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
         }finally {
			if(response!=null)response.close();
		}
         return null;
   }
	 
	 public List<Desaparecidos> getAll() {
		return this.desaparecidosRole.findAll();
	 }
	 
	public Part getImagem() {
		return imagem;
	}

	public void setImagem(Part imagem) {
		this.imagem = imagem;
	}

	public DesaparecidosRole getDesaparecidosRole() {
		return desaparecidosRole;
	}

	public void setDesaparecidosRole(DesaparecidosRole desaparecidosRole) {
		this.desaparecidosRole = desaparecidosRole;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public DesaparecidosDAO getDesaparecidosDAO() {
		return desaparecidosDAO;
	}

	public void setDesaparecidosDAO(DesaparecidosDAO desaparecidosDAO) {
		this.desaparecidosDAO = desaparecidosDAO;
	}
}
