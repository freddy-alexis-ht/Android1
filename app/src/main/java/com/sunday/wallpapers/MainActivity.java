package com.sunday.wallpapers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import com.sunday.wallpapers.ClientFragments.AcercaDeClient;
import com.sunday.wallpapers.ClientFragments.CompartirClient;
import com.sunday.wallpapers.ClientFragments.InicioClient;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // androidx.appcompat.widget.Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // icon colors by default are grey
        // null, so the icons have their real color
        navigationView.setItemIconTintList(null);

        // this: refers to this activity
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new InicioClient()).commit();
            navigationView.setCheckedItem(R.id.InicioClient);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.InicioClient:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InicioClient()).commit();
                break;
            case R.id.AcercaDeClient:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AcercaDeClient()).commit();
                break;
            case R.id.CompartirClient:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CompartirClient()).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}