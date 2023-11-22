package com.example.hope_uj_clinic.Patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hope_uj_clinic.Admin.Admin;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityAppointmentsBinding;

public class Appointments extends AppCompatActivity {
    ActivityAppointmentsBinding binding;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_appointments);

        binding.Booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Appointments.this, Booking.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
            }
        });


        binding.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Appointments.this, ShowBooking.class);
                intent.putExtra("user_id", userId);

                startActivity(intent);
            }
        });

    }
}