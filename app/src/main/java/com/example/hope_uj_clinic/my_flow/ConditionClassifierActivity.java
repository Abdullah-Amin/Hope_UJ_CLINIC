package com.example.hope_uj_clinic.my_flow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityConditionClassifierBinding;

public class ConditionClassifierActivity extends AppCompatActivity {

    private ActivityConditionClassifierBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConditionClassifierBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void classify(View view) {

        if ((binding.HREt.getText().toString().isEmpty() || !isNumeric(binding.HREt.getText().toString()))) {
            binding.HREt.setError("Please enter a valid number");
            return;
        }
        if ((binding.oxygenEt.getText().toString().isEmpty() || !isNumeric(binding.oxygenEt.getText().toString()))) {
            binding.oxygenEt.setError("Please enter a valid number");
            return;
        }
        if ((binding.respEt.getText().toString().isEmpty() || !isNumeric(binding.respEt.getText().toString()))) {
            binding.respEt.setError("Please enter a valid number");
            return;
        }
        if ((binding.tempEt.getText().toString().isEmpty() || !isNumeric(binding.tempEt.getText().toString()))) {
            binding.tempEt.setError("Please enter a valid number");
            return;
        }

        startActivity(new Intent(ConditionClassifierActivity.this, ConditionActivity.class));
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}