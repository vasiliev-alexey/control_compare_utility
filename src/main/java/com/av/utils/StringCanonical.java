package com.av.utils;

/**
 * Created with IntelliJ IDEA.
 * User: alexey
 * Date: 5/20/14
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringCanonical {
    public synchronized static String toCanonical (String source) {
        if (source==null) source = "";
         return source.replaceAll("[\\n\\t ]", "").toUpperCase();
    }
    public synchronized static String toTrim (String source) {
        if (source==null) source = "";
        return (source.replaceAll("[\\n\\t]", "").replace("  " , " "));
    }

    public synchronized static  boolean equalsCanonical(String source , String target ) {

        if (source==null) source = "";
        if (target==null) target = "";

        if (source.equals(target)) {
            return true;
        }

        return  toCanonical(source).equals(toCanonical(target)) ;

    }

}
