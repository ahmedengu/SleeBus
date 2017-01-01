package com.sleebus.app.controller;

/**
 * Created by ahmedengu.
 */
public class Facade {
    private Facade() {
    }

    public static int StringsIndexOf(String[] strings, String s) {
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals(s))
                return i;
        }
        return -1;
    }
}
