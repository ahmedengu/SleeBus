package com.sleebus.app.controller;

import com.codename1.notifications.LocalNotificationCallback;

/**
 * Created by ahmedengu.
 */
public class LocalNotificationCallbackImpl implements LocalNotificationCallback {
    @Override
    public void localNotificationReceived(String notificationId) {
        Facade.showFormCallback(notificationId);
    }
}
