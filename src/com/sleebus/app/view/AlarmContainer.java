package com.sleebus.app.view;

import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import com.sleebus.app.model.Alarm;

public class AlarmContainer extends com.codename1.ui.Container {
    Alarm alarm;

    public AlarmContainer(Alarm alarm) {
        this(com.codename1.ui.util.Resources.getGlobalResources(), alarm);
    }

    public AlarmContainer(Resources resourceObjectInstance, Alarm alarm) {
        this.alarm = alarm;
        initGuiBuilderComponents(resourceObjectInstance);
    }

    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new GridLayout(1));
        setScrollableX(false);
        setName("AlarmContainer");
        setUIID("AlarmContainer");
        MultiButton nameBtn = new MultiButton(alarm.getName());
        nameBtn.setUIID("Container");
        nameBtn.addActionListener(evt -> {
            ToastBar.showErrorMessage("Alarm: " + alarm.getName());
        });
        Button deleteBtn = new Button("");
        deleteBtn.addActionListener(evt -> {
            Alarm.removeFromAlarms(alarm);
            Container parent = getParent();
            remove();
            parent.revalidate();
        });
        deleteBtn.setUIID("DeleteSwipe");
        FontImage.setMaterialIcon(deleteBtn, FontImage.MATERIAL_DELETE);
        SwipeableContainer swipeableContainer = new SwipeableContainer(deleteBtn, nameBtn);
        add(swipeableContainer);
    }
}
