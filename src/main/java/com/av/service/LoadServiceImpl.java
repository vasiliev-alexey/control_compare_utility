package com.av.service;

import com.av.dao.sqllite.SQLiteControlDAO;
import com.av.dao.sqllite.SQLiteDBUtils;
import com.av.service.exception.FileFormatException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.*;
import java.sql.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created with IntelliJ IDEA.
 * User: alexey
 * Date: 5/20/14
 * Time: 7:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoadServiceImpl implements LoadService {

    private Logger logger = LogManager.getLogger(LoadServiceImpl.class);
    private  SQLiteControlDAO sqLiteControlDAO = new SQLiteControlDAO();

    @Override
    public void loadDB(File file) throws FileFormatException {

        String fileName = SQLiteDBUtils.getDBName() + ".db";

        logger.debug("fileName="+fileName);

       File newFileDb = new File(fileName);
        newFileDb.deleteOnExit();

    /*    if (newFileDb.exists()){
            logger.debug("Remove old file db");
          //  newFileDb.delete();
            newFileDb.deleteOnExit();
        }*/


        if ( ! file.getName().endsWith("zip"))  {
          throw  new FileFormatException("Файл не соотвествует формату архива zip");
        }

        Enumeration entries ;
        ZipFile zip ;
        try {
            zip = new ZipFile(file);
            entries = zip.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println("Extracting:" + entry.getName());

                write(zip.getInputStream(entry),
                        new BufferedOutputStream (new
                                FileOutputStream(fileName)));
            }

            zip.close();
        }
        catch (IOException e) {
            logger.debug("Exception in Extracting" , e);



            return;

        }
    }

    @Override
    public boolean isActualDate()  {

        Date dt = new Date(System.currentTimeMillis());

       Date  dtCtrl =   sqLiteControlDAO.getVersionDate();

        int days =   Days.daysBetween(new DateTime(dt) , new DateTime(dtCtrl)).getDays() ;

        logger.debug("Date diff="+ days);

        return  days < 2;


    }

    private static void write(InputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        in.close();
        out.close();
    }





}
