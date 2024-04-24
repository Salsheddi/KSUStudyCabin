package com.example.ksustudycabin;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.database.Cursor;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class    HomepageActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);


        // Define arrays or lists to store the data for each card
        int[] roomNumbers = {1,2,3,4,5};
        String[] locations = {"Building 6, Floor 1", "Building 5, Floor 2", "Building 6, Floor 3", "Building 1, Floor 2", "Building 3, Floor 2"};
        String[] capacities = {"4", "3", "4", "6", "4"};
        //String[] amenitiesList = {"Wi-Fi, Whiteboard, Projector", "Wi-Fi, Whiteboard, Projector", "Wi-Fi, Whiteboard, Projector", "Wi-Fi, Whiteboard, Projector", "Wi-Fi, Whiteboard, Projector"};

        int[] cardViewIds = {R.id.cardView1, R.id.cardView2, R.id.cardView3, R.id.cardView4, R.id.cardView5};

        for (int i = 0; i < cardViewIds.length; i++) {
            final int finalI = i;
            CardView cardView = findViewById(cardViewIds[i]);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the data from the arrays/lists based on the clicked card index
                    int roomNumber = roomNumbers[finalI];
                    String location = locations[finalI];
                    String capacity = capacities[finalI];
                   // String amenities = amenitiesList[finalI];

                    // Pass the data to the RoomActivity
                    Intent intent = new Intent(HomepageActivity.this, RoomActivity.class);
                    intent.putExtra("ROOM_NUMBER", roomNumber);
                    intent.putExtra("LOCATION", location);
                    intent.putExtra("CAPACITY", capacity);
                    //intent.putExtra("AMENITIES", amenities);
                    startActivity(intent);
                }
            });
        }




        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navHome) {
                    // Start Home Activity

                } else if (itemId == R.id.navRes) {
                    // Start Dashboard Activity
                    Intent dashboardIntent = new Intent(HomepageActivity.this, UserReservationActivity.class);
                    startActivity(dashboardIntent);
                } else if (itemId == R.id.navprofile) {
                    // Start Notifications Activity
                    Intent notificationsIntent = new Intent(HomepageActivity.this, profile.class);
                    startActivity(notificationsIntent);
                }
                return true;
            }
        });
    }

}