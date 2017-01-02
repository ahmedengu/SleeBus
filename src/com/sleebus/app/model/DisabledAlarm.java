package com.sleebus.app.model;

import com.codename1.location.LocationManager;

/**
 * Created by ahmedengu.
 */
public class DisabledAlarm implements AlarmState {
    @Override
    public String getStateName() {
        return "Disabled";
    }

    @Override
    public void attach(Alarm alarm) {
        LocationManager.getLocationManager().removeGeoFencing(alarm.getId());
    }
}
