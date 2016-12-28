package com.av.service.exception;

/**
 * Created with IntelliJ IDEA.
 * User: alexey
 * Date: 5/20/14
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataBaseNotActual extends Exception {
    public DataBaseNotActual(String msg) {
        super(msg);
    }
}
