package com.example.ksustudycabin;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
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
    private static final String COLUMN_AMENITIES = "amenities";

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
                + COLUMN_EMAIL + " TEXT PRIMARY KEY,"
                + ")";

        String CREATE_STUDY_ROOMS_TABLE = "CREATE TABLE " + STUDY_ROOMS_TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ROOM_NAME + " TEXT,"
                + COLUMN_CAPACITY + " INTEGER,"
                + COLUMN_LOCATION + " TEXT,"
                + COLUMN_IS_AVAILABLE + "BOOLEAN,"
                + COLUMN_AMENITIES + "TEXT,"
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
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
