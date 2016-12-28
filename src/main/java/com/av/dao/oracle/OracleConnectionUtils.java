package com.av.dao.oracle;

import com.av.ui.ClientMode;
import com.av.ui.Mode;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class OracleConnectionUtils {

    private Logger logger = LogManager.getLogger(OracleConnectionUtils.class);
    private BasicDataSource dataSource = new BasicDataSource();
    private OracleConnectionUtils() {
        setDataSource();
    }

    private static volatile OracleConnectionUtils instance;

    public static OracleConnectionUtils getInstance() {
        OracleConnectionUtils localInstance = instance;
        if (localInstance == null) {
            synchronized (OracleConnectionUtils.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new OracleConnectionUtils();
                }
            }
        }
        return localInstance;
    }



    private DataSource getDataSource() {
        if (dataSource == null) setDataSource();
        return dataSource;

    }


    private void setDataSource() {
        Properties prop = new Properties();
        InputStream input = null;

        String host = "";
        String port = "";
        String sid = "";
        String dbUser = "";
        String dbPass = "";


        try {
            String filename;
            if (ClientMode.getInstance().getMode().equals(Mode.ADMIN)) {
                filename = "SOURCE.properties";

            } else {
                filename = "TARGET.properties";
            }

           String separator = System.getProperty("file.separator")  ;

            logger.debug("separator="  +separator );

            input = (new FileInputStream(System.getProperty("user.dir") + separator +  "etc"  + separator + filename));



            if (input == null) {
                logger.info("Sorry, unable to find " + filename, new RuntimeException("Sorry, unable to find " + filename));
                new RuntimeException("Sorry, unable to find " + filename);
                return;
            }
            prop.load(input);

            host = prop.getProperty("db.host");
            sid = prop.getProperty("db.sid");
            port = prop.getProperty("db.port");
            dbUser = prop.getProperty("db.user");
            dbPass = prop.getProperty("db.password");

            logger.debug(new StringBuilder("host=").append(host).append(" port=").append(port));
            logger.debug(new StringBuilder("dbUser=").append(dbUser).append(" dbPass=").append(dbPass));

        } catch (IOException ex) {
            logger.debug("Proprties Error ", ex);
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.debug("Proprties Error ", e);
                }
            }


        }
        try {
            Class.forName("oracle.jdbc.OracleDriver");


            String connectionString =
                    new StringBuilder("jdbc:oracle:thin:@//")
                            .append(host)
                            .append(":")
                            .append(port)
                            .append("/")
                            .append(sid).toString();

             logger.debug("connectionString =" + connectionString);

            dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
            dataSource.setDefaultAutoCommit(false);
            dataSource.setUrl(connectionString);
            dataSource.setUsername(dbUser);
            dataSource.setPassword(dbPass);

            logger.debug("Connection setted");
        } catch (ClassNotFoundException e) {
            logger.debug("oracle.jdbc.OracleDriver Error ", e);
        }
    }

    public JdbcTemplate getJDBCTemplate() {

        return new JdbcTemplate(dataSource);

    }

    ;

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {

        return new NamedParameterJdbcTemplate(dataSource);
    }
}
