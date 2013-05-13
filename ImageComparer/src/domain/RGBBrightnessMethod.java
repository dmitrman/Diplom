/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import service.ImageProcessing;

/**
 *
 * @author Dima
 */
// 030.pdf
// метод цветовых гистограмм
public class RGBBrightnessMethod {
    private  int N=16; // число подмножеств (столбцов гистограммы)
    private ImageProcessing ip=new ImageProcessing();
    public RGBBrightnessMethod() {
    
    }
        
    // вычисляет вектор признаков для сравнения
    public double[] ComputeRGBBrightnessIndexHalfton(BufferedImage img){
  
          
             double[] x=PutIntoSets(ip.toHalfToningImage(img));
             return x;
      
        
    }
   public ArrayList<double[]> CompputeQuadroTree(BufferedImage img){
        BufferedImage img1=img.getSubimage(0, 0, img.getWidth()/2, img.getHeight()/2);
        BufferedImage img2=img.getSubimage(img.getWidth()/2, img.getHeight()/2, img.getWidth()/2, img.getHeight()/2);
        BufferedImage img3=img.getSubimage(0, img.getHeight()/2, img.getWidth()/2, img.getHeight()/2);
        BufferedImage img4=img.getSubimage(img.getWidth()/2, 0, img.getWidth()/2, img.getHeight()/2);
        double[] a1= ComputeRGBBrightnessIndexHalfton(img1);
         double[] b1= ComputeRGBBrightnessIndexHalfton(img2);
          double[] c1= ComputeRGBBrightnessIndexHalfton(img3);
           double[] d1= ComputeRGBBrightnessIndexHalfton(img4);
        ArrayList<double[]> list=new ArrayList();
        list.add(a1); list.add(b1); list.add(c1); list.add(d1);
        return list;
   }
    public ArrayList<double[]> CompputeQuadroTreeBY_R_G_B(BufferedImage img){
        BufferedImage img1=img.getSubimage(0, 0, img.getWidth()/2, img.getHeight()/2);
        BufferedImage img2=img.getSubimage(img.getWidth()/2, img.getHeight()/2, img.getWidth()/2, img.getHeight()/2);
        BufferedImage img3=img.getSubimage(0, img.getHeight()/2, img.getWidth()/2, img.getHeight()/2);
        BufferedImage img4=img.getSubimage(img.getWidth()/2, 0, img.getWidth()/2, img.getHeight()/2);
        
         ArrayList<double[]> list=new ArrayList();
         
        double[] a1= ComputeRedChannelBrightnessIndexColor(img1);
        double[] b1= ComputeGreenChannelBrightnessIndexColor(img1);
        double[] c1= ComputeBlueChannelBrightnessIndexColor(img1);
       
           list.add(a1); list.add(b1); list.add(c1);
         double[]  a2= ComputeRedChannelBrightnessIndexColor(img2);
       double[]  b2= ComputeGreenChannelBrightnessIndexColor(img2);
       double[]  c2= ComputeBlueChannelBrightnessIndexColor(img2);
          list.add(a2); list.add(b2); list.add(c2);
       double[]    a3= ComputeRedChannelBrightnessIndexColor(img3);
      double[]   b3= ComputeGreenChannelBrightnessIndexColor(img3);
      double[]   c3= ComputeBlueChannelBrightnessIndexColor(img3);
          list.add(a3); list.add(b3); list.add(c3);
     double[]      a4= ComputeRedChannelBrightnessIndexColor(img4);
     double[]    b4= ComputeGreenChannelBrightnessIndexColor(img4);
     double[]    c4= ComputeBlueChannelBrightnessIndexColor(img4);
          list.add(a4); list.add(b4); list.add(c4);        
          
     
        return list;
   }
   public double ComputeQuadroDifference(ArrayList<double[]>list1,ArrayList<double[]>list2){
       double a1=computeDifference(list1.get(0), list2.get(0));
        double b1=computeDifference(list1.get(1), list2.get(1));
         double c1=computeDifference(list1.get(2), list2.get(2));
          double d1=computeDifference(list1.get(3), list2.get(3));
          return Math.pow(Math.pow(a1,2)+Math.pow(b1,2)+Math.pow(c1,2)+Math.pow(d1,2), 0.5);
       //   return (a1+b1+c1+d1)/4;
   }
    public double ComputeQuadroDifferenceEvclid(ArrayList<double[]>list1,ArrayList<double[]>list2){
       double a1=computeDifferenceEvclid(list1.get(0), list2.get(0));
        double b1=computeDifferenceEvclid(list1.get(1), list2.get(1));
         double c1=computeDifferenceEvclid(list1.get(2), list2.get(2));
          double d1=computeDifferenceEvclid(list1.get(3), list2.get(3));
          return Math.pow(Math.pow(a1,2)+Math.pow(b1,2)+Math.pow(c1,2)+Math.pow(d1,2), 0.5);
       //   return (a1+b1+c1+d1)/4;
   }
     public double ComputeQuadroDifferenceEvclidRGB_1(ArrayList<double[]>list1,ArrayList<double[]>list2){
         
         double r1=computeDifference(list1.get(0), list2.get(0));
         // printIntMas(list1.get(0));printIntMas(list2.get(0));
         double g1=computeDifference(list1.get(1), list2.get(1));
         double b1=computeDifference(list1.get(2), list2.get(2));
         
         double r2=computeDifference(list1.get(3), list2.get(3));
         double g2=computeDifference(list1.get(4), list2.get(4));
         double b2=computeDifference(list1.get(5), list2.get(5));
         
         double r3=computeDifference(list1.get(6), list2.get(6));
         double g3=computeDifference(list1.get(7), list2.get(7));
         double b3=computeDifference(list1.get(8), list2.get(8));
         
         double r4=computeDifference(list1.get(9), list2.get(9));
         double g4=computeDifference(list1.get(10), list2.get(10));
         double b4=computeDifference(list1.get(11), list2.get(11));
         
        // System.out.println(r1+" "+g1+" "+b1);
         
       /*  double i1=Math.pow(Math.pow(r1,2)+Math.pow(g1,2)+Math.pow(b1,2), 0.5);
         double i2=Math.pow(Math.pow(r2,2)+Math.pow(g2,2)+Math.pow(b2,2), 0.5);
         double i3=Math.pow(Math.pow(r3,2)+Math.pow(g3,2)+Math.pow(b3,2), 0.5);
         double i4=Math.pow(Math.pow(r4,2)+Math.pow(g4,2)+Math.pow(b4,2), 0.5);*/
         
      /*   double i1=Math.pow(Math.pow(r1,2)+Math.pow(r2,2)+Math.pow(r3,2)+Math.pow(r4,2),0.5);
         double i2=Math.pow(Math.pow(g1,2)+Math.pow(g2,2)+Math.pow(g3,2)+Math.pow(g4,2),0.5);
         double i3=Math.pow(Math.pow(b1,2)+Math.pow(b2,2)+Math.pow(b3,2)+Math.pow(b4,2),0.5);*/
         double i1=(r1+r2+r3+r4)/4;
         double i2=(g1+g2+g3+g4)/4;
         double i3=(b1+b2+b3+b4)/4;
        
         //double i4=Math.pow(Math.pow(r4,2)+Math.pow(g4,2)+Math.pow(b4,2), 0.5);
         return Math.pow(Math.pow(i1, 2)+Math.pow(i2, 2)+Math.pow(i3, 2), 0.5);
       // return (i1+i2+i3)/3;
     }
      public double ComputeQuadroDifferenceEvclidRGB_2(ArrayList<double[]>list1,ArrayList<double[]>list2){
         
         double r1=computeDifference(list1.get(0), list2.get(0));
         // printIntMas(list1.get(0));printIntMas(list2.get(0));
         double g1=computeDifference(list1.get(1), list2.get(1));
         double b1=computeDifference(list1.get(2), list2.get(2));
         
         double r2=computeDifference(list1.get(3), list2.get(3));
         double g2=computeDifference(list1.get(4), list2.get(4));
         double b2=computeDifference(list1.get(5), list2.get(5));
         
         double r3=computeDifference(list1.get(6), list2.get(6));
         double g3=computeDifference(list1.get(7), list2.get(7));
         double b3=computeDifference(list1.get(8), list2.get(8));
         
         double r4=computeDifference(list1.get(9), list2.get(9));
         double g4=computeDifference(list1.get(10), list2.get(10));
         double b4=computeDifference(list1.get(11), list2.get(11));
         
         double i1=Math.pow(Math.pow(r1,2)+Math.pow(g1,2)+Math.pow(b1,2), 0.5);
         double i2=Math.pow(Math.pow(r2,2)+Math.pow(g2,2)+Math.pow(b2,2), 0.5);
         double i3=Math.pow(Math.pow(r3,2)+Math.pow(g3,2)+Math.pow(b3,2), 0.5);
         double i4=Math.pow(Math.pow(r4,2)+Math.pow(g4,2)+Math.pow(b4,2), 0.5);
         
      /*   double i1=Math.pow(Math.pow(r1,2)+Math.pow(r2,2)+Math.pow(r3,2)+Math.pow(r4,2),0.5);
         double i2=Math.pow(Math.pow(g1,2)+Math.pow(g2,2)+Math.pow(g3,2)+Math.pow(g4,2),0.5);
         double i3=Math.pow(Math.pow(b1,2)+Math.pow(b2,2)+Math.pow(b3,2)+Math.pow(b4,2),0.5);*/
      /*   double i1=(r1+r2+r3+r4)/4;
         double i2=(g1+g2+g3+g4)/4;
         double i3=(b1+b2+b3+b4)/4;*/
        
         //double i4=Math.pow(Math.pow(r4,2)+Math.pow(g4,2)+Math.pow(b4,2), 0.5);
         return Math.pow(Math.pow(i1, 2)+Math.pow(i2, 2)+Math.pow(i3, 2)+Math.pow(i4, 2), 0.5);
    //    return (i1+i2+i3)/3;
     }
       public double ComputeQuadroDifferenceEvclidRGB_3(ArrayList<double[]>list1,ArrayList<double[]>list2){
         
         double r1=computeDifference(list1.get(0), list2.get(0));
         // printIntMas(list1.get(0));printIntMas(list2.get(0));
         double g1=computeDifference(list1.get(1), list2.get(1));
         double b1=computeDifference(list1.get(2), list2.get(2));
         
         double r2=computeDifference(list1.get(3), list2.get(3));
         double g2=computeDifference(list1.get(4), list2.get(4));
         double b2=computeDifference(list1.get(5), list2.get(5));
         
         double r3=computeDifference(list1.get(6), list2.get(6));
         double g3=computeDifference(list1.get(7), list2.get(7));
         double b3=computeDifference(list1.get(8), list2.get(8));
         
         double r4=computeDifference(list1.get(9), list2.get(9));
         double g4=computeDifference(list1.get(10), list2.get(10));
         double b4=computeDifference(list1.get(11), list2.get(11));
         
       /*  double i1=Math.pow(Math.pow(r1,2)+Math.pow(g1,2)+Math.pow(b1,2), 0.5);
         double i2=Math.pow(Math.pow(r2,2)+Math.pow(g2,2)+Math.pow(b2,2), 0.5);
         double i3=Math.pow(Math.pow(r3,2)+Math.pow(g3,2)+Math.pow(b3,2), 0.5);
         double i4=Math.pow(Math.pow(r4,2)+Math.pow(g4,2)+Math.pow(b4,2), 0.5);*/
         
         double i1=Math.pow(Math.pow(r1,2)+Math.pow(r2,2)+Math.pow(r3,2)+Math.pow(r4,2),0.5);
         double i2=Math.pow(Math.pow(g1,2)+Math.pow(g2,2)+Math.pow(g3,2)+Math.pow(g4,2),0.5);
         double i3=Math.pow(Math.pow(b1,2)+Math.pow(b2,2)+Math.pow(b3,2)+Math.pow(b4,2),0.5);
     /*    double i1=(r1+r2+r3+r4)/4;
         double i2=(g1+g2+g3+g4)/4;
         double i3=(b1+b2+b3+b4)/4;*/
        
         //double i4=Math.pow(Math.pow(r4,2)+Math.pow(g4,2)+Math.pow(b4,2), 0.5);
         return Math.pow(Math.pow(i1, 2)+Math.pow(i2, 2)+Math.pow(i3, 2), 0.5);
       // return (i1+i2+i3)/3;
     }
    public double[] ComputeRedChannelBrightnessIndexColor(BufferedImage img){
  
          
             double[] x=PutIntoSets(ip.getRedChannelOfImage(img));
             return x;
      
        
    }
    public double[] ComputeGreenChannelBrightnessIndexColor(BufferedImage img){
  
          
             double[] x=PutIntoSets(ip.getGreenChannelOfImage(img));
             return x;
      
        
    }
    public double[] ComputeBlueChannelBrightnessIndexColor(BufferedImage img){
  
          
             double[] x=PutIntoSets(ip.getBlueChannelOfImage(img));
             return x;
      
        
    }
   
