package com.av.service;

import com.av.service.exception.FileFormatException;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: alexey
 * Date: 5/20/14
 * Time: 7:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface LoadService {

    public void loadDB(File file) throws FileFormatException;

    public  boolean isActualDate();

}
