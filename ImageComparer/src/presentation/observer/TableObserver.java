/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.observer;

import java.util.List;
import sql.DbEntityImage;

/**
 *
 * @author dgoncharenko
 */
public interface TableObserver {
    public void updateTableSelectionInterval( Observable o,String action);  
    public void deleteImageRecord( Observable o); 
    public void addImageRecord( Observable o);  
    public void loadCollection(Observable o,String path);
    public void refreshTable();
    public void displayResult(List<DbEntityImage> result);
}
