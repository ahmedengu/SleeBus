package com.sleebus.app.view;

import com.codename1.googlemaps.MapContainer;
import com.codename1.maps.providers.GoogleMapsProvider;

/**
 * Created by ahmedengu.
 */
public class MapCnt extends MapContainer {
    private static MapCnt mapCnt;

    private MapCnt() {
        super(new GoogleMapsProvider("AIzaSyCOz5no0XXXCb3KTcXyskeZzysefXOvCNc"));
    }

    public static synchronized MapCnt getInstance() {
        if (mapCnt == null)
            mapCnt = new MapCnt();
        return mapCnt;
    }
}
