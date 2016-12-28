package com.av.domain;


import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.av.utils.TextDiffUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Date;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.av.utils.StringCanonical.equalsCanonical;
import static com.av.utils.StringCanonical.toCanonical;

public class Control {

    private Logger logger = LogManager.getLogger(Control.class);

    private int version = 1;

    private String guidheader;
    private String code;

    private  Map<Integer , String>  errors = new HashMap<Integer, String>();


    public String getErrStr (Integer lineNum)  {

        return  errors.get(lineNum);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Control)) return false;

        Control control = (Control) o;
       logger.debug("Conrol_code="+ getCode());
       logger.debug("start_date et=" + startdate + " cur=" + ((Control) o).getStartdate());
        logger.debug("code");
        if (code != null ? !code.equals(control.code) : control.code != null) return false;

        logger.debug("description");
        if (description != null ? !equalsCanonical(description, control.description) : control.description != null)
            return false;
        logger.debug("docGroupCode");
        if (docGroupCode != null ? !docGroupCode.equals(control.docGroupCode) : control.docGroupCode != null)
            return false;
        logger.debug("docTypeCode");
        if (docTypeCode != null ? !equalsCanonical(docTypeCode, control.docTypeCode) : control.docTypeCode != null)
            return false;
        logger.debug("enabled_flag");
        if (enabled_flag != null ? !enabled_flag.equals(control.enabled_flag) : control.enabled_flag != null)
            return false;
        logger.debug("enddate");
        if (enddate != null ? !enddate.equals(control.enddate) : control.enddate != null) return false;

        logger.debug("name");
        if (name != null ? !name.equals(control.name) : control.name != null) return false;
        logger.debug("startdate");
        if (startdate != null ? !startdate.equals(control.startdate) : control.startdate != null) return false;

        logger.debug("lines");
        MapDifference<Integer, ControlLine> diff = Maps.difference(lines, control.getLines());


        Map<Integer, MapDifference.ValueDifference<ControlLine>> ddd = diff.entriesDiffering();
        if (ddd.size() != 0) {
            Iterator iterator = ddd.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry mapEntry = (Map.Entry) iterator.next();

                logger.debug("The key is: " + mapEntry.getKey()
                        + ",value is :" + mapEntry.getValue());

                List<String> left =ddd.get(mapEntry.getKey()).leftValue().getLineListAttrs();
                List<String> right =ddd.get(mapEntry.getKey()).rightValue().getLineListAttrs();

               String errForNum =  TextDiffUtils.getDiffStr(left , right) ;

                errors.put( (Integer)mapEntry.getKey(), errForNum);

            }
            return false;

        }
        return true;
    }

    ;

    @Override
    public int hashCode() {
        int result = version;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? toCanonical(description).hashCode() : 0);
        result = 31 * result + (startdate != null ? startdate.hashCode() : 0);
        result = 31 * result + (enddate != null ? enddate.hashCode() : 0);
        result = 31 * result + (docGroupCode != null ? docGroupCode.hashCode() : 0);
        result = 31 * result + (docTypeCode != null ? docTypeCode.hashCode() : 0);
        result = 31 * result + (enabled_flag != null ? enabled_flag.hashCode() : 0);
        return result;
    }

    private String name;
    private String description;
    private Date startdate;
    private Date enddate;
    private String docGroupCode;
    private String docTypeCode;
    private String enabled_flag;

    public Map<Integer, ControlLine> getLines() {
        return lines;
    }

    public void setLines(Map<Integer, ControlLine> lines) {
        this.lines = lines;
    }

    private Map<Integer, ControlLine> lines;


    public String getGuidheader() {
        return guidheader;
    }

    public void setGuidheader(String guidheader) {
        this.guidheader = guidheader;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Control{" +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", docGroupCode='" + docGroupCode + '\'' +
                ", docTypeCode='" + docTypeCode + '\'' +
                ", enabled_flag='" + enabled_flag + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getDocGroupCode() {
        return docGroupCode;
    }

    public void setDocGroupCode(String docGroupCode) {
        this.docGroupCode = docGroupCode;
    }

    public String getDocTypeCode() {
        return docTypeCode;
    }

    public void setDocTypeCode(String doc_type_code) {
        this.docTypeCode = doc_type_code;
    }

    public String getEnabled_flag() {
        return enabled_flag;
    }

    public void setEnabled_flag(String enabled_flag) {
        this.enabled_flag = enabled_flag;
    }


}
