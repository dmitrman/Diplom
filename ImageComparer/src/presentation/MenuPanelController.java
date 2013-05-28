/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import presentation.observer.ActionObserver;
import presentation.observer.Observable;
import presentation.observer.PanelObserver;
import presentation.observer.TableObserver;
import service.BoxLayoutUtils;

/**
 *
 * @author dgoncharenko
 */
public class MenuPanelController implements Observable{
   private ListenerFactory lf=new ListenerFactory();
   private HashMap<String, JLabel> menus=new HashMap<>();
   private JPanel p;
    /** список всех подписанных наблюдателей */
    private ArrayList observers = new ArrayList();
    
    public MenuPanelController(JPanel panel) {
            panel.setLayout(new BorderLayout());
             panel.setBorder(BorderFactory.createEtchedBorder());
             p=BoxLayoutUtils.createHorizontalJPanel();        
            MenuInit();
            loadMenu();
            setAdapters();
            panel.add(p);          
        
    }

   private void loadMenu(){     
      p.add(menus.get("start"));
      p.add(Box.createHorizontalStrut(10));
      p.add(menus.get("save"));
      p.add(Box.createHorizontalStrut(10));
      p.add(menus.get("read"));    
      p.add(Box.createHorizontalStrut(10));
      p.add(menus.get("search"));
      p.add(Box.createHorizontalStrut(10));
      p.add(menus.get("directory"));
      p.add(Box.createHorizontalStrut(10));
      p.add(menus.get("refresh"));
    //  p.add(Box.createHorizontalStrut(10));
    //  p.add(menus.get("method_param"));
      p.add(Box.createHorizontalStrut(10));
   //   p.add(menus.get("preferences"));
   //   p.add(Box.createHorizontalStrut(10));
      p.add(menus.get("about"));
      p.add(Box.createHorizontalStrut(10));
     
        
    }
   private void MenuInit(){
        menus.put("start",(createLabelImage(new ImageIcon(getClass().getResource("/resources/run_copy.png")))));
        menus.put("directory",(createLabelImage(new ImageIcon(getClass().getResource("/resources/directory.png")))));
        menus.put("search",(createLabelImage(new ImageIcon(getClass().getResource("/resources/search.png")))));
        menus.put("read",(createLabelImage(new ImageIcon(getClass().getResource("/resources/direct.png")))));
        menus.put("about",(createLabelImage(new ImageIcon(getClass().getResource("/resources/info.png")))));
        menus.put("save",(createLabelImage(new ImageIcon(getClass().getResource("/resources/save.png")))));
        menus.put("refresh",(createLabelImage(new ImageIcon("C:\\13.png"))));
        menus.put("preferences",(createLabelImage(new ImageIcon(getClass().getResource("/resources/run.png")))));
        menus.put("method_param",(createLabelImage(new ImageIcon(getClass().getResource("/resources/pills5.png"))))); // параметры метода
        
        menus.get("save").setToolTipText(getMenuToolText("save"));
        menus.get("read").setToolTipText(getMenuToolText("read"));
        menus.get("about").setToolTipText(getMenuToolText("about"));
        menus.get("start").setToolTipText(getMenuToolText("start"));
        menus.get("directory").setToolTipText(getMenuToolText("directory"));
        menus.get("search").setToolTipText(getMenuToolText("search"));
        menus.get("preferences").setToolTipText(getMenuToolText("preferences"));
        menus.get("refresh").setToolTipText(getMenuToolText("refresh"));
        menus.get("method_param").setToolTipText(getMenuToolText("method_param"));
    }
    
    // привязка слушателей
   private void setAdapters(){
        menus.get("save").addMouseListener(lf.getSaveAction());
        menus.get("read").addMouseListener(lf.getExitAction());
        menus.get("about").addMouseListener(lf.getAboutAction());
        menus.get("start").addMouseListener(lf.getStartAction());
        menus.get("directory").addMouseListener(lf.getDirectoryAction());
        menus.get("search").addMouseListener(lf.getSearchAction());
        menus.get("preferences").addMouseListener(lf.getPreferencesAction());
        menus.get("refresh").addMouseListener(lf.getRefreshAction());
        
       
    }
   private JLabel createLabelImage(ImageIcon icon){
        final JLabel lab=new JLabel(icon);
     //   lab.setMinimumSize(new Dimension(50,50)); 
        lab.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        final Border thickBorder = new LineBorder(Color.BLUE, 2);
         lab.setToolTipText(null);
         lab.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
              lab.setBorder(thickBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {
              lab.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            }
            
         });
        return lab;
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
    // обработка событий
    class ListenerFactory{
        
