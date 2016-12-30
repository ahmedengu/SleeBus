package com.sleebus.app.controller;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.codename1.maps.Coord;
import com.codename1.util.Callback;
import com.codename1.util.MathUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ahmedengu.
 */
public class MapController {
    public static final String MAPS_KEY = "AIzaSyCOz5no0XXXCb3KTcXyskeZzysefXOvCNc";
    private Coord currentCoord;
    private static MapController mapController;

    public static synchronized MapController getInstance() {
        if (mapController == null)
            mapController = new MapController();
        return mapController;
    }

    public Coord getCurrentCoord() {
        Location currentLocation;
        try {
            currentLocation = LocationManager.getLocationManager().getCurrentLocation();
        } catch (IOException e) {
            currentLocation = LocationManager.getLocationManager().getLastKnownLocation();
        }
        if (currentLocation != null) {
            if (currentCoord == null) {
                currentCoord = new Coord(currentLocation.getLatitude(), currentLocation.getLongitude());
            } else {
                currentCoord.setLatitude(currentLocation.getLatitude());
                currentCoord.setLongitude(currentLocation.getLongitude());
            }
        }
        return currentCoord;
    }

    public Coord[] decode(final String encodedPath) {
        int len = encodedPath.length();
        final ArrayList<Coord> path = new ArrayList<Coord>();
        int index = 0;
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int result = 1;
            int shift = 0;
            int b;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lat += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            result = 1;
            shift = 0;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lng += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            path.add(new Coord(lat * 1e-5, lng * 1e-5));
        }
        Coord[] p = new Coord[path.size()];
        for (int i = 0; i < path.size(); i++) {
            p[i] = path.get(i);
        }

