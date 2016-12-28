package com.av.service;

import com.google.common.collect.Sets;
import com.av.dao.oracle.OracleControlDAO;
import com.av.dao.sqllite.SQLiteControlDAO;
import com.av.domain.Control;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vasiljev.alexey
 * Date: 21.05.14
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
public class ControlCompareServiceImpl implements ControlCompareService {

    Logger log = LogManager.getLogger(ControlCompareServiceImpl.class);


    public void testCompare(Control etalon, Control control) {

    }


    public Map<Control, Integer> compare() {
        Map<String, Control> mapCurrent = new OracleControlDAO().findAllMap();
        Map<String, Control> mapEtal = new SQLiteControlDAO().findAllMap();
/*

      String [] ss  = new String[]  { "0507068-5MDK" };/*/
/* , "0503321-5_F_010112"*//*
*/
/*};
        Map <String, Control> mapCurrent = new HashMap<String, Control>();
        Map<String, Control> mapEtal = new HashMap<String, Control>();
        for (String s :ss) {
            Control ora = new OracleControlDAO().finfByCode(s);
            Control sqllite = new SQLiteControlDAO().findByCode(s);

            mapCurrent.put(ora.getCode(), ora);

            mapEtal.put(sqllite.getCode() , sqllite);
            log.debug("*****************");
           Map<Integer , ControlLine> l  = sqllite.getLines();

             for (Map.Entry e : l.entrySet()) {

                 log.debug(e.getValue());
             }


                log.debug("*****************");

        }

*/
        Map<Control, Integer> rez = new HashMap<Control, Integer>();

        // Сравним ключи все ли из эталона есть  на инстансе
        Set<String> etalKeys = mapEtal.keySet();
        Set curKeys = mapCurrent.keySet();

        log.debug("cr=" + curKeys.size() + " et=" + etalKeys.size());

        Sets.SetView<String> dif = Sets.difference(etalKeys, curKeys);

        Iterator<String> iter = dif.iterator();

        if (iter.hasNext()) {
            log.info("Контроли отсутсвующие на инстансе");
            while (iter.hasNext()) {
                log.info(iter.next().toString());
            }
        }

        Sets.SetView<String> dif2 = Sets.difference(curKeys, etalKeys);

        iter = dif2.iterator();

        if (dif2.size() != 0) {
            log.info("Контроли лишние на инстансе");
            while (iter.hasNext()) {
                log.info(iter.next().toString());
            }
        }


        // сверим хеш коды

        iter = etalKeys.iterator();
        Control ctrEt;
        Control ctrCur;
        String code;

        while (iter.hasNext()) {

            code = iter.next();

            ctrEt = mapEtal.get(code);
            ctrCur = mapCurrent.get(code);

            if (ctrEt.equals(ctrCur)) {
                log.info("Контроль " + code + " идентичен");

                rez.put(ctrEt, 0);

            } else {


                log.info("Контроль " + code + " не иднетичны");
                rez.put(ctrEt, 1);


            }


        }


        return rez;

    }


}
