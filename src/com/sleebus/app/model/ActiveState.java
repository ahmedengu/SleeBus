package com.sleebus.app.model;

import com.codename1.location.Geofence;
import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.sleebus.app.controller.GeofenceListenerImpl;

/**
 * Created by ahmedengu.
 */
public class ActiveState implements AlarmState {
    @Override
    public String getStateName() {
        return "Active";
    }

    @Override
    public void attach(Alarm alarm) {
        Location location = new Location();
        location.setLatitude(alarm.getLocation().getLatitude());
        location.setLongitude(alarm.getLocation().getLongitude());
        LocationManager.getLocationManager().addGeoFencing(GeofenceListenerImpl.class, new Geofence(alarm.getId(), location, alarm.getRadius(), alarm.getExpire()));
    }
}
