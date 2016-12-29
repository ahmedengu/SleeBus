package com.sleebus.app.view;

import com.codename1.ui.Component;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

/**
 * Created by ahmedengu.
 */
public class NullForm extends AbstractForm {
    public NullForm(String name, String back, Object parse) {
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
        setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
        setTitle(name);
        setName(name);
        add(new Label("Form Not Available"));
    }
}
