package com.sunday.wallpapers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sunday.wallpapers.AdminFragments.InicioAdmin;
import com.sunday.wallpapers.AdminFragments.ListarAdmin;
import com.sunday.wallpapers.AdminFragments.PerfilAdmin;
import com.sunday.wallpapers.AdminFragments.RegistrarAdmin;

public class MainActivityAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout; // declaration
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        // androidx.appcompat.widget.Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_admin);

        NavigationView navigationView = findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);

        // icon colors by default are grey
        // null, so the icons have their real color
        navigationView.setItemIconTintList(null);

        // this: refers to this activity
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Initialization
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                    new InicioAdmin()).commit();
            navigationView.setCheckedItem(R.id.InicioAdmin);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.InicioAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new InicioAdmin()).commit();
                break;
            case R.id.PerfilAdmin:
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new PerfilAdmin()).commit();
                break;
            case R.id.RegistrarAdmin:
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new RegistrarAdmin()).commit();
                break;
            case R.id.ListarAdmin:
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new ListarAdmin()).commit();
                break;
            case R.id.SalirAdmin:
            Toast.makeText(this, "Cerraste sesión", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sessionStartedCheck() {
        if(firebaseUser != null) {
            // if admin started session
            Toast.makeText(this, "Se ha iniciado sesión", Toast.LENGTH_SHORT).show();
        } else {
            // if session wasn't started, then it's a client
            startActivity(new Intent(MainActivityAdmin.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        sessionStartedCheck();
        super.onStart();
    }
}