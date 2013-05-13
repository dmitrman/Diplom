/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author dgoncharenko
 */
public class DbConnect { 
    public String sUrl; 
    String user;
    String password;
    private Connection conn = null;
    private Statement stmt = null;
 
    public DbConnect() {      
        readConfigurationInformation();
            try {
			setConnection();
		} catch (Exception e) {
			System.out.println("Cannot connect");
			e.printStackTrace();
		}
    } 
    private void setConnection() throws Exception {
            // create a database connection
            conn = DriverManager.getConnection(sUrl,user,password);
            try {
                stmt = conn.createStatement();
              
              
            } catch (Exception e) {
                try { conn.close(); } catch (Exception ignore) {}
                conn = null;
              
            }
    }
 
    // this method should undoubtedly be public as we'll want to call this
    // to close connections externally to the class
    public void closeConnection() {
        if (stmt!=null) { try { stmt.close(); } catch (Exception ignore) {} }
        if (conn!=null) { try { conn.close(); } catch (Exception ignore) {} }
    }
 
    // and we will definitely want to be able to call the following two
    // functions externally since they expose the database
    // behaviour which we are trying to access
    public ResultSet executeQuery(String instruction) throws SQLException, Exception {
        return stmt.executeQuery(instruction);
    }
 
    public void execute(String instruction) throws SQLException, Exception { 
        stmt.executeUpdate(instruction);
    }
private  void readConfigurationInformation(){
        try { 
            
            File fXmlFile = new File(getClass().getResource("/resources/db-settings.xml").getPath());
               
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile); 
                doc.getDocumentElement().normalize();
              //  System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
                NodeList nList = doc.getElementsByTagName("local-datasource");
              //  System.out.println("----------------------------");
                for (int temp = 0; temp < nList.getLength(); temp++) {         
                        Node nNode = nList.item(temp);         
                        System.out.println("\nCurrent Element :" + nNode.getNodeName());         
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {         
                                Element eElement = (Element) nNode;
                        user=eElement.getElementsByTagName("user-name").item(0).getTextContent();
                        password=eElement.getElementsByTagName("password").item(0).getTextContent();
                        sUrl=eElement.getElementsByTagName("connection-url").item(0).getTextContent();
                        }
                }
        } catch (SAXException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
      
}

}
