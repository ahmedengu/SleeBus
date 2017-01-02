package com.sleebus.app.controller;

import com.sleebus.app.model.*;

/**
 * Created by ahmedengu.
 */
public class AlarmStateFactory {
    public static final int DISABLED = 0;
    public static final int SNOOZED = 1;
    public static final int ACTIVE = 2;

    public static final String[] STATES = new String[]{"Disabled", "Snoozed", "Active"};

    public static AlarmState getState(String name) {
        return getState(Facade.StringsIndexOf(STATES, name));
    }

    public static AlarmState getState(int i) {
        switch (i) {
            case DISABLED:
                return new DisabledAlarm();
            case SNOOZED:
                return new SnoozedState();
            case ACTIVE:
                return new ActiveState();
            default:
                return new NullAlarmState();
        }
    }
}