        public MouseAdapter getSaveAction(){
            return new MouseAdapter(){

                @Override
                public void mouseClicked(MouseEvent e) {
                  System.out.println("Save pressed");
                     //frame.getButtonHandler().saveIndexes();
                     notifySaveIndexes();
                }

                private void notifySaveIndexes() {
                     Iterator i = observers.iterator();
            while( i.hasNext() ) {
                  ActionObserver o = ( ActionObserver ) i.next();
                  o.saveIndexes();
                 
            }
                }
                
            };
        }
        
        public MouseAdapter getExitAction(){
            return new MouseAdapter(){

                @Override
                public void mouseClicked(MouseEvent e) {
                     System.out.println("Exit pressed");                     
                     notifyReadIndexes();
                }
                private void notifyReadIndexes() {
                     Iterator i = observers.iterator();
            while( i.hasNext() ) {
                  ActionObserver o = ( ActionObserver ) i.next();
                  o.readIndexes();
                 
            }
                }
            };
        }
        
        public MouseAdapter getDirectoryAction(){
            return new MouseAdapter(){

                @Override
                public void mouseClicked(MouseEvent e) {
                      System.out.println("Directory pressed");
                       notifyChooseDirectory();
                }

                private void notifyChooseDirectory() {
                    Iterator i = observers.iterator();
            while( i.hasNext() ) {
                  ActionObserver o = ( ActionObserver ) i.next();
                  o.chooseDirectory();
                 
            }
                }
                
            };
        }
        
        public MouseAdapter getRefreshAction(){
            return new MouseAdapter(){

                @Override
                public void mouseClicked(MouseEvent e) {
                     System.out.println("Refresh pressed");
                      notifyRefresh();
                }

                private void notifyRefresh() {
                      Iterator i = observers.iterator();
            while( i.hasNext() ) {
                  ActionObserver o = ( ActionObserver ) i.next();
                  o.refresh();
                 
            }
                }
                
            };
        }
        
        private MouseAdapter getAboutAction(){
            return new MouseAdapter(){

                @Override
                public void mouseClicked(MouseEvent e) {
                      System.out.println("About pressed");
                       notifyShowAboutDialog();
                }

                private void notifyShowAboutDialog() {
                        Iterator i = observers.iterator();
            while( i.hasNext() ) {
                  ActionObserver o = ( ActionObserver ) i.next();
                  o.showAboutDialog();
                 
            }
                }
                
            };
        }
        
        public MouseAdapter getStartAction(){
            return new MouseAdapter(){

                @Override
                public void mouseClicked(MouseEvent e) {
                    if(menus.get("start").isEnabled())
                  notifyStartIndexing();
                    
                }

                private void notifyStartIndexing() {
                      Iterator i = observers.iterator();
            while( i.hasNext() ) {
                  ActionObserver o = ( ActionObserver ) i.next();
                  o.startIndexing();
                 
            }
                }
                
            };
        }
        
        public MouseAdapter getSearchAction(){
            return new MouseAdapter(){

                @Override
                public void mouseClicked(MouseEvent e) {
                     System.out.println("Search pressed");
                     notifyStartSearching();
                    
                }

                private void notifyStartSearching() {
                    Iterator i = observers.iterator();
            while( i.hasNext() ) {
                  ActionObserver o = ( ActionObserver ) i.next();
                  o.startSearching();
                 
            }
                }
                
            };
        }
        
        public MouseAdapter getPreferencesAction(){
            return new MouseAdapter(){

                @Override
                public void mouseClicked(MouseEvent e) {
                     System.out.println("Preferences pressed");
                    
                }
                
            };
        }       
        
    }
   private String  getMenuToolText(String type){
        switch(type){
            case "save": return "Сохранить";
            case "read": return "Загрузить индексы изображений";
            case "about": return "О программе";
            case "start": return "Индексировать коллекцию";
            case "directory": return "Установить директорию";
            case "search": return "Запуск сравнения";
            case "preferences": return "Настройки";
            case "method_param": return "Параметры метода сравнения";
            case "refresh": return "Обновить коллекцию";
        }
        return null;
    }
    
    public JLabel getMenuLabel(String name){
        return menus.get(name);
    }
    
}
