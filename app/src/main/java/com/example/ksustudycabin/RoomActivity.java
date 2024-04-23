package com.example.ksustudycabin;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;

public class RoomActivity extends AppCompatActivity {
    ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activityroom);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn = findViewById(R.id.buttongoback);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Retrieve the data from the Intent extras
        Intent intent = getIntent();

        String location = intent.getStringExtra("LOCATION");
        String capacity = intent.getStringExtra("CAPACITY");

        // Find the TextViews in the layout
        TextView locationTextView = findViewById(R.id.textView3);
        TextView capacityTextView = findViewById(R.id.textView4);

        // Set the retrieved data to the TextViews
        locationTextView.setText("Location: " + location);
        capacityTextView.setText("Capacity: " + capacity + " people");

        Button reserveButton = findViewById(R.id.reservebtn);
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the data from the Intent extras
                Intent intent = getIntent();
                String roomNumber = intent.getStringExtra("ROOM_NUMBER");
                DBHandler dbHelper = new DBHandler(getApplicationContext());


                // Assuming you have obtained other necessary information like start time, end time, student email, and member emails

                // Retrieve the room ID from the STUDY_ROOMS_TABLE using the room number
                int roomId = getRoomIdFromRoomNumber(roomNumber);

                // Check if the room ID is valid
                if (roomId != -1) {
                    // inserting the reservation into the database
                    Context context = RoomActivity.this;
                    String userEmail = UserSessionManager.getInstance(context).getUserEmail();
                    // Define arrays for start and end times
                    String[] startTimes = {"9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00"};
                    String[] endTimes = {"10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00"};

// Retrieve the selected spinner item
                    Spinner spinner = findViewById(R.id.spinner);
                    String selectedInterval = spinner.getSelectedItem().toString();

// Find the index of the selected item in the spinner array
                    int index = Arrays.asList(getResources().getStringArray(R.array.my_spinner_items)).indexOf(selectedInterval);

// Get the corresponding start and end times
                    String startTime = startTimes[index];
                    String endTime = endTimes[index];

                    // Call the addReservation method
                    boolean success = dbHelper.addReservation(userEmail, roomId, startTime, endTime, null); // Assuming memberEmails is null for now


                    if (success) {
                        // Handle successful insertion
                        boolean updateSuccess = dbHelper.updateRoomAvailability(roomId, false);
                        Toast.makeText(getApplicationContext(), "Reservation added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle insertion failure
                        Toast.makeText(getApplicationContext(), "Failed to add reservation", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle invalid room number (room not found in the database)
                    Toast.makeText(getApplicationContext(), "Invalid room number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to get room ID from room number
    private int getRoomIdFromRoomNumber(String roomNumber) {
        DBHandler dbHelper = new DBHandler(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int roomId = -1; // Default value indicating invalid room number

        // Execute the raw query and directly return the result
        Cursor cursor = db.rawQuery("SELECT " + DBHandler.getColumnId() + " FROM " + DBHandler.getStudyRoomsTable() + " WHERE " + DBHandler.getColumnRoomName() + "=?", new String[]{roomNumber});

        // Check if the cursor has any rows
        if (cursor.moveToFirst()) {
            roomId = cursor.getInt(0); // Retrieve the value from the first column (index 0)
        }

        cursor.close();
        db.close();
        return roomId;
    }
}
