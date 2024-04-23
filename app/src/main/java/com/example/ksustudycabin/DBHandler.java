package com.example.ksustudycabin;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import androidx.annotation.Nullable;

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
    public static String getColumnStudentEmail() {
        return COLUMN_STUDENT_EMAIL;
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



    private UserSessionManager sessionManager;
    private Context context;

    public DBHandler (@Nullable Context context) {
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
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ROOM_NAME + " TEXT,"
                + COLUMN_CAPACITY + " INTEGER,"
                + COLUMN_LOCATION + " TEXT,"
                + COLUMN_IS_AVAILABLE + "TEXT,"
                + COLUMN_OPEN_TIME + "DATETIME,"
                + COLUMN_CLOSE_TIME + "DATETIME,"
                + COLUMN_AMENITIES + "TEXT,"
                + COLUMN_PHOTO_PATH + "TEXT"
                + ")";

        String CREATE_RESERVATIONS_TABLE = "CREATE TABLE " + RESERVATIONS_TABLE + "("
                + COLUMN_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_STUDENT_EMAIL + " TEXT,"
                + COLUMN_ROOM_ID + " INTEGER,"
                + COLUMN_START_TIME + " DATETIME,"
                + COLUMN_END_TIME + " DATETIME,"
                + COLUMN_MEMBER_EMAILS + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_STUDENT_EMAIL + ") REFERENCES " + STUDENT_TABLE + "(" + COLUMN_EMAIL + "),"
                + "FOREIGN KEY(" + COLUMN_ROOM_ID + ") REFERENCES " + STUDY_ROOMS_TABLE + "(" + COLUMN_ID + ")"
                + ")";

        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_STUDY_ROOMS_TABLE);
        db.execSQL(CREATE_RESERVATIONS_TABLE);

        insertStudyRoom(db, "Room 1", 4, "Building 6 ,Floor 1", "true", "08:00", "22:00", "wifi,Whiteboard,Projector", "@drawable/study_place");

        insertStudyRoom(db, "Room 6", 3, "Building 5 ,Floor 1", "true", "09:00", "21:00", "wifi,Whiteboard,Projector", "@drawable/study_place");

        insertStudyRoom(db, "Room 3", 4, "Building 6 ,Floor 2", "true", "10:00", "20:00", "wifi,Whiteboard,Projector", "@drawable/study_place");

        insertStudyRoom(db, "Room 22", 6, "Building 1 ,Floor 2", "true", "08:00", "22:00", "wifi,Whiteboard,Projector", "@drawable/study_place");

        insertStudyRoom(db, "Room 5", 4, "Building 3 ,Floor 2", "true", "09:00", "21:00", "wifi,Whiteboard,Projector", "@drawable/study_place");

    }



    private void insertStudyRoom(SQLiteDatabase db, String roomName, int capacity, String location, String isAvailable, String openTime, String closeTime, String amenities, String photoPath) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_NAME, roomName);
        values.put(COLUMN_CAPACITY, capacity);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_IS_AVAILABLE, isAvailable);
        values.put(COLUMN_OPEN_TIME, openTime);
        values.put(COLUMN_CLOSE_TIME, closeTime);
        values.put(COLUMN_AMENITIES, amenities);
        values.put(COLUMN_PHOTO_PATH, photoPath);



        db.insert(STUDY_ROOMS_TABLE, null, values);
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
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
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
    public boolean updateRoomAvailability(int roomId, boolean isAvailable) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_AVAILABLE, isAvailable ? "true" : "false");

        int rowsAffected = db.update(STUDY_ROOMS_TABLE, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(roomId)});

        return rowsAffected > 0;
    }

    public List<Integer> getReservedRoomIdsForStudent(String studentEmail) {
        List<Integer> roomIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        String[] projection = {
                COLUMN_ROOM_ID
        };

        String selection = COLUMN_STUDENT_EMAIL + " = ?";
        String[] selectionArgs = { studentEmail };

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

    public Cursor getStudyRoomById(int roomId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(STUDY_ROOMS_TABLE, null, COLUMN_ID + "=?",
                new String[]{String.valueOf(roomId)}, null, null, null);
        return cursor;
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
    public  StudyRoom fetchStudyRoom(int roomId) {
        StudyRoom studyRoom = null;
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null) {
            // Query the study rooms table to get the details of the study room with the specified ID
            Cursor cursor = db.rawQuery("SELECT * FROM " + STUDY_ROOMS_TABLE +
                            " WHERE " + COLUMN_ID + " = ?",
                    new String[]{String.valueOf(roomId)});
            if (cursor.moveToFirst()) {
                // Extract the study room details from the cursor
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String roomName = cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_NAME));
                @SuppressLint("Range") int capacity = cursor.getInt(cursor.getColumnIndex(COLUMN_CAPACITY));
                @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
                @SuppressLint("Range") boolean isAvailable = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_AVAILABLE)) == 1;
                @SuppressLint("Range") String openTime = cursor.getString(cursor.getColumnIndex(COLUMN_OPEN_TIME));
                @SuppressLint("Range") String closeTime = cursor.getString(cursor.getColumnIndex(COLUMN_CLOSE_TIME));
                @SuppressLint("Range") String amenities = cursor.getString(cursor.getColumnIndex(COLUMN_AMENITIES));
                @SuppressLint("Range") String photoPath = cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO_PATH));

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





}
