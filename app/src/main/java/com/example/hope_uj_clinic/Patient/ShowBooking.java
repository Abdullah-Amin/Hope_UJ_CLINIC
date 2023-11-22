package com.example.hope_uj_clinic.Patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.Patient.Adapters.AdapterViewBooking;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityShowBookingBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ShowBooking extends AppCompatActivity {
    ActivityShowBookingBinding binding;

    private RecyclerView recyclerView;
    private ArrayList<DataBooking> bookingList = new ArrayList<>();

    private AdapterViewBooking bookingAdapter;
    private DatabaseHelper databaseHelper;
    private SimpleDateFormat sdformat;
    private Calendar myCalendar;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.rec;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookingAdapter = new AdapterViewBooking();
        recyclerView.setAdapter(bookingAdapter);

        databaseHelper = new DatabaseHelper(this);
        sdformat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        myCalendar = Calendar.getInstance();
        currentDate = sdformat.format(myCalendar.getTime());

        showUpcomingBookings();
    }

    private void showUpcomingBookings() {
        String userId = getCurrentUserId();
        String userName = databaseHelper.getUserName(userId); // Retrieve the user name

        if (userId != null && currentDate != null) {
            ArrayList<DataBooking> upcomingBookings = databaseHelper.getUpcomingBookings(userId, currentDate);

            if (!upcomingBookings.isEmpty()) {
                bookingList.addAll(upcomingBookings);
                bookingAdapter.setBookingList(bookingList);
                bookingAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No upcoming bookings found.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No user found.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentUserId() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("user_id", null);
    }
}