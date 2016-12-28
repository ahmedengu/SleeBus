package com.sleebus.app.model;

import ca.weblite.codename1.json.JSONArray;
import ca.weblite.codename1.json.JSONException;
import ca.weblite.codename1.json.JSONObject;
import com.codename1.io.Preferences;
import com.codename1.maps.Coord;
import com.codename1.ui.Display;

import java.util.ArrayList;

/**
 * Created by ahmedengu.
 */
public class Alarm {
    private String name;
    private boolean vibrate;
    private boolean sound;
    private boolean flashlight;
    private Coord location;
    private int radius;
    private long expire;
    private String id;
    private static ArrayList<Alarm> alarms = new ArrayList<>();

    public Alarm(String JsonString) throws JSONException {
        this(new JSONObject(JsonString));
    }

    private Alarm(JSONObject jsonObject) throws JSONException {
        this(jsonObject.getString("name"), jsonObject.getBoolean("vibrate"), jsonObject.getBoolean("sound"), jsonObject.getBoolean("flashlight"), new Coord(jsonObject.getJSONObject("location").getDouble("lat"), jsonObject.getJSONObject("location").getDouble("lan")), jsonObject.getInt("radius"), jsonObject.getLong("expire"), jsonObject.getString("id"));
    }

    private Alarm(String name, boolean vibrate, boolean sound, boolean flashlight, Coord location, int radius, long expire) {
        this(name, vibrate, sound, flashlight, location, radius, expire, Display.getInstance().getUdid());
    }

    private Alarm(String name, boolean vibrate, boolean sound, boolean flashlight, Coord location, int radius, long expire, String id) {
        this.name = name;
        this.vibrate = vibrate;
        this.sound = sound;
        this.flashlight = flashlight;
        this.location = location;
        this.radius = radius;
        this.expire = expire;
        this.id = (id == null) ? String.valueOf((System.currentTimeMillis())) : id;

        for (Alarm alarm : alarms) {
            if (alarm.equals(this))
                return;
        }

        alarms.add(this);
        saveToDesk();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public boolean isFlashlight() {
        return flashlight;
    }

    public void setFlashlight(boolean flashlight) {
        this.flashlight = flashlight;
    }

    public Coord getLocation() {
        return location;
    }

    public void setLocation(Coord location) {
        this.location = location;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static ArrayList<Alarm> getAlarms() {
        return alarms;
    }

    private static String getAlarmsString() {
        String ret = "[";
        for (Alarm alarm : alarms) {
            ret += alarm.toString() + ",";
        }
        return ret.substring(0, ret.length() - 1) + "]";
    }

    public static void setAlarms(ArrayList<Alarm> alarms) {
        Alarm.alarms = alarms;
        saveToDesk();
    }

    public static void addToAlarms(Alarm alarm) {
        Alarm.alarms.add(alarm);
        saveToDesk();
    }

    public static void removeFromAlarms(Alarm alarm) {
        Alarm.alarms.remove(alarm);
        saveToDesk();
    }

    public static void clearAlarms() {
        Alarm.alarms.clear();
        saveToDesk();
    }

    public static void removeFromAlarms(int i) {
        Alarm.alarms.remove(i);
        saveToDesk();
    }

    public static Alarm getFromAlarms(int i) {
        return Alarm.alarms.get(i);
    }

    public static Alarm getFromAlarms(String id) {
        for (Alarm alarm : Alarm.alarms) {
            if (alarm.id.equals(id)) {
                return alarm;
            }
        }
        return null;
    }

    public Alarm clone() {
        return copy(this);
    }

    private static Alarm copy(Alarm alarm) {
        return new Alarm(alarm.name, alarm.vibrate, alarm.sound, alarm.flashlight, alarm.location, alarm.radius, alarm.expire);
    }

    @Override
    public String toString() {
        return "{"
                + "\"name\":\"" + name + "\""
                + ",\"vibrate\":\"" + vibrate + "\""
                + ",\"sound\":\"" + sound + "\""
                + ",\"flashlight\":\"" + flashlight + "\""
                + ",\"location\":{\"lat\":" + location.getLatitude() + ",\"lan\":" + location.getLongitude() + "}"
                + ",\"radius\":\"" + radius + "\""
                + ",\"expire\":\"" + expire + "\""
                + ",\"id\":\"" + id + "\""
                + "}";
    }

    private static void saveToDesk() {
        Preferences.set("Alarms", Alarm.getAlarmsString());
    }

    public static void loadFromDisk() throws JSONException {
        String alarms = Preferences.get("Alarms", "");
        loadFromJsonArray(alarms);
    }

    public static boolean firstRunExample() {
        if (Preferences.get("Alarms", "").equals("")) {
            new Alarm("Test", true, false, false, new Coord(31.205753, 29.924526), 10, 1000000);
            return true;
        }
        return false;
    }

    private static void loadFromJsonArray(String alarms) throws JSONException {
        if (alarms.length() > 0) {
            JSONArray jsonArray = new JSONArray(alarms);
            for (int i = 0; i < jsonArray.length(); i++) {
                new Alarm(jsonArray.getJSONObject(i));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alarm alarm = (Alarm) o;

        return id.equals(alarm.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public static class AlarmBuilder {
        private String name;
        private boolean vibrate;
        private boolean sound;
        private boolean flashlight;
        private Coord location;
        private int radius;
        private long expire;

        public AlarmBuilder() {
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setVibrate(boolean vibrate) {
            this.vibrate = vibrate;
        }

        public void setSound(boolean sound) {
            this.sound = sound;
        }

        public void setFlashlight(boolean flashlight) {
            this.flashlight = flashlight;
        }

        public void setLocation(Coord location) {
            this.location = location;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public void setExpire(long expire) {
            this.expire = expire;
        }

        public Alarm build() {
            if (name != null && location != null && radius != 0 && expire != 0)
                return new Alarm(name, vibrate, sound, flashlight, location, radius, expire);
            return null;
        }
    }
}
