package com.example.hope_uj_clinic.Employee.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hope_uj_clinic.Employee.Adapters.AdapterDetilseBookingbyEmployee;
import com.example.hope_uj_clinic.Employee.Adapters.AdapterShowDate;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityDetilseClinecEmployeeBinding;
import com.example.hope_uj_clinic.Patient.DataBooking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class DetilseClinecEmployee extends AppCompatActivity implements OnClickgetDate {
    String name;
    SQLiteDatabase database;
    ArrayList<String> list = new ArrayList<>();
    AdapterShowDate date;
    Calendar myCalendar;
    ArrayList<DataBooking> list2 = new ArrayList<>();
    ActivityDetilseClinecEmployeeBinding binding;
    String d;
    AdapterDetilseBookingbyEmployee bookingbyEmployee;
    ArrayList<String> listadapter = new ArrayList<>();
    private String selectedDate;
    private AdapterShowDate dateAdapter;
    private AdapterDetilseBookingbyEmployee bookingbyEmployeeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detilse_clinec_employee);
        name = getIntent().getStringExtra("name");
        database = openOrCreateDatabase("last_hope.dp.db", MODE_PRIVATE, null);
        myCalendar = Calendar.getInstance();
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        d = dateFormat.format(myCalendar.getTime());
        bookingbyEmployee = new AdapterDetilseBookingbyEmployee();
        binding.recdetilse.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.recdetilse.setAdapter(bookingbyEmployee);
        date = new AdapterShowDate(this);
        binding.rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rec.setAdapter(date);
        selectedDate = d;
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM Booking WHERE clinics = ? AND date >= ?", new String[]{name, String.valueOf(selectedDate)});
        if (cursor.moveToFirst()) {
            int columnIndexDate = cursor.getColumnIndex("date");
            if (columnIndexDate >= 0) {
                do {
                    String date = cursor.getString(columnIndexDate);
                    list.add(date);
                } while (cursor.moveToNext());
                listadapter = removeDuplicates(list);
                date.setList(listadapter);
                date.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), "Column 'date' not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        fetchBookingsForSelectedDate();
    }
    @Override
    public void getDate(String names) {
        Toast.makeText(this, "" + names, Toast.LENGTH_SHORT).show();
        selectedDate = names;
        fetchBookingsForSelectedDate();
    }

      private void fetchBookingsForSelectedDate() {
        list2.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM Booking WHERE date = ?", new String[]{selectedDate});
        if (cursor.moveToFirst()) {
            int columnIndexUserID = cursor.getColumnIndex("user_id");
            int columnIndexDate = cursor.getColumnIndex("date");
            int columnIndexTime = cursor.getColumnIndex("time");
            int columnIndexDoctorName = cursor.getColumnIndex("doctor_name");
            int columnIndexAppId = cursor.getColumnIndex("appid");


            do {
                DataBooking dataBooking = new DataBooking();

                if (columnIndexUserID != -1) {
                    String user_id = cursor.getString(columnIndexUserID);
                    if (user_id != null) {
                        dataBooking.setuser_id(user_id);
                    } else {
                        // Handle null value for 'user_id' column
                    }
                }

                if (columnIndexDate != -1) {
                    String date = cursor.getString(columnIndexDate);
                    if (date != null) {
                        dataBooking.setDate(date);
                    } else {
                        // Handle null value for 'date' column
                    }
                }
                if (columnIndexTime != -1) {
                    String time = cursor.getString(columnIndexTime);
                    if (time != null) {
                        dataBooking.setTime(time);
                    } else {
                        // Handle null value for 'time' column
                    }
                }
                if (columnIndexAppId != -1) {
                    String appId = cursor.getString(columnIndexAppId);
                    if (appId != null) {
                        dataBooking.setAppid(appId);
                    } else {
                        // Handle null value for 'appid' column
                    }
                }


                if (columnIndexDoctorName != -1) {
                    String doctorName = cursor.getString(columnIndexDoctorName);
                    if (doctorName != null) {
                        dataBooking.setDoctor(doctorName);
                    } else {
                        // Handle null value for 'doctor_name' column
                    }
                }
                list2.add(dataBooking);
            } while (cursor.moveToNext());

            bookingbyEmployee.setList(list2);
            bookingbyEmployee.notifyDataSetChanged();
        } else {

            setContentView(binding.getRoot());

            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    public ArrayList<String> removeDuplicates(ArrayList<String> list) {
        System.out.println("ArrayList "+list);
        // Create a new ArrayList
        ArrayList<String> newList = new ArrayList<String>();

        // Traverse through the first list
        for (String element  : list) {

            // If this element is not present in newList
            // then add it


            if (!newList.contains(element)) {

                newList.add(element);
            }
        }
        System.out.println("ArrayList "+newList);

        // return the new list
        return newList;
    }
}