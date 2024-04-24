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
import java.util.ArrayList;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;
import android.widget.Button;
import android.widget.ArrayAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.ksustudycabin.DBHandler;
import com.example.ksustudycabin.StudyRoom;
import com.example.ksustudycabin.StudyRoomAdapter;
import com.example.ksustudycabin.UserSessionManager;
// ...any other custom classes you use

public class UserReservationActivity extends AppCompatActivity {
    ImageButton delete , edit;


    private EditText editTextSearch; // The search input field
    private ListView listViewReservations;
    private DBHandler dbHandler;
    private StudyRoomAdapter adapter;
    private List<StudyRoom> roomList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_reservation);
        Context context = getApplicationContext();
        DBHandler dbHelper = new DBHandler(context);

        dbHandler = new DBHandler(this);

        editTextSearch = findViewById(R.id.editTextSearch); // Replace with your actual EditText ID
        listViewReservations = findViewById(R.id.listViewReservations);
        delete = findViewById(R.id.deletebtn);
        edit = findViewById(R.id.editbtn);
        loadReservedRooms();
        editTextSearch = findViewById(R.id.editTextSearch); // Replace with your actual EditText ID
        listViewReservations = findViewById(R.id.listViewReservations);
        delete = findViewById(R.id.deletebtn);
        edit = findViewById(R.id.editbtn);
        loadReservedRooms();

        adapter = new StudyRoomAdapter(this, roomList);
        listViewReservations.setAdapter(adapter);

        Button searchButton = findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editTextSearch.getText().toString();

                List<StudyRoom> results = dbHandler.searchReservationsForStudent(getUserEmail(),searchText,searchText,searchText,searchText);

                adapter.clear();
                adapter.addAll(results);
                adapter.notifyDataSetChanged();
            }
        });
    }
    private String getUserEmail() {
        // Assuming UserSessionManager has a method called getUserEmail
        return UserSessionManager.getInstance(getApplicationContext()).getUserEmail();
    }
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
    private void loadReservedRooms() {
        List<StudyRoom> rooms =  dbHandler.searchReservedStudyRoomsForUser();
        StudyRoomAdapter adapter = new StudyRoomAdapter(this, rooms);
        ListView listView = findViewById(R.id.listViewReservations);
        listView.setAdapter(adapter);
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
                    Context context = getApplicationContext();
                    DBHandler dbHelper = new DBHandler(context);
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