package com.sleebus.app.controller;

import com.sleebus.app.model.Alarm;

/**
 * Created by ahmedengu.
 */
public interface AlarmState {
    public String getStateName();

    public boolean precondition(Alarm alarm);

    public void attach(Alarm alarm);

    public boolean isNull();

    public void action(Alarm alarm);
}
