package com.example.hope_uj_clinic.Patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.R;

public class AccountUser extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private TextView mrnTextView;
    private TextView nationalityTextView;
    private TextView userIdTextView;
    private TextView genderTextView;
    private TextView dateOfBirthTextView;
    private TextView ageTextView;
    private TextView phoneTextView;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_user);

        databaseHelper = new DatabaseHelper(this);
        mrnTextView = findViewById(R.id.mrn);
        nationalityTextView = findViewById(R.id.nationality);
        userIdTextView = findViewById(R.id.userId);
        genderTextView = findViewById(R.id.gender);
        dateOfBirthTextView = findViewById(R.id.dataofbrith);
        ageTextView = findViewById(R.id.age);
        phoneTextView = findViewById(R.id.phone);
    }

    @Override
    protected void onStart() {
        super.onStart();
        retrieveAccountInformation();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updatePhoneNumber();
    }

    private void retrieveAccountInformation() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_USER_ID,
                DatabaseHelper.COLUMN_NATIONAL_ID,
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_GENDER,
                DatabaseHelper.COLUMN_BIRTH,
                DatabaseHelper.COLUMN_AGE,
                DatabaseHelper.COLUMN_PHONE
        };
        String selection = DatabaseHelper.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {getCurrentUserId()};
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            String userId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID));
            String nationality = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NATIONAL_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENDER));
            String dateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BIRTH));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AGE));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE));

            mrnTextView.setText("MRN - " + userId.substring(0, 7));
            nationalityTextView.setText("Nationality number: " + nationality);
            userIdTextView.setText("Name: " + name);
            genderTextView.setText("Gender: " + gender);
            dateOfBirthTextView.setText("Date of Birth: " + dateOfBirth);
            ageTextView.setText("Age: " + age);
            phoneTextView.setHint("Phone: " + phone);
        }
        cursor.close();
        db.close();
    }

    private String getCurrentUserId() {
        return getIntent().getStringExtra("user_id");
    }

    private void updatePhoneNumber() {
        String newPhoneNumber = phoneTextView.getText().toString();
        if (!newPhoneNumber.isEmpty()) {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_PHONE, newPhoneNumber);

            String selection = DatabaseHelper.COLUMN_USER_ID + " = ?";
            String[] selectionArgs = {getCurrentUserId()};

            db.update(DatabaseHelper.TABLE_USERS, values, selection, selectionArgs);
            db.close();
        }
    }
}