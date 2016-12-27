package com.sleebus.app.form;

import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.util.Resources;

/**
 * Created by ahmedengu.
 */
public abstract class AbstractForm extends Form {
    protected Form back;
    protected String name;
    protected Object parse;
    protected Resources resources;

    public AbstractForm(String name, Form back, Object parse) {
        this.back = back;
        this.name = name;
        this.parse = parse;
        this.resources = Resources.getGlobalResources();
        ShowingFormCalls();
    }

    private void ShowingFormCalls() {
        beforeForm();
        initGuiComponents(resources);
        setBackCmd();
        this.show();
        afterForm();
    }

    private void setBackCmd() {
        setBackCommand("Back", FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 3), evt -> {
            if (back == null) {
                if (Display.getInstance().isAllowMinimizing())
                    Display.getInstance().minimizeApplication();
                else
                    Display.getInstance().exitApplication();
            } else {
                back.show();
            }
        });
    }

    protected abstract void beforeForm();

    protected abstract void afterForm();

    abstract void initGuiComponents(Resources resourceObjectInstance);

    public void showForm(AbstractForm back) {
        this.back = back;
        this.show();
    }

    public void showForm() {
        this.show();
    }
}
