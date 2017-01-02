package com.sleebus.app.model;

/**
 * Created by ahmedengu.
 */
public class NullAlarmState implements AlarmState {
    @Override
    public String getStateName() {
        return "Null";
    }

    @Override
    public void alert(Alarm alarm) {

    }
}
