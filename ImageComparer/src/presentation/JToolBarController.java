/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import presentation.observer.Observable;
import presentation.observer.PanelObserver;
import presentation.observer.TableObserver;


/**
 *
 * @author dgoncharenko
 */
public class JToolBarController implements Observable{
    private JToolBar toolbar;   
     /** список всех подписанных наблюдателей */
    private ArrayList observers = new ArrayList();
       
    JToolBarController(JToolBar toolbar) {
      
       this.toolbar=toolbar;
       Init();
      
    }

    void Init(){
        toolbar.add(Box.createVerticalStrut(50));
        GeneralButton but=new GeneralButton(new AddAction());       
        toolbar.add(but);
        but=new GeneralButton(new RemoveAction());
        toolbar.add(but);
        toolbar.addSeparator();
        but=new GeneralButton(new UpAction());
        toolbar.add(but);
        but=new GeneralButton(new DownAction());
        toolbar.add(but);
        toolbar.setFloatable(false);// перемещаемый или нет тулбар
    }
    
    @Override
    public void addObserver(PanelObserver o) {
       observers.add(o);
    }

    @Override
    public void removeObserver(PanelObserver o) {
        observers.remove(o);
    }
    public void addObserver(TableObserver o) {
       observers.add(o);
    }
    
    public void removeObserver(TableObserver o) {
        observers.remove(o);
    }
            
    class UpAction extends AbstractAction{
            public UpAction (){
               //настроим значок подсказки
              putValue(AbstractAction.SMALL_ICON, new ImageIcon(getClass().getResource("/resources/arrow_up1.png")));
              // текст подсказки
              putValue(AbstractAction.SHORT_DESCRIPTION,"Вверх");              
            }
            @Override
            public void actionPerformed(ActionEvent e) {
               notifyTableSelectionIntervalChanged("UP");
            }                          
    }
    // введён для управления курсором
    class GeneralButton extends JButton{

        public GeneralButton(Action act) {
         super(act);
         this.addMouseListener(new MouseAdapter() {        
             @Override
         public void mouseEntered(java.awt.event.MouseEvent me) {
            toolbar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));            
        }        
             @Override
        public void mouseExited(java.awt.event.MouseEvent me) {
            toolbar.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        }
        });
        }       
    }    
    
    class DownAction extends AbstractAction{
            public DownAction (){
               //настроим значок подсказки
              putValue(AbstractAction.SMALL_ICON, new ImageIcon(getClass().getResource("/resources/arrow_down1.png")));
              // текст подсказки
              putValue(AbstractAction.SHORT_DESCRIPTION,"Вниз");
            }
            @Override
            public void actionPerformed(ActionEvent e) {
          //   if(table.getRowCount()!=0){
                System.out.println("DOWN Action...");
                notifyTableSelectionIntervalChanged("DOWN");
            }              
    }
    
   class AddAction extends AbstractAction{
            public AddAction (){
               //настроим значок подсказки
              putValue(AbstractAction.SMALL_ICON, new ImageIcon(getClass().getResource("/resources/plus1.png")));
              // текст подсказки
              putValue(AbstractAction.SHORT_DESCRIPTION,"Добавить");
            }
            @Override
            public void actionPerformed(ActionEvent e) {            
                System.out.println("Add document...");
                notifyAddImageRecord();
            }             
   }

   class RemoveAction extends AbstractAction{
            public RemoveAction (){
               //настроим значок подсказки
              putValue(AbstractAction.SMALL_ICON, new ImageIcon(getClass().getResource("/resources/minus.png")));
              // текст подсказки
              putValue(AbstractAction.SHORT_DESCRIPTION,"Удалить");
            }
            @Override
            public void actionPerformed(ActionEvent e) {
               notifyDeleteImageRecord();
            }              
    }
   
    private void notifyDeleteImageRecord(){
         // loop through and notify each observer
            Iterator i = observers.iterator();
            while( i.hasNext() ) {
                  TableObserver o = ( TableObserver ) i.next();
                  o.deleteImageRecord(this);
                 
            }
    }
    
     private void notifyAddImageRecord(){
         // loop through and notify each observer
            Iterator i = observers.iterator();
            while( i.hasNext() ) {
                  TableObserver o = ( TableObserver ) i.next();
                  o.addImageRecord(this);
                 
            }
    }
     
    private void notifyTableSelectionIntervalChanged(String action){
         // loop through and notify each observer
            Iterator i = observers.iterator();
            while( i.hasNext() ) {
                  TableObserver o = ( TableObserver ) i.next();
                  o.updateTableSelectionInterval(this,action);
                 
            }
    }
    
}
 