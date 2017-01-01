package com.sleebus.app.controller;

import com.sleebus.app.model.*;

/**
 * Created by ahmedengu.
 */
public class AlarmStateFactory {
    public static final int DISABLED = 0;
    public static final int EXPIRED = 1;
    public static final int SNOOZED = 2;
    public static final int ACTIVE = 3;
    public static final int ENTERED = 4;

    public static final String[] STATES = new String[]{"Disabled", "Expired", "Snoozed", "Active", "Entered"};

    public static AlarmState getState(String name) {
        return getState(Facade.StringsIndexOf(STATES, name));
    }

    public static AlarmState getState(int i) {
        switch (i) {
            case DISABLED:
                return new DisabledAlarm();
            case EXPIRED:
                return new ExpiredAlarm();
            case SNOOZED:
                return new SnoozedState();
            case ACTIVE:
                return new ActiveState();
            case ENTERED:
                return new EnteredState();
            default:
                return new NullAlarmState();
        }
    }
}
