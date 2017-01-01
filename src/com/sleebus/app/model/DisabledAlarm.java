package com.sleebus.app.model;

/**
 * Created by ahmedengu.
 */
public class DisabledAlarm implements AlarmState {
    @Override
    public String getStateName() {
        return "Disabled";
    }

    @Override
    public void alert(Alarm alarm) {

    }
}
