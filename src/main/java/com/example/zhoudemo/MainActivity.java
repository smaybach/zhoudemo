package com.example.zhoudemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zhoudemo.View.MyCustomCirclerArrowView;

public class MainActivity extends AppCompatActivity {
    private MyCustomCirclerArrowView myCustomCirclerArrowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCustomCirclerArrowView = (MyCustomCirclerArrowView) findViewById(R.id.my_view);
    }
    public void onClick(View view) {
        myCustomCirclerArrowView.setColor(Color.BLUE);
    }

    public void add(View view) {
        int speed = myCustomCirclerArrowView.speed();
        if (speed == 10){
            Intent intent = new Intent(MainActivity.this,ShowActivity.class);
            startActivity(intent);
        }

    }

    public void slow(View view) {
        myCustomCirclerArrowView.slowDown();
    }

}
