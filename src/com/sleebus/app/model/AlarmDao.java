package com.sleebus.app.model;

import ca.weblite.codename1.json.JSONException;

import java.util.ArrayList;

/**
 * Created by ahmedengu.
 */
public interface AlarmDao {
    public ArrayList<Alarm> getAlarms();

    public String getAlarmsString();

    public void setAlarms(ArrayList<Alarm> alarms);

    public void addToAlarms(Alarm alarm);

    public void removeFromAlarms(Alarm alarm);

    public void clearAlarms();

    public void removeFromAlarms(int i);

    public Alarm getFromAlarms(String id);

    public Alarm getFromAlarms(int i);

    public void saveToDesk();

    public void loadFromDisk() throws JSONException;

    public boolean firstRunExample();

    public void loadFromJsonArray(String alarms) throws JSONException;
}
