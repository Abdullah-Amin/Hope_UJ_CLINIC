package com.example.hope_uj_clinic.Patient;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityElectronicCardBinding;

public class Electronic_Card extends AppCompatActivity {
    ActivityElectronicCardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_electronic_card);
        binding.dataofbrith.setText("@umscUJ");
    }
}
