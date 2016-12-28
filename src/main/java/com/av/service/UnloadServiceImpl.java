package com.av.service;

import com.av.dao.sqllite.SQLiteDBUtils;
import com.av.dao.oracle.OracleControlDAO;
import com.av.domain.Control;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: vasiljev.alexey
 * Date: 13.05.14
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public class UnloadServiceImpl implements UnloadService {

    private Logger log = LogManager.getLogger(UnloadServiceImpl.class);

    @Override
    public List<Control> unloadControls() {

        return new OracleControlDAO().findAll();

    }

    @Override
    public void saveConrols(List<Control> controlList) {
        SQLiteDBUtils.createDB();
         SQLiteDBUtils.getInstance().insertConrols(controlList);

    }


    private byte[] getArchiveDB() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zipfile = new ZipOutputStream(bos);
        FileInputStream fileInputStream = null;
        String fileName = SQLiteDBUtils.getDBName() + ".db";
        ZipEntry zipentry = null;

        log.debug("fileName=" + fileName);

        File file = new File(fileName);

        byte[] bFile = new byte[(int) file.length()];
        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();

            log.debug("bFile.size =" + bFile.length);


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            zipentry = new ZipEntry("controls.db");
            zipfile.putNextEntry(zipentry);
            zipfile.write((bFile));
            zipfile.flush();
            zipfile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();

    }

    @Override
    public void saveArc(File file) {



        try {
            byte[] bytes = getArchiveDB();
            FileOutputStream fw = new FileOutputStream(file);
            fw.write(bytes);
            fw.flush();
            fw.close();

        } catch (IOException e1) {
            log.info("Some Error ocurred", e1);
        }

    }


}

