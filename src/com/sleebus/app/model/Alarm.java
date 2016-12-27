package com.sleebus.app.model;

import ca.weblite.codename1.json.JSONException;
import ca.weblite.codename1.json.JSONObject;
import com.codename1.maps.Coord;
import com.codename1.ui.Display;

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

    public Alarm(String JsonString) throws JSONException {
        this(new JSONObject(JsonString));
    }

    public Alarm(JSONObject jsonObject) throws JSONException {
        this(jsonObject.getString("name"), jsonObject.getBoolean("vibrate"), jsonObject.getBoolean("sound"), jsonObject.getBoolean("flashlight"), (Coord) jsonObject.get("location"), jsonObject.getInt("radius"), jsonObject.getLong("expire"), jsonObject.getString("id"));
    }

    public Alarm(String name, boolean vibrate, boolean sound, boolean flashlight, Coord location, int radius, long expire, String id) {
        this.name = name;
        this.vibrate = vibrate;
        this.sound = sound;
        this.flashlight = flashlight;
        this.location = location;
        this.radius = radius;
        this.expire = expire;
        this.id = id;
    }

    public Alarm(String name, boolean vibrate, boolean sound, boolean flashlight, Coord location, int radius, long expire) {
        this.name = name;
        this.vibrate = vibrate;
        this.sound = sound;
        this.flashlight = flashlight;
        this.location = location;
        this.radius = radius;
        this.expire = expire;
        this.id = Display.getInstance().getUdid();
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

    @Override
    public String toString() {
        return "{\"Alarm\":{"
                + "                        \"name\":\"" + name + "\""
                + ",                         \"vibrate\":\"" + vibrate + "\""
                + ",                         \"sound\":\"" + sound + "\""
                + ",                         \"flashlight\":\"" + flashlight + "\""
                + ",                         \"location\":" + location
                + ",                         \"radius\":\"" + radius + "\""
                + ",                         \"expire\":\"" + expire + "\""
                + ",                         \"id\":\"" + id + "\""
                + "}}";
    }
}
