package com.av.ui.renders;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: vasiljev.alexey
 * Date: 22.05.14
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */
public class ImageTableCell {
    public Icon icon;
    public String text;
    // удобный конструктор
    public ImageTableCell(String text, Icon icon) {
        this.icon = icon;
        this.text = text;
    }
}
