package com.sleebus.app;

import ca.weblite.codename1.json.JSONException;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.sleebus.app.controller.FormFactory;
import com.sleebus.app.model.Alarm;

public class Main {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);
        if (!Alarm.firstRunExample())
            try {
                Alarm.loadFromDisk();
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        current = FormFactory.showForm(FormFactory.LIST_ALARMS);
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }

    public void destroy() {
    }

}