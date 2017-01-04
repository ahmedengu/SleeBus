package com.sleebus.app.controller;

import com.codename1.location.GeofenceListener;
import com.codename1.location.LocationManager;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Display;
import com.sleebus.app.model.Alarm;
import com.sleebus.app.model.AlarmDaoImpl;

/**
 * Created by ahmedengu.
 */
public class GeofenceListenerImpl implements GeofenceListener {

    @Override
    public void onExit(String id) {

    }

    @Override
    public void onEntered(String id) {
        LocationManager.getLocationManager().removeGeoFencing(id);
        LocalNotification notification = new LocalNotification();
        notification.setId(id);
        notification.setAlertTitle("SleeBus");
        notification.setAlertBody("You arrived!");
        Display.getInstance().scheduleLocalNotification(notification, System.currentTimeMillis() + 10, LocalNotification.REPEAT_NONE);

        if (!Display.getInstance().isMinimized()) {
            UtilsFacade.showFormCallback(id);
        }

        Display.getInstance().scheduleBackgroundTask(new Runnable() {
            @Override
            public void run() {
                Alarm alarm = AlarmDaoImpl.getInstance().getFromAlarms(id);
                alarm.getState().action(alarm);
            }
        });
    }
}
