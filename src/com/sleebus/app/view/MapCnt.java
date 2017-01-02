package com.sleebus.app.view;

import com.codename1.googlemaps.MapContainer;
import com.codename1.maps.Coord;
import com.codename1.maps.providers.GoogleMapsProvider;
import com.codename1.ui.FontImage;
import com.codename1.ui.plaf.Style;
import com.sleebus.app.controller.MapController;

/**
 * Created by ahmedengu.
 */
public class MapCnt extends MapContainer {

    public MapCnt() {
        super(GoogleMapsPSingleton.getInstance());
        setShowMyLocation(true);
        setRotateGestureEnabled(true);
        zoomTo(MapController.getInstance().getCurrentCoord());
    }

    public void zoomTo(Coord location) {
        if (location != null)
            zoom(location, getMaxZoom());
    }

    public void newMarker(Style s, Coord coord, int radius) {
        clearMapLayers();

        addMarker(FontImage.createMaterial(FontImage.MATERIAL_LOCATION_ON, s).toEncodedImage(), coord, "Destination", "", null);
        Coord[] coords = MapController.getInstance().drawAround(coord.getLatitude(), coord.getLongitude(), radius);
        addPath(coords);
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