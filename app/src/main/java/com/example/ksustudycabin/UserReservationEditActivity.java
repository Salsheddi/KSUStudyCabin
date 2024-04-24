package com.example.ksustudycabin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class UserReservationEditActivity extends AppCompatActivity {
    ImageView room;
    TextView description , location , capacity, date ,duration;
    CalendarView calendarView;

    Button confirmbtn;
    ImageButton btn;
    Button reservebtn;

    Spinner spinner;

    String selectedDate ,endTime ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation_edit);

        Context context = getApplicationContext();
        DBHandler dbHelper = new DBHandler(context);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Week");
        arrayList.add("Month");
        arrayList.add("3 Months");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,                         android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,          Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        btn = findViewById(R.id.buttongoback);
        reservebtn = findViewById(R.id.reservebtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), UserReservationActivity.class);
                startActivity(intent1);
            }
        });

        Intent intent = getIntent();

        int reservationId = intent.getIntExtra("RESERVATION_ID", -1);
        // Create instances of StudyRoom to represent the study rooms

        // Retrieve the data from the Intent extras
        intent = getIntent();

        int roomId = intent.getIntExtra("ROOM_NUMBER", -1);
        Log.d("RoomActivity", "Received room number: " + roomId); // Log the room number

        String location = intent.getStringExtra("LOCATION");
        String capacity = intent.getStringExtra("CAPACITY");

        Log.d("RoomActivity", "Received room id: " + roomId);
        Log.d("RoomActivity", "Received location: " + location);
        Log.d("RoomActivity", "Received capacity: " + capacity);

        // Find the TextViews in the layout
        TextView locationTextView = findViewById(R.id.textView3);
        TextView capacityTextView = findViewById(R.id.textView4);

        // Set the retrieved data to the TextViews
        locationTextView.setText("Location: " + location);
        capacityTextView.setText("Capacity: " + capacity + " people");

        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Here, you get the selected date. You can use year, month, and dayOfMonth to do something with the selected date.
                selectedDate = year + "/" + (month + 1) + "/" + dayOfMonth;
            }
        });

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                endTime = selectedDate + selectedItem;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        reservebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.editReservation(reservationId,selectedDate,endTime);
                Intent intent = new Intent(getApplicationContext(), UserReservationActivity.class);
                startActivity(intent);
            }
        });


    }
}