package com.sleebus.app.model;

/**
 * Created by ahmedengu.
 */
public interface AlarmState {
    public String getStateName();

    public void attach(Alarm alarm);

}
