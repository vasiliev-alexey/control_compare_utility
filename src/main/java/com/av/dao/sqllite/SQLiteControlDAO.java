package com.av.dao.sqllite;

import com.av.domain.Control;
import com.av.domain.ControlLine;
import com.av.domain.Version;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: alexey
 * Date: 5/20/14
 * Time: 8:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class SQLiteControlDAO {
    private static Logger logger = LogManager.getLogger(SQLiteControlDAO.class);
    private static String ALL_CONTROL = "select FR.GUID                AS H_GUID,\n" +
            "       CODE,\n" +
            "       NAME,\n" +
            "       FR.DESCRIPTION         AS H_DESCRIPTION,\n" +
            "       FR.START_DATE_ACTIVE   AS H_START_DATE_ACTIVE,\n" +
            "       FR.END_DATE_ACTIVE     AS H_END_DATE_ACTIVE,\n" +
            "       FR.ENABLED_FLAG        AS H_ENABLED_FLAG,\n" +
            "       FR.DOC_TYPE_CODE        AS DOC_TYPE_CODE,\n" +
            "       FR.DOC_GROUP_CODE        AS DOC_GROUP_CODE,\n" +
            "       L.GUID                 AS L_GUID,\n" +
            "       L.ENABLED_FLAG         AS L_ENABLED_FLAG,\n" +
            "       NUM,\n" +
            "       NUM_KC,\n" +
            "       DOC_TAB_TYPE,\n" +
            "       DOC_SQL,\n" +
            "       CHECK_OPERATION,\n" +
            "       IND_TYPE,\n" +
            "       IND_VAL,\n" +
            "       CONTROL_TYPE,\n" +
            "       MESSAGE_CODE,\n" +
            "       TMR_MESSAGE_CODE,\n" +
            "       SCC_MESSAGE_CODE,\n" +
            "       COND_WHERE_CLAUSE,\n" +
            "       COND_WHERE_CLAUSE_R,\n" +
            "       COND_WHERE_CLAUSE_OTCH,\n" +
            "       L.START_DATE_ACTIVE    AS L_START_DATE_ACTIVE,\n" +
            "       L.END_DATE_ACTIVE      AS L_END_DATE_ACTIVE,\n" +
            "       L.USERNAME_UPDATED     AS L_USERNAME_UPDATED,\n" +
            "       L.DESCRIPTION          as L_DESCRIPTION\n" +
            "  from XXT_BS_CTRL_PROC fr\n" +
            " inner join XXT_BS_CTRL_PROC_LINES l\n" +
            "    on l.header_guid = fr.guid";
    // + " where  fr.code ='0507068-5MDK'";

    private static String ONECONTROL =
            "select FR.GUID              AS H_GUID,\n" +
                    "       CODE,\n" +
                    "       NAME,\n" +
                    "       FR.DESCRIPTION       AS H_DESCRIPTION,\n" +
                    "       FR.START_DATE_ACTIVE AS H_START_DATE_ACTIVE,\n" +
                    "       FR.END_DATE_ACTIVE   AS H_END_DATE_ACTIVE,\n" +
                    "       FR.ENABLED_FLAG   AS H_ENABLED_FLAG,\n" +
                    "       FR.DOC_TYPE_CODE        AS DOC_TYPE_CODE,\n" +
                    "       FR.DOC_GROUP_CODE        AS DOC_GROUP_CODE,\n" +
                    "       L.GUID         AS L_GUID,\n" +
                    "       L.ENABLED_FLAG AS L_ENABLED_FLAG,\n" +
                    "       NUM,\n" +
                    "       NUM_KC,\n" +
                    "       DOC_TAB_TYPE,\n" +
                    "       DOC_SQL,\n" +
                    "       CHECK_OPERATION,\n" +
                    "       IND_TYPE,\n" +
                    "       IND_VAL,\n" +
                    "       CONTROL_TYPE,\n" +
                    "       MESSAGE_CODE,\n" +
                    "       TMR_MESSAGE_CODE,\n" +
                    "       SCC_MESSAGE_CODE,\n" +
                    "       COND_WHERE_CLAUSE,\n" +
                    "       COND_WHERE_CLAUSE_R,\n" +
                    "       COND_WHERE_CLAUSE_OTCH,\n" +
                    "       L.START_DATE_ACTIVE AS L_START_DATE_ACTIVE,\n" +
                    "       L.END_DATE_ACTIVE AS  L_END_DATE_ACTIVE,\n" +
                    "       L.USERNAME_UPDATED AS L_USERNAME_UPDATED, \n" +
                    "       L.DESCRIPTION as L_DESCRIPTION" +
                    "  from XXT_BS_CTRL_PROC fr inner join XXT_BS_CTRL_PROC_LINES l\n" +
                    " on l.header_guid = fr.guid\n" +
                    " where  fr.code = :code";

    public Date getVersionDate() {

        JdbcTemplate tem = SQLiteDBUtils.getInstance().getJDBCTemplate();
        Version vr = tem.query("select version_date from version ", new Maper());

        Timestamp ts = (vr.getDate());
        Date dt = new Date(ts.getTime());
        logger.info("retval=" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(dt));
        return dt;
    }

    public Map<String, Control> findAllMap() {

        JdbcTemplate template = SQLiteDBUtils.getInstance().getJDBCTemplate();

        Map<String, Control> mapControl = template.query(ALL_CONTROL, new ControlMapMapper());

        return mapControl;


    }

    ;

    public Control findByCode(String code) {

        NamedParameterJdbcTemplate template = SQLiteDBUtils.getInstance().getNamedParameterJdbcTemplate();
        SqlParameterSource namedParam = new MapSqlParameterSource("code", code);

        return template.queryForObject(ONECONTROL, namedParam, new ControlMapper());


    }

    ;


    public List<Control> findAll() {

        JdbcTemplate template = SQLiteDBUtils.getInstance().getJDBCTemplate();

        Map<String, Control> mapControl = template.query(ALL_CONTROL, new ControlMapMapper());

        return new ArrayList<Control>(mapControl.values());


    }

    ;

    private static final class Maper implements ResultSetExtractor<Version> {
        @Override
        public Version extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Version version = new Version();
            while (resultSet.next()) {
                version.setDate(resultSet.getTimestamp("version_date"));
            }
            return version;
        }


    }


    private static final class ControlMapMapper implements ResultSetExtractor<Map<String, Control>> {
        @Override
        public Map<String, Control> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

            try {
                Map<String, Control> maps = new HashMap<String, Control>();

                while (resultSet.next()) {
                    if (!maps.containsKey(resultSet.getString("CODE"))) {

                        Control ctrl = new Control();
                        ctrl.setGuidheader(resultSet.getString("H_GUID"));
                        ctrl.setCode(resultSet.getString("CODE"));
                        ctrl.setName(resultSet.getString("NAME"));
                        ctrl.setDocTypeCode(resultSet.getString("DOC_TYPE_CODE"));
                        ctrl.setDocGroupCode(resultSet.getString("DOC_GROUP_CODE"));
                        ctrl.setStartdate(resultSet.getDate("H_START_DATE_ACTIVE"));
                        ctrl.setEnddate(resultSet.getDate("H_END_DATE_ACTIVE"));
                        ctrl.setEnabled_flag(resultSet.getString("H_ENABLED_FLAG"));
                        ctrl.setLines(new TreeMap<Integer, ControlLine>());

                        ControlLine line = new ControlLine();
                        line.setHeaderGuid(resultSet.getString("H_GUID"));
                        line.setEnabledFlag(resultSet.getString("L_ENABLED_FLAG"));
                        line.setNum(resultSet.getInt("NUM"));
                        line.setNumKC(resultSet.getString("NUM_KC"));
                        line.setDocTabType(resultSet.getString("DOC_TAB_TYPE"));
                        line.setDocSQL(resultSet.getString("DOC_SQL"));
                        line.setCheckOperation(resultSet.getString("CHECK_OPERATION"));
                        line.setIndType(resultSet.getString("IND_TYPE"));
                        line.setIndVal(resultSet.getString("IND_VAL"));
                        line.setControlType(resultSet.getString("CONTROL_TYPE"));
                        line.setMessageCode(resultSet.getString("MESSAGE_CODE"));
                        line.setTmrMssageCode(resultSet.getString("TMR_MESSAGE_CODE"));
                        line.setSccMessageCode(resultSet.getString("SCC_MESSAGE_CODE"));
                        line.setCondWhereClause(resultSet.getString("COND_WHERE_CLAUSE"));
                        line.setCondWhereClauseR(resultSet.getString("COND_WHERE_CLAUSE_R"));
                        line.setCondWhereClauseOtch(resultSet.getString("COND_WHERE_CLAUSE_OTCH"));
                        line.setStartDateActive(resultSet.getDate("L_START_DATE_ACTIVE"));
                        line.setEndDateActive(resultSet.getDate("L_END_DATE_ACTIVE"));
                        line.setUsernameUpdated(resultSet.getString("L_USERNAME_UPDATED"));
                        line.setEnabledFlag(resultSet.getString("L_ENABLED_FLAG"));
                        line.setDescription(resultSet.getString("L_DESCRIPTION"));
                        ctrl.getLines().put(line.getNum(), line);
                        maps.put(ctrl.getCode(), ctrl);
                    } else {

                        Control ctrl = maps.get(resultSet.getString("CODE"));
                        ControlLine line = new ControlLine();


                        line.setControl(ctrl);
                        line.setHeaderGuid(resultSet.getString("H_GUID"));
                        line.setEnabledFlag(resultSet.getString("L_ENABLED_FLAG"));
                        line.setNum(resultSet.getInt("NUM"));
                        line.setNumKC(resultSet.getString("NUM_KC"));
                        line.setDocTabType(resultSet.getString("DOC_TAB_TYPE"));
                        line.setDocSQL(resultSet.getString("DOC_SQL"));
                        line.setCheckOperation(resultSet.getString("CHECK_OPERATION"));
                        line.setIndType(resultSet.getString("IND_TYPE"));
                        line.setIndVal(resultSet.getString("IND_VAL"));
                        line.setControlType(resultSet.getString("CONTROL_TYPE"));
                        line.setMessageCode(resultSet.getString("MESSAGE_CODE"));
                        line.setTmrMssageCode(resultSet.getString("TMR_MESSAGE_CODE"));
                        line.setSccMessageCode(resultSet.getString("SCC_MESSAGE_CODE"));
                        line.setCondWhereClause(resultSet.getString("COND_WHERE_CLAUSE"));
                        line.setCondWhereClauseR(resultSet.getString("COND_WHERE_CLAUSE_R"));
                        line.setCondWhereClauseOtch(resultSet.getString("COND_WHERE_CLAUSE_OTCH"));
                        line.setStartDateActive(resultSet.getDate("L_START_DATE_ACTIVE"));
                        line.setEndDateActive(resultSet.getDate("L_END_DATE_ACTIVE"));
                        line.setUsernameUpdated(resultSet.getString("L_USERNAME_UPDATED"));
                        line.setEnabledFlag(resultSet.getString("L_ENABLED_FLAG"));
                        line.setDescription(resultSet.getString("L_DESCRIPTION"));

                        // ctrl.setLines(new HashMap<Integer, ControlLine>() );
                        ctrl.getLines().put(line.getNum(), line);
                    }

                }

                return maps;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ;


            return null;
        }
    }

    private static final class ControlMapper implements RowMapper<Control> {

        @Override
        public Control mapRow(ResultSet resultSet, int i) throws SQLException {
            Control ctrl = null;

            int cointer = 1;


            ctrl = new Control();
            ctrl.setGuidheader(resultSet.getString("H_GUID"));
            ctrl.setCode(resultSet.getString("CODE"));
            ctrl.setName(resultSet.getString("NAME"));
            ctrl.setDocTypeCode(resultSet.getString("DOC_TYPE_CODE"));
            ctrl.setDocGroupCode(resultSet.getString("DOC_GROUP_CODE"));
            ctrl.setStartdate(resultSet.getDate("H_START_DATE_ACTIVE"));
            ctrl.setEnddate(resultSet.getDate("H_END_DATE_ACTIVE"));
            ctrl.setEnabled_flag(resultSet.getString("H_ENABLED_FLAG"));
            ctrl.setLines(new TreeMap<Integer, ControlLine>());
            ControlLine line = new ControlLine();
            line.setControl(ctrl);
            line.setHeaderGuid(resultSet.getString("H_GUID"));
            line.setEnabledFlag(resultSet.getString("L_ENABLED_FLAG"));
            line.setNum(resultSet.getInt("NUM"));
            line.setNumKC(resultSet.getString("NUM_KC"));

            line.setDocTabType(resultSet.getString("DOC_TAB_TYPE"));
            line.setDocSQL(resultSet.getString("DOC_SQL"));
            line.setCheckOperation(resultSet.getString("CHECK_OPERATION"));
            line.setIndType(resultSet.getString("IND_TYPE"));
            line.setIndVal(resultSet.getString("IND_VAL"));
            line.setControlType(resultSet.getString("CONTROL_TYPE"));
            line.setMessageCode(resultSet.getString("MESSAGE_CODE"));
            line.setTmrMssageCode(resultSet.getString("TMR_MESSAGE_CODE"));
            line.setSccMessageCode(resultSet.getString("SCC_MESSAGE_CODE"));
            line.setCondWhereClause(resultSet.getString("COND_WHERE_CLAUSE"));
            line.setCondWhereClauseR(resultSet.getString("COND_WHERE_CLAUSE_R"));
            line.setCondWhereClauseOtch(resultSet.getString("COND_WHERE_CLAUSE_OTCH"));
            line.setStartDateActive(resultSet.getDate("L_START_DATE_ACTIVE"));
            line.setEndDateActive(resultSet.getDate("L_END_DATE_ACTIVE"));
            line.setUsernameUpdated(resultSet.getString("L_USERNAME_UPDATED"));
            line.setEnabledFlag(resultSet.getString("L_ENABLED_FLAG"));
            line.setDescription(resultSet.getString("L_DESCRIPTION"));


            ctrl.getLines().put(line.getNum(), line);

            while (resultSet.next()) {

                line = new ControlLine();
                line.setControl(ctrl);

                line.setHeaderGuid(resultSet.getString("H_GUID"));
                line.setEnabledFlag(resultSet.getString("L_ENABLED_FLAG"));
                line.setNum(resultSet.getInt("NUM"));
                line.setNumKC(resultSet.getString("NUM_KC"));
                line.setDocTabType(resultSet.getString("DOC_TAB_TYPE"));
                line.setDocSQL(resultSet.getString("DOC_SQL"));
                line.setCheckOperation(resultSet.getString("CHECK_OPERATION"));
                line.setIndType(resultSet.getString("IND_TYPE"));
                line.setIndVal(resultSet.getString("IND_VAL"));
                line.setControlType(resultSet.getString("CONTROL_TYPE"));
                line.setMessageCode(resultSet.getString("MESSAGE_CODE"));
                line.setTmrMssageCode(resultSet.getString("TMR_MESSAGE_CODE"));
                line.setSccMessageCode(resultSet.getString("SCC_MESSAGE_CODE"));
                line.setCondWhereClause(resultSet.getString("COND_WHERE_CLAUSE"));
                line.setCondWhereClauseR(resultSet.getString("COND_WHERE_CLAUSE_R"));
                line.setCondWhereClauseOtch(resultSet.getString("COND_WHERE_CLAUSE_OTCH"));
                line.setStartDateActive(resultSet.getDate("L_START_DATE_ACTIVE"));
                line.setEndDateActive(resultSet.getDate("L_END_DATE_ACTIVE"));
                line.setUsernameUpdated(resultSet.getString("L_USERNAME_UPDATED"));
                line.setEnabledFlag(resultSet.getString("L_ENABLED_FLAG"));
                line.setDescription(resultSet.getString("L_DESCRIPTION"));

                ctrl.getLines().put(line.getNum(), line);
            }

            return ctrl;
        }
    }

}



