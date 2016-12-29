package com.sleebus.app.controller;

import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.sleebus.app.view.AbstractForm;
import com.sleebus.app.view.Alert;
import com.sleebus.app.view.ListAlarms;
import com.sleebus.app.view.NewAlarm;

/**
 * Created by ahmedengu.
 */
public class FormFactory {
    public static final int ALERT = 0;
    public static final int LIST_ALARMS = 1;
    public static final int NEW_ALARM = 2;
    public static String[] FORMS = new String[]{"Alert", "Home", "New Alarm"};

    private FormFactory() {

    }

    public static AbstractForm showForm(String f) {
        return showForm(f, null);
    }

    private static AbstractForm showForm(String f, Object parse) {
        int index = -1;
        for (int i = 0; i < FORMS.length; i++) {
            if (FORMS[i].equals(f))
                index = i;
        }
        return showForm(index, parse);
    }

    public static AbstractForm showForm(int f) {
        return showForm(f, null);
    }

    private static AbstractForm showForm(int f, Object parse) {
        Form current = Display.getInstance().getCurrent();
        String back = (current == null) ? null : current.getName();
        switch (f) {
            case ALERT:
                return new Alert("Alert", back, parse);
            case LIST_ALARMS:
                return new ListAlarms("Home", back, parse);
            case NEW_ALARM:
                return new NewAlarm("New Alarm", back, parse);
            default:
                return null;
        }
    }
}
