package com.sleebus.app.model;

/**
 * Created by ahmedengu.
 */
public class ExpiredAlarm implements AlarmState {
    @Override
    public String getStateName() {
        return "Expired";
    }

    @Override
    public void alert(Alarm alarm) {

    }
}
