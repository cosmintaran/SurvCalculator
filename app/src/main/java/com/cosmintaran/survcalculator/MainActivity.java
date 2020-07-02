package com.cosmintaran.survcalculator;

import android.graphics.PixelFormat;
import android.os.Bundle;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cosmintaran.survcalculator.MapControl.view.MapSurfaceView;

public class MainActivity extends AppCompatActivity {

    private MapSurfaceView mapSurfaceView;
private DrawerLayout drawer;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed ( ) {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
        super.onBackPressed();
        }
    }

    @Override
    protected void onResume ( ) {
        super.onResume();
    }

    @Override
    protected void onPause ( ) {
        super.onPause();
    }
}