 private double[] PutIntoSets(ArrayList<Integer> mas){
     double[] regions=new double[N+2];
     double[] reg=new double[N+2];
     regions[0]=3333333;
     regions[N+1]=3333333;
     for(int i=1;i<N+1;i++){
         regions[i]=0;
     }
     for(int y:mas){
         regions[(int)y/N+1]++;
     }
     double s=0;
      for(double y:regions){
          s+=y;
      }
      int i=0;
         for(double y:regions){
             reg[i]=y/s;
             i++;
         }
     //printIntMas(reg);
    // printIntMas(reg);
     return reg;
 }   
 public static double computeDifferenceEvclid(double[] regions1,double[] regions2){
     double s=0;
     for (int i=1;i<regions1.length-1;i++){
         s+=Math.pow(regions1[i]-regions2[i],2);
     }
     return Math.pow(s,0.5);
 }
 public double computeDifference(double[] regions1,double[] regions2){
     double s=0;
     for(int i=1;i<regions1.length-1;i++){
         double r1=Math.abs(regions1[i]-regions2[i-1]);
         double r2=Math.abs(regions1[i]-regions2[i]);
         double r3=Math.abs(regions1[i]-regions2[i+1]);
       //  System.out.println(r1+" "+r2+" "+r3);
         if(r1<r2)
             if(r1<r3)
                 s+=r1;
             else
                 s+=r3;
         else
              if(r2<r3)
                 s+=r2;
             else
                 s+=r3;
     }
     return s;
 }
 
  public static void main(String[] args) {
      new RGBBrightnessMethod();
  }
   // вывести массив
 void printIntMas(double[] mas){
     for (int i=0;i<mas.length;i++){
     System.out.print(mas[i]+" ");    
     }
      System.out.println();
 } 
 void printIntMas(int[] mas){
     for (int i=0;i<mas.length;i++){
     System.out.print(mas[i]+" ");    
     }
      System.out.println();
 } 

}
// чем больше похожи изображения тем меньше коеффициент у абсолютно похожих = 0