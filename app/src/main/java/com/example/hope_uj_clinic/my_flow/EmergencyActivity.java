package com.example.hope_uj_clinic.my_flow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hope_uj_clinic.R;

public class EmergencyActivity extends AppCompatActivity {

    String userType;
    String userId;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        preferences = getSharedPreferences("userId", Context.MODE_PRIVATE);

        userType = preferences.getString("userType", "null");

        userId = preferences.getString("userId", "user");

        Log.i("abdo", "onCreate: emerg " + userType);

        editor = preferences.edit();
        editor.putString("userId", userId);
        editor.putString("userType", userType);
    }

    public void newOrder(View view) {
        if (userType.equals("employee")){
            Intent intent = new Intent(EmergencyActivity.this, OrderHistoryActivity.class);
            intent.putExtra("userType", userType);
            intent.putExtra("orderState", "new");
            editor.putString("orderState", "new");
            Log.i("abdo", "orderHistory: " + userType);
            editor.apply();
            startActivity(intent);
//            EmergencyActivity.this.finish();
            return;
        }
        Intent intent = new Intent(EmergencyActivity.this, NewOrderActivity.class);
        intent.putExtra("userType", userType);
        intent.putExtra("user_id", userId);
        editor.apply();
        startActivity(intent);
//        EmergencyActivity.this.finish();
    }

    public void orderHistory(View view) {
        Intent intent = new Intent(EmergencyActivity.this, OrderHistoryActivity.class);
        intent.putExtra("userType", userType);
        intent.putExtra("orderState", "old");
        editor.putString("orderState", "old");
        Log.i("abdo", "orderHistory: " + userType);
        intent.putExtra("user_id", userId);
        editor.apply();
        startActivity(intent);
//        EmergencyActivity.this.finish();
    }
}