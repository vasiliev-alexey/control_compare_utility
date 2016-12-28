package com.av.dao.oracle;

import com.av.domain.Control;
import com.av.domain.ControlLine;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class OracleControlDAO {


    private static String ALL_CONTROLS = "select FR.GUID              AS H_GUID,\n" +
            "       CODE,\n" +
            "       NAME,\n" +
            "       FR.DOC_TYPE_CODE        AS DOC_TYPE_CODE,\n" +
            "       FR.DOC_GROUP_CODE        AS DOC_GROUP_CODE,\n" +
            "       FR.DESCRIPTION       AS H_DESCRIPTION,\n" +
            "       FR.START_DATE_ACTIVE AS H_START_DATE_ACTIVE,\n" +
            "       FR.END_DATE_ACTIVE   AS H_END_DATE_ACTIVE,\n" +
            "       FR.ENABLED_FLAG   AS H_ENABLED_FLAG,\n" +
            "       L.GUID         AS L_GUID,\n" +
            "       L.ENABLED_FLAG AS L_ENABLED_FLAG,\n" +
            "       NUM,\n" +
            "       NUM_KC,\n" +
            "       DOC_TAB_TYPE,\n" +
            "       DOC_SQL,\n" +
            "       CHECK_OPERATION,\n" +
            "       IND_TYPE,\n" +
            "       xxt_rv_tocanon(IND_VAL) as IND_VAL ,\n" +
            "       CONTROL_TYPE,\n" +
            "       ERRMSG as MESSAGE_CODE,\n" +
            "       TMR_MSG as TMR_MESSAGE_CODE,\n" +
            "       SCC_MESSAGE_TEXT as SCC_MESSAGE_CODE,\n" +
            "       COND_WHERE_CLAUSE,\n" +
            "       COND_WHERE_CLAUSE_R,\n" +
            "       COND_WHERE_CLAUSE_OTCH,\n" +
            "       L.START_DATE_ACTIVE AS L_START_DATE_ACTIVE,\n" +
            "       L.END_DATE_ACTIVE AS  L_END_DATE_ACTIVE,\n" +
            "       L.USERNAME_UPDATED AS L_USERNAME_UPDATED, \n" +
            "       L.DESCRIPTION as L_DESCRIPTION" +
            "  from XXT_BS_CTRL_PROC_V fr, XXT_BS_CTRL_PROC_LINES_v l\n" +
            " where fr.DOC_GROUP_CODE = 'ОТЧ'\n" +
            "   and fr.enabled_flag = 'Y'\n" +
            "   and l.header_guid = fr.guid\n" +
            "   and exists (select null\n" +
            "          from xxt_rp_formversions f\n" +
            "         where fr.code = f.code_check\n" +
            "            or fr.code = f.mdk_check\n" +
            "            or fr.code = f.format_check)";


    private static String ONECONTROL =
            "select FR.GUID              AS H_GUID,\n" +
                    "       CODE,\n" +
                    "       NAME,\n" +
                    "       FR.DOC_TYPE_CODE        AS DOC_TYPE_CODE,\n" +
                    "       FR.DOC_GROUP_CODE        AS DOC_GROUP_CODE,\n" +
                    "       FR.DESCRIPTION       AS H_DESCRIPTION,\n" +
                    "       FR.START_DATE_ACTIVE AS H_START_DATE_ACTIVE,\n" +
                    "       FR.END_DATE_ACTIVE   AS H_END_DATE_ACTIVE,\n" +
                    "       FR.ENABLED_FLAG   AS H_ENABLED_FLAG,\n" +
                    "       L.GUID         AS L_GUID,\n" +
                    "       L.ENABLED_FLAG AS L_ENABLED_FLAG,\n" +
                    "       NUM,\n" +
                    "       NUM_KC,\n" +
                    "       DOC_TAB_TYPE,\n" +
                    "       DOC_SQL,\n" +
                    "       CHECK_OPERATION,\n" +
                    "       IND_TYPE,\n" +
                    "       xxt_rv_tocanon(IND_VAL) as IND_VAL ,\n" +
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
                    "  from XXT_BS_CTRL_PROC_V fr, XXT_BS_CTRL_PROC_LINES_v l\n" +
                    " where fr.DOC_GROUP_CODE = 'ОТЧ'\n" +
                    "   and fr.enabled_flag = 'Y'\n" +
                    "   and l.header_guid = fr.guid\n" +
                    "   and fr.code = :code";


    public Control finfByCode(String code) {

        NamedParameterJdbcTemplate template = OracleConnectionUtils.getInstance().getNamedParameterJdbcTemplate();
        SqlParameterSource namedParam = new MapSqlParameterSource("code", code);
        return template.queryForObject(ONECONTROL, namedParam, new ControlMapper());
    }

    public List<Control> findAll() {
        JdbcTemplate template = OracleConnectionUtils.getInstance().getJDBCTemplate();
        Map<String, Control> maps = template.query(ALL_CONTROLS, new ControlMapMapper());
        return new ArrayList(maps.values());
    }

    public Map<String, Control> findAllMap() {
        JdbcTemplate template = OracleConnectionUtils.getInstance().getJDBCTemplate();
        Map<String, Control> maps = template.query(ALL_CONTROLS, new ControlMapMapper());
        return maps;
    }

    private static final class ControlMapMapper implements ResultSetExtractor<Map<String, Control>> {
        @Override
        public Map<String, Control> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<String, Control> maps = new HashMap<String, Control>();
            //     ArrayList<Control> arrayList = new ArrayList<Control>();

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

                    line.setControl(ctrl);

                    line.setHeaderGuid(resultSet.getString("H_GUID"));
                    //  line.setGuid(resultSet.getString("L_GUID"));
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
                    // line.setHeaderGuid(resultSet.getString("H_GUID"));
                    // line.setGuid(resultSet.getString("L_GUID"));
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

            }
            return maps;
        }

    }

   /* private static final class ControlListMapper implements ResultSetExtractor<List<Control>> {


        @Override
        public List<Control> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<String, Control> maps;

            maps = new ControlMapMapper().extractData(resultSet);
            return new ArrayList(maps.values());
        }
    }*/

    private static final class ControlMapper implements RowMapper<Control> {

        @Override
        public Control mapRow(ResultSet resultSet, int i) throws SQLException {
            Control ctrl = new Control();
            ;
            ctrl.setGuidheader(resultSet.getString("H_GUID"));
            ctrl.setCode(resultSet.getString("CODE"));
            ctrl.setName(resultSet.getString("NAME"));
            ctrl.setStartdate(resultSet.getDate("H_START_DATE_ACTIVE"));
            ctrl.setEnddate(resultSet.getDate("H_END_DATE_ACTIVE"));
            ctrl.setEnabled_flag(resultSet.getString("H_ENABLED_FLAG"));
            ctrl.setLines(new TreeMap<Integer, ControlLine>());

            addControlLine(resultSet, ctrl);
            while (resultSet.next()) {
                addControlLine(resultSet, ctrl);
            }

            return ctrl;
        }


        private void addControlLine(ResultSet resultSet, Control ctrl) throws SQLException {

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
            line.setControl(ctrl);
            ctrl.getLines().put(line.getNum(), line);


        }

        ;


    }

}
