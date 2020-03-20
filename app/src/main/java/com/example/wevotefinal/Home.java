package com.example.wevotefinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import static com.example.wevotefinal.Vote.SELECTION;
import static com.example.wevotefinal.MainActivity.PASSWORD;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    public int selection;
    private ImageView candidateImage;
    private TextView candidateText;
    public static String SHARED_PREFS = "sharedPrefs";
    public static final String VOTED = "VOTED";
    public static final String SAVED_PASS = "savedPass";
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        candidateImage = findViewById(R.id.voted_for_image);
        candidateText = findViewById(R.id.voted_for_text);

        loadData();

        if(selection == 0) {
            Intent voteIntent = getIntent();
            selection = voteIntent.getIntExtra(SELECTION, 0);
            saveData();
        }

        if(pwd.isEmpty()){
            Intent pwdIntent = getIntent();
            pwd = pwdIntent.getStringExtra(PASSWORD);
            saveData();
        }

        switch (selection){
            case 0:
                break;
            case 1:
                candidateImage.setImageResource(R.drawable.sam_hilton);
                candidateImage.setBackground(ContextCompat.getDrawable(this, R.drawable.image_view_round_left));
                candidateText.setText("You Voted For:\nSam Hilton");
                candidateText.setBackground(ContextCompat.getDrawable(this, R.drawable.text_view_round_right));
                break;
            case 2:
                candidateImage.setImageResource(R.drawable.amanda_brown);
                candidateImage.setBackground(ContextCompat.getDrawable(this, R.drawable.image_view_round_left));
                candidateText.setText("You Voted For:\nAmanda Brown");
                candidateText.setBackground(ContextCompat.getDrawable(this, R.drawable.text_view_round_right));
                break;
            case 3:
                candidateImage.setImageResource(R.drawable.hillary_cliff);
                candidateImage.setBackground(ContextCompat.getDrawable(this, R.drawable.image_view_round_left));
                candidateText.setText("You Voted For:\nHillary Cliff");
                candidateText.setBackground(ContextCompat.getDrawable(this, R.drawable.text_view_round_right));
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

            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    public void viewInfo (View view){
        Intent intentInfo = new Intent(Home.this, General_Info.class);
        startActivity(intentInfo);
    }

    public void viewForums (View view){
        Intent intentForums = new Intent(Home.this, Forum.class);
        startActivity(intentForums);
    }

    public void viewCandidates (View view){
        Intent intentCandidates = new Intent(Home.this, Show_Candidates.class);
        startActivity(intentCandidates);
    }

    public void startVote (View view){
        if(selection == 0) {
            Intent intentVote = new Intent(Home.this, Start_Vote.class);
            intentVote.putExtra(PASSWORD, pwd);
            startActivity(intentVote);
        }
        else{
            Toast.makeText(getApplicationContext(),"You Have Already Voted",Toast.LENGTH_SHORT).show();
        }
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(VOTED, selection);
        editor.putString(SAVED_PASS, pwd);

        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        selection = sharedPreferences.getInt(VOTED, 0);
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
