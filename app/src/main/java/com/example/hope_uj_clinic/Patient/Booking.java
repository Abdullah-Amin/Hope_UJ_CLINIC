package com.example.hope_uj_clinic.Patient;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.hope_uj_clinic.DatabaseHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityBookingBinding;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Booking extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    String user_id;

    private SQLiteDatabase database;
    private ActivityBookingBinding binding;
    private Calendar myCalendar;
    private int mHour, mMinute;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> list2 = new ArrayList<>();
    private ArrayList<String> lisdata = new ArrayList<>();
    private String datew;
    private String Times;
    private int randomNumber;
    private String Doctor_name;
    private String appid;
    private String Clinecs;
    private ArrayList<String> doctorList = new ArrayList<>();
    private ArrayList<String> clinicList = new ArrayList<>();
    private String selectedClinic;
    private String selectedDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking);
        myCalendar = Calendar.getInstance();
        this.databaseHelper = new DatabaseHelper(this);
//        String user_id = getCurrentUserId();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        binding.Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Booking.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.Booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });

        binding.Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Set the available time range
                int startHour = 8;  // Start hour (8 AM)
                int endHour = 17;   // End hour (5 PM)

                // Calculate the number of available time slots
                int numSlots = (endHour - startHour + 1) * 2;

                // Calculate the initial selected slot
                int initialSlot = (mHour - startHour) * 2 + (mMinute >= 30 ? 1 : 0);

                // Create a dialog with a custom layout
                Dialog dialog = new Dialog(Booking.this);
                dialog.setContentView(R.layout.custom_time_picker_dialog);
                dialog.setTitle("Select Time Slot");

                NumberPicker numberPicker = dialog.findViewById(R.id.numberPicker);
                Button btnSetTime = dialog.findViewById(R.id.btnSetTime);

                // Set the displayed values for time slots
                String[] displayedValues = generateDisplayedValues(startHour, numSlots);
                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(numSlots - 1);
                numberPicker.setDisplayedValues(displayedValues);
                numberPicker.setValue(initialSlot);

                btnSetTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int selectedSlot = numberPicker.getValue();

                        // Calculate the time based on the selected slot
                        int selectedHour = startHour + selectedSlot / 2;
                        int selectedMinute = (selectedSlot % 2) * 30;

                        // Update the selected time in the TextView
                        binding.Time.setText(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute));

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

            private String[] generateDisplayedValues(int startHour, int numSlots) {
                String[] values = new String[numSlots];
                int hour = startHour;
                int minutes = 0;

                for (int i = 0; i < numSlots; i++) {
                    values[i]= String.format(Locale.getDefault(), "%02d:%02d", hour, minutes);
                    minutes += 30;
                    if (minutes == 60) {
                        hour++;
                        minutes = 0;
                    }
                }

                return values;
            }
        });

        binding.Clinecs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClinic = parent.getItemAtPosition(position).toString();
                doctorList.clear();
                doctorList.addAll(getDoctorsForClinic(selectedClinic)); // Update the doctorList
                ArrayAdapter<String> doctorAdapter = new ArrayAdapter<>(Booking.this, android.R.layout.simple_spinner_item, doctorList);
                binding.Doctor.setAdapter(doctorAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle empty selection here
            }
        });

        binding.Doctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDoctor = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle empty selection here
            }
        });
        clinicList.addAll(getClinics());
        ArrayAdapter<String> clinicAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clinicList);
        binding.Clinecs.setAdapter(clinicAdapter);
    }

    private List<String> getClinics() {
        List<String> clinics = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        // Define the columns to be queried
        String[] projection = {DatabaseHelper.COLUMN_CLINIC_NAME};

        // Query the database
        Cursor cursor = database.query(DatabaseHelper.TABLE_CLINIC, projection, null, null, null, null, null);

        // Iterate through the cursor and add clinic names to the list
        while (cursor.moveToNext()) {
            String clinicName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CLINIC_NAME));
            clinics.add(clinicName);
        }

        // Close the cursor and the database
        cursor.close();
        database.close();

        return clinics;
    }
    private List<String> getDoctorsForClinic(String clinic) {
        List<String> doctors = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        // Define the columns to be queried
        String[] projection = {DatabaseHelper.COLUMN_DOCTOR_NAME};

        // Define the WHERE clause
        String selection = DatabaseHelper.COLUMN_CLINICS + " = ?";

        // Define the selection arguments
        String[] selectionArgs = {clinic};

        // Query the database
        Cursor cursor = database.query(DatabaseHelper.TABLE_DOCTOR, projection, selection, selectionArgs, null, null, null);

        // Iterate through the cursor and add doctor names to the list
        while (cursor.moveToNext()) {
            String doctorName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DOCTOR_NAME));
            doctors.add(doctorName);
        }

        // Close the cursor and the database
        cursor.close();
        database.close();

        return doctors;
    }

    private void send() {
        // Get the selected date, time, clinic, and doctor
        String date = binding.Date.getText().toString().trim();
        String time = binding.Time.getText().toString().trim();
        String clinic = selectedClinic;
        String doctor = selectedDoctor;
        String userId = getCurrentUserId();
        String userName = databaseHelper.getUserName(userId); // Retrieve the user name

        // Generate a random appointment ID
        int randomNumber = new Random().nextInt(900000) + 100000;
        String appointmentId = String.valueOf(randomNumber);

        // Open the database
        database = databaseHelper.getWritableDatabase();

        // Create a new record with the appointment details
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_APPID, appointmentId);
        values.put(DatabaseHelper.COLUMN_DATE, date);
        values.put(DatabaseHelper.COLUMN_TIME, time);
        values.put(DatabaseHelper.COLUMN_CLINICS, clinic);
        values.put(DatabaseHelper.COLUMN_DOCTOR_NAME, doctor);
        values.put(DatabaseHelper.COLUMN_USER_ID, userId);
        values.put(DatabaseHelper.COLUMN_USER_NAME, userName);

        // Insert the record into the database
        long newRowId = database.insert(DatabaseHelper.TABLE_BOOKING, null, values);

        // Close the database
        database.close();

        if (newRowId != -1) {
            Toast.makeText(this, "Appointment booked successfully. Appointment ID: " + appointmentId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to book appointment. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        binding.Date.setText(dateFormat.format(myCalendar.getTime()));
    }
    private String getCurrentUserId() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("user_id", null);
    }

}