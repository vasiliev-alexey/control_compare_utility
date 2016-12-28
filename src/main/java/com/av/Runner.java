package com.av;

import ch.randelshofer.quaqua.QuaquaManager;
import com.av.ui.ClientMode;
import com.av.ui.MainForm;
import com.av.ui.Mode;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vasiljev.alexey
 * Date: 13.05.14
 * Time: 11:09
 */
public class Runner {

    public static String APPVER = "1.0";

    private static Logger logger = LogManager.getLogger(Runner.class);

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
            ClientMode.getInstance().setMode(Mode.ADMIN);
        } else {
            logger.debug("MODE CLIENT");
            ClientMode.getInstance().setMode(Mode.CLIENT);

        }


        if (args.length > 1 && args[1].equals("DEBUG")) {
            logger.debug("MODE DEBUG");
            ClientMode.getInstance().setDebug(true);
        } else {
            logger.debug("MODE PRODUCTION");
            ClientMode.getInstance().setDebug(false);

        }


        logger.debug("Go go");

        launchUI();
    }


    private static void launchUI() {
        try {
           // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(QuaquaManager.getLookAndFeel());

      /*  } catch (ClassNotFoundException e) {
            e.printStackTrace();  
        } catch (InstantiationException e) {
            e.printStackTrace();  
        } catch (IllegalAccessException e) {
            e.printStackTrace();  */
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();  
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new MainForm();
            }
        });

    }


}
