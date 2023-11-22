package com.example.hope_uj_clinic.Patient;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.Patient.Adapters.AdapterViewBooking;
import com.example.hope_uj_clinic.databinding.ActivityMedicalhistoryBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Medicalhistory extends AppCompatActivity {
    ActivityMedicalhistoryBinding binding;

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
        binding= DataBindingUtil.setContentView(this,R.layout.activity_medicalhistory);

        recyclerView = binding.rec;
        binding.rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        bookingAdapter = new AdapterViewBooking();
        recyclerView.setAdapter(bookingAdapter);

        databaseHelper = new DatabaseHelper(this);
        sdformat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        myCalendar = Calendar.getInstance();

        currentDate = sdformat.format(myCalendar.getTime());

        showPreviousBookings();
    }

    private void showPreviousBookings() {
        String userId = getCurrentUserId();

        if (userId != null && currentDate != null) {
            ArrayList<DataBooking> previousBookings = databaseHelper.getPreviousBookings(userId, currentDate);

            if (!previousBookings.isEmpty()) {
                bookingList.addAll(previousBookings);
                bookingAdapter.setBookingList(bookingList);
                bookingAdapter.notifyDataSetChanged();
            } else {
                // No previous bookings found
                Toast.makeText(this, "No previous bookings found.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // No user found
            Toast.makeText(this, "No user found.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentUserId() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("user_id", null);
    }
}