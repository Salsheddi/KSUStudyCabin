package com.example.ksustudycabin;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.ksustudycabin.StudyRoom;

import java.util.List;
public class StudyRoomAdapter extends ArrayAdapter<StudyRoom> {
    private Context mContext;
    private List<StudyRoom> studyRoomList;
    private DBHandler dbHandler;

    public StudyRoomAdapter(@NonNull Context context, @NonNull List<StudyRoom> objects) {
        super(context, 0, objects);
        mContext = context;
        studyRoomList = objects;
        dbHandler = new DBHandler(context);
        Log.d("StudyRoomAdapter", "studyRoomList size: " + studyRoomList.size());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.reservation_cards, parent, false);
        }

        StudyRoom room = studyRoomList.get(position);

        ImageView wifiImageView = convertView.findViewById(R.id.wifi);
        ImageView headerImageView = convertView.findViewById(R.id.header_image);
        TextView roomNumberTextView = convertView.findViewById(R.id.roomNumber);
        TextView locationTextView = convertView.findViewById(R.id.location);
        //ImageView capacityImageView = convertView.findViewById(R.id.user);
        TextView capacityTextView = convertView.findViewById(R.id.capacity);
        TextView amenitiesTextView = convertView.findViewById(R.id.amenities);
        ImageButton deleteButton = convertView.findViewById(R.id.deletebtn);
        ImageButton editButton = convertView.findViewById(R.id.editbtn);

        // Assuming StudyRoom class has getters for these properties
        roomNumberTextView.setText(room.getRoomName());
        locationTextView.setText(room.getLocation());
        capacityTextView.setText(String.valueOf(room.getCapacity()));
        amenitiesTextView.setText(room.getAmenities());

        // Assuming there is a WiFi amenity, you can set the visibility based on whether the room has it or not
       // wifiImageView.setVisibility(room.hasWifi() ? View.VISIBLE : View.GONE);


        // For drawable resource you could do something like this:
        headerImageView.setImageResource(R.drawable.study_place);

        // Set click listeners for the buttons if needed
        deleteButton.setOnClickListener(v -> {
            // Retrieve the corresponding StudyRoom object
            StudyRoom roomToDelete = studyRoomList.get(position);

            // Perform deletion operation (e.g., delete reservation from database)
            // Assuming you have a method in your DBHandler to delete a reservation

            boolean isDeleted = dbHandler.deleteReservation(roomToDelete.getId());

            // Check if deletion was successful
            if (isDeleted) {
                // Remove the deleted reservation from the studyRoomList
                studyRoomList.remove(position);

                // Notify the adapter that the dataset has changed
                notifyDataSetChanged();

                // Optionally, show a toast or perform any other UI update
                Toast.makeText(mContext, "Reservation deleted", Toast.LENGTH_SHORT).show();
            } else {
                // Handle deletion failure
                Toast.makeText(mContext, "Failed to delete reservation", Toast.LENGTH_SHORT).show();
            }
        });


        editButton.setOnClickListener(v -> {
            // Handle edit action
        });



        return convertView;
    }
}
