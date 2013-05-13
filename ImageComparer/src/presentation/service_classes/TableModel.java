/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.service_classes;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dima
 */
public class TableModel extends DefaultTableModel {
             @Override
             public boolean isCellEditable(int row, int col) {
                    return false;
             }
 }