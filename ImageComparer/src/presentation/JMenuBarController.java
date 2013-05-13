/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;


import xml.XmlMenuLoader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import presentation.observer.ActionObserver;
import presentation.observer.Observable;
import presentation.observer.PanelObserver;

/**
 *
 * @author dgoncharenko
 */
public class JMenuBarController  implements Observable{
         /** список всех подписанных наблюдателей */
    ArrayList observers = new ArrayList();   
  
    JMenuBarController(XmlMenuLoader loader) {      
       
       loader.addActionListener("method1", getRGBAction());
       loader.addActionListener("method2", getCurvatureAction());
       loader.addActionListener("method3", getQuadroRGB3());
       loader.addActionListener("method4",getQuadroEvclid());
       loader.addActionListener("method5",getQuadroEvclidRGB());
       loader.addActionListener("method6",getQuadroRGB2());
       loader.addActionListener("method7",getQuadroHistogram());
       loader.addActionListener("loadpattern",loadPatternImage());
       loader.addActionListener("exit", new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
    }
    
    ActionListener getRGBAction(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifySetMethod("RGBbrightness");
            }
        };
    }
    ActionListener getCurvatureAction(){
        return new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
              notifySetMethod("Curvature");
            }
        };
    }
    private ActionListener loadPatternImage() {
      return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                notifyLoadPatternImage();
            }            
        };
    }

    private ActionListener getQuadroEvclid() {
      return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                 notifySetMethod("Quadro(Evclid)");
            }
        };
    }

    private ActionListener getQuadroEvclidRGB() {
          return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                notifySetMethod("Quadro(RGB)1");
            }
        };
     
    }

    private ActionListener getQuadroRGB3() {
           return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                 notifySetMethod("Quadro(RGB)3");
            }
        };
    }

    private ActionListener getQuadroRGB2() {
          return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                 notifySetMethod("Quadro(RGB)2");
            }
        };
    }

    private ActionListener getQuadroHistogram() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                notifySetMethod("Quadro(Histogram)");
            }
        };
    }

     @Override
    public void addObserver(PanelObserver o) {
         observers.add(o);
    }

    @Override
    public void removeObserver(PanelObserver o) {
        observers.remove(o);
    }
public void addObserver(ActionObserver o) {
         observers.add(o);
    }


    public void removeObserver(ActionObserver o) {
        observers.remove(o);
    }
 private void notifySetMethod(String method) {
                Iterator i = observers.iterator();
            while( i.hasNext() ) {
                  ActionObserver o = ( ActionObserver ) i.next();
                  o.setMethod(method);
                 
            }
            }
 private void notifyLoadPatternImage() {
                Iterator i = observers.iterator();
            while( i.hasNext() ) {
                  ActionObserver o = ( ActionObserver ) i.next();
                  o.loadPatternImage();
                 
            }
            }
}
