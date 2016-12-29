package com.sleebus.app.view;

import com.codename1.googlemaps.MapContainer;
import com.codename1.maps.Coord;
import com.codename1.maps.providers.GoogleMapsProvider;
import com.sleebus.app.controller.MapController;

/**
 * Created by ahmedengu.
 */
public class MapCnt extends MapContainer {

    public MapCnt(Coord coord, int radius) {
        this();
        Coord[] coords = MapController.getInstance().drawAround(coord.getLatitude(), coord.getLongitude(), radius);
        addPath(coords);
    }

    public MapCnt() {
        super(GoogleMapsPSingleton.getInstance());
        setShowMyLocation(true);
        zoom(MapController.getInstance().getCurrentCoord(), getMaxZoom());
        setRotateGestureEnabled(true);
    }
}

class GoogleMapsPSingleton extends GoogleMapsProvider {
    private static GoogleMapsPSingleton googleMapsPSingleton;

    private GoogleMapsPSingleton(String apiKey) {
        super(apiKey);
    }

    public static synchronized GoogleMapsPSingleton getInstance() {
        if (googleMapsPSingleton == null)
            googleMapsPSingleton = new GoogleMapsPSingleton(MapController.MAPS_KEY);
        return googleMapsPSingleton;
    }
}