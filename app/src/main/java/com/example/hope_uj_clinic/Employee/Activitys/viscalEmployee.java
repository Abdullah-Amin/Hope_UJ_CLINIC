package com.example.hope_uj_clinic.Employee.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.hope_uj_clinic.Admin.ModelUser;
import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.Employee.Adapters.AdapterPattint;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityViscalEmployeeBinding;
import java.util.ArrayList;

public class viscalEmployee extends AppCompatActivity implements OnClickItemPationt {
    AdapterPattint pattint;
    ActivityViscalEmployeeBinding binding;
    SQLiteDatabase database;
    ArrayList<ModelUser> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viscal_employee);
        pattint = new AdapterPattint(this);
        binding.rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.rec.setAdapter(pattint);

        // Create an instance of your custom DatabaseHelper class
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Get an instance of SQLiteDatabase
        database = dbHelper.getWritableDatabase();

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        // Load data from the "users" table
        loadData();
    }

    private void loadData() {
        // Clear the list before loading data
        list.clear();

        // Query the "users" table and get the records with user_type = "user"
        Cursor cursor = database.rawQuery("SELECT * FROM users WHERE user_type = ?", new String[]{"user"});

        // Get the column indexes
        int nameIndex = cursor.getColumnIndex("name");
        int userIdIndex = cursor.getColumnIndex("user_id");

        // Check if any records are found
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(nameIndex);
                String userId = cursor.getString(userIdIndex);

                ModelUser modelUser = new ModelUser();
                modelUser.setName(name);
                modelUser.setUserid(userId);

                list.add(modelUser);
            } while (cursor.moveToNext());

            // Set the list in the adapter and notify the changes
            pattint.setList(list);
            pattint.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No records found", Toast.LENGTH_SHORT).show();
        }

        // Close the cursor
        cursor.close();
    }

    private void filter(String text) {
        ArrayList<ModelUser> filteredList = new ArrayList<>();

        for (ModelUser user : list) {
            if (user.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(user);
            }
        }

        // Update the list in the adapter
        pattint.filterList(filteredList);
    }

    private void getData() {
        // Clear the list before loading data
        list.clear();

        // Check if the type parameter is not null
        String type = "user"; // Assuming the type is "user"
        if (type != null) {
            // Query the database to retrieve relevant data
            Cursor cursor = database.rawQuery("SELECT * FROM vital_signs WHERE type = ?", new String[]{type});

            // Get the column indexes
            int nameIndex = cursor.getColumnIndex("name");
            int userIdIndex = cursor.getColumnIndex("user_id");

            // Check if any records are found
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(nameIndex);
                    String userId = cursor.getString(userIdIndex);

                    ModelUser modelUser = new ModelUser();
                    modelUser.setName(name);
                    modelUser.setUserid(userId);

                    list.add(modelUser);
                } while (cursor.moveToNext());

                // Set the list in the adapter and notify the changes
                pattint.setList(list);
                pattint.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No records found", Toast.LENGTH_SHORT).show();
            }

            // Close the cursor
            cursor.close();
        }
    }

    @Override
    public void getdata(String key, String name, String userid) {
        // Implement the logic for the getdata method here
        // This method will be called when the data is retrieved from the interface
        // You can use the received parameters to perform any necessary operations
        // For example, you can update the UI or start a new activity with the received data
        Intent intent = new Intent(this, AddDataInClienc.class);
        intent.putExtra("key", key);
        intent.putExtra("name", name);
        intent.putExtra("userid", userid);
        startActivity(intent);
    }
}