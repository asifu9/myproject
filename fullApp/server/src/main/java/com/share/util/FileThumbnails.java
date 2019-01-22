package com.share.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.base.domain.PhotoDetails;

@Service
public class FileThumbnails {
	
	@Value("${filePath}")
	private String filePath;

	public List<PhotoDetails> createThumbNail(String userId, String albumId, String fileName) throws IOException {
		return createThumbs(filePath+userId+"/"+albumId,fileName);
	}

	public List<PhotoDetails> createThumbs(String path,String fileName) throws IOException{
		List<PhotoDetails> list=new ArrayList<>();
		System.out.println("patjh = " + path + " :" + fileName);
		File image = new File(path+"/"+fileName);
		BufferedImage img = ImageIO.read(image); // load image
		//resize to 150 pixels max
		List<Integer> arr=new ArrayList<>();
		int valu=300;
		arr.add(valu);
		int total=img.getWidth()>img.getHeight()?img.getWidth():img.getHeight();
		int diff= total-300;
		int points = diff/5;
		for(int i=1;i<=5;i++){
			if(points>200){
				arr.add(valu+200);
				valu+=200;
			}else if(points>100){
				arr.add(valu+100);
				valu+=100;
			}
		}
		int ss=1;
		for (Integer i : arr) {
			try {
				PhotoDetails d=new PhotoDetails();
				createThumbNail(img, i,path,fileName,d);
				d.setOrder(ss++);
				list.add(d);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
		return list;
	}
	
	public void createThumbNail(BufferedImage img,int size,String path,String fileName,PhotoDetails pd) throws IOException{
		BufferedImage thumbnail = Scalr.resize(img, size, null);
		ImageIO.write(thumbnail, "jpg", new File(path+"/"+size+"_"+fileName));
		pd.setHeight(thumbnail.getHeight());
		pd.setWidth(thumbnail.getWidth());
		pd.setName(size+"_"+fileName);
	}

	
}
