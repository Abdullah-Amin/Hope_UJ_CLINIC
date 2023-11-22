package com.example.hope_uj_clinic.Admin;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.Login;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityAdminBinding;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Admin extends AppCompatActivity {
    ArrayList<String> listgender = new ArrayList<>();
    ActivityAdminBinding binding;
    String gender;
    Calendar myCalendar;
    int age;
    SQLiteDatabase database;
    ArrayList<String> types = new ArrayList<>();
    ArrayList<String> Braunch = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin);

        // Create or open the SQLite database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        types.add("user");
        types.add("Employee");

        Braunch.add("فرع جده");
        Braunch.add("الفرع الرئيسي");
        Braunch.add("فرع الفيصله");
        Braunch.add("فرع الخليص");
        Braunch.add("فرع الكامل");
        Braunch.add("فرع السلامه");
        Braunch.add("فرع الشرقيه");
        Braunch.add("فرع الرحاب");

        listgender.add("male");
        listgender.add("female");

        ArrayAdapter<String> ad1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listgender);
        ad1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.edtGender.setAdapter(ad1);

        ArrayAdapter<String> type = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.type.setAdapter(type);

        ArrayAdapter<String> branch = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Braunch);
        branch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.Branch.setAdapter(branch);

        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        binding.edtBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Admin.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.edtGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = binding.edtGender.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.edtName.getText().toString().trim();
                String phone = binding.edtPhone.getText().toString().trim();
                String age = binding.edtAge.getText().toString().trim();
                String password = binding.edtPassword.getText().toString();
                String userid = binding.edtID.getText().toString().trim();
                String national_id = binding.edtNationality.getText().toString().trim();
                String birth = binding.edtBirth.getText().toString().trim();
                String userType = binding.type.getSelectedItem().toString();

                if (userid.length() < 17) {
                    binding.edtID.setError("Email is incorrect");
                    return;
                }
                if (name.isEmpty()) {
                    binding.edtName.setError("Name is empty");
                    return;
                }
                if (phone.isEmpty()) {
                    binding.edtPhone.setError("Phone is empty");
                    return;
                }
                if (age.isEmpty() || age.length() == 0) {
                    binding.edtAge.setError("Age is empty");
                    return;
                }
                if (password.isEmpty() || password.length() < 6) {
                    binding.edtPassword.setError("Password is empty or too short");
                    return;
                }
                if (userid.isEmpty()) {
                    binding.edtID.setError("Email is empty");
                    return;
                }
                if (national_id.isEmpty()) {
                    binding.edtNationality.setError("National ID is empty");
                    return;
                }
                if (birth.isEmpty()) {
                    binding.edtBirth.setError("Birth date is empty");
                    return;
                }

                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.COLUMN_NAME, name);
                contentValues.put(DatabaseHelper.COLUMN_PHONE, phone);
                contentValues.put(DatabaseHelper.COLUMN_GENDER, gender);
                contentValues.put(DatabaseHelper.COLUMN_AGE, age);
                contentValues.put(DatabaseHelper.COLUMN_PASSWORD, password);
                contentValues.put(DatabaseHelper.COLUMN_USER_TYPE, userType);
                contentValues.put(DatabaseHelper.COLUMN_BRANCH, binding.Branch.getSelectedItem().toString());
                contentValues.put(DatabaseHelper.COLUMN_USER_ID, userid);
                contentValues.put(DatabaseHelper.COLUMN_NATIONAL_ID, national_id);
                contentValues.put(DatabaseHelper.COLUMN_BIRTH, birth);

                long result = database.insert(DatabaseHelper.TABLE_USERS, null, contentValues);
                if (result != -1) {
                    Toast.makeText(Admin.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Admin.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Admin.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.edtBirth.setText(sdf.format(myCalendar.getTime()));
    }
}