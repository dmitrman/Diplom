/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import sql.DbEntityImage;
import presentation.service_classes.ImageRenderer;
import presentation.service_classes.TableModel;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.imgscalr.Scalr;
import presentation.observer.Observable;
import presentation.observer.PanelObserver;
import presentation.observer.TableObserver;

/**
 *
 * @author Dima
 */
public class TableController implements Observable,TableObserver{    
     /** список всех подписанных наблюдателей */
     private ArrayList observers = new ArrayList();
     private JTable table;   
     private TableModel model = new TableModel();
     private ListenerFactory lf=new ListenerFactory();
    
    public TableController(JTable table) {
        this.table=table;
     
             // Динамическое заполнение таблицы   
        
         model.addColumn("Name");
         model.addColumn("Location");
         model.addColumn("Coefficient");
         model.addColumn("Icon");
         model.addColumn("Size");      
         table.setModel(model);
           
        
           table.getColumnModel().getColumn(3).setCellRenderer(new ImageRenderer());
           table.getColumnModel().getColumn(3).setPreferredWidth(50);
           table.getColumnModel().getColumn(3).setResizable(false);
           table.setRowHeight(50);
           ListSelectionModel selModel = table.getSelectionModel();
           selModel.addListSelectionListener(lf.getListSelectionListener());
           table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
           // загрузка коллекции по умолчанию
         // loadImageCollection(directoryPath);
    }
    
 
    // загружает все изображения из директории в таблицу
    private void loadImageCollection(final String file){        
     class ComplexJobThread extends Thread{
     
     @Override
     public void run(){
         
             fillImageDB(file);
            
             // job is finished. We need to change the interface
          RefreshTable();
          
     
     }
 }
        new ComplexJobThread().start();     
   
    }
    private void RefreshTable(){
           EventQueue.invokeLater(new Runnable(){
                 @Override
                 public void run(){
                     try {
                         while (model.getRowCount()>0){
                              model.removeRow(0);
                         }
                          int i=0;              
                           for (DbEntityImage db :  ControllerFactory.getRepository().selectAllImageRecords() ){
                                try {
                                    System.out.println(db.path.replace("*","\\"));
                                    Object[] rowData = {db.name,db.path.replace("*","\\"),db.coeffic,new ImageIcon(Scalr.resize(ImageIO.read(new File(db.path.replace("*","\\"))), Scalr.Method.QUALITY,75,50, Scalr.OP_ANTIALIAS)),db.size};
                                    model.insertRow(i,rowData);
                                    i++;
                                } catch (IOException ex) {
                                    Logger.getLogger(TableController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                           }
                           if(table.getRowCount()!=0)
                           table.setRowSelectionInterval(0, 0);
                           
                          //  ((JLabel)frame.getStatusPanel().getComponent(0)).setText(Integer.toString(i));
                     } catch (Exception ex) {
                         Logger.getLogger(TableController.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }
                 
             });
    }
   private void DisplayResult(final List<DbEntityImage> result){
         
         EventQueue.invokeLater(new Runnable(){
                 @Override
                 public void run(){
                 try {
                     while (model.getRowCount()>0){
                          model.removeRow(0);
                     }
                      int i=0;              
                       for (DbEntityImage db : result ){
                            try {
                          //      if(i<10){
                                Object[] rowData = {db.name,db.path.replace("*","\\"),db.coeffic,new ImageIcon(Scalr.resize(ImageIO.read(new File(db.path.replace("*","\\"))), Scalr.Method.QUALITY,75,50, Scalr.OP_ANTIALIAS)),db.size};
                         
                                model.insertRow(i,rowData);//}
                                i++;
                            } catch (IOException ex) {
                                Logger.getLogger(TableController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                       }
                     // ((JLabel)frame.getStatusPanel().getComponent(0)).setText(Integer.toString(result.size()));
                      
                      
                       if(table.getRowCount()!=0)
                       table.setRowSelectionInterval(0, 0);
                 } catch (Exception ex) {
                     Logger.getLogger(TableController.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 }
                 
             });
   }

    @Override
    public void updateTableSelectionInterval(Observable o, String action) {        
        if(table.getRowCount()==0) return;        
        switch(action){ 
            case "DOWN":{
                 System.out.println("DOWN Action...");                
                 if((table.getSelectedRow()+1)<table.getRowCount())
                   table.setRowSelectionInterval(table.getSelectedRow()+1,table.getSelectedRow()+1);
                 else
                       table.setRowSelectionInterval(0,0);
            }break;
                
            case "UP":{
                System.out.println("UP Action...");
                if(table.getSelectedRow()!=0)
                         table.setRowSelectionInterval(table.getSelectedRow()-1,
                                                table.getSelectedRow()-1);
             else
                         table.setRowSelectionInterval(table.getRowCount()-1,
                                                table.getRowCount()-1);
            
            }break;
    };
    }

    @Override
    public void deleteImageRecord(Observable o) {
                   if(table.getRowCount()!=0){                          
                    try {
                        int row = table.getSelectedRow();                    
                        TableColumn col = table.getColumn("Location");
                        int column=col.getModelIndex();                       
                           
                       // frame.getTableController().getCollectionImages().remove(table.getValueAt(row, column));
                        ControllerFactory.getRepository().deleteImageRecord(table.getValueAt(row, column).toString().replace("\\","*"));
                       ((DefaultTableModel)table.getModel()).removeRow(row);
                
                        
                   if(table.getRowCount()!=0){
                            
                                int x=0;
                                
                                if(row!=0){
                                   table.setRowSelectionInterval(row-1,row-1); x=row-1;}  
                                 else
                                   table.setRowSelectionInterval(0,0);
                                   
                                // frame.getSamplePanelController().SetImage(ImageIO.read(new File(table.getValueAt(x, column).toString())));
                                  notifyImageChanged(table.getValueAt(x, column).toString());
                              //   frame.revalidate();
                            
                    }else                       
                              notifyImageChanged(getClass().getResource("/resources/image-1.png").getPath());                        
                    } catch (Exception ex) {
                        Logger.getLogger(JToolBarController.class.getName()).log(Level.SEVERE, null, ex);
                    }
        
            }
    }

    @Override
    public void addImageRecord(Observable o) {
        
    }

    @Override
    public void loadCollection(Observable o,String path) {
        loadImageCollection(path);
    }

    @Override
    public void refreshTable() {
       RefreshTable();
    }

    @Override
    public void displayResult(List<DbEntityImage> result) {
        DisplayResult(result);
    }

    
    //*****************************************************************************  
   // factory of classes(фабрика классов)
class ListenerFactory{
      
    public ListSelectionListener getListSelectionListener(){
      return new ListSelectionListener() {  
               @Override          
               public void valueChanged(ListSelectionEvent e) {
                   
                    String result = "";
                     int row = table.getSelectedRow();
                        TableColumn col = table.getColumn("Location");
                        int column=col.getModelIndex();
                   /* for(int i = 0; i < selectedRows.length; i++) {
                         int selIndex = selectedRows[i];
                         TableModel  model = (TableModel)table.getModel();
                         Object value = model.getValueAt(selIndex, 0);
                         result = result + value;
                         
                    }*/
                  
                          if(row!=-1)
                            notifyImageChanged(table.getValueAt(row, column).toString());
                    
               }  
          };
}
}    
    // заполняет массив изображениями из коллекции
 private void fillImageDB(String pathname){
        try {
         
           ControllerFactory.getRepository().deleteAllImageRecords();   
            
             File  path = new File(pathname);
             File[] files;       
         if (path.isFile()) {
               files = new File[]{path};
         } else {
               files = path.listFiles();
               //Arrays.sort(files, new FilesComparator());
          }
          
           for (final File f: files) {
               if(!f.isDirectory()){
                      //    System.out.println(f.getAbsolutePath());
                        if(isImage(f.getAbsolutePath())){               
                     
                               ControllerFactory.getRepository().insertImageRecord(f.getPath().replace("\\","*"), f.getName(), getFileSize(f), "", -1);
                    
                   
                  }
               }
           
           }
        } catch (Exception ex) {
            Logger.getLogger(TableController.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
 private boolean isImage(String str){
     
     int dotPos = str.lastIndexOf(".") + 1;  
     if(str.substring(dotPos).equals("jpg")){
         return  true;
     }
      if(str.substring(dotPos).equals("gif")){
         return  true;
     }
       if(str.substring(dotPos).equals("bmp")){
         return  true;
     }
        if(str.substring(dotPos).equals("png")){
         return  true;
     }
     return false;
 }
private String getFileSize(File f){
     String str;
     long fileSize = f.length();
    // if((double)fileSize/(1024*1024)>=1)
         str= Double.toString((double)fileSize/(1024*1024)).substring(0, 4)+"MB";
     /* if((double)fileSize/(1024)>=1)
          str= Double.toString((double)fileSize/(1024)).substring(0, 4)+"KB";  
      else str= Double.toString(fileSize).substring(0, 4)+"B";
      */
      return str;
 } 
@Override
    public void addObserver(PanelObserver o) {
       observers.add(o);
    }

    @Override
    public void removeObserver(PanelObserver o) {
        observers.remove(o);
    }
    private void notifyImageChanged(String path) {
            // loop through and notify each observer
            Iterator i = observers.iterator();
            while( i.hasNext() ) {
                  PanelObserver o = ( PanelObserver ) i.next();
                  o.updateImage(this,path);
                 
            }
    }
    
}
