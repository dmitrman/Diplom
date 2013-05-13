/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;




import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JPanel;
import domain.CurvaturePhaseIndex;
import domain.CurvaturePhaseMethod;
import domain.RGBBrightnessMethod;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;import org.imgscalr.Scalr;
import presentation.observer.Observable;
import presentation.observer.PanelObserver;

/**
 *
 * @author dgoncharenko
 */
// текущее изображение
public class SamplePanelController extends PanelController implements PanelObserver{
    
    private  CurvaturePhaseMethod curvatureMethod=new CurvaturePhaseMethod();
    private RGBBrightnessMethod rgbbrightnessMethod=new RGBBrightnessMethod();   
    boolean flag=true;
    
    SamplePanelController(JPanel panel) {
         this.panel=panel;
        
          h=panel.getHeight();w=panel.getWidth();
           panel.setLayout(new BorderLayout());
           panel.setBorder(BorderFactory.createTitledBorder("Изображение образец"));
         try {
            showImage(ImageIO.read(getClass().getResource("/resources/128.jpg")));
            path="src\\resources\\128.jpg";
        } catch (IOException ex) {
            Logger.getLogger(SamplePanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     
    @Override
   public void showImage(BufferedImage img){
           currentImage=img;
           class ComplexJobThread extends Thread{
     
     @Override
     public void run(){
          
             sampleind=curvatureMethod.ComputeCurvaturePhaseIndex(currentImage);
             halfs=rgbbrightnessMethod.ComputeRGBBrightnessIndexHalfton(currentImage);
             quedro=rgbbrightnessMethod.CompputeQuadroTree(currentImage);
             quedroRGB=rgbbrightnessMethod.CompputeQuadroTreeBY_R_G_B(currentImage);
             flag=false;}
           }
           new ComplexJobThread().start();
        Image newimg = Scalr.resize(img, Scalr.Method.QUALITY,w, h, Scalr.OP_ANTIALIAS);   
      //   System.out.println("W= "+w+" H= "+h);
        label=new JLabel(new ImageIcon(newimg));
        panel.add(label);
       
    } 
    public CurvaturePhaseIndex sampleind;
    public double[] halfs;
    public ArrayList<double[]>  quedro;
 public ArrayList<double[]>  quedroRGB;
 private  void setImage(String img){
         flag=true;
        try {
             currentImage=ImageIO.read(new File(img));
              class ComplexJobThread extends Thread{
     
     @Override
     public void run(){
             sampleind=curvatureMethod.ComputeCurvaturePhaseIndex(currentImage);
             halfs=rgbbrightnessMethod.ComputeRGBBrightnessIndexHalfton(currentImage);
             quedro=rgbbrightnessMethod.CompputeQuadroTree(currentImage);
             quedroRGB=rgbbrightnessMethod.CompputeQuadroTreeBY_R_G_B(currentImage);
             flag=false;}
           }
           new ComplexJobThread().start();
             path=img;
         //     System.out.println("W= "+w+" H= "+h);
            Image newimg = Scalr.resize( currentImage, Scalr.Method.SPEED,w, h, Scalr.OP_ANTIALIAS);    
            label.setIcon(new ImageIcon(newimg));
        } catch (IOException ex) {
            Logger.getLogger(SamplePanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
}  

    @Override
     public void updateImage(Observable o,String path ){
         setImage(path);
     }
    public BufferedImage getCurrentImage(){
        return currentImage;
    }
    public String getCurrentImagePath(){
        return path;
    }
}
