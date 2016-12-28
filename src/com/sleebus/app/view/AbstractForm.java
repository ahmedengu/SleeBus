package com.sleebus.app.view;

import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.util.Resources;
import com.sleebus.app.controller.FormFactory;

/**
 * Created by ahmedengu.
 */
public abstract class AbstractForm extends Form {
    protected String back;
    protected String name;
    protected Object parse;
    protected Resources resources;

    public AbstractForm(String name, String back, Object parse) {
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
                FormFactory.showForm(back);
            }
        });
    }

    protected abstract void beforeForm();

    protected abstract void afterForm();

    abstract void initGuiComponents(Resources resourceObjectInstance);

    public void showForm(String back) {
        this.back = back;
        this.show();
    }

    public void showForm() {
        this.show();
    }

    public void showBack() {
        FormFactory.showForm(back);
    }

}
