package br.com.petshow.web.util;

import java.io.IOException;

import javax.servlet.http.Part;

import org.bouncycastle.util.encoders.Base64;

public class ImagemUtil {

	public static String transformBase64AsString(Part imagem){
		if(imagem == null) 
			return null;
		byte[] imageAsByte = new byte[(int) imagem.getSize()];
		try {
			imagem.getInputStream().read(imageAsByte);
		} catch (IOException e) {

			e.printStackTrace();
		}
		String base64AsString = new String(Base64.encode(imageAsByte));
		return base64AsString;
	}
}
