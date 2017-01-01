package com.sleebus.app.model;

/**
 * Created by ahmedengu.
 */
public class ActiveState implements AlarmState {
    @Override
    public String getStateName() {
        return "Active";
    }

    @Override
    public void alert(Alarm alarm) {

    }
}
