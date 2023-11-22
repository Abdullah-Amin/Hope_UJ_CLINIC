package com.example.hope_uj_clinic.Employee.Activitys;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.Patient.Booking;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityBookingByEmployeeBinding;
import com.example.hope_uj_clinic.databinding.ActivityBookingByEmployeeBinding;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class BookingByEmployee extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private ActivityBookingByEmployeeBinding binding;
    private Calendar myCalendar;
    private ArrayList<String> clinicList = new ArrayList<>();
    private ArrayList<String> doctorList = new ArrayList<>();
    private String selectedClinic;
    private String selectedDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_by_employee);
        myCalendar = Calendar.getInstance();
        this.databaseHelper = new DatabaseHelper(this);

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
                new DatePickerDialog(BookingByEmployee.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                Dialog dialog = new Dialog(BookingByEmployee.this);
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
                    values[i] = String.format(Locale.getDefault(), "%02d:%02d", hour, minutes);
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
                doctorList.addAll(getDoctorsForClinic(selectedClinic));
                ArrayAdapter<String> doctorAdapter = new ArrayAdapter<>(BookingByEmployee.this, android.R.layout.simple_spinner_item, doctorList);
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
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String[] projection = {DatabaseHelper.COLUMN_CLINIC_NAME};
        Cursor cursor = database.query(DatabaseHelper.TABLE_CLINIC, projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String clinicName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CLINIC_NAME));
            clinics.add(clinicName);
        }

        cursor.close();
        database.close();
        return clinics;
    }

    private List<String> getDoctorsForClinic(String clinic) {
        List<String> doctors = new ArrayList<>();
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String[] projection = {DatabaseHelper.COLUMN_DOCTOR_NAME};
        String selection = DatabaseHelper.COLUMN_CLINICS + " = ?";
        String[] selectionArgs = {clinic};
        Cursor cursor = database.query(DatabaseHelper.TABLE_DOCTOR, projection, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            String doctorName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DOCTOR_NAME));
            doctors.add(doctorName);
        }


        cursor.close();
        database.close();
        return doctors;

    }
    private void send() {
        // Implementation for the send() method
        String date = binding.Date.getText().toString().trim();
        String time = binding.Time.getText().toString().trim();
        String clinic = selectedClinic;
        String doctor = selectedDoctor;
        String mrn = binding.MRN.getText().toString().trim();

        // Check if the MRN exists in the SQLite database
        String userId = getUserIdByMRN(mrn);
        if (isUserIdExists(userId)) {
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

            if (newRowId != -1) {
                Toast.makeText(this, "Appointment booked successfully. Appointment ID: " + appointmentId, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to book appointment. Please try again.", Toast.LENGTH_SHORT).show();
            }

            // Close the database
            database.close();
        } else {
            Toast.makeText(this, "Invalid MRN, please enter a valid MRN.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isUserIdExists(String userId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {
                "user_id"
        };

        String selection = "user_id = ?";
        String[] selectionArgs = {userId};

        Cursor cursor = db.query(
                "users",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    private String getUserIdByMRN(String mrn) {
        String emailDomain = "@uj.edu.sa"; // Replace with the actual email domain
        String userId = mrn + emailDomain;
        return userId;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.Date.setText(sdf.format(myCalendar.getTime()));
    }
}