package com.example.hope_uj_clinic.my_flow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.Employee.models.PatientLocation;
import com.example.hope_uj_clinic.databinding.ActivityOrderHistoryBinding;

import java.util.ArrayList;
import java.util.Objects;

public class OrderHistoryActivity extends AppCompatActivity {

    ActivityOrderHistoryBinding binding;
    ArrayList<PatientLocation> locations = null;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locations = new ArrayList<>();

        preferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        String orderState = preferences.getString("orderState", "old");

        String userType = preferences.getString("userType", "user");
        Log.i("abdo", "orderHistory: before adapter " + userType);

        if (userType.equals("employee")) {


            if (orderState.equals("new")){
                binding.head.setText("New Orders");
                binding.orderRecycler.setAdapter(new OrderHistoryAdapter(getNewOrders(), userType, new OrderDetailsI() {
                    @Override
                    public void getOrder(PatientLocation patientLocation) {
                        Intent intent = new Intent(OrderHistoryActivity.this, OrderDetailsActivity.class);
                        intent.putExtra("user", "employee");
                        intent.putExtra("order_state", "inProgress");
                        intent.putExtra("patient", patientLocation);
                        startActivity(intent);
//                        OrderHistoryActivity.this.finish();
                    }
                }));
            }else {
                binding.orderRecycler.setAdapter(new OrderHistoryAdapter(getCompletedOrders(), userType, new OrderDetailsI() {
                    @Override
                    public void getOrder(PatientLocation patientLocation) {
                        Intent intent = new Intent(OrderHistoryActivity.this, OrderDetailsActivity.class);
                        intent.putExtra("user", "employee");
                        intent.putExtra("order_state", "complete");
                        intent.putExtra("patient", patientLocation);
                        startActivity(intent);
//                        OrderHistoryActivity.this.finish();
                    }
                }));
            }
//            binding.orderRecycler.setAdapter(new OrderHistoryAdapter(getDataFromDatabase(), userType, new OrderDetailsI() {
//                @Override
//                public void getOrder(PatientLocation patientLocation) {
//                    Intent intent = new Intent(OrderHistoryActivity.this, OrderDetailsActivity.class);
////                    patientLocation.setUser("employee");
//                    intent.putExtra("patient", patientLocation);
//                    intent.putExtra("user", "employee");
//                    startActivity(intent);
//                }
//            }));
        }else{
            binding.orderRecycler.setAdapter(new OrderHistoryAdapter(getDataFromDatabase(), userType, new OrderDetailsI() {
                @Override
                public void getOrder(PatientLocation patientLocation) {
                    Intent intent = new Intent(OrderHistoryActivity.this, OrderDetailsActivity.class);
                    intent.putExtra("user", "user");
                    intent.putExtra("patient", patientLocation);
                    startActivity(intent);
//                    OrderHistoryActivity.this.finish();
                }
            }));
        }
    }

    public ArrayList<PatientLocation> getDataFromDatabase() {
        DatabaseHelper db = new DatabaseHelper(OrderHistoryActivity.this);

        Cursor cursor = db.getOrders();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Log.i("abdo", "getDataFromDatabase: all " + cursor.getString(0) + " - " + cursor.getString(3));
                SharedPreferences preferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
                String userId = preferences.getString("userId", "");
                String userType = preferences.getString("userType", "");

                if (!userType.equals("employee")) {
                    if (Objects.equals(cursor.getString(2), userId)) {
                        locations.add(new PatientLocation(
                                cursor.getString(3),
                                cursor.getString(0),
                                cursor.getString(2),
                                cursor.getString(1),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7),
                                cursor.getString(8)));
                    }
                } else {
                    locations.add(new PatientLocation(
                            cursor.getString(3),
                            cursor.getString(0),
                            cursor.getString(2),
                            cursor.getString(1),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getString(8)));
                }
            }
        }
        return locations;
    }

    public ArrayList<PatientLocation> getNewOrders() {
        DatabaseHelper db = new DatabaseHelper(OrderHistoryActivity.this);

        Cursor cursor = db.getNewOrdersForEmployee();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Log.i("abdo", "getDataFromDatabase: new " + cursor.getString(0) + " - " + cursor.getString(3));
                SharedPreferences preferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
                String userId = preferences.getString("userId", "");

//                if (Objects.equals(cursor.getString(2), userId)) {
                    locations.add(new PatientLocation(
                            cursor.getString(3),
                            cursor.getString(0),
                            cursor.getString(2),
                            cursor.getString(1),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getString(8)));
//                }
            }
        }
//        Log.i("abdo", "getNewOrders: " + locations.get(0).toString());
        return locations;
    }

    public ArrayList<PatientLocation> getCompletedOrders() {
        DatabaseHelper db = new DatabaseHelper(OrderHistoryActivity.this);

        Cursor cursor = db.getCompletedOrdersForEmployee();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Log.i("abdo", "getDataFromDatabase: completed " + cursor.getString(0) + " - " + cursor.getString(3));
                SharedPreferences preferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
                String userId = preferences.getString("userId", "");

//                if (Objects.equals(cursor.getString(2), userId)) {
                    locations.add(new PatientLocation(
                            cursor.getString(3),
                            cursor.getString(0),
                            cursor.getString(2),
                            cursor.getString(1),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getString(8)));
//                }
            }
        }
        return locations;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}