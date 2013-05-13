
import sql.DbEntityImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dgoncharenko
 */

public class JDBCTest {
    static Connection connection;
    static Statement statement;

    public static void main(String args[]){

        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
            String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=C:\\PASS.mdb";
            connection = DriverManager.getConnection( database ,"",""); 

            buildStatement();
            executeQuery();

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error!");
        }
    }

    public static void buildStatement() throws SQLException {
        statement = connection.createStatement();
    }

    public static void executeQuery() throws SQLException {

        boolean foundResults = statement.execute("SELECT * FROM Price;");
        if(foundResults){
            ResultSet set = statement.getResultSet();
            //if(set!=null) displayResults(set);
              while (set.next()) 
                        {  
                               
                                String id=set.getString("id");
                                String path=set.getString("Nested");
                                String name=set.getString("materialcost");
                                String indexes=set.getString("productionhour");
                                System.out.println(id+" "+path+" "+name+" "+indexes);
                        }
        }else {
            connection.close();
        }
    }
}