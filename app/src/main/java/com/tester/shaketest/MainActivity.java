package com.tester.shaketest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    //宣告要被改背景的linearlayout為public，這樣才能被ShakeService.java給讀取
    public static LinearLayout square;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewById方法
        square = findViewById(R.id.square);
        //生成所需Intent物件
        Intent intent0 = new Intent(this, ShakeService.class);
        //開始執行服務
        startService(intent0);
    }
}
