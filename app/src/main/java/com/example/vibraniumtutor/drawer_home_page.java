package com.example.vibraniumtutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class drawer_home_page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    FirebaseUser currentuser;
    FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_home_page);

        mauth = FirebaseAuth.getInstance();
        currentuser = mauth.getCurrentUser();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragement_home()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.item_logout);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragement_home()).commit();
                break;
            case R.id.nav_find:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragement_findtutor()).commit();
                break;
            case R.id.nav_Rrequest:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragement_recievedrequest()).commit();
                break;
            case R.id.nav_Srequest:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragement_sentrequest()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragement_viewprofile()).commit();
                break;
            case R.id.nav_editprofile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragement_editprofile()).commit();
                break;
            case R.id.nav_btutor:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragement_btutor()).commit();
                break;
//            case R.id.nav_Logout:
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                finish();
//                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}