package main;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

import javax.imageio.ImageIO;

import javafx.scene.control.cell.*;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
public class shibie {
	public shibie(String path,int length) 
    {    
		
		for(int i=1;i<=length;i++)
		{
		
			writeBufferedImage(alphaProcess(initBufferedImage(path+"\\"+i+".png")),"E://tupian//"+i+".jpg");
			cut("E://tupian//"+i+".jpg","F://tupian1//"+i+".1");
			cut1("E://tupian//"+i+".jpg","F://tupian1//"+i+".2");
		
		}
		
		
		
    }  

private static void writeBufferedImage(BufferedImage img,String filePath){
    String format = filePath.substring(filePath.indexOf('.')+1);
    
    try {
        ImageIO.write(img,format,new File(filePath));
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public static boolean setExcel(String[][] x) {
	boolean t=false;
	  WritableWorkbook wwb = null;  
	  jxl.Cell cell = null;  
	   try {  
            // ����һ����д��Ĺ�������WorkBook������,  
            //�����ø��෽��createWorkbook��������WritableWorkbook���������˹�������  
            wwb = Workbook.createWorkbook(new File("G:\\2.xlsx"));  //��ַ
              
            // ����һ����д��Ĺ�����   
            // Workbook��createSheet������������������һ���ǹ���������ƣ��ڶ����ǹ������ڹ������е�λ��  
            WritableSheet ws = wwb.createSheet("sheetTest", 0);  
            for(int i=0;i<x.length;i++){   
                	
                   Label labelC = new Label(0,i,x[i][0]);  
                    ws.addCell( labelC);  
                    labelC = new Label(1,i,x[i][1]);  
                    ws.addCell( labelC);  
    	            t=true;
    	        
              }
              
            wwb.write();// ���ڴ���д���ļ���  
            wwb.close();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            t=false;
        }  
	return t;
}



/**
�򵥽���һ��˼·�����ȱ����������أ�����һ������ͼ�ĻҶȵ�ƽ��ֵ��grayMean����������ɫ����Ҷ�ֵ�Ƚ�С��������ɫ��ǳ���Ҷ�ֵ�ϴ󣬼�Ȩƽ��֮���ܵ�ƽ��ֵ��ȱ����ĻҶ�ֵ�Եͣ�Ȼ���ٴα����������أ�������ÿ�����ص�ĻҶȣ������Ҷ�ֵ��grayMean������alphaֵ��Ϊ0������ȫ͸��������������
*/  /**
     * ����͸����
     */
    public static BufferedImage alphaProcess(BufferedImage bufferedImage) {
        //��ȡԴͼ��Ŀ��
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
    //ʵ����һ��ͬ����С��ͼƬ������type��Ϊ BufferedImage.TYPE_4BYTE_ABGR��֧��alphaͨ����rgbͼ��
        BufferedImage resImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

        double grayMean = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = bufferedImage.getRGB(i,j);
                int r = (0xff&rgb);
                int g = (0xff&(rgb>>8));
                int b = (0xff&(rgb>>16));
                //���ǻҶ�ֵ�ļ��㹫ʽ
                grayMean += (r*0.299+g*0.587+b*0.114);
            }
        }
        //����ƽ���Ҷ�
        grayMean = grayMean/(width*height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = bufferedImage.getRGB(i,j);
                //һ��int��32λ,java�а�abgr��˳��洢����ǰ8λ��alpha�����8λ��r�����Կ���ͨ������ķ�ʽ��ȡ��rgb��ֵ
                int r = (0xff&rgb);
                int g = (0xff&(rgb>>8));
                int b = (0xff&(rgb>>16));
                double gray = (r*0.299+g*0.587+b*0.114);
                //����Ҷ�ֵ����֮ǰ���ƽ���Ҷ�ֵ������alpha��Ϊ0������׼ȷдӦ����rgb = r + (g << 8) + (b << 16) �� ��0 << 24��;
                if (gray>grayMean){
                    rgb = r + (g << 8) + (b << 16);
                }
                resImage.setRGB(i,j,rgb);
            }
        }
        //ok�����صľ��ǽ�ǳɫ������Ϊ͸����BufferedImage�ˣ������ûҶȻ����ᵽ�ķ�ʽд���ļ�
        return resImage;
    }


    private static BufferedImage initBufferedImage(String imagePath) {
        File file = new File(imagePath);
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


public static void cut(String file, String descDir)     
{     
    try    
    {    
        String dir = null;  
        // ��ȡԴͼ��     
        BufferedImage bi = ImageIO.read(new File(file));     
        BufferedImage outImg=bi.getSubimage(145, 10, 300, 30);   

                    // ���Ϊ�ļ�    
                    dir = descDir + ".jpg";  
                    File f = new File(dir);  
                    
                    ImageIO.write(outImg, "jpg", f); 
                    //System.out.println(dir);  
                    
    }     
    catch (Exception e)     
    {     
        e.printStackTrace();     
    }     
}


public static void cut1(String file, String descDir)     
{     
    try    
    {    
        String dir = null;  
        // ��ȡԴͼ��     
        BufferedImage bi = ImageIO.read(new File(file));     
        BufferedImage outImg=bi.getSubimage(130, 40, 900, 40);   

                    // ���Ϊ�ļ�    
                    dir = descDir + ".jpg";  
                    File f = new File(dir);  
                    
                    ImageIO.write(outImg, "jpg", f); 
                    //System.out.println(dir);  
                    
    }     
    catch (Exception e)     
    {     
        e.printStackTrace();     
    }     
}
}