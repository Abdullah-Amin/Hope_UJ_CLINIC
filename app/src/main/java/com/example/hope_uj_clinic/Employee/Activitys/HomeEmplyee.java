package com.example.hope_uj_clinic.Employee.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

//import com.example.hope_uj_clinic.MedcalHosutry;
import com.example.hope_uj_clinic.MedcalHosutry;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityHomeEmplyeeBinding;
import com.example.hope_uj_clinic.my_flow.ConditionClassifierActivity;
import com.example.hope_uj_clinic.my_flow.EmergencyActivity;

public class HomeEmplyee extends AppCompatActivity {
    ActivityHomeEmplyeeBinding binding;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home_emplyee);

        preferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("userType", "employee");

        binding.Elalctronec.setOnClickListener(view -> {
            startActivity(new Intent(HomeEmplyee.this, ConditionClassifierActivity.class));
        });

        binding.emergencyBtn.setOnClickListener(view -> {
            editor.apply();
            Intent intent = new Intent(this, EmergencyActivity.class);
            intent.putExtra("userType", "employee");
            startActivity(intent);
        });

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