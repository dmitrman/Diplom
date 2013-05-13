/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


 import java.sql.Connection;  
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;  
import java.util.Properties;
import java.util.logging.Level;

 public class ConnectSQLite 
 {  
  public static void main(String[] args) 
  {  
     Connection connection = null;  
     ResultSet resultSet = null;  
     Statement statement = null;  


     try 
     {  
         
       //  Class.forName("org.sqlite.JDBC"); 
         Class.forName("oracle.jdbc.driver.OracleDriver");
          Properties db_credentials = new Properties(); 
            db_credentials.put("user", "sys"); 
            db_credentials.put("password", "123"); 
            db_credentials.put("internal_logon", "sysdba");

         connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",db_credentials);  
         statement = connection.createStatement();  
        resultSet = statement.executeQuery("SELECT table_name  FROM user_tables");  
       //  String sMakeInsert = "INSERT INTO Monitor VALUES(1,'Toshiba',23)";
        // String h="CREATE TABLE IF NOT EXISTS Monitor ( id numeric, name text,size real)  ";
        // statement.executeUpdate(sMakeInsert);
         
         while (resultSet.next()) 
         {  
             System.out.println("EMPLOYEE NAME:"  
                     + resultSet.getString("table_name"));  
         }  
     }
     catch (Exception e) 
     {  
         e.printStackTrace();  
     }
     finally 
     {  
         try 
         {  
//             resultSet.close();  
             statement.close();  
             connection.close();  
         } 
         catch (Exception e) 
         {  
             e.printStackTrace();  
         }  
     }  
 }  
}  