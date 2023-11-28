package com.example.hope_uj_clinic.Patient;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.hope_uj_clinic.Admin.Admin;
import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityUserHomeBinding;
import com.example.hope_uj_clinic.my_flow.EmergencyActivity;

public class UserHome extends AppCompatActivity {
    private SQLiteDatabase database;
    String userId;
    String name;
    String branch;
    String idNationality;
    String birth;
    ActivityUserHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_home);


        binding.constraintLayout.setOnClickListener(view -> {
            Intent intent = new Intent(this, EmergencyActivity.class);
            String name = binding.name.getText().toString();
            intent.putExtra("userType", "patient");
            intent.putExtra("userName", userId);
            startActivity(intent);
        });

        binding.Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountUserIntent = new Intent(getApplicationContext(), AccountUser.class);
                accountUserIntent.putExtra("user_id", userId);
                startActivity(accountUserIntent);
            }
        });

        binding.calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, Appointments.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
            }
        });

        binding.viscalhostriy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this,Medicalhistory.class);
                startActivity(intent);
            }
        });

        binding.viscal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserHome.this,VitalSigns.class);
                startActivity(intent);
            }
        });

        binding.Elalctronec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user information
                String[] columns = {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_BRANCH, DatabaseHelper.COLUMN_NATIONAL_ID, DatabaseHelper.COLUMN_BIRTH};
                String selection = DatabaseHelper.COLUMN_USER_ID + " = ?";
                String[] selectionArgs = {userId};

                Cursor cursor = database.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

                if (cursor.moveToFirst()) {
                    int nameColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
                    int branchColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_BRANCH);
                    int nationalityColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NATIONAL_ID);
                    int birthColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_BIRTH);

                    if (nameColumnIndex >= 0 && branchColumnIndex >= 0 && nationalityColumnIndex >= 0 && birthColumnIndex >= 0) {
                        name = cursor.getString(nameColumnIndex);
                        branch = cursor.getString(branchColumnIndex);
                        idNationality = cursor.getString(nationalityColumnIndex);
                        birth = cursor.getString(birthColumnIndex);

                        // Create and show the dialog
                        final Dialog dialog = new Dialog(UserHome.this, R.style.WideDialog);
                        dialog.setContentView(R.layout.item_card);
                        ColorDrawable dialogColor = new ColorDrawable(Color.TRANSPARENT);
                        dialogColor.setAlpha(50);
                        dialog.getWindow().setBackgroundDrawable(dialogColor);
                        TextView names = dialog.findViewById(R.id.nationality);
                        TextView id = dialog.findViewById(R.id.gender);
                        TextView dataOfBirth = dialog.findViewById(R.id.dataofbrith);
                        TextView age = dialog.findViewById(R.id.age);
                        TextView mrn = dialog.findViewById(R.id.mrn);
                        names.setText("name :  " + name);
                        id.setText("Nationality number : " + idNationality);
                        dataOfBirth.setText("Birth of day : " + birth);
                        age.setText("Branch : " + branch);
                        mrn.setText("MRN - " + userId.substring(0, 7));
                        dialog.show();
                    } else {
                        // Handle the case where the desired columns are not found
                        Log.e("Cursor Error", "Desired columns not found in the cursor");
                        // or show an error message to the user
                        // or take any other appropriate action
                    }
                }
                cursor.close();
            }
        });
        binding.contextus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, Electronic_Card.class);
                startActivity(intent);
            }
        });

        // Initialize the database
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getReadableDatabase();

        // Fetch user information from the database and set it in the UI
        fetchUserInfo();
    }

    private void fetchUserInfo() {
        // Retrieve the user_id from the intent
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra("user_id");
        }

        String[] columns = {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_USER_ID};
        String selection = DatabaseHelper.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {userId};

        Cursor cursor = database.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
            int userIdColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID);

            if (nameColumnIndex >= 0 && userIdColumnIndex >= 0) {
                name = cursor.getString(nameColumnIndex);
                userId = cursor.getString(userIdColumnIndex);

                // Set the retrieved values in the UI
                binding.name.setText(name);
                binding.userId.setText("MRN - " + userId.substring(0, 7));
            } else {
                // Handle the case where the desired columns are not found
                Log.e("Cursor Error", "Desired columns not found in the cursor");
                // or show an error message to the user
                // or take any other appropriate action
            }
        }
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}