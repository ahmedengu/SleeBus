package com.sleebus.app.form;

import com.codename1.ui.Display;

/**
 * Created by ahmedengu.
 */
public class FormFactory {
    public static final int ALERT = 1;
    public static final int LIST_ALARMS = 2;
    public static final int NEW_ALARM = 3;

    private FormFactory() {

    }

    public static AbstractForm getForm(int f) {
        switch (f) {
            case 1:
                return new Alert("Alert", Display.getInstance().getCurrent(), null);
            case 2:
                return new ListAlarms("Home", Display.getInstance().getCurrent(), null);
            case 3:
                return new NewAlarm("New Alarm", Display.getInstance().getCurrent(), null);
            default:
                return null;
        }
    }
}
