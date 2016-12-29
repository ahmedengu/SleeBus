package com.sleebus.app.view;

import com.codename1.components.ToastBar;
import com.codename1.maps.Coord;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.sleebus.app.model.Alarm;

/**
 * Created by ahmedengu.
 */
public class NewAlarm extends AbstractForm {
    public NewAlarm(String name, String back, Object parse) {
        super(name, back, parse);
    }

    Alarm.AlarmBuilder alarmBuilder;

    @Override
    protected void beforeForm() {
        if (parse == null)
            alarmBuilder = new Alarm.AlarmBuilder();
        else
            alarmBuilder = new Alarm.AlarmBuilder((Alarm) parse);
    }

    @Override
    protected void afterForm() {

    }

    @Override
    void initGuiComponents(Resources resourceObjectInstance) {
        setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
        setTitle(name);
        setName(name);

        Container inputCnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        inputCnt.add(new Label("Alarm Name"));
        TextField name = new TextField(alarmBuilder.getName(), "Alarm Name");
        name.addActionListener(evt -> alarmBuilder.setName(name.getText()));
        inputCnt.add(name);

        inputCnt.add(new Label("Radius"));
        TextField radius = new TextField(String.valueOf(alarmBuilder.getRadius()), "Radius");
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

        inputCnt.add(new Label("Expire"));
        TextField expire = new TextField(String.valueOf(alarmBuilder.getExpire()), "Expire");
        expire.setConstraint(TextArea.NUMERIC);
        expire.addActionListener(evt -> {
            long parseLong;
            try {
                parseLong = Long.parseLong(expire.getText());
                alarmBuilder.setExpire(parseLong);
            } catch (NumberFormatException e) {
                expire.setText("");
            }
        });
        inputCnt.add(expire);

        CheckBox vibrateBox = new CheckBox("Vibrate");
        vibrateBox.setSelected(alarmBuilder.isVibrate());
        vibrateBox.addActionListener(evt -> alarmBuilder.setVibrate(vibrateBox.isSelected()));
        inputCnt.add(vibrateBox);

        CheckBox flashlightBox = new CheckBox("Flashlight");
        flashlightBox.setSelected(alarmBuilder.isFlashlight());
        flashlightBox.addActionListener(evt -> alarmBuilder.setFlashlight(flashlightBox.isSelected()));
        inputCnt.add(flashlightBox);

        CheckBox soundBox = new CheckBox("Sound");
        soundBox.setSelected(alarmBuilder.isSound());
        soundBox.addActionListener(evt -> alarmBuilder.setSound(soundBox.isSelected()));
        inputCnt.add(soundBox);

        Button locationBtn = new Button("Location");
        FontImage.setMaterialIcon(locationBtn, FontImage.MATERIAL_MY_LOCATION);
        locationBtn.addActionListener(evt -> {
            Style s = new Style();
            s.setBgTransparency(0);
            s.setFgColor(0x0000FF);

            Dialog mapDialog = new Dialog("Map");
            mapDialog.setLayout(new BorderLayout());
            MapCnt mapCnt = new MapCnt();

            if (alarmBuilder.getLocation() != null)
                mapCnt.addMarker(FontImage.createMaterial(FontImage.MATERIAL_LOCATION_ON, s).toEncodedImage(), alarmBuilder.getLocation(), "Destination", "", null);

            mapCnt.addTapListener(evt1 -> {
                mapCnt.clearMapLayers();
                Coord coord = mapCnt.getCoordAtPosition(evt1.getX(), evt1.getY());

                mapCnt.addMarker(FontImage.createMaterial(FontImage.MATERIAL_LOCATION_ON, s).toEncodedImage(), coord, "Destination", "", null);
                alarmBuilder.setLocation(coord);
                mapDialog.dispose();
            });
            mapDialog.add(BorderLayout.CENTER, mapCnt);

            Button cancelBtn = new Button("Cancel");
            cancelBtn.addActionListener(evt1 -> {
                mapDialog.dispose();
            });
            mapDialog.add(BorderLayout.SOUTH, cancelBtn);
            mapDialog.show();
        });
        inputCnt.add(locationBtn);

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
