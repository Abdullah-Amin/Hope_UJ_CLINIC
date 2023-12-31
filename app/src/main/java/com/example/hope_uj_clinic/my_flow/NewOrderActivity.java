package com.example.hope_uj_clinic.my_flow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityNewOrderBinding;

public class NewOrderActivity extends AppCompatActivity {

    private String userType;
    private String userId;

    SharedPreferences preferences;
    private ActivityNewOrderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        userType = preferences.getString("userType", "");
        userId = preferences.getString("userId", "");

//        userType = getIntent().getStringExtra("userType");
//        userId = getIntent().getStringExtra("user_id");
    }

    public void yourself(View view) {
        Intent intent = new Intent(NewOrderActivity.this, YourselfActivity.class);
        intent.putExtra("userType", userType);
        Log.i("abdo", "yourself: "+ userId);
        intent.putExtra("user_id", userId);
        startActivity(intent);
//        NewOrderActivity.this.finish();
    }

    public void others(View view) {
        Intent intent = new Intent(NewOrderActivity.this, OthersActivity.class);
        intent.putExtra("userType", userType);
        intent.putExtra("user_id", userId);
        startActivity(intent);
//        NewOrderActivity.this.finish();
    }
}