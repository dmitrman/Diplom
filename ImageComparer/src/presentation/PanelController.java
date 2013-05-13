/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;


import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author dgoncharenko
 */
 public abstract class PanelController{
   
     protected JPanel panel;   
     protected JLabel label;
     protected BufferedImage currentImage;
     protected String path;
     protected  int h;
     protected int w;
     abstract void showImage(BufferedImage img);  
    
}
