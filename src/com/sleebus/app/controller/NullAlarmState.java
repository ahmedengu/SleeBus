package com.sleebus.app.controller;

import com.codename1.location.LocationManager;
import com.sleebus.app.model.Alarm;

/**
 * Created by ahmedengu.
 */
public class NullAlarmState implements AlarmState {
    @Override
    public String getStateName() {
        return "Null";
    }

    @Override
    public boolean precondition(Alarm alarm) {
        if (alarm.getState().equals(getStateName()))
            return false;
        return true;
    }

    @Override
    public void attach(Alarm alarm) {
        LocationManager.getLocationManager().removeGeoFencing(alarm.getId());
    }

    @Override
    public boolean isNull() {
        return true;
    }
}
