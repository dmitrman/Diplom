/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.service_classes;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dima
 */
 public class ImageRenderer extends DefaultTableCellRenderer {
       private JButton lbl = new JButton();    
        @Override
       public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Border thickBorder = new LineBorder(Color.BLUE, 2);
            lbl.setBorder(thickBorder);
            lbl.setIcon((ImageIcon)value);
            return lbl;
       }
    }