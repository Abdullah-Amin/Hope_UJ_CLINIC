package com.example.hope_uj_clinic;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.hope_uj_clinic.Patient.DataBooking;
import com.example.hope_uj_clinic.Patient.DataClinics;
import com.example.hope_uj_clinic.Patient.ModelVitalSigns;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "last_hope.dp.db";
    private static final int DATABASE_VERSION = 1;
    // Table names
    public static final String TABLE_ADMIN = "admin";
    public static final String TABLE_USERS = "users";

    public static final String TABLE_BOOKING = "Booking";
    public static final String TABLE_DOCTOR = "Doctor";
    public static final String TABLE_CLINIC = "Clinic";
    public static final String TABLE_VITAL_SIGNS = "vital_signs";
    public static final String ORDERS = "orders";
//    public static final String YOURSELF_ORDERS = "yourself_orders";

    // Column names
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_USER_TYPE = "user_type";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_BRANCH = "branch";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_BIRTH = "birth";
    public static final String COLUMN_NATIONAL_ID = "national_id";
    public static final String COLUMN_USER_ID = "user_id";

    //Column names for others orders
    public static final String USER_ID = "user_id";
    public static final String NAME = "others_name";
    public static final String USER_NAME = "user_name";
    public static final String NOTE = "others_note";
    public static final String ORDER_STATUS = "order_status";
    private static final String PERSON_TYPE  = "person_type";
    public static final String LAT = "others_lat";
    public static final String LNG = "others_lng";

//    //Column names for yourself orders
//    public static final String YOURSELF_ID = "yourself_id";
//    public static final String YOURSELF_NOTE = "yourself_note";
//    public static final String YOURSELF_LAT = "yourself_lat";
//    public static final String YOURSELF_LNG = "yourself_lng";

    // New column names for the booking table
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DOCTOR = "doctor_name";
    public static final String COLUMN_CLINICS = "clinics";
    public static final String COLUMN_APPID = "appid";
    public static final String COLUMN_CLINIC_ID = "clinic_id";
    public static final String COLUMN_CLINIC_NAME = "clinic_name";
    public static final String COLUMN_DOCTOR_NAME = "doctor_name";
    public static final String COLUMN_DOCTOR_ID = "doctor_id";
    public static final String COLUMN_USER_NAME = "user_name";

    // New column names for the VITAL SIGNS table
    public static final String COLUMN_VITAL_ID = "id";

    public static final String COLUMN_VITAL_TYPE = "type";
    public static final String COLUMN_VITAL_RATE = "rate";
    public static final String COLUMN_VITAL_DATE = "date";
    public static final String COLUMN_VITAL_TIME = "time";
    public static final String COLUMN_VITAL_USER_NAME = "user_name";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;

        initializeDatabase();
    }

    private void initializeDatabase() {
        SQLiteDatabase db = getWritableDatabase();

        // Create the admin table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ADMIN + " ("
                + COLUMN_USER_ID + " TEXT PRIMARY KEY, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_USER_TYPE + " TEXT"
                + ")");

        // Create the users table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " ("
                + COLUMN_USER_ID + " TEXT PRIMARY KEY, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_PHONE + " TEXT, "
                + COLUMN_GENDER + " TEXT, "
                + COLUMN_AGE + " INTEGER, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_USER_TYPE + " TEXT, "
                + COLUMN_BRANCH + " TEXT, "
                + COLUMN_NATIONAL_ID + " TEXT, "
                + COLUMN_BIRTH + " TEXT, "
                + "CHECK (" + COLUMN_AGE + " >= 16)"
                + ")");

        // Create the booking table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_BOOKING + " ("
                + COLUMN_APPID + " TEXT PRIMARY KEY, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_DOCTOR + " TEXT, "
                + COLUMN_CLINICS + " TEXT, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_USER_ID + " TEXT, "
                + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "), "
                + "FOREIGN KEY (" + COLUMN_USER_NAME + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_NAME + "), "
                + "FOREIGN KEY (" + COLUMN_DOCTOR + ") REFERENCES " + TABLE_DOCTOR + "(" + COLUMN_DOCTOR_NAME + ")"
                + ")");

        // Create the doctor table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_DOCTOR + " ("
                + COLUMN_DOCTOR_ID + " TEXT PRIMARY KEY, "
                + COLUMN_DOCTOR_NAME + " TEXT, "
                + COLUMN_CLINICS + " TEXT"
                + ")");

        // Create the clinic table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CLINIC + " ("
                + COLUMN_CLINIC_ID + " TEXT PRIMARY KEY, "
                + COLUMN_CLINIC_NAME + " TEXT NOT NULL"
                + ")");


        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_VITAL_SIGNS + " ("
                + COLUMN_VITAL_ID + " TEXT PRIMARY KEY,"
                + COLUMN_VITAL_TYPE + " TEXT NOT NULL,"
                + COLUMN_VITAL_USER_NAME + " TEXT NOT NULL,"
                + COLUMN_VITAL_DATE + " TEXT NOT NULL,"
                + COLUMN_VITAL_TIME + " TEXT NOT NULL,"
                + COLUMN_VITAL_RATE + " TEXT NOT NULL,"
                + "FOREIGN KEY (" + COLUMN_VITAL_USER_NAME + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + ORDERS + " ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " TEXT ,"
                + USER_NAME + " TEXT ,"
                + USER_ID + " TEXT ,"
                + NOTE + " TEXT ,"
                + PERSON_TYPE + " TEXT ,"
                + ORDER_STATUS + " TEXT ,"
                + LAT + " REAL NOT NULL,"
                + LNG + " REAL NOT NULL"
                + ")");

