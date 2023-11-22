package com.example.hope_uj_clinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hope_uj_clinic.Patient.Adapters.AdapterViewBooking;
import com.example.hope_uj_clinic.Patient.DataBooking;
import com.example.hope_uj_clinic.Patient.DataClinics;
import com.example.hope_uj_clinic.databinding.ActivityDetilseClinecBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetilseClinec extends AppCompatActivity {
    String name;
    AdapterViewBooking booking;
    ActivityDetilseClinecBinding binding;
    DatabaseHelper databaseHelper;
    ArrayList<DataBooking> st;
    String d;
    Calendar myCalendar;
    ArrayList<DataBooking> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detilse_clinec);
        name = getIntent().getStringExtra("name");
        myCalendar = Calendar.getInstance();
        databaseHelper = new DatabaseHelper(this);
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        d = dateFormat.format(myCalendar.getTime());
        st = new ArrayList<>();
        booking = new AdapterViewBooking();
        binding.rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.rec.setAdapter(booking);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String text) {
        ArrayList<DataBooking> filteredlist = new ArrayList<DataBooking>();
        for (DataBooking item : list) {
            if (item.getAppid().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            booking.filterList(filteredlist);
            booking.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
        list.clear();
        ArrayList<DataBooking> bookings = databaseHelper.getBookingsByClinic(name);
        if (!bookings.isEmpty()) {
            for (DataBooking booking : bookings) {
                try {
                    Date d1 = sdformat.parse(booking.getDate());
                    Date d2 = sdformat.parse(d);
                    if (d1.compareTo(d2) < 0) {
                        list.add(booking);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            booking.setBookingList(list);
            booking.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
    }
}