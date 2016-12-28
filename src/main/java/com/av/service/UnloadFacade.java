package com.av.service;

import com.av.dao.sqllite.SQLiteDBUtils;
import com.av.domain.Control;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vasiljev.alexey
 * Date: 20.05.14
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class UnloadFacade {

    private Logger log = LogManager.getLogger(UnloadFacade.class);

    public void unload(File file) {

        log.debug("Run  UnloadFacade.unload file=" + file.getName());
        SQLiteDBUtils ss =  SQLiteDBUtils.getInstance();
        log.debug("Run Create SQLLite DB");
        SQLiteDBUtils.createDB();

        UnloadService serv = new UnloadServiceImpl();
        log.debug("Run unloadControls");
        List<Control> list = serv.unloadControls();
        log.debug("Run insertConrols");
        ss.insertConrols(list);
        log.debug("Run Save file");
        serv.saveArc(file);
        log.debug("exit from UnloadFacade.unload");
    }

}
