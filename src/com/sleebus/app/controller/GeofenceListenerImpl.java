package com.sleebus.app.controller;

import com.codename1.location.GeofenceListener;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Display;

/**
 * Created by ahmedengu.
 */
public class GeofenceListenerImpl implements GeofenceListener {

    @Override
    public void onExit(String id) {

    }

    @Override
    public void onEntered(String id) {
        LocalNotification notification = new LocalNotification();
        notification.setId(id);
        notification.setAlertTitle("SleeBus");
        notification.setAlertBody("You arrived!");
        Display.getInstance().scheduleLocalNotification(notification, System.currentTimeMillis() + 10, LocalNotification.REPEAT_NONE);

        if (!Display.getInstance().isMinimized()) {
            Facade.showFormCallback(id);
        }
        Facade.makingNoise(id);
    }
}
