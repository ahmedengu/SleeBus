package com.sleebus.app.controller;

import com.codename1.ui.Display;
import com.sleebus.app.model.AlarmDaoImpl;

/**
 * Created by ahmedengu.
 */
public class Facade {
    private Facade() {
    }

    public static int StringsIndexOf(String[] strings, String s) {
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals(s))
                return i;
        }
        return -1;
    }

    public static void showFormCallback(String notificationId) {
        Display.getInstance().callSerially(() -> {
            FormFactory.showForm(FormFactory.ALERT, AlarmDaoImpl.getInstance().getFromAlarms(notificationId));
        });
    }

}
