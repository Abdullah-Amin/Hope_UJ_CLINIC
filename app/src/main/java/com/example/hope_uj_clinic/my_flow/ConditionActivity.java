package com.example.hope_uj_clinic.my_flow;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityConditionBinding;

public class ConditionActivity extends AppCompatActivity {

    private ActivityConditionBinding binding;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String predictionResponse = getIntent().getStringExtra("prediction");

        if ("Abnormal".equals(predictionResponse)) {
            binding.conditionTxt.setText(predictionResponse.concat("\nCondition"));
            binding.conditionTxt.setTextColor(Color.RED);
            binding.conditionImage.setImageResource(R.drawable.dangerous_condition);
            binding.treatmentTxt.setText("The Patient must receive\ntreatment in the hospital !!");
            binding.treatmentTxt.setTextColor(Color.RED);
        }
    }
}