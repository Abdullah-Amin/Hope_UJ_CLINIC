package com.example.hope_uj_clinic.my_flow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hope_uj_clinic.R;

public class NewOrderActivity extends AppCompatActivity {

    private String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        userType = getIntent().getStringExtra("userType");
    }

    public void yourself(View view) {
        Intent intent = new Intent(NewOrderActivity.this, YourselfActivity.class);
        intent.putExtra("userType", userType);
        startActivity(intent);
    }

    public void others(View view) {
        Intent intent = new Intent(NewOrderActivity.this, OthersActivity.class);
        intent.putExtra("userType", userType);
        startActivity(intent);
    }
}