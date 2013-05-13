/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Version {

    public static void main(String[] args) {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
    String createTable="create table if not exists Images (id int not null auto_increment, path text not null, name text, size text, indexes text, coeffic double,primary key (id))";

        String url = "jdbc:mysql://localhost:3306/Images";
        String user = "root";
        String password = "123";
// create table if not exists Images (id int not null auto_increment, path text not null, name text, size text, indexes text, coeffic double,primary key (id));
        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
           rs = st.executeQuery("SELECT count(id) from Images");
//  st.execute(createTable);
          if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Version.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}