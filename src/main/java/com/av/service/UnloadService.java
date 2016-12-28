package com.av.service;

import com.av.domain.Control;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vasiljev.alexey
 * Date: 13.05.14
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public interface UnloadService {

  public List<Control> unloadControls();

  public void saveConrols(List<Control> controlList);

  //public   byte[] getArchiveDB ();


  public void saveArc (File file);

}
