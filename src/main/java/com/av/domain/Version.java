package com.av.domain;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: alexey
 * Date: 5/20/14
 * Time: 9:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Version {

    private Timestamp timestamp;

    public Timestamp getDate() {
        return timestamp;
    }

    public void setDate(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
