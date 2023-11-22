package com.example.hope_uj_clinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hope_uj_clinic.Admin.Admin;
import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.Employee.Activitys.HomeEmplyee;
import com.example.hope_uj_clinic.Patient.AccountUser;
import com.example.hope_uj_clinic.Patient.UserHome;
import com.example.hope_uj_clinic.databinding.ActivityLoginBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    DatabaseHelper dbHelper;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        dbHelper = new DatabaseHelper(this);

        binding.login.setOnClickListener(v -> {
            String user_id = binding.email.getText().toString();
            String password = binding.password.getText().toString();

            if (user_id.isEmpty()) {
                binding.email.setError("Email is empty");
                return;
            }
            if (password.isEmpty()) {
                binding.password.setError("Password is empty");
                return;
            }

            // Authenticate the user using SQLite
            boolean isAuthenticated = authenticateUser(user_id, password);
            if (isAuthenticated) {
                openUserTypeActivity(user_id);
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean authenticateUser(String user_id, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COLUMN_USER_TYPE};
        String selection = DatabaseHelper.COLUMN_USER_ID + "=? AND " + DatabaseHelper.COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {user_id, password};

        try (Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.getCount() > 0) {
                // User found in the "users" table
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Cursor cursor = db.query(DatabaseHelper.TABLE_ADMIN, columns, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.getCount() > 0) {
                // User found in the "admin" table
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void openUserTypeActivity(String user_id) {
        // Store the user ID in SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_id", user_id);
        editor.apply();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COLUMN_USER_TYPE};
        String selection = DatabaseHelper.COLUMN_USER_ID + "=?";
        String[] selectionArgs = {user_id};

        // Check if the user is in the "users" table
        try (Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int userTypeColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_TYPE);
                if (userTypeColumnIndex >= 0) {
                    String user_type = cursor.getString(userTypeColumnIndex);
                    Intent intent;
                    switch (user_type) {
                        case "user":
                            Intent userHomeIntent = new Intent(getApplicationContext(), UserHome.class);
                            userHomeIntent.putExtra("user_id", user_id);
                            startActivity(userHomeIntent);
                            return;

                        case "Employee":
                            // Replace Admin.class with the actual activity for employees
                            intent = new Intent(getApplicationContext(), HomeEmplyee.class);
                            startActivity(intent);
                            return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Check if the user is in the "admin" table
        try (Cursor cursor = db.query(DatabaseHelper.TABLE_ADMIN, columns, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int userTypeColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_TYPE);
                if (userTypeColumnIndex >= 0) {
                    String user_type = cursor.getString(userTypeColumnIndex);
                    if (user_type.equals("admin")) {
                        Intent intent = new Intent(getApplicationContext(), Admin.class);
                        startActivity(intent);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
    }
}