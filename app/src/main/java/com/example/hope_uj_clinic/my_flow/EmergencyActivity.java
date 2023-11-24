package com.example.hope_uj_clinic.my_flow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hope_uj_clinic.R;

public class EmergencyActivity extends AppCompatActivity {

    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        userType = getIntent().getStringExtra("userType");
    }

    public void newOrder(View view) {
        Intent intent = new Intent(EmergencyActivity.this, NewOrderActivity.class);
        intent.putExtra("userType", userType);
        startActivity(intent);
    }

    public void orderHistory(View view) {
        Intent intent = new Intent(EmergencyActivity.this, OrderHistoryActivity.class);
        intent.putExtra("userType", userType);
        startActivity(intent);
    }
}