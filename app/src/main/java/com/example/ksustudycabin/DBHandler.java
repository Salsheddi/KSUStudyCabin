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

        insertStudyRoom(new StudyRoom(1, "Room 1", 4, "Building 6 ,Floor 1", "true", "08:00", "22:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));
        insertStudyRoom(new StudyRoom(2, "Room 6", 3, "Building 5 ,Floor 1", "true", "09:00", "21:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));
        insertStudyRoom(new StudyRoom(3, "Room 3", 4, "Building 6 ,Floor 2", "true", "10:00", "20:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));
        insertStudyRoom(new StudyRoom(4, "Room 22", 6, "Building 1 ,Floor 2", "true", "08:00", "22:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));
        insertStudyRoom(new StudyRoom(5, "Room 5", 4, "Building 3 ,Floor 2", "true", "09:00", "21:00", "wifi,Whiteboard,Projector", "@drawable/study_place"));

    }

    public Boolean insertStudyRoom(StudyRoom room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, room.getId());
        contentValues.put(COLUMN_ROOM_NAME, room.getRoomName());
        contentValues.put(COLUMN_CAPACITY, room.getCapacity());
        contentValues.put(COLUMN_LOCATION, room.getLocation());
        contentValues.put(COLUMN_IS_AVAILABLE, room.isAvailable());
        contentValues.put(COLUMN_OPEN_TIME, room.getOpenTime());
        contentValues.put(COLUMN_END_TIME, room.getCloseTime());
        long result = db.insert(STUDY_ROOMS_TABLE, null, contentValues);

        return result != -1;
    }


//    private void insertStudyRoom(SQLiteDatabase db, int id, String roomName, int capacity, String location, String openTime, String closeTime, String amenities, String photoPath) {
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_ID, id);
//        values.put(COLUMN_ROOM_NAME, roomName);
//        values.put(COLUMN_CAPACITY, capacity);
//        values.put(COLUMN_LOCATION, location);
//        values.put(COLUMN_IS_AVAILABLE, "true");
//        values.put(COLUMN_OPEN_TIME, openTime);
//        values.put(COLUMN_CLOSE_TIME, closeTime);
//        values.put(COLUMN_AMENITIES, amenities);
//        values.put(COLUMN_PHOTO_PATH, photoPath);
//
//
//
//        db.insert(STUDY_ROOMS_TABLE, null, values);
//    }


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

        return result != -1; // Return true if insert is successful
    }


    public List<Integer> getReservedRoomIdsForStudent(String studentEmail) {
        List<Integer> roomIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        String[] projection = {
                COLUMN_ROOM_ID
        };

        String selection = COLUMN_STUDENT_EMAIL + " = ?";
        String[] selectionArgs = {studentEmail};

        Cursor cursor = db.query(
                RESERVATIONS_TABLE,    // The table to query
                projection,            // The array of columns to return (null for all)
                selection,             // The columns for the WHERE clause
                selectionArgs,         // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null                   // The sort order
        );

        // Iterate through the cursor to retrieve room IDs
        while (cursor.moveToNext()) {
            int roomId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ROOM_ID));
            roomIds.add(roomId);
        }
        cursor.close();
        db.close();

        return roomIds;
    }


    @SuppressLint("Range")

    public StudyRoom fetchStudyRoom(int roomId) {
        StudyRoom studyRoom = null;
        SQLiteDatabase db = this.getReadableDatabase();

        if (db != null) {
            // Query the study rooms table to get the details of the study room with the specified ID
            Cursor cursor = db.rawQuery("SELECT * FROM " + STUDY_ROOMS_TABLE +
                    " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(roomId)});

            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String roomName = cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_NAME));
                int capacity = cursor.getInt(cursor.getColumnIndex(COLUMN_CAPACITY));
                String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
                String isAvailable = cursor.getString(cursor.getColumnIndex(COLUMN_IS_AVAILABLE));
                String openTime = cursor.getString(cursor.getColumnIndex(COLUMN_OPEN_TIME));
                String closeTime = cursor.getString(cursor.getColumnIndex(COLUMN_CLOSE_TIME));
                String amenities = cursor.getString(cursor.getColumnIndex(COLUMN_AMENITIES));
                String photoPath = cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO_PATH));

                // Create a new StudyRoom object with the retrieved details
                studyRoom = new StudyRoom(id, roomName, capacity, location, isAvailable, openTime, closeTime, amenities, photoPath);
            }
            cursor.close();
            db.close();
        }
        return studyRoom;
    }

    @SuppressLint("Range")
    public int getReservationId(String studentEmail, int roomId) {
        int reservationId = -1; // Default value if no reservation found

        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT " + COLUMN_RESERVATION_ID +
                            " FROM " + RESERVATIONS_TABLE +
                            " WHERE " + COLUMN_STUDENT_EMAIL + " = ?" +
                            " AND " + COLUMN_ROOM_ID + " = ?",
                    new String[]{studentEmail, String.valueOf(roomId)});

            if (cursor.moveToFirst()) {
                reservationId = cursor.getInt(cursor.getColumnIndex(COLUMN_RESERVATION_ID));
            }

            cursor.close();
            db.close();
        }

        return reservationId;
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
//        String email = null;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT " + COLUMN_EMAIL + " FROM " + STUDENT_TABLE, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
//            cursor.close();
//        }
        return sessionManager.getInstance(context).getUserEmail();
    }

    public List<StudyRoom> searchReservedStudyRoomsForUser() {
        List<StudyRoom> rooms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String userEmail = getUserEmail(); // Get the current user's email

        // SQL query to join the rooms and reservations on room ID where the student's email matches
        String query = "SELECT * FROM " + STUDY_ROOMS_TABLE +
                " INNER JOIN " + RESERVATIONS_TABLE +
                " ON " + STUDY_ROOMS_TABLE + "." + COLUMN_ID + " = " + RESERVATIONS_TABLE + "." + COLUMN_ROOM_ID +
                " WHERE " + RESERVATIONS_TABLE + "." + COLUMN_STUDENT_EMAIL + " = ?";

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
