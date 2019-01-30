package com.project.hc.imtest.util;

import android.os.CountDownTimer;
import com.example.hongcheng.common.util.SPUtils;
import com.project.hc.imtest.application.BaseApplication;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * 短信验证码公共倒计时类
 * Created by hongcheng on 17/8/1.
 */

public class SMSCountDownUtil {
    private static final String SMS_LAST_TIME = "SMSLASTTIME";
    private static final String SMS_MILLISIN_FUTURE = "SMSMILLISINFUTURE";

    private static final int MILLISINFUTURE = 60 * 1000;
    private static final int COUNTDOWNINTERVAL = 1000;
    private List<ICountDownTimer> timerListener;
    private TimeCountdown timer;

    /**
     * boolean representing if the timer was cancelled
     */
    private boolean isRunning = false;

    private SMSCountDownUtil() {
        timerListener = new ArrayList<>();


        if (getMillisInFuture() > 1000) {
            isRunning = true;
            timer = new TimeCountdown(getMillisInFuture(), COUNTDOWNINTERVAL);
            timer.start();
        } else {
            isRunning = false;
        }
    }

    private static class SingleHolder {
        public static SMSCountDownUtil instance = new SMSCountDownUtil();
    }

    public static SMSCountDownUtil getInstance() {
        return SingleHolder.instance;
    }

    private long getMillisInFuture() {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+8"));
        long currentTime = calendar.getTimeInMillis();
        String lastTime = SPUtils
                .getStringFromSP(BaseApplication.getInstance(), "123456" + SMS_LAST_TIME, "0");
        String countDownTime = SPUtils
                .getStringFromSP(BaseApplication.getInstance(), "123456" + SMS_MILLISIN_FUTURE, "0");
        long lastMillisTime = Long.parseLong(lastTime);
        long lostTime = Long.parseLong(countDownTime);
        long result = lostTime - currentTime + lastMillisTime;

        if (result > MILLISINFUTURE) {
            return 0;
        }

        return result;
    }

    public void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        timer = new TimeCountdown(MILLISINFUTURE, COUNTDOWNINTERVAL);
        timer.start();
    }

    public void cancel() {
        isRunning = false;
        timer.cancel();
        timer = null;
    }

    public void addListener(ICountDownTimer listener) {
        listener.showCountDownState(isRunning);
        timerListener.add(listener);
    }

    public void removeListener(ICountDownTimer listener) {
        timerListener.remove(listener);
    }

    /* 定义一个倒计时的内部类 */
    private class TimeCountdown extends CountDownTimer {
        public TimeCountdown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        // 计时完毕时触发
        @Override
        public void onFinish() {
            for (ICountDownTimer listener : timerListener) {
                listener.onFinish();
            }
            isRunning = false;
            SPUtils.putValueToSP(BaseApplication.getInstance(), "123456" + SMS_LAST_TIME, "0");
            SPUtils.putValueToSP(BaseApplication.getInstance(), "123456" + SMS_MILLISIN_FUTURE, "0");
        }

        // 计时过程显示
        @Override
        public void onTick(long millisUntilFinished) {
            for (ICountDownTimer listener : timerListener) {
                listener.onTick(millisUntilFinished);
            }

            GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+8"));
            SPUtils.putValueToSP(BaseApplication.getInstance(), "123456" + SMS_LAST_TIME, calendar.getTimeInMillis() + "");
            SPUtils.putValueToSP(BaseApplication.getInstance(), "123456" + SMS_MILLISIN_FUTURE, millisUntilFinished + "");
        }
    }

    public interface ICountDownTimer {
        void showCountDownState(boolean isRunning);

        void onFinish();

        void onTick(long millisUntilFinished);
    }
}
