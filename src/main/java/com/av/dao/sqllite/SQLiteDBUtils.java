package com.av.dao.sqllite;

import com.av.domain.ControlLine;
import com.av.domain.Control;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vasiljev.alexey
 * Date: 14.05.14
 * Time: 16:39
 * To change this template use File | Settings | File Templates.
 */
public class SQLiteDBUtils {
    private static Logger log = LogManager.getLogger(SQLiteDBUtils.class);
    private static String dbname = "";
    private static String separator = System.getProperty("file.separator");
    private DataSource dataSource;


    private static volatile SQLiteDBUtils instance;

    private SQLiteDBUtils() {
        setDataSource();
    }

    public static SQLiteDBUtils getInstance() {
        SQLiteDBUtils localInstance = instance;
        if (localInstance == null) {
            synchronized (SQLiteDBUtils.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SQLiteDBUtils();
                }
            }
        }
        return localInstance;
    }


    public static String getDBName() {
        if (dbname.length() == 0) {
            dbname = System.getProperty("user.dir") + separator + "sqldb" + separator + "controls";
        }
        return dbname;
    }

    public static void createDB() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + getDBName() + ".db");
            stmt = c.createStatement();
            log.debug("DROP TABLE  XXT_BS_CTRL_PROC");
            stmt.executeUpdate("DROP TABLE IF EXISTS XXT_BS_CTRL_PROC");
            log.debug("DROP TABLE  XXT_BS_CTRL_PROC_LINES");
            stmt.executeUpdate("DROP TABLE IF EXISTS XXT_BS_CTRL_PROC_LINES");
            log.debug("DROP TABLE  VERSION");
            stmt.executeUpdate("DROP TABLE IF EXISTS VERSION");

            log.debug("Create headers");


            String sql = "create table XXT_BS_CTRL_PROC (\n" +
                    "GUID  TEXT,\n" +
                    "CODE  TEXT,\n" +
                    "NAME  TEXT,\n" +
                    "DESCRIPTION  TEXT,\n" +
                    "DOC_GROUP_CODE  TEXT,\n" +
                    "DOC_TYPE_CODE  TEXT,\n" +
                    "ENABLED_FLAG  TEXT,\n" +
                    "START_DATE_ACTIVE DATE,\n" +
                    "END_DATE_ACTIVE DATE)";

            stmt.executeUpdate(sql);
            log.debug("Create lines");
            sql = "create table XXT_BS_CTRL_PROC_LINES (\n" +
                    "GUID TEXT ,\n" +
                    "ENABLED_FLAG TEXT,\n" +
                    "HEADER_GUID TEXT,\n" +
                    "NUM  INTEGER,\n" +
                    "NUM_KC  TEXT,\n" +
                    "DOC_TAB_TYPE TEXT,\n" +
                    "DOC_SQL TEXT,\n" +
                    "CHECK_OPERATION TEXT,\n" +
                    "IND_TYPE TEXT,\n" +
                    "IND_VAL TEXT,\n" +
                    "CONTROL_TYPE TEXT ,\n" +
                    "MESSAGE_CODE  TEXT,\n" +
                    "TMR_MESSAGE_CODE  TEXT,\n" +
                    "SCC_MESSAGE_CODE  TEXT,\n" +
                    "COND_WHERE_CLAUSE  TEXT,\n" +
                    "COND_WHERE_CLAUSE_R  TEXT,\n" +
                    "COND_WHERE_CLAUSE_OTCH  TEXT,\n" +
                    "START_DATE_ACTIVE  DATE,\n" +
                    "END_DATE_ACTIVE  DATE,\n" +
                    "USERNAME_UPDATED   TEXT , \n" +
                    "DESCRIPTION TEXT)";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE VERSION (\n" +
                    "\tVERSION_DATE DATETIME NOT NULL\n" +
                    ");";
            stmt.executeUpdate(sql);
            stmt.close();

            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        log.debug("Database created");
    }


    public void insertConrols(List<Control> controls) {

        Connection c;
        PreparedStatement stmt;
        PreparedStatement stmtLine;
        PreparedStatement stmtVersion;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + getDBName() + ".db");
            c.setAutoCommit(false);


            Date date = new Date();


            String sql = "INSERT INTO XXT_BS_CTRL_PROC (" +
                    "GUID ," +
                    "CODE ," +
                    "NAME ," +
                    "DESCRIPTION, " +
                    "DOC_GROUP_CODE," +
                    "DOC_TYPE_CODE," +
                    "ENABLED_FLAG," +
                    "START_DATE_ACTIVE," +
                    "END_DATE_ACTIVE)" +
                    "VALUES ( ? , ?, ?, ?, ?, ?, ?, ?, ? )";

            String insLine = " insert into XXT_BS_CTRL_PROC_LINES (GUID , ENABLED_FLAG , " +
                    "HEADER_GUID ,  NUM , DOC_TAB_TYPE , " +
                    "CHECK_OPERATION , IND_TYPE , IND_VAL , CONTROL_TYPE , MESSAGE_CODE,\n" +
                    "  TMR_MESSAGE_CODE , SCC_MESSAGE_CODE , COND_WHERE_CLAUSE , COND_WHERE_CLAUSE_R " +
                    " , COND_WHERE_CLAUSE_OTCH, START_DATE_ACTIVE , END_DATE_ACTIVE , USERNAME_UPDATED  , DESCRIPTION , DOC_SQL , NUM_KC)\n" +
                    "  values (? , ? , ?,? , ? , ?,? , ? , ?,? , ? , ?,? , ? , ?,? , ? , ? , ? , ? , ?)";

            stmt = c.prepareStatement(sql);
            stmtLine = c.prepareStatement(insLine);


            stmtVersion = c.prepareStatement("insert into VERSION (VERSION_DATE) values (?)");
            stmtVersion.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmtVersion.execute();


            for (Control control : controls) {

                log.debug("Inserted");

                stmt.setString(1, control.getGuidheader());
                stmt.setString(2, control.getCode());
                stmt.setString(3, control.getName());
                stmt.setString(4, control.getDescription());
                stmt.setString(5, control.getDocGroupCode());
                stmt.setString(6, control.getDocTypeCode());
                stmt.setString(7, control.getEnabled_flag());
                stmt.setDate(8, control.getStartdate());
                stmt.setDate(9, control.getEnddate());
                stmt.execute();


                for (ControlLine line : control.getLines().values()) {
                    //     stmtLine.setString(1, line.getGuid());
                    stmtLine.setString(2, line.getEnabledFlag());
                    stmtLine.setString(3, control.getGuidheader());
                    stmtLine.setInt(4, line.getNum());
                    stmtLine.setString(5, line.getDocTabType());
                    stmtLine.setString(6, line.getCheckOperation());
                    stmtLine.setString(7, line.getIndType());
                    stmtLine.setString(8, line.getIndVal());
                    stmtLine.setString(9, line.getControlType());
                    stmtLine.setString(10, line.getMessageCode());
                    stmtLine.setString(11, line.getTmrMssageCode());
                    stmtLine.setString(12, line.getSccMessageCode());
                    stmtLine.setString(13, line.getCondWhereClause());
                    stmtLine.setString(14, line.getCondWhereClauseR());
                    stmtLine.setString(15, line.getCondWhereClauseOtch());
                    stmtLine.setDate(16, line.getStartDateActive());
                    stmtLine.setDate(17, line.getEndDateActive());
                    stmtLine.setString(18, line.getUsernameUpdated());
                    stmtLine.setString(19, line.getDescription());
                    stmtLine.setString(20, line.getDocSQL());
                    stmtLine.setString(21, line.getNumKC());
                    stmtLine.execute();

                }

            }


            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");

    }

    public static java.sql.Date convertDate(Date date) {

        java.util.Calendar cal = Calendar.getInstance();
        java.util.Date utilDate = new java.util.Date(); // your util date
        cal.setTime(utilDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime()); // your sql date
        return sqlDate;

    }

    ;

    private DataSource getDataSource() {
        return dataSource;
    }

    private void setDataSource() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.sqlite.JDBC");
        String dbfilename = SQLiteDBUtils.getDBName() + ".db";

        basicDataSource.setUrl("jdbc:sqlite:/" + dbfilename);

        dataSource = basicDataSource;

    }

    public JdbcTemplate getJDBCTemplate() {

        return new JdbcTemplate(dataSource);
    }

    ;

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
