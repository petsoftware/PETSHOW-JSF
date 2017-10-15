package br.com.petshow.web.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;

import org.bouncycastle.util.encoders.Base64;
import org.primefaces.model.UploadedFile;

import br.com.petshow.enums.EnumSizePhoto;

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
	
	public static String transformBase64AsString(byte[] imageAsByte){
		String base64AsString = new String(Base64.encode(imageAsByte));
		return base64AsString;
	}
	public static byte[] resizeImage(UploadedFile file ,EnumSizePhoto size ) {
		return resizeImage( file ,size.getSize());
	}
	public static byte[] resizeImage(UploadedFile file ,int maxSize ) {


		BufferedImage resizeImageJpg = null;
		byte[] imageBytes = null ;
		BufferedImage originalImage =null;
		try{
			originalImage = ImageIO.read(file.getInputstream());
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();


		if(maxSize < originalImage.getHeight() || maxSize < originalImage.getWidth() ){
			BigDecimal scale = new BigDecimal(maxSize);

			scale = scale.divide(new BigDecimal(originalImage.getWidth()),7, RoundingMode.HALF_UP) ;

			BigDecimal height = new BigDecimal(originalImage.getHeight());
			height =  height.multiply(scale);


			BufferedImage resizedImage = new BufferedImage(maxSize, height.toBigInteger().intValue(), type);//set width and height of image
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, maxSize, height.toBigInteger().intValue(), null);
			g.dispose();

			try{

				ImageIO.write(resizedImage, "jpg", baos);
			}catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
		}else{
			try{
				ImageIO.write(originalImage, "jpg", baos);
			}catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
		}


		imageBytes = baos.toByteArray();

		return  imageBytes;


	}
}
