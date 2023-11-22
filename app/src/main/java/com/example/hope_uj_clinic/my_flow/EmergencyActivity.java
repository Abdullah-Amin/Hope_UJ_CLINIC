package com.example.hope_uj_clinic.my_flow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hope_uj_clinic.R;

public class EmergencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
    }

    public void newOrder(View view) {
        startActivity(new Intent(EmergencyActivity.this, NewOrderActivity.class));
    }

    public void orderHistory(View view) {
    }
}