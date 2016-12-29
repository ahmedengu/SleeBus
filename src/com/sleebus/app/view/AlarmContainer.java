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
        setUIID("AlarmContainer");
        MultiButton nameBtn = new MultiButton(alarm.getName());
        nameBtn.setUIID("Container");
        nameBtn.addActionListener(evt -> ToastBar.showErrorMessage("Alarm: " + alarm.getName()));
        Button deleteBtn = new Button("");
        deleteBtn.addActionListener(evt -> {
            AlarmDaoImpl.getInstance().removeFromAlarms(alarm);
            Container parent = getParent();
            remove();
            parent.revalidate();
        });
        deleteBtn.setUIID("DeleteSwipe");
        FontImage.setMaterialIcon(deleteBtn, FontImage.MATERIAL_DELETE);

        Button copyBtn = new Button("");
        copyBtn.addActionListener(evt -> {
            getParent().add(new AlarmContainer(alarm.clone()));
            getParent().revalidate();
        });
        FontImage.setMaterialIcon(copyBtn, FontImage.MATERIAL_CONTENT_COPY);

        SwipeableContainer swipeableContainer = new SwipeableContainer(GridLayout.encloseIn(2, copyBtn, deleteBtn), nameBtn);
        add(swipeableContainer);
    }
}
