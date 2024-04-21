package com.example.ksustudycabin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.database.Cursor;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomepageActivity extends AppCompatActivity {

    CardView card_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

// Initialize the DBHandler
        DBHandler dbHandler = new DBHandler(this);

        LinearLayout cardContainer = findViewById(R.id.cardContainer);
// Fetch study room data as Cursor
        Cursor cursor = dbHandler.getAllStudyRoomsCursor();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract data from the Cursor
                int roomNumberIndex = cursor.getColumnIndex("COLUMN_ROOM_NAME");
                int locationIndex = cursor.getColumnIndex("COLUMN_LOCATION");
                int capacityIndex = cursor.getColumnIndex("COLUMN_CAPACITY");
                int isAvailableIndex  = cursor.getColumnIndex("COLUMN_IS_AVAILABLE");
                int amenitiesIndex = cursor.getColumnIndex("COLUMN_AMENITIES");

                String roomNumber = cursor.getString(roomNumberIndex);
                String location = cursor.getString(locationIndex);
                int capacity = cursor.getInt(capacityIndex);
                boolean isAvailable = cursor.getInt(isAvailableIndex) == 1;
                String amenities = cursor.getString(amenitiesIndex);

                if (isAvailable) {
                    // Inflate the card layout
                    View cardView = getLayoutInflater().inflate(R.layout.activity_homepage, cardContainer, false);

                    // Find views within the card layout
                    ImageView wifiImageView = cardView.findViewById(R.id.wifi);
                    ImageView headerImageView = cardView.findViewById(R.id.header_image);
                    TextView roomNumberTextView = cardView.findViewById(R.id.roomNumber);
                    TextView locationTextView = cardView.findViewById(R.id.location);
                    TextView capacityTextView = cardView.findViewById(R.id.capacity);
                    TextView wifiAvailabilityTextView = cardView.findViewById(R.id.wifiAvailability);

                    // Set data to views
                    wifiImageView.setImageResource(R.drawable.wifi);
                    // Set header image based on roomNumber or use a default image
                    headerImageView.setImageResource(R.drawable.study_place);
                    roomNumberTextView.setText(roomNumber);
                    locationTextView.setText(location);
                    capacityTextView.setText(String.valueOf(capacity));
                    wifiAvailabilityTextView.setText(amenities);

                    // Add the card to the container
                    cardContainer.addView(cardView);
                }
            } while (cursor.moveToNext());
        }

        // Close the cursor after use
        if (cursor != null) {
            cursor.close();
        }
        CardView card_view = (CardView) findViewById(R.id.cardView);
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomepageActivity.this, RoomActivity.class);
                startActivity(intent);
            }
        });

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