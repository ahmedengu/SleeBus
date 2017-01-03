package com.sleebus.app.controller;

import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.sleebus.app.view.*;

/**
 * Created by ahmedengu.
 */
public class FormFactory {
    public static final int ALERT = 0;
    public static final int LIST_ALARMS = 1;
    public static final int NEW_ALARM = 2;
    public static final int SHOW_ALARM = 3;

    public static String[] FORMS = new String[]{"Alert", "Home", "New Alarm", "Show Form"};

    private FormFactory() {

    }

    public static AbstractForm showForm(String f) {
        return showForm(f, null);
    }

    public static AbstractForm showForm(String f, Object parse) {
        return showForm(UtilsFacade.StringsIndexOf(FORMS, f), parse);
    }

    public static AbstractForm showForm(int f) {
        return showForm(f, null);
    }

    public static AbstractForm showForm(int f, Object parse) {
        Form current = Display.getInstance().getCurrent();
        String back = (current == null) ? null : current.getName();
        switch (f) {
            case ALERT:
                return new Alert(FORMS[ALERT], back, parse);
            case LIST_ALARMS:
                return new ListAlarms(FORMS[LIST_ALARMS], back, parse);
            case NEW_ALARM:
                return new NewAlarm(FORMS[NEW_ALARM], back, parse);
            case SHOW_ALARM:
                return new NewAlarm(FORMS[SHOW_ALARM], back, parse);
            default:
                return new NullForm("Form", back, parse);
        }
    }
}
