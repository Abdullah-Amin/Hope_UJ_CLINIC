package com.example.hope_uj_clinic.Employee.Activitys;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityAccountUserByEmployeeBinding;

public class AccountUserByEmployee extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ActivityAccountUserByEmployeeBinding binding;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_user_by_employee);
        key = getIntent().getStringExtra("user_id");
        databaseHelper = new DatabaseHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        if (key != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_USERS + " WHERE " + DatabaseHelper.COLUMN_USER_ID + " = ?", new String[]{key});
            if (cursor.moveToFirst()) {
                String userid = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID));
                String nationality = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NATIONAL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
                String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENDER));
                String birth = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BIRTH));
                String age = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AGE));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE));

                binding.mrn.setText("MRN - " + userid.substring(0, 7));
                binding.nationality.setText("Nationality number :     \t " + nationality);
                binding.userId.setText("Name :  " + name);
                binding.gender.setText("Gender : \t                          " + gender);
                binding.dataofbrith.setText("Brith : \t                        " + birth);
                binding.age.setText("Age : \t                                  " + age);
                binding.phone.setText("Phone : \t                    " + phone);
            }

            cursor.close();
        }
            db.close();
        }
    }
