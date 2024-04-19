package com.example.ksustudycabin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profile extends AppCompatActivity {
    ImageButton setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navHome) {
                    Intent dashboardIntent = new Intent(profile.this, HomepageActivity.class);
                    startActivity(dashboardIntent);

                } else if (itemId == R.id.navRes) {
                    // Start Dashboard Activity
                    Intent dashboardIntent = new Intent(profile.this, UserReservationActivity.class);
                    startActivity(dashboardIntent);
                } else if (itemId == R.id.navprofile) {

                }
                return true;
            }
        });
        setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change the interface here
                Intent intent = new Intent(profile.this, EditProfilePage.class);
                startActivity(intent);
            }
        });
    }
}