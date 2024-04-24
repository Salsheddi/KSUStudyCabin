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


    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);
        dbHandler = new DBHandler(this);
        insertStudyRooms();


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
    private void insertStudyRooms() {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        dbHandler.insertStudyRoom(db, new StudyRoom(1, "Room 1", 4, "Building 6 ,Floor 1", "true", "08:00", "22:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));
        dbHandler.insertStudyRoom(db, new StudyRoom(2, "Room 6", 3, "Building 5 ,Floor 1", "true", "09:00", "21:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));
        dbHandler.insertStudyRoom(db, new StudyRoom(3, "Room 3", 4, "Building 6 ,Floor 2", "true", "10:00", "20:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));
        dbHandler.insertStudyRoom(db, new StudyRoom(4, "Room 22", 6, "Building 1 ,Floor 2", "true", "08:00", "22:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));
        dbHandler.insertStudyRoom(db, new StudyRoom(5, "Room 5", 4, "Building 3 ,Floor 2", "true", "09:00", "21:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));

        // Close the database connection
        db.close();
    }

}