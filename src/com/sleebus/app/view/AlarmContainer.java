package com.sleebus.app.view;

import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import com.sleebus.app.controller.AlarmState;
import com.sleebus.app.controller.AlarmStateFactory;
import com.sleebus.app.controller.FormFactory;
import com.sleebus.app.model.Alarm;
import com.sleebus.app.model.AlarmDaoImpl;

class AlarmContainer extends com.codename1.ui.Container {
    private final Alarm alarm;

    public AlarmContainer(Alarm alarm) {
        this(com.codename1.ui.util.Resources.getGlobalResources(), alarm);
    }

    private AlarmContainer(Resources resourceObjectInstance, Alarm alarm) {
        this.alarm = alarm;
        initGuiBuilderComponents(resourceObjectInstance);
    }

    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new GridLayout(1));
        setScrollableX(false);
        setName("AlarmContainer");

        MultiButton nameBtn = new MultiButton(alarm.getName());
        nameBtn.setUIID("Button");
        nameBtn.addActionListener(evt -> {
            FormFactory.showForm(FormFactory.SHOW_ALARM, alarm);
        });
        Button deleteBtn = new Button("");
        deleteBtn.addActionListener(evt -> {
            AlarmDaoImpl.getInstance().removeFromAlarms(alarm);
            Container parent = getParent();
            remove();
            parent.revalidate();
        });

        FontImage.setMaterialIcon(deleteBtn, FontImage.MATERIAL_DELETE);
        Button copyBtn = new Button("");
        copyBtn.addActionListener(evt -> {
            getParent().add(new AlarmContainer(alarm.clone()));
            getParent().revalidate();
        });
        FontImage.setMaterialIcon(copyBtn, FontImage.MATERIAL_CONTENT_COPY);

        Button stateBtn = new Button("");
        stateBtn.addActionListener(evt -> {
            if (alarm.getState().getStateName().equals("Disabled")) {
                AlarmState state = AlarmStateFactory.getState(AlarmStateFactory.ACTIVE);
                if (state.precondition(alarm)) {
                    alarm.setState(state);
                    AlarmDaoImpl.getInstance().updateAlarm(alarm);
                    FontImage.setMaterialIcon(stateBtn, FontImage.MATERIAL_ALARM_ON);
                }
            } else {
                AlarmState state = AlarmStateFactory.getState(AlarmStateFactory.DISABLED);
                if (state.precondition(alarm)) {
                    alarm.setState(state);
                    AlarmDaoImpl.getInstance().updateAlarm(alarm);
                    FontImage.setMaterialIcon(stateBtn, FontImage.MATERIAL_ALARM_OFF);
                }
            }
            getParent().revalidate();
        });

        if (alarm.getState().getStateName().equals("Disabled"))
            FontImage.setMaterialIcon(stateBtn, FontImage.MATERIAL_ALARM_OFF);
        else
            FontImage.setMaterialIcon(stateBtn, FontImage.MATERIAL_ALARM_ON);

        SwipeableContainer swipeableContainer = new SwipeableContainer(GridLayout.encloseIn(3, stateBtn, copyBtn, deleteBtn), nameBtn);
        add(swipeableContainer);
    }
}
