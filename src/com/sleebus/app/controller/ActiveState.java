package com.sleebus.app.controller;

import com.codename1.location.Geofence;
import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.sleebus.app.model.Alarm;

/**
 * Created by ahmedengu.
 */
public class ActiveState implements AlarmState {
    @Override
    public String getStateName() {
        return "Active";
    }

    @Override
    public boolean precondition(Alarm alarm) {
        if (alarm.getState().getStateName().equals(AlarmStateFactory.STATES[AlarmStateFactory.SNOOZED]) || alarm.getState().equals(getStateName()))
            return false;
        return true;
    }

    @Override
    public void attach(Alarm alarm) {
        LocationManager.getLocationManager().removeGeoFencing(alarm.getId());
        Location location = new Location();
        location.setLatitude(alarm.getLocation().getLatitude());
        location.setLongitude(alarm.getLocation().getLongitude());
        LocationManager.getLocationManager().addGeoFencing(GeofenceListenerImpl.class, new Geofence(alarm.getId(), location, alarm.getRadius(), alarm.getExpire()));
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public void action(Alarm alarm) {
        UtilsFacade.makingNoise(alarm, 100);
    }
}
