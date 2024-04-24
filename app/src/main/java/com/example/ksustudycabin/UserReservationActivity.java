package com.example.ksustudycabin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.ksustudycabin.StudyRoomAdapter;
public class UserReservationActivity extends AppCompatActivity {
    ImageButton delete , edit;

    private EditText editTextSearch; // The search input field
    private ListView listViewReservations;
    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_reservation);

        dbHandler = new DBHandler(this);

        editTextSearch = findViewById(R.id.editTextSearch); // Replace with your actual EditText ID
        listViewReservations = findViewById(R.id.listViewReservations);
        delete = findViewById(R.id.deletebtn);
        edit = findViewById(R.id.editbtn);
        loadReservedRooms();
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

}