package com.example.hope_uj_clinic.Employee.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import com.example.hope_uj_clinic.MedcalHosutry;
import com.example.hope_uj_clinic.MedcalHosutry;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityHomeEmplyeeBinding;

public class HomeEmplyee extends AppCompatActivity {
    ActivityHomeEmplyeeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home_emplyee);

        binding.Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeEmplyee.this,Users.class);
                startActivity(intent);
            }
        });

        binding.calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeEmplyee.this,AppointmentsEmployee.class);
                startActivity(intent);
            }
        });
        binding.viscal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),viscalEmployee.class);
                startActivity(intent);
            }
        });
        binding.viscalhostriy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeEmplyee.this, MedcalHosutry.class);
                startActivity(intent);
            }
        });


    }
}