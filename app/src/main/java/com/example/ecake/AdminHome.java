package com.example.ecake;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class AdminHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout adminDrawer;
    private NavigationView adminNavigation;
    private Toolbar adminToolBar;
    private FrameLayout adminFrameLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Assign layout variables
        adminDrawer = findViewById(R.id.admin_drawer_layout);
        adminNavigation = findViewById(R.id.admin_navigation_view);
        adminToolBar = findViewById(R.id.toolbar);
       toggle = new ActionBarDrawerToggle(this,adminDrawer, R.string.open, R.string.close);

        //
        adminToolBar.setTitle("Sprinkles Admin");
        setSupportActionBar(adminToolBar);
        adminDrawer.addDrawerListener(toggle);
       toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adminNavigation.setNavigationItemSelectedListener(this);

        // Create fragment objects

//        setFragment(new HomePage());
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.admin_drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    public void displaySelectedListner(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_home:
//                fragment = new HomePage();
                break;
            case R.id.nav_admin_account:
                fragment = new AdminProfile();
                break;
            case R.id.nav_admin_cake:
                fragment = new AddCake();
                break;
        }

        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        DrawerLayout drawerLayout = findViewById(R.id.admin_drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedListner(item.getItemId());
        return false;
    }
}