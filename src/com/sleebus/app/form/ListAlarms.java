package com.sleebus.app.form;

import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 * Created by ahmedengu.
 */
public class ListAlarms extends AbstractForm {
    public ListAlarms(String name, Form back, Object parse) {
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
    }
}
