package com.sleebus.app.model;

import ca.weblite.codename1.json.JSONArray;
import ca.weblite.codename1.json.JSONException;
import com.codename1.io.Preferences;
import com.codename1.maps.Coord;

import java.util.ArrayList;

/**
 * Created by ahmedengu.
 */
public class AlarmDaoImpl implements AlarmDao {
    private static AlarmDaoImpl alarmDao;
    private ArrayList<Alarm> alarms = new ArrayList<>();

    private AlarmDaoImpl() {

    }

    public static synchronized AlarmDaoImpl getInstance() {
        if (alarmDao == null)
            alarmDao = new AlarmDaoImpl();
        return alarmDao;
    }

    @Override
    public ArrayList<Alarm> getAlarms() {
        return alarms;
    }

    @Override
    public String getAlarmsString() {
        String ret = "[";
        for (Alarm alarm : alarms) {
            ret += alarm.toString() + ",";
        }
        return ret.substring(0, ret.length() - 1) + "]";
    }

    @Override
    public void setAlarms(ArrayList<Alarm> alarms) {
        this.alarms = alarms;
        saveToDesk();
    }

    @Override
    public void addToAlarms(Alarm alarm) {
        for (Alarm a : alarms) {
            if (a.equals(alarm))
                return;
        }
        this.alarms.add(alarm);
        saveToDesk();
    }

    @Override
    public void removeFromAlarms(Alarm alarm) {
        this.alarms.remove(alarm);
        saveToDesk();
    }

    @Override
    public void clearAlarms() {
        this.alarms.clear();
        saveToDesk();

    }

    @Override
    public void removeFromAlarms(int i) {
        this.alarms.remove(i);
        saveToDesk();

    }

    @Override
    public Alarm getFromAlarms(int i) {
        return this.alarms.get(i);
    }

    @Override
    public Alarm getFromAlarms(String id) {
        for (Alarm alarm : this.alarms) {
            if (alarm.getId().equals(id)) {
                return alarm;
            }
        }
        return null;
    }

    @Override
    public void saveToDesk() {
        Preferences.set("Alarms", getAlarmsString());
    }

    @Override
    public void loadFromDisk() throws JSONException {
        String alarms = Preferences.get("Alarms", "");
        loadFromJsonArray(alarms);

    }

    @Override
    public boolean firstRunExample() {
        if (Preferences.get("Alarms", "").equals("")) {
            new Alarm("Test", true, false, false, new Coord(31.205753, 29.924526), 10, 1000000);
            return true;
        }
        return false;
    }

    @Override
    public void loadFromJsonArray(String alarms) throws JSONException {
        if (alarms.length() > 0) {
            JSONArray jsonArray = new JSONArray(alarms);
            for (int i = 0; i < jsonArray.length(); i++) {
                new Alarm(jsonArray.getJSONObject(i));
            }
        }
    }

}
