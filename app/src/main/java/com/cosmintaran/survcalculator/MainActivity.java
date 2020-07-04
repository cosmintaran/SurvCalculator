package com.cosmintaran.survcalculator;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cosmintaran.survcalculator.MapControl.view.DistanceFragment;
import com.cosmintaran.survcalculator.MapControl.view.MapSurfaceView;
import com.cosmintaran.survcalculator.MapControl.view.PointFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawer , toolbar ,
                R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment_container , new PointFragment()).commit();

            navigationView.setCheckedItem(R.id.nav_points);
        }
    }

    @Override
    public void onBackPressed ( ) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

    @Override
    public boolean onNavigationItemSelected ( @NonNull MenuItem menuItem ) {

        switch (menuItem.getItemId()) {
            case R.id.nav_points:
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container , new PointFragment()).commit();

                break;
            case R.id.nav_distance:
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container , new DistanceFragment()
                ).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START , true);
        return true;
    }
}

