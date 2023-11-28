package com.example.hope_uj_clinic.my_flow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.Employee.models.PatientLocation;
import com.example.hope_uj_clinic.databinding.ActivityOrderHistoryBinding;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    ActivityOrderHistoryBinding binding;
    ArrayList<PatientLocation> locations = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locations = new ArrayList<>();

        String userType = getIntent().getStringExtra("userType");
        Log.i("abdo", "orderHistory: before adapter " + userType);

        if (userType.equals("employee")){
            binding.head.setText("New Order");
        }

        binding.orderRecycler.setAdapter(new OrderHistoryAdapter(getDataFromDatabase(), userType, new OrderDetailsI() {
            @Override
            public void getOrder(PatientLocation patientLocation) {
                Intent intent = new Intent(OrderHistoryActivity.this, OrderDetailsActivity.class);
                intent.putExtra("patient", patientLocation);
                startActivity(intent);
            }
        }));
    }

    public ArrayList<PatientLocation> getDataFromDatabase(){
        DatabaseHelper db = new DatabaseHelper(OrderHistoryActivity.this);

        Cursor cursor = db.getOrders();
//        Cursor cursorYourself = db.getYourselfOrders();

        if (cursor.getCount() == 0){
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                Log.i("abdo", "getDataFromDatabase: "+ cursor.getString(0) + cursor.getString(3));
                locations.add(new PatientLocation(
                        cursor.getString(2),
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)));
            }
        }
        return locations;
    }
}