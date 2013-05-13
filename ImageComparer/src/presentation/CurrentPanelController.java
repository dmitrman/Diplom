/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import presentation.observer.Observable;
import presentation.observer.PanelObserver;

/**
 *
 * @author Dima
 */
public class CurrentPanelController extends PanelController implements PanelObserver {
      
    public CurrentPanelController(JPanel panel) {
        this.panel=panel;       
        h=panel.getHeight();w=panel.getWidth();
        panel.setLayout(new BorderLayout());   
        panel.setBorder(BorderFactory.createTitledBorder("Текущее изображение"));       
        try {       
            showImage(ImageIO.read(getClass().getResource("/resources/128.jpg")));
        } catch (IOException ex) {
            Logger.getLogger(CurrentPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }   
  
    @Override
    public void showImage(BufferedImage img){
        currentImage=img;
        Image newimg = Scalr.resize(img, Method.QUALITY,w, Scalr.OP_ANTIALIAS);   
        label=new JLabel(new ImageIcon(newimg));        
        panel.add(label);
       
    }
  
  private void setImage(BufferedImage img){
    currentImage=img;
    Image newimg = Scalr.resize(img, Method.SPEED,w, Scalr.OP_ANTIALIAS);    
    label.setIcon(new ImageIcon(newimg));  
  }

    @Override
    public void updateImage(Observable o, String path) {
        try {
             setImage(ImageIO.read(new File(path)));
        } catch (IOException ex) {
             Logger.getLogger(CurrentPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
