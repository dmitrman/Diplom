/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.observer;

/**
 *
 * @author dgoncharenko
 */
public interface ActionObserver {
    public void refresh();
    public void readIndexes();
    public void saveIndexes();
    public void startSearching();
    public void loadPatternImage();
    public void setMethod(String str);
    public void startIndexing();
    public void chooseDirectory();
    public void getExitHandler();
    public void showAboutDialog();
}
