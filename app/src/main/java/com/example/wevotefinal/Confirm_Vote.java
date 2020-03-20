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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import static com.example.wevotefinal.Vote.SELECTION;
import static com.example.wevotefinal.MainActivity.PASSWORD;

public class Confirm_Vote extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private int selection;
    private ImageView candidateImage;
    private String passwordCheck;
    private EditText password;
    public static String SHARED_PREFS = "sharedPrefs";
    public static final String SAVED_PASS = "savedPass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm__vote);

        password = findViewById(R.id.password);
        candidateImage = findViewById(R.id.candidateImage);

        Intent intent = getIntent();
        selection = intent.getIntExtra(SELECTION, 0);

        loadData();

        if(passwordCheck.isEmpty()) {
            passwordCheck = intent.getStringExtra(PASSWORD);
            saveData();
        }

        switch (selection){
            case 1:
                candidateImage.setImageResource(R.drawable.sam_hilton);
                break;
            case 2:
                candidateImage.setImageResource(R.drawable.amanda_brown);
                break;
            case 3:
                candidateImage.setImageResource(R.drawable.hillary_cliff);
                break;
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

    public void confirm (View view){
        String pwd = password.getText().toString();

        if(pwd.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please Enter Your Password",Toast.LENGTH_SHORT).show();
        }
        else if(!pwd.equals(passwordCheck)){
            Toast.makeText(getApplicationContext(),"Incorrect Password",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intentInfo = new Intent(Confirm_Vote.this, Thank_Vote.class);
            intentInfo.putExtra(SELECTION, selection);
            startActivity(intentInfo);
        }
    }

    public void cancel (View view){
        Intent intentInfo = new Intent(Confirm_Vote.this, Vote.class);
        startActivity(intentInfo);
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SAVED_PASS, passwordCheck);

        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        passwordCheck = sharedPreferences.getString(SAVED_PASS, "");
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