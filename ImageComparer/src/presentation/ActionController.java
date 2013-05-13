
package presentation;

import com.google.gson.Gson;
import domain.CurvaturePhaseIndex;
import domain.CurvaturePhaseMethod;
import domain.ImageIndexes;
import domain.RGBBrightnessMethod;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import presentation.observer.ActionObserver;
import presentation.observer.Observable;
import presentation.observer.PanelObserver;
import presentation.observer.TableObserver;
import service.ImagePreviewPanel;
import sql.DbEntityImage;

/**
 * Класс ActionController реализует все функции отражающие взаимодействие
 * пользователя с UI
 * 
 * @version 1.0 30 Apr 2013
 * @author Dmitriy Goncharenko
 */

public class ActionController implements Observable,ActionObserver{      
    
    /** описание типа метода поиска по умолчанию */
    private String method="RGBbrightness";                                    
    /** создание экземпляра класса реализующего поиск по форме */
    private CurvaturePhaseMethod curvatureMethod=new CurvaturePhaseMethod();          
    /** создание экземпляра класса реализующего поиск по цвету */
    private RGBBrightnessMethod rgbbrightnessMethod=new RGBBrightnessMethod();  
    /** создание ссылки на лист всех изображений из БД */
    private List<DbEntityImage> list;// = repository.selectAllImageRecords();    
    /** список всех подписанных наблюдателей */
    private ArrayList observers = new ArrayList();
    /** создание логгера для класса Action Controller */
    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ActionController.class);   

    ActionController() {        
     
      pb=new IndicationBarFrame();    
    }
    
    /**
     * выводит сообщение о программе
     */
    @Override
    public void showAboutDialog(){        
      JOptionPane.showMessageDialog(null, "ImageComparer \n Программа предназначена для "
              + "поиска похожих изображений \n в коллекциях изображений "
              + "Copyright 2013.",
                "О программе", 1,
                new ImageIcon(getClass().getResource("/resources/info_blue.png")));  
       log.debug("About pressed!");
    }
    
    /**
     * Обрабатывает кнопку выхода
     */    
    @Override
    public void getExitHandler(){        
    int reply = JOptionPane.showConfirmDialog(null, "Вы действительно хотите выйти?", 
            "Подтвеждение", JOptionPane.YES_NO_OPTION,1,
            new ImageIcon(getClass().getResource("/resources/attention2.png")));  
    if (reply == JOptionPane.YES_OPTION)  
    {  
      System.exit(0);  
      log.info("Exit pressed!");
    }        
    }
    
    /**
     * метод выбора пути к директории 
     */
    @Override
    public void chooseDirectory(){
    JFileChooser chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("D:\\Dropbox\\DIPLOM\\"
            + "101_ObjectCategories\\101_ObjectCategories\\Image_DATA_BASE1"));
    chooser.setCurrentDirectory(new java.io.File("C:\\Users\\dgoncharenko\\"
            + "Dropbox\\DIPLOM\\101_ObjectCategories\\101_ObjectCategories\\"
            + "Image_DATA_BASE1"));
    chooser.setDialogTitle("Укажите путь к директории");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                   "All supported images Images", "jpg", "png");
    chooser.setFileFilter(filter);
    ImagePreviewPanel preview = new ImagePreviewPanel();
    chooser.setAccessory(preview);
    chooser.addPropertyChangeListener(preview);
    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);   
    //
    /* disable the "All files" option. */    
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): " 
         +  chooser.getCurrentDirectory().toString());
      //
    notifyLoadImageCollection(chooser.getSelectedFile().toString());
            //
   //    frame.getTableController().loadImageCollection(chooser.getSelectedFile()
                                                    //  .toString());
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());
      log.debug("Directory was choosen: "+chooser.getSelectedFile());
      }
    else {
           System.out.println("No Selection ");
         }
     }
    
    /**
     * реализует процедуру индексации изображений
     */
    @Override
     public void startIndexing(){        
        try {
            list =  ControllerFactory.getRepository().selectAllImageRecords();
            final Gson gson=new Gson();
            ProgressBarInit(list.size(),"Индексирование коллекции ...");                      
            log.debug("Indexing started..");
        class IndexingJobThread extends Thread{

          public IndexingJobThread() {
            ControllerFactory.getMenuPanelController().getMenuLabel("start").setEnabled(false);
            ControllerFactory.getMenuPanelController().getMenuLabel("start")            
                        .setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
          //  frame.setEnabled(false);
          }
         
         @Override
         public void run(){       
           
              for (DbEntityImage db : list ){
                 try {
                     ImageIndexes indexes=new ImageIndexes();
                     BufferedImage img=ImageIO.read(new File(db.path.replace("*","\\")));
                          System.out.println("Image "+db.name);
                          System.out.println("-------------------------");
                          
                          indexes.curvPhaseIndex=curvatureMethod
                                  .ComputeCurvaturePhaseIndex(img);    
                          indexes.rgbhalfton=rgbbrightnessMethod
                                  .ComputeRGBBrightnessIndexHalfton(img);
                          indexes.quadroTree=rgbbrightnessMethod
                                  .CompputeQuadroTree(img);
                          indexes.quadroTreeRGB=rgbbrightnessMethod
                                  .CompputeQuadroTreeBY_R_G_B(img);
               
                         ControllerFactory.getRepository()
                                  .updateIndexesImageRecord(db.path, gson.toJson(indexes));
                     a+=10;  
                 } catch (Exception ex) {
                     Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
                 } 
              }
           ControllerFactory.getMenuPanelController().getMenuLabel("start").setEnabled(true);
          
           log.debug("Indexing's finished!");
           /*   frame.setEnabled(true);    */
         }
     }
            new IndexingJobThread().start();
        } catch (Exception ex) {
            Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
}
     private int a;
     private Timer displayTimer;
     private final IndicationBarFrame pb;      
 private void ProgressBarInit(final int size, String task){  
          final JProgressBar jb= pb.getProgressBar();         
          jb.setMaximum((size)*10);
          pb.setStatusText(task);
          jb.setStringPainted(true);
          a=0;     
          pb.setVisible(true);        
          jb.setValue(0);
            ActionListener listener = new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event){
                       SwingUtilities.invokeLater(new Runnable() {
                           @Override
                          public void run() {                         
                              jb.setValue(a);                     
                          }
                       });
                       if(jb.getValue()==size*10){
                           displayTimer.stop();
                           pb.dispose();
                       }
                }
            };
    displayTimer = new Timer(150, listener);
    displayTimer.start();        
    pb.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  
    }
 
    /**
     * устанавливает тип метода для сравнения 
     */
    @Override
    public void setMethod(String str){
        this.method=str;
    }
    
    /**
     * выполняет загрузку образца для поиска     * 
     */
    @Override
    public void loadPatternImage(){
        JFileChooser chooser = new JFileChooser(); 
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
    "All supported images Images", "jpg", "gif","png");
     chooser.setFileFilter(filter);
     ImagePreviewPanel preview = new ImagePreviewPanel();
    chooser.setAccessory(preview);
    chooser.addPropertyChangeListener(preview);
   chooser.setCurrentDirectory(new java.io.File("D:\\Dropbox\\DIPLOM\\"
           + "101_ObjectCategories\\101_ObjectCategories\\Image_DATA_BASE1\\"
           + "Эксперимент_Форма"));
   // chooser.setCurrentDirectory(new java.io.File("C:\\Users\\dgoncharenko\\Dropbox\\DIPLOM\\101_ObjectCategories\\101_ObjectCategories\\Image_DATA_BASE1\\Эксперимент_3_Схожие_логотипы_БД"));
    chooser.setDialogTitle("Укажите путь к образцу");
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    //
    // disable the "All files" option.
    //
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): " 
         +  chooser.getCurrentDirectory().toString());
  /** 
   */
      notifyImageChanged(chooser.getSelectedFile().toString());
     
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());
      log.debug("Image pattern loaded:"+chooser.getSelectedFile());
      }
    else {
           System.out.println("No Selection ");
         }
    }
    
   /**
    * сравнивает текущее изображение из базы с образцом
    */
   public void compareCurrentWithSample(){
       
   }
   
  /**
   * поиск похожих изображений на образец в Базе коллекции
   */
  
    @Override
  public void  startSearching() {      
        try {              
            list =  ControllerFactory.getRepository().selectAllImageRecords();
            final Gson gson=new Gson();        
      
      if(ControllerFactory.getCurrentPanelController().flag) return;
      log.debug("Searching started!");
      final CurvaturePhaseIndex sampleind=ControllerFactory.getCurrentPanelController().sampleind;
      final double[] halfs=ControllerFactory.getCurrentPanelController().halfs;
      final ArrayList<double[]> quedro=ControllerFactory.getCurrentPanelController().quedro;
      final ArrayList<double[]> quedroRGB=ControllerFactory.getCurrentPanelController().quedroRGB;
            class SearchingJobThread extends Thread{

                public SearchingJobThread() {
                     ControllerFactory.getMenuPanelController().getMenuLabel("search")
                                                             .setEnabled(false);
                    ControllerFactory.getMenuPanelController().getMenuLabel("search")
                           .setBorder(BorderFactory
                                                .createEmptyBorder(2, 2, 2, 2));
                    // frame.setEnabled(false);
                }         
         @Override
         public void run(){       
             ImageIndexes indexes;
          
            for (DbEntityImage db : list ){                
               System.out.println("lll "+db.path);   
               indexes=gson.fromJson(db.indexes, ImageIndexes.class);             
             if(method.equals("Curvature")){
                    try {
                        double p=curvatureMethod.CorrelationCoefficient(sampleind
                                .phase,indexes.curvPhaseIndex.phase);
                        double n=curvatureMethod.CorrelationCoefficient(sampleind
                                .normal,indexes.curvPhaseIndex.normal);
                        double t=curvatureMethod.CorrelationCoefficient(sampleind
                                .tangential,indexes.curvPhaseIndex.tangential);
                        double s=curvatureMethod.CorrelationCoefficient(sampleind
                                .shape,indexes.curvPhaseIndex.shape);
                    
                        double general=(s+n+t+p)/4;
                        System.out.println("General= "+s+n+t+s);
                       //  double general=Math.pow(Math.pow(p,2)+Math.pow(n,2)+Math.pow(t,2)+Math.pow(s,2),0.5);
                         ControllerFactory.getRepository().updateCoefficImageRecord(db.path,general );
                    } catch (Exception ex) {
                        Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }                          
               if(method.equals("RGBbrightness")){
                    try {
                        double index= rgbbrightnessMethod.computeDifference(halfs, indexes.rgbhalfton);        
                      //  double index=rgbbrightnessMethod.ComputeQuadroDifferenceEvclid(quedro, indexes.quadroTree);
                        ControllerFactory.getRepository().updateCoefficImageRecord(db.path, index);
                    } catch (Exception ex) {
                        Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
               }
               if(method.equals("Quadro(Evclid)")){
                    try {
                        double index=rgbbrightnessMethod.ComputeQuadroDifferenceEvclid(quedro, indexes.quadroTree);
               ControllerFactory.getRepository().updateCoefficImageRecord(db.path, index);
                    } catch (Exception ex) {
                        Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
               
               }
              if(method.equals("Quadro(Histogram)")){
                    try {
                          double index=rgbbrightnessMethod.ComputeQuadroDifference(quedro, indexes.quadroTree);
                       ControllerFactory.getRepository().updateCoefficImageRecord(db.path, index);
                    } catch (Exception ex) {
                        Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
              }
              if(method.equals("Quadro(RGB)1")){
                    try {
                        double index=rgbbrightnessMethod.ComputeQuadroDifferenceEvclidRGB_1(quedroRGB, indexes.quadroTreeRGB);
                      ControllerFactory.getRepository().updateCoefficImageRecord(db.path, index);
                    } catch (Exception ex) {
                        Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
              }
              if(method.equals("Quadro(RGB)2")){
                    try {
                        double index=rgbbrightnessMethod.ComputeQuadroDifferenceEvclidRGB_2(quedroRGB, indexes.quadroTreeRGB);
                       ControllerFactory.getRepository().updateCoefficImageRecord(db.path, index);
                    } catch (Exception ex) {
                        Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
              }
              if(method.equals("Quadro(RGB)3")){
                    try {
                      /*  double p=curvatureMethod.CorrelationCoefficient(sampleind.phase,indexes.curvPhaseIndex.phase);
                        double n=curvatureMethod.CorrelationCoefficient(sampleind.normal,indexes.curvPhaseIndex.normal);
                        double t=curvatureMethod.CorrelationCoefficient(sampleind.tangential,indexes.curvPhaseIndex.tangential);
                        double s=curvatureMethod.CorrelationCoefficient(sampleind.shape,indexes.curvPhaseIndex.shape);
                        double general=Math.pow(Math.pow(p,2)+Math.pow(n,2)+Math.pow(t,2)+Math.pow(s,2),0.5);*/
                        double index=rgbbrightnessMethod.ComputeQuadroDifferenceEvclidRGB_3(quedroRGB, indexes.quadroTreeRGB);
                        double index1=rgbbrightnessMethod.ComputeQuadroDifferenceEvclid(quedro, indexes.quadroTree);
                        double index2= rgbbrightnessMethod.computeDifference(halfs, indexes.rgbhalfton);    
                      ControllerFactory.getRepository().updateCoefficImageRecord(db.path, (index+index1+index2)/3);
                    } catch (Exception ex) {
                        Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
              }
             }               
              EventQueue.invokeLater(new Runnable(){
                     @Override
                     public void run(){
                      try {
                          List<DbEntityImage> result= ControllerFactory.getRepository().selectAllImageRecordsOrderBy(method);
                            
                          notifyDisplayResult(result);
                         
                          ControllerFactory.getMenuPanelController().getMenuLabel("search").setEnabled(true); 
  //                        frame.setEnabled(true);
                          log.debug("Searching finished!");
                      } catch (Exception ex) {
                          Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
                      }                       
                     }
            
             });
         }         
         }  
            //
               new SearchingJobThread().start();
            //
        } catch (Exception ex) {
            Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
  
 /**
  * сохраняет индексы из БД в файл
  */      
    @Override
 public void saveIndexes(){
    try {
         DocumentBuilderFactory docFactory = 
                 DocumentBuilderFactory.newInstance();
                        DocumentBuilder docBuilder =
                                docFactory.newDocumentBuilder();
                        list = ControllerFactory.getRepository().selectAllImageRecords();
                        // root elements
                        Document doc = docBuilder.newDocument();
                        Element rootElement = doc.createElement("Indexes");
                        doc.appendChild(rootElement);
                        
                        for (DbEntityImage db : list ){                       
                                Element staff = doc.createElement("Image");
                                rootElement.appendChild(staff);   
                                Attr attr = doc.createAttribute("path");
                                attr.setValue(db.path);
                                staff.setAttributeNode(attr);         
                                staff.setAttribute("size", db.size);                        
         
                                // firstname elements
                                Element firstname = doc.createElement("index1");
                                firstname.appendChild(doc
                                                   .createTextNode(db.indexes));
                                staff.appendChild(firstname);   
                          }
                        // write the content into xml file
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File("/file.xml"));         
                        // Output to console for testing
                        // StreamResult result = new StreamResult(System.out);         
                        transformer.transform(source, result);
                        System.out.println("File saved!");
                        log.debug("Indexes of images saved into file!");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (Exception ex) {
            Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    @Override    
    public void readIndexes(){    
        try {
              ControllerFactory.getRepository().deleteAllImageRecords();  
              File fXmlFile = new File("/file.xml");
               
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);
         
                //optional, but recommended
                //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                doc.getDocumentElement().normalize();
         
                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         
                NodeList nList = doc.getElementsByTagName("Image");
         
                System.out.println("----------------------------");
         
                for (int temp = 0; temp < nList.getLength(); temp++) {
         
                        Node nNode = nList.item(temp);
         
                        System.out.println("\nCurrent Element :" + nNode.getNodeName());
         
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
         
                                Element eElement = (Element) nNode;
        File f=new File(eElement.getAttribute("path").replace("*","\\"));
       
       ControllerFactory.getRepository().insertImageRecord(eElement.getAttribute("path"), f.getName(), eElement.getAttribute("size"),eElement.getElementsByTagName("index1").item(0).getTextContent(), -1);
                        }
                }
        } catch (Exception ex) {
            Logger.getLogger(ActionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        log.debug("Indexes of images read from file!");
        notifyRefreshTable();
}
    @Override
    public void refresh() {
       HistogramRGB h= new HistogramRGB(ControllerFactory.getCurrentPanelController().getCurrentImage(),ControllerFactory.getCurrentPanelController().getCurrentImagePath());
       h.setVisible(true);
        
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
    private void notifyImageChanged(String path) {
         Iterator i = observers.iterator();
            while( i.hasNext() ) {
                Object ob=i.next();
                 if((ob instanceof PanelObserver)){
                  PanelObserver o = ( PanelObserver ) ob;
                  o.updateImage(this,path);
                 }
                  System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
                 
            }
    }
    private void notifyLoadImageCollection(String path) {
         Iterator i = observers.iterator();
            while( i.hasNext() ) {
                Object ob=i.next();
                 if((ob instanceof TableObserver)){
                  TableObserver o = ( TableObserver ) ob;
                  o.loadCollection(this,path);                
                 
                 }
                  System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
                 
            }
    }

    private void notifyRefreshTable() {
     Iterator i = observers.iterator();
            while( i.hasNext() ) {
                Object ob=i.next();
                 if((ob instanceof TableObserver)){
                  TableObserver o = ( TableObserver ) ob;
                  o.refreshTable();
                 }
                  System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
                 
            }
    }
         private void notifyDisplayResult(List<DbEntityImage> result) {
                     Iterator i = observers.iterator();
            while( i.hasNext() ) {
                Object ob=i.next();
                 if((ob instanceof TableObserver)){
                  TableObserver o = ( TableObserver ) ob;
                  o.displayResult(result);
                 }
                  System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
                 
            }
                
         
         }
}
