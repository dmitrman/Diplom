/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Dima
 */
public class XmlMenuLoader {
    // источник данных ХМЛ
    private InputSource source;
    // анализатор ХМЛ
    private SAXParser parser;
    // обработчик ХМЛ
    private DefaultHandler documentHandler;
    // хранилище для всех частей системы меню
    private Map menuStorage=new HashMap();
    
    public  XmlMenuLoader(InputStream stream){
        try {
            // настраиваем источник данных
            Reader reader=new InputStreamReader(stream,Charset.forName("UTF-8"));
            source=new InputSource(reader);
            parser=SAXParserFactory.newInstance().newSAXParser();
            
            // создаём обработчик XML
            documentHandler=new XMLParser();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XmlMenuLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XmlMenuLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   // считывает ХМЛ - включает распознаватель XML
    public void parse() throws IOException, SAXException{
        parser.parse(source, documentHandler);
    }
   // позволяет получить строку меню
    public JMenuBar getMenuBar(String name){
        return (JMenuBar)menuStorage.get(name);
    }
    // позволяет получить выпадающее меню
    public JMenu getMenu(String name){
        return (JMenu)menuStorage.get(name);
    }
    // позволяет получить элемент меню
    public JMenuItem getMenuItem(String name){
        return (JMenuItem)menuStorage.get(name);
    }
    // позволяет получить элемент меню
    public JCheckBoxMenuItem getCheckBoxMenuItem(String name){
        return (JCheckBoxMenuItem )menuStorage.get(name);
    }
    // удобный метод для быстрого добавления слушателя событий
    public void addActionListener(String name, ActionListener listener){
        getMenuItem(name).addActionListener(listener);
    }
    // текущая строка меню
    private JMenuBar currentMenuBar;
    // список для упорядочения выподающих меню
    private LinkedList menus=new LinkedList();
    
   // обработчик XML
    class XMLParser extends DefaultHandler{
        // 
        @Override
         public void startElement(String uri,String localName,String qName,Attributes attributes){
            //
            if(qName.equals("menubar"))
                parseMenuBar(attributes);
            else
                if(qName.equals("menu"))
                    parseMenu(attributes);
                else
                    if(qName.equals("menuitem"))   
                        parseMenuItem(attributes);
                    else  
                       if(qName.equals("checkboxmenuitem"))   
                        parseCheckBoxMenuItem(attributes);
        }
        
        @Override
        public void endElement(String uri,String localName,String qName){
           if(qName.equals("menu"))  menus.removeFirst();
        }
        // 
        protected void parseMenuBar(Attributes attrs){
            JMenuBar menuBar=new JMenuBar();
            //
            String name=attrs.getValue("name");
            menuStorage.put(name, menuBar);
            currentMenuBar=menuBar;
        }
        //
        protected void parseMenu(Attributes attrs){
            JMenu menu=new JMenu();
            String name=attrs.getValue("name");
            adjustProperties(menu,attrs);
            menuStorage.put(name, menu);
            //
            if(menus.size()!=0){
                ((JMenu)menus.getFirst()).add(menu);
            }else
            {
                currentMenuBar.add(menu);
            }
            //
            menus.addFirst(menu);
         }
        // 
         protected void parseMenuItem(Attributes attrs){
             //
             String name=attrs.getValue("name");
             if(name.equals("separator")){
                 ((JMenu)menus.getFirst()).addSeparator();
                 return;
             }
             //
             JMenuItem menuItem=new JMenuItem();
             //
             adjustProperties(menuItem, attrs);
             menuStorage.put(name,menuItem);
             //
             ((JMenu)menus.getFirst()).add(menuItem);
                 
         }
         protected void parseCheckBoxMenuItem(Attributes attrs) {
           String name=attrs.getValue("name");
           JCheckBoxMenuItem checkBoxItem=new JCheckBoxMenuItem();
           
           adjustProperties(checkBoxItem, attrs);
            menuStorage.put(name,checkBoxItem);
             //
             ((JMenu)menus.getFirst()).add(checkBoxItem);
        }
         //
         private void adjustProperties(JMenuItem menuItem, Attributes attrs){
             //
             String text=attrs.getValue("text");
             String mnemonic=attrs.getValue("mnemonic");
             String accelerator=attrs.getValue("accelerator");
             String enabled=attrs.getValue("enabled");
             String group=attrs.getValue("buttongroup");
           //  String status=attrs.getValue("status");
             //
             menuItem.setText(text);
             if(mnemonic!=null){
                 menuItem.setMnemonic(mnemonic.charAt(0));
             }
             if(accelerator!=null){
                 menuItem.setAccelerator(KeyStroke.getKeyStroke(accelerator));
             }
             if(enabled!=null){
                 boolean isEnabled=true;
                 if(enabled.equals("false"))
                     isEnabled=false;
                 menuItem.setEnabled(isEnabled);
             }
             
               if(group!=null){
                   javax.swing.ButtonGroup  gr;
                  if(menuStorage.get(group)==null){ 
                       gr=new ButtonGroup();                      
                       gr.add(menuItem);
                        gr.setSelected(menuItem.getModel(), true);
                       menuStorage.put(group, gr);
                  }else
                  {
                      gr= (ButtonGroup) menuStorage.get(group);
                      gr.add(menuItem);
                  }
               }
         }

        
    }
}
