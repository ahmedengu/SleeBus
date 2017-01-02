package com.sleebus.app.model;

import com.codename1.location.LocationManager;

/**
 * Created by ahmedengu.
 */
public class NullAlarmState implements AlarmState {
    @Override
    public String getStateName() {
        return "Null";
    }

    @Override
    public void attach(Alarm alarm) {
        LocationManager.getLocationManager().removeGeoFencing(alarm.getId());
    }
}
