package com.sleebus.app.controller;

import com.sleebus.app.model.Alarm;

/**
 * Created by ahmedengu.
 */
public class SnoozedState implements AlarmState {
    @Override
    public String getStateName() {
        return "Snoozed";
    }

    @Override
    public boolean precondition(Alarm alarm) {
        if (alarm.getState().getStateName().equals(AlarmStateFactory.STATES[AlarmStateFactory.DISABLED]) || alarm.getState().equals(getStateName()))
            return false;
        return true;
    }

    @Override
    public void attach(Alarm alarm) {

    }

    @Override
    public boolean isNull() {
        return false;
    }
}
