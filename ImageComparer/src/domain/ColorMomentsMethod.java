/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import service.ImageProcessing;

/**
 * Реализует метод распознавания изображенний по цвету при помощи метода
 * цветовых моментов 
 * 
 * @author dgoncharenko
 */
/**
 * Полезная ссылка для перевода из одного метрического пространства в другое
 * http://www.f4.fhtw-berlin.de/~barthel/ImageJ/ColorInspector/HTMLHelp/farbraumJava.htm
 */
public class ColorMomentsMethod {
    ImageProcessing ip=new ImageProcessing();
/** функция для перевода цветового пространства RGB в цв. пространство HSV */
private int[] rgb2hsv(int r, int g, int b) {
		int[] hsv=new int[3];
		int min;    //Min. value of RGB
		int max;    //Max. value of RGB
		int delMax; //Delta RGB value
		
		if (r > g) { min = g; max = r; }
		else { min = r; max = g; }
		if (b > max) max = b;
		if (b < min) min = b;
								
		delMax = max - min;
	 
		float H = 0, S;
		float V = max;
		   
		if ( delMax == 0 ) { H = 0; S = 0; }
		else {                                   
			S = delMax/255f;
			if ( r == max ) 
				H = (      (g - b)/(float)delMax)*60;
			else if ( g == max ) 
				H = ( 2 +  (b - r)/(float)delMax)*60;
			else if ( b == max ) 
				H = ( 4 +  (r - g)/(float)delMax)*60;   
		}								 
		hsv[0] = (int)(H);
		hsv[1] = (int)(S*100);
		hsv[2] = (int)(V);
                return hsv;
	}
/** возвращвет вектор признаков изображения по методу цветовых моментов */
public ColorMomentsIndex getColorMoments(BufferedImage img){
     ArrayList<Integer> H=new ArrayList<Integer>();  
     ArrayList<Integer> S=new ArrayList<Integer>();     
     ArrayList<Integer> V=new ArrayList<Integer>();     
           int iw=img.getWidth();
           int ih=img.getHeight();
    for(int i=0;i<ih;i++){
    for(int j=0;j<iw;j++){
            int c= img.getRGB(j,i);
            int[] rgb=ip.GetColorToRGB(c);
               H.add(rgb2hsv(rgb[0], rgb[1], rgb[2])[0]);
               S.add(rgb2hsv(rgb[0], rgb[1], rgb[2])[1]);
               V.add(rgb2hsv(rgb[0], rgb[1], rgb[2])[2]);
    }
    }
    /* для хранения цветовых моментов по каждому каналу HSV */
    double[] h=new double[3];
    double[] s=new double[3];
    double[] v=new double[3];
    h[0]=getMean(H);h[1]=getStandardDeviation(H,h[0]);h[2]=getStewness(H, h[0]);
    s[0]=getMean(S);s[1]=getStandardDeviation(S, s[0]);s[2]=getStewness(S, s[0]);
    v[0]=getMean(V);v[1]=getStandardDeviation(V, v[0]);v[2]=getStewness(V, v[0]);
  //  System.out.println(h[0]+" "+h[1]+" "+h[2]+" "+v[0]+" "+v[1]+" "+v[2]+" "+s[0]+" "+s[1]+" "+s[2]+" ");
    return  new ColorMomentsIndex(h, s, v);
}
/* сравнение изображений по методу цветовых моментов */
public double getColorMomentsComparision(ColorMomentsIndex img1,ColorMomentsIndex img2){
     double[] h1=img1.getH();
     double[] s1=img1.getS();
     double[] v1=img1.getV();
     double[] h2=img2.getH();
     double[] s2=img2.getS();
     double[] v2=img2.getV();
     
    double res=0;
    for(int i=0;i<3;i++){
        res+=1*Math.abs(h1[i]-h2[i])+2*Math.abs(s1[i]-s2[i])+1*Math.abs(v1[i]-v2[i]);
    }
return res;
}
/** получить среднее по вектору признаков */
private double getMean(ArrayList<Integer> mas){
    int s=0;
    for (int n:mas){
        s+=n;
    }
    return s/mas.size();
}
/** получить стандартное отклонение по вектору признаков */
private double getStandardDeviation(ArrayList<Integer> mas,double mean){
    //double mean=getMean(mas);
    int s=0;
    for (int n:mas){
        s+=Math.pow(n-mean, 2d);
    }
    s/=mas.size();
    return Math.pow(s, 0.5d);
}
/** получить признак ассиметрии */
private double getStewness(ArrayList<Integer> mas, double mean){
   // double mean=getMean(mas);
    double s=0;
    for (int n:mas){
        s+=Math.pow(n-mean, 3d);
    }
    s/=mas.size();
    
    // Здесь столкнался с проблемой в том, что Math.pow(,) - не вычисляет 
    // кубический корень из отрицательного числа    
    if(s<0)
        return -Math.pow(Math.abs(s), 1/3d);
    else
        return Math.pow(s, 1/3d);
}
/* Точка входа в метод 
 * Создан для тестирования
 */
public static void main(String[] args) {
        try {
            ImageProcessing l=new ImageProcessing();
          //  int [] m=new int [3];
           // m=l.rgb2hsv(100, 45, 20);
            /* вывод значений цвета HSV */
          //  System.out.println(m[0]+" "+m[1]+" "+m[2]);
            ColorMomentsIndex ind1=new ColorMomentsMethod().getColorMoments(ImageIO.read(new File("C:\\Users\\dgoncharenko\\Dropbox\\DIPLOM\\101_ObjectCategories\\101_ObjectCategories\\Image_DATA_BASE1\\Image_DATA_BASE\\Adidas_Cup_large.png")));
            ColorMomentsIndex ind2=new ColorMomentsMethod().getColorMoments(ImageIO.read(new File("C:\\Users\\dgoncharenko\\Dropbox\\DIPLOM\\101_ObjectCategories\\101_ObjectCategories\\Image_DATA_BASE1\\Image_DATA_BASE\\logo83.png")));
            System.out.println("RES "+new ColorMomentsMethod().getColorMomentsComparision(ind1, ind2));
             /*System.out.println(" "+ind1.getH()[0]+" "+ind1.getH()[1]+" "+ind1.getH()[2]);
              System.out.println(" "+ind1.getS()[0]+" "+ind1.getS()[1]+" "+ind1.getS()[2]);
               System.out.println(" "+ind1.getV()[0]+" "+ind1.getV()[1]+" "+ind1.getV()[2]);*/
        } catch (IOException ex) {
            Logger.getLogger(ImageProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
}
