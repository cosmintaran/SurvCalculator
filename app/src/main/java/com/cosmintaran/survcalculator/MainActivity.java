package com.cosmintaran.survcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.MotionEvent;

import com.cosmintaran.survcalculator.Map.opengl.Map;

public class MainActivity extends AppCompatActivity {

    private Map map;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = findViewById(R.id.map);
        getWindow().setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public boolean onTouchEvent ( MotionEvent event ) {
        return super.onTouchEvent(event);

    }
}