package com.sleebus.app.controller;

import com.codename1.location.LocationManager;
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
        LocationManager.getLocationManager().removeGeoFencing(alarm.getId());
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public void action(Alarm alarm) {
        UtilsFacade.makingNoise(alarm, 600);
    }
}
