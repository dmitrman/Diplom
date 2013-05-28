/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.ColorMomentsIndex;
import domain.RGBBrightnessMethod;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import presentation.ControllerFactory;

/**
 *
 * @author Dima
 */
public class ImageProcessing {
    // Преобразование изображения в бинарное
public BufferedImage toBinaryImage(final BufferedImage img,final int porog){
               final int iw=img.getWidth();
               final int ih=img.getHeight();
   for(int i=0;i<ih;i++){
    for(int j=0;j<iw;j++){
            int c= img.getRGB(j,i);     
             if((c&255)>porog)
            //255- blue если изобр. цветное о преобразование будет идти по синему каналу
            // 65280 -green 16711680- red
                img.setRGB(j,i, GetColorFromRGB(0, 0, 0)); 
             else
                img.setRGB(j, i, GetColorFromRGB(255, 255, 255));                      
    } 
    
    }
    return img;
}
private int GetColorFromRGB(int r,int g,int b){
    int col = (r << 16) | (g << 8) | b;
    return col;
}
////////////////////////////////////////////////////////////////////////////////
// Запись изображения в файл
public static void writeImageToFile(BufferedImage im,String type,String file){
        try {               
            File file1 = new File(file);
            ImageIO.write(im, type, file1);
        } catch (IOException ex) {
            Logger.getLogger(ImageProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
}
// заполняет массив интенсивностями пикселей
public ArrayList<Integer> toHalfToningImage(BufferedImage img){
        ArrayList<Integer> mas=new ArrayList<Integer>();     
           int iw=img.getWidth();
           int ih=img.getHeight();
    for(int i=0;i<ih;i++){
    for(int j=0;j<iw;j++){
            int c= img.getRGB(j,i); 
            
               int a=(int)(((c&16711680)>>16)*0.3+((c&65280)>>8)*0.59+((c&255))*0.11);
               mas.add(a);
            
           
         
    }
    }
    return mas;
} 
public BufferedImage toHalfToning(BufferedImage img){
        ArrayList<Integer> mas=new ArrayList<Integer>();     
           int iw=img.getWidth();
           int ih=img.getHeight();
           BufferedImage img1=new BufferedImage(iw, ih,  BufferedImage.TYPE_INT_RGB);
    for(int i=0;i<ih;i++){
    for(int j=0;j<iw;j++){
            int c= img.getRGB(j,i); 
            
               int a=(int)(((c&16711680)>>16)*0.3+((c&65280)>>8)*0.59+((c&255))*0.11);
              img1.setRGB(j, i, GetColorFromRGB(a, a, a));
            
           
         
    }
    }
    return img1;
} 
// заполняет массив интенсивностями пикселей
public ArrayList<Integer> getRedChannelOfImage(BufferedImage img){
        ArrayList<Integer> mas=new ArrayList<Integer>();     
           int iw=img.getWidth();
           int ih=img.getHeight();
    for(int i=0;i<ih;i++){
    for(int j=0;j<iw;j++){
            int c= img.getRGB(j,i); 
            
                mas.add(GetColorToRGB(c)[0]);
           
         
    }
    }
    return mas;
} 
// заполняет массив интенсивностями пикселей
public ArrayList<Integer> getGreenChannelOfImage(BufferedImage img){
        ArrayList<Integer> mas=new ArrayList<Integer>();     
           int iw=img.getWidth();
           int ih=img.getHeight();
    for(int i=0;i<ih;i++){
    for(int j=0;j<iw;j++){
            int c= img.getRGB(j,i); 
            
                mas.add(GetColorToRGB(c)[1]);
            
           
         
    }
    }
    return mas;
} 
// заполняет массив интенсивностями пикселей
public ArrayList<Integer> getBlueChannelOfImage(BufferedImage img){
        ArrayList<Integer> mas=new ArrayList<Integer>();     
           int iw=img.getWidth();
           int ih=img.getHeight();
    for(int i=0;i<ih;i++){
    for(int j=0;j<iw;j++){
            int c= img.getRGB(j,i); 
            
              
               mas.add(GetColorToRGB(c)[2]);
            
           
         
    }
    }
    return mas;
} 
public int [] GetColorToRGB(int col){    
          int r=(col&16711680)>>16;
          int g=(col&65280)>>8;
          int b=(col&255);
          int[] rgb=new int[]{r,g,b};
          return rgb;
}
////////////////////////////////////////////////////////////////////////////////



}
