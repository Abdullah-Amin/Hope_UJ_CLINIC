package com.example.hope_uj_clinic.Employee.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hope_uj_clinic.Admin.Admin;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityAppointmentsEmployeeBinding;

public class AppointmentsEmployee extends AppCompatActivity {
    ActivityAppointmentsEmployeeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_appointments_employee);
        binding.Booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BookingByEmployee.class);
                startActivity(intent);
            }
        });
        binding.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowByEmployee.class);
                startActivity(intent);
            }
        });

    }
}
