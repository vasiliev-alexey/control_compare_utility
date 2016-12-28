package com.av.utils;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: alexey
 * Date: 1/1/14
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageIconUtils {


    public static ImageIcon getImigeIcon(String name , Class clazz)  {

        ImageIcon icon = new ImageIcon(clazz.getClassLoader().getResource(name));
        return  icon;
    }
}
