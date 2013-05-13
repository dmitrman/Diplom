/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author dgoncharenko
 */
public class BoxLayoutUtils {
    public static JPanel createVerticalJPanel(){
        JPanel p=new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        return p;
    }
    public static JPanel createHorizontalJPanel(){
        JPanel p=new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        return p;
    }
}
