package com.av.ui.renders;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


public class ImageTableCellRenderer extends DefaultTableCellRenderer {
    // метод возвращает компонент для прорисовки
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        ImageTableCell imageCell = (ImageTableCell)value;
// получаем настроенную надпись от базового класса
        JLabel label = (JLabel)super.getTableCellRendererComponent(table,
                imageCell.text, isSelected, hasFocus, row, column);
// устанавливаем значок
        label.setIcon(imageCell.icon);
        return label;
    }
}
