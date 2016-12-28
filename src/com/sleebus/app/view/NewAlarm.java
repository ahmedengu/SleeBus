package com.sleebus.app.view;

import com.codename1.components.ToastBar;
import com.codename1.maps.Coord;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.sleebus.app.model.Alarm;

/**
 * Created by ahmedengu.
 */
public class NewAlarm extends AbstractForm {
    public NewAlarm(String name, String back, Object parse) {
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

        Alarm.AlarmBuilder alarmBuilder = new Alarm.AlarmBuilder();

        Container inputCnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        TextField name = new TextField("", "Alarm Name");
        name.addActionListener(evt -> {
            alarmBuilder.setName(name.getText());
        });
        inputCnt.add(name);

        TextField radius = new TextField("", "Radius");
        radius.setConstraint(TextArea.NUMERIC);
        radius.addActionListener(evt -> {
            try {
                int parseInt = Integer.parseInt(radius.getText());
                alarmBuilder.setRadius(parseInt);
            } catch (NumberFormatException e) {
                radius.setText("");
            }
        });
        inputCnt.add(radius);

        TextField expire = new TextField("", "Expire");
        expire.setConstraint(TextArea.NUMERIC);
        expire.addActionListener(evt -> {
            long parseLong = 0;
            try {
                parseLong = Long.parseLong(expire.getText());
                alarmBuilder.setExpire(parseLong);
            } catch (NumberFormatException e) {
                expire.setText("");
            }
        });
        inputCnt.add(expire);

        CheckBox vibrateBox = new CheckBox("Vibrate");
        vibrateBox.addActionListener(evt -> {
            alarmBuilder.setVibrate(vibrateBox.isSelected());
        });
        inputCnt.add(vibrateBox);

        CheckBox flashlightBox = new CheckBox("Flashlight");
        flashlightBox.addActionListener(evt -> {
            alarmBuilder.setFlashlight(flashlightBox.isSelected());
        });
        inputCnt.add(flashlightBox);

        CheckBox soundBox = new CheckBox("Sound");
        soundBox.addActionListener(evt -> {
            alarmBuilder.setSound(soundBox.isSelected());
        });
        inputCnt.add(soundBox);

        alarmBuilder.setLocation(new Coord(31.205753, 29.924526));
        Button addBtn = new Button("Add");
        FontImage.setMaterialIcon(addBtn, FontImage.MATERIAL_ADD);
        addBtn.addActionListener(evt -> {
            if (alarmBuilder.build() != null)
                showBack();
            else
                ToastBar.showErrorMessage("Fill the required!");
        });
        inputCnt.add(addBtn);

        add(inputCnt);
    }
}
