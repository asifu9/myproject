package com.share.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

public class Test {

	public static void mai1n(String[] args) throws Exception{
		File image = new File("/Users/i340632/Desktop/imges/height.jpg");
		BufferedImage img = ImageIO.read(image); // load image
		//resize to 150 pixels max
		System.out.println("height " + img.getHeight());
		System.out.println("width " + img.getWidth());
		
		List<Integer> arr=new ArrayList<>();
		arr.add(300);
		int extra=0;
		if(img.getHeight()>img.getWidth()){
			int points=img.getHeight()/6;
			for(int i=1;i<=5;i++){
				if(i==1 && (points*i)<300){
					extra=300-points;
				}
				System.out.println((points*i)+extra);
				arr.add((points*i)+extra);
			
			}
		}else{
			int points=img.getWidth()/6;
			for(int i=1;i<=6;i++){
				arr.add(points*i);
			}
		}
		arr.forEach(i->{
			try {
				createThumbNail(img, i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		//BufferedImage thumbnail = Scalr.resize(img, 600, null);
		//ImageIO.write(thumbnail, "jpg", new File("/Users/i340632/Desktop/width600.jpg"));
		//height 3120
		//width 9865

//		ImageIO.write(thumbnail,"jpeg" , "/rotated.png");
//		ImageIO.wr
	}
	
	public static void createThumbNail(BufferedImage img,int size) throws IOException{
		BufferedImage thumbnail = Scalr.resize(img, size, null);
		ImageIO.write(thumbnail, "jpg", new File("/Users/i340632/Desktop/imges/heigth"+size+".jpg"));
	}

}
