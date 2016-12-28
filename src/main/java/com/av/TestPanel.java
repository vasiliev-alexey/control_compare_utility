package com.av;

import com.av.dao.sqllite.SQLiteControlDAO;
import com.av.domain.Control;
import com.av.ui.ClientMode;
import com.av.ui.Mode;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import static com.av.ui.ClientMode.*;

/**
 * Created with IntelliJ IDEA.
 * User: alexey
 * Date: 5/23/14
 * Time: 10:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestPanel {
    private static Logger logger = LogManager.getLogger(TestPanel.class);

    public static void main(String[] args) {

        Properties props = new Properties();
        String separator = System.getProperty("file.separator");
        try {
            URL url = new File(System.getProperty("user.dir")
                    + separator + "etc" + separator + "log4j.properties").toURI().toURL();
            PropertyConfigurator.configure(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        if (args.length == 0) {
            throw new RuntimeException("Пустые параметры");
        }

        if (args[0].equals(Mode.ADMIN.toString())) {
            logger.debug("MODE ADMIN");
            getInstance().setMode(Mode.ADMIN);
        } else {
            logger.debug("MODE CLIENT");
            getInstance().setMode(Mode.CLIENT);

        }


        if (args.length > 1 && args[1].equals("DEBUG")) {
            logger.debug("MODE DEBUG");
            getInstance().setDebug(true);
        } else {
            logger.debug("MODE PRODUCTION");
            getInstance().setDebug(false);

        }


        logger.debug("Go go");

        SQLiteControlDAO dao = new SQLiteControlDAO();


        Control ctrl = dao.findByCode("MDK0521416");
        Control ctrl2 = dao.findByCode("VDK0531981");



        System.out.println(ctrl);


    }


}