//        db.close();
    }

    public void insertNewOrder(String name, String userId, String userName, String note, String personType,
                               String orderStatus,  double lat, double lng) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(USER_ID, userId);
        values.put(USER_NAME, userName);
        values.put(NOTE, note);
        values.put(PERSON_TYPE, personType);
        values.put(ORDER_STATUS, orderStatus);
        values.put(LAT, lat);
        values.put(LNG, lng);

        db.insert(ORDERS, null, values);

//        db.close();
    }

    public Cursor getOrders() {
        String query = "SELECT * FROM " + ORDERS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getNewOrdersForEmployee() {
        String query = "SELECT * FROM " + ORDERS + " WHERE " + ORDER_STATUS + " = " + "'In Progress'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getCompletedOrdersForEmployee() {
        String query = "SELECT * FROM " + ORDERS + " WHERE " + ORDER_STATUS + " = " + "'Completed'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getUser(String user) {
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE user_id = " + "'" + user + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        if (cursor != null){
            Log.i("abdo", "getUser: cursor not null " + cursor.getCount() + " " + user);
        }
//        while (cursor.()){
//            Log.i("abdo", "getUser: "+ cursor.getString(0));
//        }
        return cursor;
    }

    public void updateOrderWith(String orderId, String newOrderState){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ORDER_STATUS, newOrderState);

        Log.i("abdo", "updateOrderWith: " + orderId);

        String whereClause = "ID=?";
        String[] whereArgs = {String.valueOf(orderId)};


        try {
            int rowsUpdated = db.update(ORDERS, values, whereClause, whereArgs);

            if (rowsUpdated > 0) {
//                Toast.makeText(context, " > 0", Toast.LENGTH_SHORT).show();
            } else {
                // No rows were updated, check your condition
            }
        } catch (Throwable e){
            Log.i("abdo", "updateOrderWith: " + e.getLocalizedMessage());
        } finally{
            db.close(); // Close the database connection
        }
    }

