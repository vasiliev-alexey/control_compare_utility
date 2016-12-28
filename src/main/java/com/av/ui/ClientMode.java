package com.av.ui;

/**
 * Created with IntelliJ IDEA.
 * User: vasiljev.alexey
 * Date: 13.05.14
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public class ClientMode {
    private static volatile ClientMode instance;
    private Mode mode;
    private boolean debug;

    private ClientMode() {

    }

    public static ClientMode getInstance() {
        ClientMode localInstance = instance;
        if (localInstance == null) {
            synchronized (ClientMode.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ClientMode();
                }
            }
        }
        return localInstance;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

}
