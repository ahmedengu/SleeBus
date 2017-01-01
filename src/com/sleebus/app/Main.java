package com.sleebus.app;

import ca.weblite.codename1.json.JSONException;
import com.codename1.notifications.LocalNotificationCallback;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.sleebus.app.controller.Facade;
import com.sleebus.app.controller.FormFactory;
import com.sleebus.app.model.AlarmDaoImpl;

public class Main implements LocalNotificationCallback {

    private Form current;

    public void init(Object context) {
        Resources theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);
        if (!AlarmDaoImpl.getInstance().firstRunExample())
            try {
                AlarmDaoImpl.getInstance().loadFromDisk();
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

    @Override
    public void localNotificationReceived(String notificationId) {
        Facade.showFormCallback(notificationId);
    }
}