//    public Cursor getYourselfOrders() {
//        String query = "SELECT * FROM " + YOURSELF_ORDERS;
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = null;
//        if (db != null) {
//            cursor = db.rawQuery(query, null);
//        }
//        return cursor;
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLINIC);
        db.execSQL("DROP TABLE IF EXISTS " + ORDERS);

        initializeDatabase();
    }

    // Getters for table and column names
    public String getTableAdmin() {
        return TABLE_ADMIN;
    }

    public String getTableUsers() {
        return TABLE_USERS;
    }

    public String getTableBooking() {
        return TABLE_BOOKING;
    }

    public String getTableDoctor() {
        return TABLE_DOCTOR;
    }

    public String getTableClinic() {
        return TABLE_CLINIC;
    }


    public ArrayList<DataBooking> getUpcomingBookings(String currentUserId, String currentDate) {
        ArrayList<DataBooking> upcomingBookings = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                COLUMN_DATE,
                COLUMN_TIME,
                COLUMN_DOCTOR,
                COLUMN_CLINICS,
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_APPID
        };
        String selection = COLUMN_USER_ID + " = ? AND " + COLUMN_DATE + " >= ?";
        String[] selectionArgs = {currentUserId, currentDate};
        Cursor cursor = db.query(TABLE_BOOKING, columns, selection, selectionArgs, null, null, null);

        try {
            int columnIndexDate = cursor.getColumnIndex(COLUMN_DATE);
            int columnIndexTime = cursor.getColumnIndex(COLUMN_TIME);
            int columnIndexDoctor = cursor.getColumnIndex(COLUMN_DOCTOR);
            int columnIndexClinics = cursor.getColumnIndex(COLUMN_CLINICS);
            int columnIndexUserId = cursor.getColumnIndex(COLUMN_USER_ID);
            int columnIndexAppId = cursor.getColumnIndex(COLUMN_APPID);
            int columnIndexName = cursor.getColumnIndex(COLUMN_USER_NAME);


            while (cursor.moveToNext()) {
                DataBooking booking = new DataBooking();

                if (columnIndexDate != -1) {
                    booking.setDate(cursor.getString(columnIndexDate));
                }

                if (columnIndexTime != -1) {
                    booking.setTime(cursor.getString(columnIndexTime));
                }

                if (columnIndexDoctor != -1) {
                    booking.setDoctor(cursor.getString(columnIndexDoctor));
                }

                if (columnIndexClinics != -1) {
                    booking.setClinics(cursor.getString(columnIndexClinics));
                }

                if (columnIndexUserId != -1) {
                    booking.setuser_id(cursor.getString(columnIndexUserId));
                }

                if (columnIndexAppId != -1) {
                    booking.setAppid(cursor.getString(columnIndexAppId));
                }
                if (columnIndexName != -1) {
                    booking.setName(cursor.getString(columnIndexName));
                }
                upcomingBookings.add(booking);
            }
        } finally {
            cursor.close();
            db.close();
        }

        return upcomingBookings;
    }

    public String getUserName(String userId) {
        SQLiteDatabase database = getReadableDatabase();

        // Define the columns to be queried
        String[] projection = {COLUMN_NAME};

        // Define the WHERE clause
        String selection = COLUMN_USER_ID + " = ?";

        // Define the selection arguments
        String[] selectionArgs = {userId};

        // Query the database
        Cursor cursor = database.query(TABLE_USERS, projection, selection, selectionArgs, null, null, null);

        String userName = null;
        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
        }

        // Close the cursor and the database
        cursor.close();
        database.close();

        return userName;
    }

    public ArrayList<DataBooking> getPreviousBookings(String userId, String currentDate) {
        ArrayList<DataBooking> previousBookings = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // Retrieve bookings with dates earlier than the current date
        String query = "SELECT * FROM " + TABLE_BOOKING +
                " WHERE " + COLUMN_USER_ID + " = ?" +
                " AND " + COLUMN_DATE + " < ?" +
                " ORDER BY " + COLUMN_DATE + " DESC";

        Cursor cursor = db.rawQuery(query, new String[]{userId, currentDate});

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
                String doctor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR));
                String clinics = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLINICS));
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
                String appid = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPID));
                // Create a DataBooking object and add it to the list
                DataBooking booking = new DataBooking();
                booking.setDate(date);
                booking.setTime(time);
                booking.setDoctor(doctor);
                booking.setClinics(clinics);
                booking.setName(userName);
                booking.setAppid(appid);
                previousBookings.add(booking);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return previousBookings;
    }


    public ArrayList<ModelVitalSigns> getAllVitalSigns(String type) {
        ArrayList<ModelVitalSigns> vitalSignsList = new ArrayList<>();

        // The table name in the database where the vital signs data is stored
        String tableName = TABLE_VITAL_SIGNS;

        // The columns to retrieve from the table
        String[] columns = {COLUMN_VITAL_TIME, COLUMN_VITAL_DATE, COLUMN_VITAL_RATE, COLUMN_VITAL_USER_NAME};

        // The selection criteria to filter the results based on the type
        String selection = "type = ?";

        // The selection arguments to replace the "?" placeholder in the selection criteria
        String[] selectionArgs = {type};

        // Perform the query to retrieve the vital signs data from the database
        Cursor cursor = getReadableDatabase().query(tableName, columns, selection, selectionArgs, null, null, null);

        // Iterate through the cursor and create ModelVitalSigns objects
        int columnIndexDate = cursor.getColumnIndex("date");
        int columnIndexTime = cursor.getColumnIndex("time");
        int columnIndexRate = cursor.getColumnIndex("rate");

        while (cursor.moveToNext()) {
            if (columnIndexDate != -1 && columnIndexTime != -1 && columnIndexRate != -1) {
                String date = cursor.getString(columnIndexDate);
                String time = cursor.getString(columnIndexTime);
                String rate = cursor.getString(columnIndexRate);

                ModelVitalSigns vitalSigns = new ModelVitalSigns();
                vitalSigns.setDate(date);
                vitalSigns.setTime(time);
                vitalSigns.setRate(rate);

                vitalSignsList.add(vitalSigns);
            }
        }
        // Close the cursor
        cursor.close();

        return vitalSignsList;
    }

    public long addVitalSigns(ModelVitalSigns vitalSigns, String userId, String type) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_VITAL_TYPE, type); // Add the "type" value
        values.put(COLUMN_VITAL_DATE, vitalSigns.getDate());
        values.put(COLUMN_VITAL_TIME, vitalSigns.getTime());
        values.put(COLUMN_VITAL_RATE, vitalSigns.getRate());
        values.put(COLUMN_VITAL_USER_NAME, userId); // Include the user ID

        long insertedId = db.insert(TABLE_VITAL_SIGNS, null, values);
        db.close();

        return insertedId;
    }

    public ArrayList<DataClinics> getAllClinics() {
        ArrayList<DataClinics> clinics = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_CLINIC, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Retrieve column indices based on column names
                    int columnIndexId = cursor.getColumnIndex(COLUMN_CLINIC_ID);
                    int columnIndexName = cursor.getColumnIndex(COLUMN_CLINIC_NAME);

                    // Retrieve data from the cursor using column indices
                    String clinicId = cursor.getString(columnIndexId);
                    String clinicName = cursor.getString(columnIndexName);

                    // Create a DataClinics object and add it to the list
                    DataClinics clinic = new DataClinics();
                    clinic.setClinicId(clinicId);
                    clinic.setName(clinicName);

                    clinics.add(clinic);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return clinics;
    }


    public ArrayList<DataBooking> getBookingsByClinic(String clinicName) {
        ArrayList<DataBooking> bookings = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_APPID,
                COLUMN_DATE,
                COLUMN_TIME,
                COLUMN_DOCTOR,
                COLUMN_CLINICS,
                COLUMN_USER_NAME
        };

        String selection = COLUMN_CLINICS + " = ?";
        String[] selectionArgs = {clinicName};

        Cursor cursor = db.query(
                TABLE_BOOKING,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                String appid = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPID));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
                String doctor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR));
                String clinics = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLINICS));
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));

                DataBooking booking = new DataBooking();
                booking.setAppid(appid);
                booking.setDate(date);
                booking.setTime(time);
                booking.setDoctor(doctor);
                booking.setClinics(clinics);
                booking.setName(userName);
                bookings.add(booking);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return bookings;
    }
}

