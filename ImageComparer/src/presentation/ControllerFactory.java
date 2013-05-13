/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;
import logging.log4jExample;
import org.apache.log4j.PropertyConfigurator;
import sql.ImageRepository;
import xml.XmlMenuLoader;

/**
 *
 * @author dgoncharenko
 */
public class ControllerFactory {
   static  TableController c;
   static  CurrentPanelController sp;
   static  SamplePanelController cc;
   static  MenuPanelController mc;
   static  ActionController bh;
   static  JMenuBarController jmbc;
   static  JToolBarController jtbc;
   static  ImageRepository repository;
   static  MainWindow frame; 
   static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ControllerFactory.class);   

    public ControllerFactory(MainWindow frame,JPanel jPanel1,JPanel jPanel2,JPanel jPanel3,JPanel jPanel4,JTable jTable1,XmlMenuLoader loader) {
      
       initLogging();
       repository=new ImageRepository();
       this.frame=frame;
       sp = new CurrentPanelController(jPanel2);
       c = new TableController(jTable1);
       c.addObserver(sp);/** реагирует на изменение текущего изображения в таблице */
       cc = new SamplePanelController(jPanel1);
       mc = new MenuPanelController(jPanel3);
       bh=new ActionController();
       mc.addObserver(bh); /** реакция на нажатие кнопок на панели меню */
       bh.addObserver(cc); /** добавление наблюдателя SamplePanelController */
       bh.addObserver(c);
       jmbc=new JMenuBarController(loader);
       jmbc.addObserver(bh);
       jPanel4.setLayout(new BorderLayout());
       jPanel4.add(createToolBar(),BorderLayout.NORTH);
       jtbc=new JToolBarController((JToolBar)jPanel4.getComponent(0));   
       jtbc.addObserver(c); /** добаление реакции на перемещение по таблице,
        *                             добавление и удаление строк из таблице */
         System.out.println("-----------------------------------");
        
    }
    public static ImageRepository  getRepository(){
            return repository;
    }
    public static TableController getTableController(){
        return c;
    }
    public static SamplePanelController getCurrentPanelController(){
        return cc;
    }
    public static MenuPanelController getMenuPanelController(){
        return mc;
    }
    public static CurrentPanelController getSamplePanelController(){
        return sp;
    }
    public static JMenuBarController getJMenuBarController(){
        return jmbc;
    }
    public static MainWindow getMainWindow(){
       return frame; 
    }
    private JToolBar createToolBar(){       
          // Панель инструментов
          JToolBar toolbar=new JToolBar();        
          toolbar.setBorderPainted(false);       
          toolbar.setCursor(new Cursor(Cursor.MOVE_CURSOR));
          toolbar.setAutoscrolls(true);
          toolbar.setOrientation(JToolBar.VERTICAL);       
          return toolbar;
  }
     private void initLogging(){
          try {
           String log4JPropertyFile = "/resources/log4j.properties";
           Properties p = new Properties();         
                String workingDir = System.getProperty("user.dir");
                System.out.println("Current working directory : " + workingDir);
           p.load(ControllerFactory.class.getResourceAsStream(log4JPropertyFile));              
           PropertyConfigurator.configure(p);
           log.info("Logger is configured!");
 } catch (IOException ex) {
           System.out.println("XYU "+ex.getMessage());
           log.error("Log error:"+ex.getMessage());
 }
    }
}
