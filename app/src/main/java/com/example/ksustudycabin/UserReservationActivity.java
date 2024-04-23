package com.example.ksustudycabin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class UserReservationActivity extends AppCompatActivity {
    ImageButton delete , edit;
    Context context = getApplicationContext();
    DBHandler dbHelper = new DBHandler(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_reservation);

        delete = findViewById(R.id.deletebtn);
        edit = findViewById(R.id.editbtn);

        //generate cardviews for the rooms

        // Retrieve the student's email from the session

        String studentEmail = UserSessionManager.getInstance(context).getUserEmail();
        // Query the reservations table to get the list of reserved room IDs for the student



        List<Integer> roomIds = dbHelper.getReservedRoomIdsForStudent(studentEmail);
        // Dynamically generate card views for each reserved room

        // Dynamically generate card views for each reserved room
        for (Integer roomId : roomIds) {
            // Fetch study room details for each reserved room ID
            StudyRoom studyRoom = dbHelper.fetchStudyRoom(roomId);
            int reservationId = dbHelper.getReservationId(studentEmail, roomId);

            // Generate a card dynamically for each reserved study room
            generateCard(studyRoom, reservationId);
        }






        delete .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change the interface here
                Intent intent = new Intent(getApplicationContext(), deleteActivity.class);
                startActivity(intent);
            }
        });

        edit .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change the interface here
                Intent intent = new Intent(getApplicationContext(), UserReservationEditActivity.class);
                startActivity(intent);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navHome) {
                    Intent dashboardIntent = new Intent(UserReservationActivity.this, HomepageActivity.class);
                    startActivity(dashboardIntent);

                } else if (itemId == R.id.navRes) {
                    // Start Dashboard Activity

                } else if (itemId == R.id.navprofile) {
                    // Start Notifications Activity
                    Intent notificationsIntent = new Intent(UserReservationActivity.this, profile.class);
                    startActivity(notificationsIntent);
                }
                return true;
            }
        });
    }


        // Method to generate a card dynamically for each reserved study room
        private void generateCard(final StudyRoom studyRoom, final int reservationId) {
            // Inflate the card layout
            LayoutInflater inflater = LayoutInflater.from(this);
            View cardView = inflater.inflate(R.layout.reservation_cards, null);

            // Populate the card with study room details
            TextView roomNumberTextView = cardView.findViewById(R.id.roomNumber);
            roomNumberTextView.setText("Room " + studyRoom.getId());

            TextView locationTextView = cardView.findViewById(R.id.location);
            locationTextView.setText(studyRoom.getLocation());

            TextView capacityTextView = cardView.findViewById(R.id.capacity);
            capacityTextView.setText(String.valueOf(studyRoom.getCapacity()));

            TextView amenitiesTextView = cardView.findViewById(R.id.amenities);
            amenitiesTextView.setText(studyRoom.getAmenities());

            // Delete button
            ImageButton deleteBtn = cardView.findViewById(R.id.deletebtn);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Delete reservation associated with this card
                    dbHelper.deleteReservation(reservationId);

                    // Remove the card from the layout
                    ((ViewGroup) cardView.getParent()).removeView(cardView);
                }
            });

            // Add the card to the parent layout
            LinearLayout parentLayout = findViewById(R.id.parentLayout);
            parentLayout.addView(cardView);
        }

}