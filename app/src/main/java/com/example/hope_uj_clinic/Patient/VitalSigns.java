package com.example.hope_uj_clinic.Patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityVitalSignsBinding;

public class VitalSigns extends AppCompatActivity {
    ActivityVitalSignsBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vital_signs);
        databaseHelper = new DatabaseHelper(this);

        binding.TheWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDetails = new Intent(VitalSigns.this, Detilse.class);
                showDetails.putExtra("type", "The Weight");
                startActivity(showDetails);
            }
        });

        binding.TheTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDetilse= new Intent(VitalSigns.this,Detilse.class);
                showDetilse.putExtra("type","The Temperature");

                startActivity(showDetilse);
            }
        });

        binding.RespiratoryRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDetilse= new Intent(VitalSigns.this,Detilse.class);
                showDetilse.putExtra("type","Respiratory Rate");
                startActivity(showDetilse);
            }
        });
        binding.Blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDetilse= new Intent(VitalSigns.this,Detilse.class);
                showDetilse.putExtra("type","Blood");

                startActivity(showDetilse);
            }
        });
        binding.Pulse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDetilse= new Intent(VitalSigns.this,Detilse.class);
                showDetilse.putExtra("type","Pulse");
                startActivity(showDetilse);
            }
        });
        binding.Oxysgen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDetilse= new Intent(VitalSigns.this,Detilse.class);
                showDetilse.putExtra("type","Oxygen");
                startActivity(showDetilse);
            }
        });


    }
}
