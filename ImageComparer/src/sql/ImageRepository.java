/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Dima
 */
public  class ImageRepository {
 DbConnect  db=new DbConnect();   
 // CREATE TABLE Images (id INT AUTO_INCREMENT  NOT NULL PRIMARY KEY, path TEXT NOT NULL , name TEXT, size TEXT, indexes TEXT, coeffic DOUBLE)
  
     
    public void insertImageRecord(String path,String name,String size,String indexes,double coeffic) throws Exception{
        try {
    
            String query="INSERT INTO Images(path,name,size,indexes,coeffic) VALUES(\""+path+"\",\""+name+"\",\""+size+"\",\'"+indexes+"\',"+coeffic+");";      
       
                 System.out.println(query);
            db.execute(query);
       
        } catch (SQLException ex) {        
            System.out.println("Insert Exception"+ex.getMessage());
        }
    }
    // обновить значение коэффиц.
    public void updateCoefficImageRecord(String path,double coeffic) throws Exception{
        try {
     
           String query="UPDATE  Images SET coeffic="+coeffic+" WHERE path='"+path+"'";          
           db.execute(query);      
        } catch (SQLException ex) {
            Logger.getLogger(ImageRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     // обновить значение индекса
    public void updateIndexesImageRecord(String path,String indexes) throws Exception{
        try {
           String query="UPDATE  Images SET indexes='"+indexes+"' WHERE path='"+path+"'";   
           db.execute(query);        
        } catch (SQLException ex) {
            Logger.getLogger(ImageRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // удалить запись из бд
    public void deleteImageRecord(String path) throws Exception{
        try {
       
            String query="DELETE FROM Images WHERE path='"+path+"'";        
           db.execute(query);
  
        } catch (SQLException ex) {
            Logger.getLogger(ImageRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<DbEntityImage> selectAllImageRecords() throws Exception{
       
       //      DbConnect  db=new DbConnect(db_path);
            try {
                List<DbEntityImage> list = new ArrayList<DbEntityImage>();
                          DbEntityImage ent;
                          ResultSet rs;
                        //  java.sql.Statement stmt =conn.createStatement();
                          
                            rs = db.executeQuery("SELECT * FROM Images");
                            
                        while (rs.next()) 
                        {  
                                ent = new DbEntityImage();
                                ent.id=rs.getInt("id");
                                ent.path=rs.getString("path");
                                ent.name=rs.getString("name");
                                ent.indexes=rs.getString("indexes");
                                ent.coeffic=rs.getDouble("coeffic");
                                ent.size=rs.getString("size");
                                list.add(ent);
                        }
               //            db.closeConnection();
                        return list;
            } catch (SQLException ex) {
                Logger.getLogger(ImageRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        
         return null;
    }
    // удалить все записи из бд
    public void deleteAllImageRecords() throws Exception{
        try {
    ///      DbConnect  db=new DbConnect(db_path);
           String query="DELETE FROM Images";
    //         java.sql.Statement stmt = conn.createStatement();
            db.execute(query);
          // db.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ImageRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public DbEntityImage selectImageRecord(String path){
        try {
            ResultSet rs;
           //     DbConnect  db=new DbConnect(db_path);
                      //   java.sql.Statement stmt = conn.createStatement();
                           rs = db.executeQuery("SELECT * FROM Images WHERE path='"+path+"'");
                   
                               DbEntityImage ent = new DbEntityImage();
                               ent.id=rs.getInt("id");
                               ent.path=rs.getString("path");
                               ent.name=rs.getString("name");
                               ent.indexes=rs.getString("indexes");
                               ent.coeffic=rs.getDouble("coeffic");
                               ent.size=rs.getString("size");
                        //            db.closeConnection();
                               return ent;
        } catch (Exception ex) {
            Logger.getLogger(ImageRepository.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
                   
    }
   /* public void removeImageRecord(String path){
          try {
        Connection conn=pool.getConnection();
           String query="DELETE FROM Images WHERE path='"+path+"'";
             java.sql.Statement stmt = conn.createStatement();
             pool.execute(stmt,query);
       conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ImageRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    public String getNumberOfRecords() throws Exception{
        try {
           // DbConnect db=new DbConnect(db_path);
            String query="SELECT COUNT(id) FROM Images";
           //   java.sql.Statement stmt = pool.getConnection().createStatement();
            ResultSet rs=db.executeQuery(query);
           
          while (rs.next()) {
 return rs.getString(1);
 }
        } catch (SQLException ex) {
            Logger.getLogger(ImageRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    public List<DbEntityImage> selectAllImageRecordsOrderBy(String s) throws Exception{
        try {
      //      DbConnect db=new DbConnect(db_path);
           //  Connection conn=pool.getConnection();
            List<DbEntityImage> list = new ArrayList<DbEntityImage>();
                      DbEntityImage ent;
                      double avg = 0;
                      ResultSet rs;
               //       rs = db.executeQuery("SELECT SUM(coeffic)/COUNT(coeffic) FROM Images");
               /*         while (rs.next()) {
                           avg=rs.getDouble(1);
                        }
                     //   java.sql.Statement stmt = conn.createStatement();
                        if(s.equals("Curvature"))
                              rs = db.executeQuery("SELECT * FROM Images where coeffic>"+avg+"  ORDER BY coeffic desc");
                        else
                              rs = db.executeQuery("SELECT * FROM Images where coeffic<"+avg+" ORDER BY coeffic asc");
                            */ 
                        rs = db.executeQuery("SELECT * FROM Images ORDER BY coeffic asc");
                   
                       
                    while (rs.next()) 
                    {  
                            ent = new DbEntityImage();
                            ent.id=rs.getInt("id");
                            ent.path=rs.getString("path");
                            ent.name=rs.getString("name");
                            ent.indexes=rs.getString("indexes");
                            ent.coeffic=rs.getDouble("coeffic");
                            ent.size=rs.getString("size");
                            list.add(ent);
                    }
                //      db.closeConnection();
                    return list;
        } catch (SQLException ex) {
            Logger.getLogger(ImageRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
