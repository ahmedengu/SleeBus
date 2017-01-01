package com.sleebus.app.model;

/**
 * Created by ahmedengu.
 */
public class EnteredState implements  AlarmState {
    @Override
    public String getStateName() {
        return "Entered";
    }

    @Override
    public void alert(Alarm alarm) {

    }
}
