package com.example.hope_uj_clinic.my_flow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hope_uj_clinic.R;

public class NewOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
    }

    public void yourself(View view) {
        startActivity(new Intent(NewOrderActivity.this, YourselfActivity.class));
    }

    public void others(View view) {
        startActivity(new Intent(NewOrderActivity.this, OthersActivity.class));
    }
}