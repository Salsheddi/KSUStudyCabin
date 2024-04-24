package com.example.ksustudycabin;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import androidx.annotation.Nullable;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "KSU studyCabin";

    // Table names
    private static final String STUDENT_TABLE = "Student";
    private static final String STUDY_ROOMS_TABLE = "study_rooms";
    private static final String RESERVATIONS_TABLE = "reservations";


    // students table columns
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    // StudyRooms table columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ROOM_NAME = "room_name";
    private static final String COLUMN_CAPACITY = "capacity";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_IS_AVAILABLE = "is_available";
    private static final String COLUMN_OPEN_TIME = "open_time";
    private static final String COLUMN_CLOSE_TIME = "close_time";

    private static final String COLUMN_AMENITIES = "amenities";
    private static final String COLUMN_PHOTO_PATH = "photo_path";

    // Reservations table columns
    private static final String COLUMN_RESERVATION_ID = "id";
    private static final String COLUMN_STUDENT_EMAIL = "student_email";
    private static final String COLUMN_ROOM_ID = "room_id";
    private static final String COLUMN_START_TIME = "start_time";
    private static final String COLUMN_END_TIME = "end_time";
    private static final String COLUMN_MEMBER_EMAILS = "member_emails";

    private UserSessionManager sessionManager;
    private Context context;


    public static String getReservationsTable() {
        return RESERVATIONS_TABLE;
    }

    public static String getStudyRoomsTable() {
        return STUDY_ROOMS_TABLE;
    }

    public static String getColumnRoomName() {
        return COLUMN_ROOM_NAME;
    }

    public static String getColumnCapacity() {
        return COLUMN_CAPACITY;
    }

    public static String getColumnLocation() {
        return COLUMN_LOCATION;
    }

    public static String getColumnAmenities() {
        return COLUMN_AMENITIES;
    }


    public static String getColumnId() {
        return COLUMN_ID;
    }


    public static String getColumnRoomId() {
        return COLUMN_ROOM_ID;
    }

    public static String getColumnStartTime() {
        return COLUMN_START_TIME;
    }

    public static String getColumnEndTime() {
        return COLUMN_END_TIME;
    }

    public static String getColumnMemberEmails() {
        return COLUMN_MEMBER_EMAILS;
    }


    public DBHandler(@Nullable Context context) {
        super(context, "KSU studyCabin", null, 1);
        this.context = context;
        this.sessionManager = UserSessionManager.getInstance(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + STUDENT_TABLE + "("
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_EMAIL + " TEXT PRIMARY KEY"
                + ")";

        String CREATE_STUDY_ROOMS_TABLE = "CREATE TABLE " + STUDY_ROOMS_TABLE + "("
                + COLUMN_ID + " INTEGER,"
                + COLUMN_ROOM_NAME + " TEXT,"
                + COLUMN_CAPACITY + " INTEGER,"
                + COLUMN_LOCATION + " TEXT,"
                + COLUMN_IS_AVAILABLE + " TEXT,"
                + COLUMN_OPEN_TIME + " TEXT,"
                + COLUMN_CLOSE_TIME + " TEXT,"
                + COLUMN_AMENITIES + " TEXT,"
                + COLUMN_PHOTO_PATH + " TEXT"
                + ")";

        String CREATE_RESERVATIONS_TABLE = "CREATE TABLE " + RESERVATIONS_TABLE + "("
                + COLUMN_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_STUDENT_EMAIL + " TEXT,"
                + COLUMN_ROOM_ID + " INTEGER,"
                + COLUMN_START_TIME + " TEXT,"
                + COLUMN_END_TIME + " TEXT,"
                + COLUMN_MEMBER_EMAILS + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_STUDENT_EMAIL + ") REFERENCES " + STUDENT_TABLE + "(" + COLUMN_EMAIL + "),"
                + "FOREIGN KEY(" + COLUMN_ROOM_ID + ") REFERENCES " + STUDY_ROOMS_TABLE + "(" + COLUMN_ID + ")"
                + ")";

        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_STUDY_ROOMS_TABLE);
        db.execSQL(CREATE_RESERVATIONS_TABLE);

        insertStudyRoom(db, new StudyRoom(1, "Room 1", 4, "Building 6 ,Floor 1", "true", "08:00", "22:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));
        insertStudyRoom(db, new StudyRoom(2, "Room 6", 3, "Building 5 ,Floor 1", "true", "09:00", "21:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));
        insertStudyRoom(db, new StudyRoom(3, "Room 3", 4, "Building 6 ,Floor 2", "true", "10:00", "20:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));
        insertStudyRoom(db, new StudyRoom(4, "Room 22", 6, "Building 1 ,Floor 2", "true", "08:00", "22:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));
        insertStudyRoom(db, new StudyRoom(5, "Room 5", 4, "Building 3 ,Floor 2", "true", "09:00", "21:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));
    }

    public void insertStudyRoom(SQLiteDatabase db, StudyRoom room) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, room.getId());
        contentValues.put(COLUMN_ROOM_NAME, room.getRoomName());
        contentValues.put(COLUMN_CAPACITY, room.getCapacity());
        contentValues.put(COLUMN_LOCATION, room.getLocation());
        contentValues.put(COLUMN_IS_AVAILABLE, room.isAvailable());
        contentValues.put(COLUMN_OPEN_TIME, room.getOpenTime());
        contentValues.put(COLUMN_CLOSE_TIME, room.getCloseTime());
        contentValues.put(COLUMN_AMENITIES, room.getAmenities());
        contentValues.put(COLUMN_PHOTO_PATH, room.getPhotoPath());

        long result = db.insert(STUDY_ROOMS_TABLE, null, contentValues);
        if (result == -1) {
            Log.d("DBHandler", "Failed to insert Study Room: " + room.getRoomName());
        } else {
            Log.d("DBHandler", "Successfully inserted Study Room: " + room.getRoomName());
        }
    }


//shdan

    public boolean checkStudentExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_TABLE + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    //shdan
    public boolean insertStudent(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.insert(STUDENT_TABLE, null, contentValues);
        return result != -1; // Return true if insert is successful
    }

    //shdan
    // Method to check user credentials
    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_TABLE + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});
        int count = cursor.getCount();
        cursor.close();
        if (count == 1) {
            // Set user email when signing in
            sessionManager.getInstance(context).setUserEmail(email);
            return true;
        }
        return false; // Return true if exactly one row is found, indicating valid credentials
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STUDY_ROOMS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RESERVATIONS_TABLE);

        onCreate(db);
    }

    public boolean updatePassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);

        int rowsAffected = db.update(STUDENT_TABLE, values, COLUMN_EMAIL + " = ?", new String[]{email});
        db.close();

        return rowsAffected > 0;
    }

    public boolean addReservation(String studentEmail, int roomId, String startTime, String endTime, String memberEmails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_EMAIL, studentEmail);
        values.put(COLUMN_ROOM_ID, roomId);
        values.put(COLUMN_START_TIME, startTime);
        values.put(COLUMN_END_TIME, endTime);
        values.put(COLUMN_MEMBER_EMAILS, memberEmails);

        long result = db.insert(RESERVATIONS_TABLE, null, values);
        db.close();

        if (result == -1) {
            // Insertion failed
            Log.e("DBHelper", "Failed to add reservation");
            return false;
        } else {
            // Insertion successful
            Log.d("DBHelper", "Reservation added successfully");
            return true;
        }
    }

    public boolean deleteReservation(int reservationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = false;

        if (db != null) {
            int rowsDeleted = db.delete(RESERVATIONS_TABLE,
                    COLUMN_RESERVATION_ID + " = ?",
                    new String[]{String.valueOf(reservationId)});

            success = rowsDeleted > 0;
            db.close();
        }

        return success;
    }


    @SuppressLint("Range")
    public String getUserEmail() {

        return sessionManager.getInstance(context).getUserEmail();
    }

    public List<StudyRoom> searchReservedStudyRoomsForUser() {
        List<StudyRoom> rooms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String userEmail = getUserEmail(); // Get the current user's email
        Log.d("DBHandler", "User email: " + userEmail);

        // SQL query to join the rooms and reservations on room ID where the student's email matches
        String query = "SELECT * FROM " + STUDY_ROOMS_TABLE +
                " INNER JOIN " + RESERVATIONS_TABLE +
                " ON " + STUDY_ROOMS_TABLE + "." + COLUMN_ID + " = " + RESERVATIONS_TABLE + "." + COLUMN_ROOM_ID +
                " WHERE " + RESERVATIONS_TABLE + "." + COLUMN_STUDENT_EMAIL + " = ?";
        Log.d("DBHandler", "SQL Query: " + query);
        Cursor cursor = db.rawQuery(query, new String[]{userEmail});

        // Process the query results
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int roomNameIndex = cursor.getColumnIndex(COLUMN_ROOM_NAME);
            int capacityIndex = cursor.getColumnIndex(COLUMN_CAPACITY);
            int locationIndex = cursor.getColumnIndex(COLUMN_LOCATION);
            int isAvailableIndex = cursor.getColumnIndex(COLUMN_IS_AVAILABLE);
            int openTimeIndex = cursor.getColumnIndex(COLUMN_OPEN_TIME);
            int closeTimeIndex = cursor.getColumnIndex(COLUMN_CLOSE_TIME);
            int amenitiesIndex = cursor.getColumnIndex(COLUMN_AMENITIES);
            int photoPathIndex = cursor.getColumnIndex(COLUMN_PHOTO_PATH);

            do {
                int id = cursor.getInt(idIndex);
                String roomName = cursor.getString(roomNameIndex);
                int capacity = cursor.getInt(capacityIndex);
                String location = cursor.getString(locationIndex);
                String isAvailable = cursor.getString(isAvailableIndex);
                String openTime = cursor.getString(openTimeIndex);
                String closeTime = cursor.getString(closeTimeIndex);
                String amenities = cursor.getString(amenitiesIndex);
                String photoPath = cursor.getString(photoPathIndex);

                StudyRoom room = new StudyRoom(
                        id,
                        roomName,
                        capacity,
                        location,
                        isAvailable,
                        openTime,
                        closeTime,
                        amenities,
                        photoPath
                );
                rooms.add(room);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return rooms;
    }
    public List<StudyRoom> searchReservationsForStudent(String studentEmail, String roomName, String capacityText, String location, String amenities) {
        List<StudyRoom> matchingRooms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Base query
        String query = "SELECT " + STUDY_ROOMS_TABLE + ".* FROM " + STUDY_ROOMS_TABLE +
                " INNER JOIN " + RESERVATIONS_TABLE +
                " ON " + STUDY_ROOMS_TABLE + "." + COLUMN_ID + " = " + RESERVATIONS_TABLE + "." + COLUMN_ROOM_ID +
                " WHERE " + RESERVATIONS_TABLE + "." + COLUMN_STUDENT_EMAIL + " = ?";

        // List to hold the arguments for the prepared statement
        List<String> selectionArgs = new ArrayList<>();
        selectionArgs.add(studentEmail);

        // Try to parse capacity from capacityText, use null if parsing fails
        Integer capacity = null;
        try {
            capacity = Integer.parseInt(capacityText.trim());
        } catch (NumberFormatException e) {
            // Log error or handle it as needed if capacityText is not empty
            if (capacityText != null && !capacityText.isEmpty()) {
                Log.d("Search", "Capacity text is not a valid integer, ignoring capacity filter.");
            }
        }
        // Dynamic query based on provided search criteria
        if (roomName != null && !roomName.isEmpty()) {
            query += " AND " + STUDY_ROOMS_TABLE + "." + COLUMN_ROOM_NAME + " LIKE ?";
            selectionArgs.add("%" + roomName + "%");
        }
        if (capacity != null && capacity > 0) {
            query += " AND " + STUDY_ROOMS_TABLE + "." + COLUMN_CAPACITY + " = ?";
            selectionArgs.add(capacity.toString());
        }
        if (location != null && !location.isEmpty()) {
            query += " AND " + STUDY_ROOMS_TABLE + "." + COLUMN_LOCATION + " LIKE ?";
            selectionArgs.add("%" + location + "%");
        }
        if (amenities != null && !amenities.isEmpty()) {
            query += " AND " + STUDY_ROOMS_TABLE + "." + COLUMN_AMENITIES + " LIKE ?";
            selectionArgs.add("%" + amenities + "%");
        }

        Cursor cursor = db.rawQuery(query, selectionArgs.toArray(new String[0]));

        if (cursor != null) {
            // Getting column indices
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int roomNameIndex = cursor.getColumnIndex(COLUMN_ROOM_NAME);
            int capacityIndex = cursor.getColumnIndex(COLUMN_CAPACITY);
            int locationIndex = cursor.getColumnIndex(COLUMN_LOCATION);
            int isAvailableIndex = cursor.getColumnIndex(COLUMN_IS_AVAILABLE);
            int openTimeIndex = cursor.getColumnIndex(COLUMN_OPEN_TIME);
            int closeTimeIndex = cursor.getColumnIndex(COLUMN_CLOSE_TIME);
            int amenitiesIndex = cursor.getColumnIndex(COLUMN_AMENITIES);
            int photoPathIndex = cursor.getColumnIndex(COLUMN_PHOTO_PATH);

            // Check if any indices are -1
            if (idIndex == -1 || roomNameIndex == -1 || capacityIndex == -1 || locationIndex == -1 ||
                    isAvailableIndex == -1 || openTimeIndex == -1 || closeTimeIndex == -1 || amenitiesIndex == -1 ||
                    photoPathIndex == -1) {
                // Handle the error
                Log.e("DBHandler", "Column not found.");
            } else if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(idIndex);
                    String roomNameResult = cursor.getString(roomNameIndex);
                    int capacityResult = cursor.getInt(capacityIndex);
                    String locationResult = cursor.getString(locationIndex);
                    String isAvailable = cursor.getString(isAvailableIndex);
                    String openTime = cursor.getString(openTimeIndex);
                    String closeTime = cursor.getString(closeTimeIndex);
                    String amenitiesResult = cursor.getString(amenitiesIndex);
                    String photoPath = cursor.getString(photoPathIndex);

                    matchingRooms.add(new StudyRoom(id, roomNameResult, capacityResult, locationResult, isAvailable, openTime, closeTime, amenitiesResult, photoPath));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        db.close();
        return matchingRooms;
    }
    public boolean editReservation(int reservationId, String startTime, String endTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_START_TIME, startTime);
        values.put(COLUMN_END_TIME, endTime);

        int rowsAffected = db.update(RESERVATIONS_TABLE, values, COLUMN_RESERVATION_ID + " = ?", new String[]{String.valueOf(reservationId)});
        db.close();

        return rowsAffected > 0;
    }

}
