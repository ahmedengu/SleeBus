package com.sleebus.app.view;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.sleebus.app.controller.AlarmStateFactory;
import com.sleebus.app.model.Alarm;
import com.sleebus.app.model.AlarmDaoImpl;

/**
 * Created by ahmedengu.
 */
public class Alert extends AbstractForm {
    public Alert(String name, String back, Object parse) {
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
        Alarm alarm = (Alarm) parse;
        setLayout(new BorderLayout());
        setTitle(name);
        setName(name);
        CountdownLabel countdownLabel = new CountdownLabel(120, FontImage.createMaterial(FontImage.MATERIAL_ALARM, new Style()), "Label", new CountdownLabel.Callback() {
            @Override
            public void done() {
                alarm.setState(AlarmStateFactory.getState(AlarmStateFactory.SNOOZED));
                AlarmDaoImpl.getInstance().updateAlarm(alarm);
            }
        });
        add(BorderLayout.CENTER, countdownLabel);

        Container southCnt = new Container(new FlowLayout(Component.CENTER, Component.CENTER));

        Button snoozeBtn = new Button("Snooze");
        FontImage.setMaterialIcon(snoozeBtn, FontImage.MATERIAL_SNOOZE);
        snoozeBtn.addActionListener(evt -> {
            alarm.setState(AlarmStateFactory.getState(AlarmStateFactory.SNOOZED));
            AlarmDaoImpl.getInstance().updateAlarm(alarm);
            if (Display.getInstance().isAllowMinimizing())
                Display.getInstance().minimizeApplication();
            else
                Display.getInstance().exitApplication();
        });
        southCnt.add(snoozeBtn);

        Button disableBtn = new Button("Disable");
        FontImage.setMaterialIcon(disableBtn, FontImage.MATERIAL_ALARM_OFF);
        disableBtn.addActionListener(evt -> {
            alarm.setState(AlarmStateFactory.getState(AlarmStateFactory.DISABLED));
            AlarmDaoImpl.getInstance().updateAlarm(alarm);
            if (Display.getInstance().isAllowMinimizing())
                Display.getInstance().minimizeApplication();
            else
                Display.getInstance().exitApplication();
        });
        southCnt.add(disableBtn);

        add(BorderLayout.SOUTH, southCnt);
    }
}
