package com.tester.shaketest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import java.util.Random;
import androidx.annotation.Nullable;

public class ShakeService extends Service implements SensorEventListener {

    //宣告SensorManager與Sensor
    private SensorManager accelerationSensorManager0;
    private Sensor accelerationSensor0;

    //加速度的相關變數
    private float acceleration0;//
    private float accelerationCurrent0;//當前量測到的加速度
    private float accelerationLast0;//上一次量測到的加速度

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //讓感應器管理員取得系統服務
        accelerationSensorManager0 = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //設定加速度感應器為感應器管理員的預設感應器
        accelerationSensor0 = accelerationSensorManager0.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //註冊感應器管理員，使用延遲UI並生成一個Handler物件
        accelerationSensorManager0.registerListener(this,accelerationSensor0,SensorManager.SENSOR_DELAY_UI,new Handler());
        //回傳
        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //取得x、y、z方向的數據
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        //如果是第一次啟動，先把「上次量測的加速度」套用目前量測的結果
        accelerationLast0 = accelerationCurrent0;
        //取得當前的加速度
        accelerationCurrent0 = (float) Math.sqrt((double) (x * x + y * y + z * z));
        //取得本次與前一次量測結果的差值
        float delta = accelerationCurrent0 - accelerationLast0;
        //經驗公式？
        acceleration0 = acceleration0 * 0.9f + delta;
        //觸發的條件
        if (acceleration0 > 10) {
            //生成亂數物件
            Random random0 = new Random();
            //隨機產生顏色的設定值
            int color0 = Color.argb(255, random0.nextInt(256), random0.nextInt(256), random0.nextInt(256));
            //套用進背景設定
            MainActivity.square.setBackgroundColor(color0);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
