package com.example.wevotefinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import static com.example.wevotefinal.MainActivity.PASSWORD;

public class Vote extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private int selection = 0;
    private String pwd;
    public static final String SELECTION = "SELECTION";
    public static String SHARED_PREFS = "sharedPrefs";
    public static final String SAVED_PASS = "savedPass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        loadData();

        if(pwd.isEmpty()){
            Intent pwdIntent = getIntent();
            pwd = pwdIntent.getStringExtra(PASSWORD);
            saveData();
        }

        {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            drawer = findViewById(R.id.drawer_layout);
            //*****
            NavigationView navigationView = findViewById(R.id.nav_view);//listener for navigation method
            navigationView.setNavigationItemSelectedListener(this);
            //*****

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }
    }

    public void selection1(View view){ selection = 1; }

    public void selection2(View view){ selection = 2; }

    public void selection3(View view){ selection = 3; }

    public void cancel (View view){
        Intent intent = new Intent(Vote.this, Home.class);
        startActivity(intent);
    }

    public void submit(View view){
        Intent intent = new Intent(Vote.this, Confirm_Vote.class);

        if(selection == 0){
            Toast.makeText(getApplicationContext(),"Please Select A Candidate",Toast.LENGTH_SHORT).show();
        }
        else{
            intent.putExtra(SELECTION, selection);
            intent.putExtra(PASSWORD, pwd);
            startActivity(intent);
        }
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SAVED_PASS, pwd);

        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        pwd = sharedPreferences.getString(SAVED_PASS, "");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent_home = new Intent(getApplicationContext(), Home.class);
                startActivity(intent_home);
                break;

            case R.id.nav_logout:
                Intent intent_logout = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_logout);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}
