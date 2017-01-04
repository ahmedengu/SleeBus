package com.sleebus.app.controller;

import com.codename1.ui.Display;
import com.sleebus.app.model.Alarm;
import com.sleebus.app.model.AlarmDaoImpl;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ahmedengu.
 */
public class UtilsFacade {
    static Timer timer;

    private UtilsFacade() {
    }

    public static int StringsIndexOf(String[] strings, String s) {
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals(s))
                return i;
        }
        return -1;
    }

    public static void showFormCallback(String notificationId) {
        Display.getInstance().callSerially(() -> {
            FormFactory.showForm(FormFactory.ALERT, AlarmDaoImpl.getInstance().getFromAlarms(notificationId));
        });
    }

    public static void makingNoise(String id) {
        makingNoise(id, 100);
    }

    public static void makingNoise(String id, int delay) {
        Display.getInstance().scheduleBackgroundTask(new Runnable() {
            @Override
            public void run() {
                Alarm alarm = AlarmDaoImpl.getInstance().getFromAlarms(id);
                makingNoise(alarm, delay);
            }
        });
    }

    public static void makingNoise(Alarm alarm, int delay) {
        getTimerInstance().cancel();
        if (!alarm.getState().getStateName().equals(AlarmStateFactory.STATES[AlarmStateFactory.DISABLED])) {
            if (alarm.isVibrate())
                Display.getInstance().vibrate(3);
            if (alarm.isFlashlight())
                Display.getInstance().flashBacklight(3);
            if (alarm.isSound())
                Display.getInstance().playBuiltinSound(Display.SOUND_TYPE_ALARM);

            getTimerInstance().schedule(new TimerTask() {
                @Override
                public void run() {
                    makingNoise(alarm.getId(), delay);
                }
            }, delay);
        }
    }

    public static synchronized Timer getTimerInstance() {
        if (timer == null)
            timer = new Timer();
        return timer;
    }
}
