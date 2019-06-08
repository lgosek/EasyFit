package com.example.easyfit.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyfit.R;
import com.example.easyfit.dataManager.DataManager;
import com.example.easyfit.fragments.GoalsFragment;
import com.example.easyfit.fragments.HistoryFragment;
import com.example.easyfit.fragments.HomeFragment;
import com.example.easyfit.fragments.NotificationsFragment;
import com.example.easyfit.fragments.ProductsFragment;
import com.example.easyfit.notifications.NotificationManager;

import java.sql.Time;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    NavigationView navigationView;
    private TextView usernameText;

    private final String sharedPreferencesFileName = "com.example.easyfit.sharedpreferences";
//    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataManager.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting up a notification manager
//        this.notificationManager = NotificationManager.getInstance();

        //setting up a toolbar instead of action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // nav drawer set up
        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        usernameText = navigationView.getHeaderView(0).findViewById(R.id.navigationDrawerUsername);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //!!!!!!!!!!!!!11
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.navHome);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        SharedPreferences sh = getSharedPreferences(this.sharedPreferencesFileName, MODE_PRIVATE);
        Set<String> set = sh.getStringSet("notificationTimes", null);

        if(set != null){
            for (String s:set) {
                NotificationManager.getInstance().add(Time.valueOf(s));
            }
        }
        String username = "";
        username = sh.getString("loggedInUsername", "username");
        usernameText.setText(username);

//        if(sh.getBoolean("edited", false)){
//            Toast.makeText(this, "changed", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sh = getSharedPreferences(this.sharedPreferencesFileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putStringSet("notificationTimes", NotificationManager.getInstance().getSet());
        editor.apply();



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.navHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
                break;
            case R.id.navProducts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProductsFragment()).commit();
                break;
            case R.id.navNotifications:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new NotificationsFragment()).commit();
                break;
            case R.id.navGoals:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new GoalsFragment()).commit();
                break;
            case R.id.navHistory:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HistoryFragment()).commit();
                break;
            case R.id.nav_logout:
//                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                logout();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void logout() {
        SharedPreferences sh = getSharedPreferences(this.sharedPreferencesFileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putBoolean("loggedIn", false);
        editor.apply();

        Intent intent = new Intent(this, LauncherActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
//            if(!navigationView.getCheckedItem().equals(R.id.navHome)){
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
//                navigationView.setCheckedItem(R.id.navHome);
//            }
//            else{
                super.onBackPressed();
//            }
        }
    }

//    public NotificationManager getNotificationManager(){
//        return this.notificationManager;
//    }

    public String getSharedPreferencesFileName(){
        return this.sharedPreferencesFileName;
    }
}
