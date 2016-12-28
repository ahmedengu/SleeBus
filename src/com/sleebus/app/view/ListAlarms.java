package com.sleebus.app.view;

import com.codename1.components.FloatingActionButton;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.sleebus.app.controller.FormFactory;
import com.sleebus.app.model.Alarm;

import java.util.ArrayList;

/**
 * Created by ahmedengu.
 */
public class ListAlarms extends AbstractForm {
    public ListAlarms(String name, String back, Object parse) {
        super(name, back, parse);
    }

    @Override
    protected void beforeForm() {

    }

    @Override
    protected void afterForm() {

    }

    @Override
    void initGuiComponents(Resources resourceObjectInstance) {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setTitle(name);
        setName(name);
        ArrayList<Alarm> alarms = Alarm.getAlarms();
        for (Alarm alarm : alarms) {
            add(new AlarmContainer(alarm));
        }

        FloatingActionButton floatingActionButton = FloatingActionButton.createFAB(FontImage.MATERIAL_ALARM_ADD);
        floatingActionButton.addActionListener(evt -> FormFactory.showForm(FormFactory.NEW_ALARM));
        floatingActionButton.bindFabToContainer(this.getContentPane());
    }
}
