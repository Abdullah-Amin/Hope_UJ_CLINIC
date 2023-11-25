package com.example.hope_uj_clinic.my_flow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityOrderHistoryBinding;
import com.example.hope_uj_clinic.databinding.ActivityOthersBinding;

public class OrderHistoryActivity extends AppCompatActivity {

    ActivityOrderHistoryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.orderRecycler.setAdapter(new OrderHistoryAdapter());
    }
}