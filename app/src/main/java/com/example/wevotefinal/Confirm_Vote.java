package com.example.wevotefinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import static com.example.wevotefinal.Vote.SELECTION;

public class Confirm_Vote extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    public static String CANDIDATE;
    private int selection;
    private ImageView candidateImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm__vote);

        Intent intent = getIntent();
        selection = intent.getIntExtra(SELECTION, 0 );
        candidateImage = findViewById(R.id.candidateImage);

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
        Intent intentInfo = new Intent(Confirm_Vote.this, Thank_Vote.class);
        intentInfo.putExtra(CANDIDATE, selection);
        startActivity(intentInfo);
    }

    public void cancel (View view){
        Intent intentInfo = new Intent(Confirm_Vote.this, Vote.class);
        startActivity(intentInfo);
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