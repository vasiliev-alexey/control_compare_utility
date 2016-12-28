package com.av.ui.renders;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: alexey
 * Date: 5/27/14
 * Time: 10:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateTableCellRender  extends DefaultTableCellRenderer {
    private static  final  SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if( value instanceof Date) {
            value = f.format(value);
        }
        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
    }
}
