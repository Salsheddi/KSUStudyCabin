package com.example.ksustudycabin;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import androidx.annotation.Nullable;


public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "KSU studyCabin";

    // Table names
    private static final String STUDENT_TABLE = "Student";
    private static final String STUDY_ROOMS_TABLE = "study_rooms";
    private static final String RESERVATIONS_TABLE = "reservations";

    private static final String COLUMN_ID = "id";

    // students table columns
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    // StudyRooms table columns
    private static final String COLUMN_ROOM_NAME = "room_name";
    private static final String COLUMN_CAPACITY = "capacity";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_IS_AVAILABLE = "is_available";
    private static final String COLUMN_OPEN_TIME = "open_time";
    private static final String COLUMN_CLOSE_TIME = "close_time";

    private static final String COLUMN_AMENITIES = "amenities";
    private static final String COLUMN_PHOTO_PATH = "photo_path";

    // Reservations table columns
    private static final String COLUMN_STUDENT_EMAIL = "student_email";
    private static final String COLUMN_ROOM_ID = "room_id";
    private static final String COLUMN_START_TIME = "start_time";
    private static final String COLUMN_END_TIME = "end_time";
    private static final String COLUMN_MEMBER_EMAILS = "member_emails";

    public DBHandler (@Nullable Context context) {
        super(context, "KSU studyCabin", null, 1);
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
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
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

        // Insert study room data
        insertStudyRoom(db, "Room 1", 4, "Building A, Floor 1", "true", "08:00", "22:00", "Wi-Fi, Whiteboard", "path/to/photo1");
        insertStudyRoom(db, "Room 2", 8, "Building B, Floor 2", "true", "09:00", "21:00", "Wi-Fi", "path/to/photo2");
        insertStudyRoom(db, "Room 3", 6, "Building C, Floor 3", "true", "10:00", "20:00", "Wi-Fi, Projector", "path/to/photo3");

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

    public Cursor getAllStudyRoomsCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + STUDY_ROOMS_TABLE, null);
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
    public boolean addReservation(String studentEmail, int roomId, long startTime, long endTime, String memberEmails) {
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



}