        return p;
    }

    public String getRoutesEncoded(Coord src, Coord dest) {
        String ret = "";
        try {
            ConnectionRequest request = new ConnectionRequest("https://maps.googleapis.com/maps/api/directions/json", false);
            request.addArgument("key", MAPS_KEY);
            request.addArgument("origin", src.getLatitude() + "," + src.getLongitude());
            request.addArgument("destination", dest.getLatitude() + "," + dest.getLongitude());

            NetworkManager.getInstance().addToQueueAndWait(request);
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));
            if (response.get("routes") != null) {
                ArrayList routes = (ArrayList) response.get("routes");
                if (routes.size() > 0)
                    ret = ((LinkedHashMap) ((LinkedHashMap) ((ArrayList) response.get("routes")).get(0)).get("overview_polyline")).get("points").toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void getRoutesEncodedAsync(Coord src, Coord dest, Callback callback) {
        ConnectionRequest request = new ConnectionRequest("https://maps.googleapis.com/maps/api/directions/json", false) {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                String ret = "";
                Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(input, "UTF-8"));
                if (response.get("routes") != null) {
                    ArrayList routes = (ArrayList) response.get("routes");
                    if (routes.size() > 0)
                        ret = ((LinkedHashMap) ((LinkedHashMap) ((ArrayList) response.get("routes")).get(0)).get("overview_polyline")).get("points").toString();
                }
                callback.onSucess(ret);
            }


        };
        request.addArgument("key", MAPS_KEY);
        request.addArgument("origin", src.getLatitude() + "," + src.getLongitude());
        request.addArgument("destination", dest.getLatitude() + "," + dest.getLongitude());

        NetworkManager.getInstance().addToQueue(request);
    }


    public double distanceInKilometers(Coord point1, Coord point2) {
        double d2r = 0.0174532925199433D;
        double lat1rad = point1.getLatitude() * d2r;
        double long1rad = point1.getLongitude() * d2r;
        double lat2rad = point2.getLatitude() * d2r;
        double long2rad = point2.getLongitude() * d2r;
        double deltaLat = lat1rad - lat2rad;
        double deltaLong = long1rad - long2rad;
        double sinDeltaLatDiv2 = Math.sin(deltaLat / 2.0D);
        double sinDeltaLongDiv2 = Math.sin(deltaLong / 2.0D);

        double a = sinDeltaLatDiv2 * sinDeltaLatDiv2 + Math.cos(lat1rad)
                * Math.cos(lat2rad) * sinDeltaLongDiv2 * sinDeltaLongDiv2;

        a = Math.min(1.0D, a);
        return 2.0D * MathUtil.asin(Math.sqrt(a)) * 6371.0D;
    }

    public Coord[] drawAround(double latitude, double longitude, double radiusKM) {
        Coord[] locs = new Coord[361];
        double lat1 = latitude * Math.PI / 180.0;
        double lon1 = longitude * Math.PI / 180.0;
        double d = radiusKM / 6371;
        for (int i = 0; i <= 360; i++) {
            double tc = (i / 90) * Math.PI / 2;
            double lat = MathUtil.asin(Math.sin(lat1) * Math.cos(d) + Math.cos(lat1) * Math.sin(d) * Math.cos(tc));
            lat = 180.0 * lat / Math.PI;
            double lon;
            if (Math.cos(lat1) == 0) {
                lon = longitude;
            } else {
                lon = ((lon1 - MathUtil.asin(Math.sin(tc) * Math.sin(d) / Math.cos(lat1)) + Math.PI) % (2 * Math.PI)) - Math.PI;
            }
            lon = 180.0 * lon / Math.PI;
            locs[i] = new Coord(lat, lon);
        }
        return locs;
    }


    public static String getFormattedAddress(Coord coord) {
        String ret = "";
        try {
            ConnectionRequest request = new ConnectionRequest("https://maps.googleapis.com/maps/api/geocode/json", false);
            request.addArgument("key", MAPS_KEY);
            request.addArgument("latlng", coord.getLatitude() + "," + coord.getLongitude());

            NetworkManager.getInstance().addToQueueAndWait(request);
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));
            if (response.get("results") != null) {
                ArrayList results = (ArrayList) response.get("results");
                if (results.size() > 0)
                    ret = (String) ((LinkedHashMap) results.get(0)).get("formatted_address");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void getFormattedAddressAsync(Coord coord, Callback callback) {
        ConnectionRequest request = new ConnectionRequest("https://maps.googleapis.com/maps/api/geocode/json", false) {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                String ret = "";
                Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(input, "UTF-8"));
                if (response.get("results") != null) {
                    ArrayList results = (ArrayList) response.get("results");
                    if (results.size() > 0)
                        ret = (String) ((LinkedHashMap) results.get(0)).get("formatted_address");
                }
                callback.onSucess(ret);
            }


        };
        request.addArgument("key", MAPS_KEY);
        request.addArgument("latlng", coord.getLatitude() + "," + coord.getLongitude());

        NetworkManager.getInstance().addToQueue(request);
    }

    public static Coord getCoords(String address) {
        Coord ret = null;
        try {
            ConnectionRequest request = new ConnectionRequest("https://maps.googleapis.com/maps/api/geocode/json", false);
            request.addArgument("key", MAPS_KEY);
            request.addArgument("address", address);

            NetworkManager.getInstance().addToQueueAndWait(request);
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));
            if (response.get("results") != null) {
                ArrayList results = (ArrayList) response.get("results");
                if (results.size() > 0) {
                    LinkedHashMap location = (LinkedHashMap) ((LinkedHashMap) ((LinkedHashMap) results.get(0)).get("geometry")).get("location");
                    ret = new Coord((double) location.get("lat"), (double) location.get("lng"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void getCoordsAsync(String address, Callback callback) {
        ConnectionRequest request = new ConnectionRequest("https://maps.googleapis.com/maps/api/geocode/json", false) {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                Coord ret = null;
                Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(input, "UTF-8"));
                if (response.get("results") != null) {
                    ArrayList results = (ArrayList) response.get("results");
                    if (results.size() > 0) {
                        LinkedHashMap location = (LinkedHashMap) ((LinkedHashMap) ((LinkedHashMap) results.get(0)).get("geometry")).get("location");
                        ret = new Coord((double) location.get("lat"), (double) location.get("lng"));
                    }
                }
                callback.onSucess(ret);
            }


        };
        request.addArgument("key", MAPS_KEY);
        request.addArgument("address", address);

        NetworkManager.getInstance().addToQueue(request);
    }

    public static ArrayList<String> getAutoComplete(String input, String language, String types) {
        ArrayList<String> ret = new ArrayList<>();
        try {
            ConnectionRequest request = new ConnectionRequest("https://maps.googleapis.com/maps/api/place/autocomplete/json", false);
            request.addArgument("key", MAPS_KEY);
            request.addArgument("input", input);
            request.addArgument("types", types);
            request.addArgument("language", language);

            NetworkManager.getInstance().addToQueueAndWait(request);
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));
            if (response.get("predictions") != null) {
                ArrayList results = (ArrayList) response.get("predictions");
                if (results.size() > 0)
                    for (int i = 0; i < results.size(); i++) {
                        ret.add((String) ((LinkedHashMap) results.get(i)).get("description"));
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void getAutoCompleteAsync(String input, String language, String types, Callback callback) {
        ConnectionRequest request = new ConnectionRequest("https://maps.googleapis.com/maps/api/place/autocomplete/json", false) {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                ArrayList<String> ret = new ArrayList<>();
                Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(input, "UTF-8"));
                if (response.get("predictions") != null) {
                    ArrayList results = (ArrayList) response.get("predictions");
                    if (results.size() > 0)
                        for (int i = 0; i < results.size(); i++) {
                            ret.add((String) ((LinkedHashMap) results.get(i)).get("description"));
                        }
                }
                callback.onSucess(ret);
            }


        };
        request.addArgument("key", MAPS_KEY);
        request.addArgument("input", input);
        request.addArgument("types", types);
        request.addArgument("language", language);

        NetworkManager.getInstance().addToQueue(request);
    }
}
