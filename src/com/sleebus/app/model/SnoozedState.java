package com.sleebus.app.model;

/**
 * Created by ahmedengu.
 */
public class SnoozedState implements AlarmState {
    @Override
    public String getStateName() {
        return "Snoozed";
    }

    @Override
    public void attach(Alarm alarm) {

    }
}
