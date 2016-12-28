package com.av.domain;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.av.utils.StringCanonical.equalsCanonical;
//import static com.av.utils.StringCanonical.toCanonical;
import static com.av.utils.StringCanonical.toTrim;

/**
 * Created with IntelliJ IDEA.
 * User: vasiljev.alexey
 * Date: 13.05.14
 * Time: 14:28
 */
public class ControlLine {
    private Logger logger = LogManager.getLogger(ControlLine.class);
    private String enabledFlag;
    private String headerGuid;
    private Integer num;
    private String docTabType;
    private String checkOperation;
    private String indType;
    private String indVal;
    private String controlType;
    private String messageCode;
    private String tmrMssageCode;

    public String getNumKC() {
        return numKC;
    }

    public void setNumKC(String numKC) {
        this.numKC = numKC;
    }

    private String numKC;


    public void setDocSQL(String docSQL) {
        this.docSQL = docSQL;
    }

    private String docSQL;

    private Control control;

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    private String sccMessageCode;
    private String condWhereClause;
    private String condWhereClauseR;
    private String condWhereClauseOtch;
    private Date startDateActive;
    private Date endDateActive;
    private String usernameUpdated;
    private String description;

    private boolean errorFlag = false;

    private static DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    public boolean isErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(boolean errorFlag) {
        this.errorFlag = errorFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getHeaderGuid() {
        return headerGuid;
    }

    public void setHeaderGuid(String headerGuid) {
        this.headerGuid = headerGuid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDocTabType() {
        return docTabType;
    }

    public void setDocTabType(String docTabType) {
        this.docTabType = docTabType;
    }

    public String getCheckOperation() {
        return checkOperation;
    }

    public void setCheckOperation(String checkOperation) {
        this.checkOperation = checkOperation;
    }

    public String getIndType() {
        return indType;
    }

    public void setIndType(String indType) {
        this.indType = indType;
    }

    public String getIndVal() {
        return indVal;
    }

    public void setIndVal(String indVal) {
        this.indVal = indVal;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getTmrMssageCode() {
        return tmrMssageCode;
    }

    public void setTmrMssageCode(String tmrMssageCode) {
        this.tmrMssageCode = tmrMssageCode;
    }

    public String getSccMessageCode() {
        return sccMessageCode;
    }

    public void setSccMessageCode(String sccMessageCode) {
        this.sccMessageCode = sccMessageCode;
    }

    public String getCondWhereClause() {
        return condWhereClause;
    }

    public void setCondWhereClause(String condWhereClause) {
        this.condWhereClause = condWhereClause;
    }

    public String getCondWhereClauseR() {
        return condWhereClauseR;
    }

    public void setCondWhereClauseR(String condWhereClauseR) {
        this.condWhereClauseR = condWhereClauseR;
    }

    public String getCondWhereClauseOtch() {
        return condWhereClauseOtch;
    }

    public void setCondWhereClauseOtch(String condWhereClauseOtch) {
        this.condWhereClauseOtch = condWhereClauseOtch;
    }

    public Date getStartDateActive() {
        return startDateActive;
    }

    public void setStartDateActive(Date startDateActive) {
        this.startDateActive = startDateActive;
    }

    public Date getEndDateActive() {
        return endDateActive;
    }

    public void setEndDateActive(Date endDateActive) {
        this.endDateActive = endDateActive;
    }

    public String getUsernameUpdated() {
        return usernameUpdated;
    }


    public List<String> getLineListAttrs() {

        ArrayList<String> result = new ArrayList();


        result.add("Шаг:" + num);
        result.add("Флаг активности:'" + enabledFlag);
        result.add("Источник:'" + docTabType);
        result.add("SQL:" + toTrim( docSQL));
        result.add("Оператор:'" + checkOperation);
        result.add("Тип показателя:'" + indType);
        result.add("Значение показателя:'" + indVal);
        result.add("Тип Контроля:'" + controlType);
        result.add("Собщение:'" + messageCode);
        result.add("Собщение при множестве строк:'" + tmrMssageCode);
        result.add("Собщение при успехе:'" + sccMessageCode);
        result.add("Условие для контроля:'" + toTrim(condWhereClause));
        result.add("Условие для правой части:'" + toTrim(condWhereClauseR));
        result.add("Условие для ОТЧ:'" + toTrim(condWhereClauseOtch));

        if (startDateActive != null) {
            result.add("Дата С:" + df.format(startDateActive));
        } else {
            result.add("Дата С:");
        }

        if (endDateActive != null) {
            result.add("Дата По:" + df.format(endDateActive));
        }
        result.add("Дата По:");
        result.add("Изменено:'" + usernameUpdated);
        result.add("Описание:'" + description);


        return result;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ControlLine)) return false;

        ControlLine that = (ControlLine) o;


        this.setErrorFlag(true);

        logger.debug("num et=" + getNum() + " cur=" + that.getNum());

        logger.debug("checkOperation");
        if (checkOperation != null ? !checkOperation.equals(that.checkOperation) : that.checkOperation != null)
            return false;
        if (condWhereClause != null ? !equalsCanonical(condWhereClause, that.condWhereClause) : that.condWhereClause != null)
            return false;
        if (condWhereClauseOtch != null ? !equalsCanonical(condWhereClauseOtch, that.condWhereClauseOtch) : that.condWhereClauseOtch != null)
            return false;
        if (condWhereClauseR != null ? !equalsCanonical(condWhereClauseR, condWhereClauseR) : that.condWhereClauseR != null)
            return false;
        if (controlType != null ? !controlType.equals(that.controlType) : that.controlType != null) return false;
        if (description != null ? !equalsCanonical(description, that.description) : that.description != null)
            return false;
        logger.debug("docTabType");

        if (docTabType != null ? !docTabType.equals(that.docTabType) : that.docTabType != null) return false;
        logger.debug("docSQL");
      //  if (docSQL != null ? !docSQL.equals(that.docSQL) : that.docSQL != null) return false;
      if (docSQL != null ? !equalsCanonical(docSQL, that.docSQL) : that.docSQL != null) return false;

        logger.debug("enabledFlag");
        if (enabledFlag != null ? !enabledFlag.equals(that.enabledFlag) : that.enabledFlag != null) return false;
        logger.debug("endDateActive");
        if (endDateActive != null ? !endDateActive.equals(that.endDateActive) : that.endDateActive != null)
            return false;
        logger.debug("indType");
        if (indType != null ? !indType.equals(that.indType) : that.indType != null) return false;
        if (indVal != null ? !equalsCanonical(new String(indVal), new String(that.indVal)) : that.indVal != null)
            return false;
        logger.debug("messageCode");
        if (messageCode != null ? !messageCode.equals(that.messageCode) : that.messageCode != null) return false;
        logger.debug("num");
        if (num != null ? !num.equals(that.num) : that.num != null) return false;
        logger.debug("sccMessageCode");
        if (sccMessageCode != null ? !sccMessageCode.equals(that.sccMessageCode) : that.sccMessageCode != null)
            return false;
        if (startDateActive != null ? !startDateActive.equals(that.startDateActive) : that.startDateActive != null)
            return false;
        if (tmrMssageCode != null ? !tmrMssageCode.equals(that.tmrMssageCode) : that.tmrMssageCode != null)
            return false;

        logger.debug("line eq");
        this.setErrorFlag(false);

        return true;
    }

    @Override
    public int hashCode() {

        int result = (enabledFlag != null ? enabledFlag.hashCode() : 0);
        result = 31 * result + (num != null ? num.hashCode() : 0);
        result = 31 * result + (docTabType != null ? docTabType.hashCode() : 0);
        result = 31 * result + (docSQL != null ? docSQL.hashCode() : 0);
        result = 31 * result + (checkOperation != null ? checkOperation.hashCode() : 0);
        result = 31 * result + (indType != null ? indType.hashCode() : 0);
        result = 31 * result + (indVal != null ? indVal.hashCode() : 0);
        result = 31 * result + (controlType != null ? controlType.hashCode() : 0);
        result = 31 * result + (messageCode != null ? messageCode.hashCode() : 0);
        result = 31 * result + (tmrMssageCode != null ? tmrMssageCode.hashCode() : 0);
        result = 31 * result + (sccMessageCode != null ? sccMessageCode.hashCode() : 0);
        result = 31 * result + (condWhereClause != null ? condWhereClause.hashCode() : 0);
        result = 31 * result + (condWhereClauseR != null ? condWhereClauseR.hashCode() : 0);
        result = 31 * result + (condWhereClauseOtch != null ? condWhereClauseOtch.hashCode() : 0);
        result = 31 * result + (startDateActive != null ? startDateActive.hashCode() : 0);
        result = 31 * result + (endDateActive != null ? endDateActive.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Строка Контроля{" +
                "  Шаг=" + num + '\n' +
                "  Флаг активности='" + enabledFlag + '\'' + '\n' +
                "  Источник='" + docTabType + '\'' + '\n' +
                "  SQL='" + docSQL + '\'' + '\n' +
                "  Оператор='" + checkOperation + '\'' + '\n' +
                "  Тип показателя='" + indType + '\'' + '\n' +
                "  Значение показателя='" + indVal + '\'' + '\n' +
                "  Тип Контроля='" + controlType + '\'' + '\n' +
                "  Собщение='" + messageCode + '\'' + '\n' +
                "  Собщение при множестве строк='" + tmrMssageCode + '\'' + '\n' +
                "  Собщение при успехе='" + sccMessageCode + '\'' + '\n' +
                "  Условие для контроля='" + condWhereClause + '\'' + '\n' +
                "  Условие для правой части='" + condWhereClauseR + '\'' + '\n' +
                "  Условие для ОТЧ='" + condWhereClauseOtch + '\'' + '\n' +
                "  Дата С=" + startDateActive + '\n' +
                "  Дата По=" + endDateActive + '\n' +
                "  Изменено='" + usernameUpdated + '\'' + '\n' +
                "  Описание='" + description + '\'' + '\n' +
                '}';
    }

    public void setUsernameUpdated(String usernameUpdated) {
        this.usernameUpdated = usernameUpdated;
    }


    public String getDocSQL() {
        return docSQL;
    }
}
