package com.sleebus.app.model;

/**
 * Created by ahmedengu.
 */
public class NullAlarmState implements AlarmState {
    @Override
    public String getStateName() {
        return null;
    }

    @Override
    public void alert(Alarm alarm) {

    }
}
