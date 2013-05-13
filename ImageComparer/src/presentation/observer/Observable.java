/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.observer;

/**
 *
 * @author dgoncharenko
 */
public interface Observable {
     public void addObserver( PanelObserver o );
     public void removeObserver( PanelObserver o );
}
