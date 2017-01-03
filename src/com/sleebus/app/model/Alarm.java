package com.sleebus.app.model;

import ca.weblite.codename1.json.JSONException;
import ca.weblite.codename1.json.JSONObject;
import com.codename1.maps.Coord;
import com.codename1.ui.Display;
import com.sleebus.app.controller.AlarmState;
import com.sleebus.app.controller.AlarmStateFactory;

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
    private AlarmState state;

    public Alarm(String JsonString) throws JSONException {
        this(new JSONObject(JsonString));
    }

    Alarm(JSONObject jsonObject) throws JSONException {
        this(jsonObject.getString("name"), jsonObject.getBoolean("vibrate"), jsonObject.getBoolean("sound"), jsonObject.getBoolean("flashlight"), new Coord(jsonObject.getJSONObject("location").getDouble("lat"), jsonObject.getJSONObject("location").getDouble("lan")), jsonObject.getInt("radius"), jsonObject.getLong("expire"), jsonObject.getString("id"), jsonObject.getString("state"));
    }

    Alarm(String name, boolean vibrate, boolean sound, boolean flashlight, Coord location, int radius, long expire) {
        this(name, vibrate, sound, flashlight, location, radius, expire, Display.getInstance().getUdid(), null);
    }

    private Alarm(String name, boolean vibrate, boolean sound, boolean flashlight, Coord location, int radius, long expire, String id, String state) {
        this.name = name;
        this.vibrate = vibrate;
        this.sound = sound;
        this.flashlight = flashlight;
        this.location = location;
        this.radius = radius;
        this.expire = expire;
        this.id = (id == null) ? String.valueOf((System.currentTimeMillis())) : id;
        if (state == null)
            this.state = AlarmStateFactory.getState(AlarmStateFactory.ACTIVE);
        else
            this.state = AlarmStateFactory.getState(state);

        AlarmDaoImpl.getInstance().addToAlarms(this);
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

    public AlarmState getState() {
        return state;
    }

    public void setState(AlarmState state) {
        this.state = state;
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
                + ",\"state\":\"" + state.getStateName() + "\""
                + "}";
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
        private Alarm alarm;

        public AlarmBuilder() {
            radius = 3;
            vibrate = true;
        }

        public AlarmBuilder(Alarm alarm) {
            this.name = alarm.name;
            this.vibrate = alarm.vibrate;
            this.sound = alarm.sound;
            this.flashlight = alarm.flashlight;
            this.location = alarm.location;
            this.radius = alarm.radius;
            this.expire = alarm.expire;
            this.alarm = alarm;
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
                if (alarm != null) {
                    alarm.setLocation(location);
                    alarm.setName(name);
                    alarm.setFlashlight(flashlight);
                    alarm.setExpire(expire);
                    alarm.setRadius(radius);
                    alarm.setSound(sound);
                    alarm.setVibrate(vibrate);
                    AlarmDaoImpl.getInstance().updateAlarm(alarm);
                    return alarm;
                } else
                    return new Alarm(name, vibrate, sound, flashlight, location, radius, expire);
            return null;
        }

        public String getName() {
            return (name == null) ? "" : name;
        }

        public boolean isVibrate() {
            return vibrate;
        }

        public boolean isSound() {
            return sound;
        }

        public boolean isFlashlight() {
            return flashlight;
        }

        public Coord getLocation() {
            return location;
        }

        public int getRadius() {
            return radius;
        }

        public long getExpire() {
            return expire;
        }

        public Alarm getAlarm() {
            return alarm;
        }
    }
}
