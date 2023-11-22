package com.example.hope_uj_clinic.Employee.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hope_uj_clinic.Employee.Adapters.AdapterUsers;
import com.example.hope_uj_clinic.Employee.models.DataUsers;
import com.example.hope_uj_clinic.Patient.DataBooking;
import com.example.hope_uj_clinic.Patient.ModelVitalSigns;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityUsersBinding;

import java.util.ArrayList;

public class Users extends AppCompatActivity implements OnClickKeyUser {
    ActivityUsersBinding binding;
    AdapterUsers users;
    SQLiteDatabase database;
    ArrayList<DataUsers> list = new ArrayList<>();

    // Table and column names
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_TYPE = "user_type";
    private static final String COLUMN_USER_ID = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users);
        users = new AdapterUsers(this);
        binding.rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.rec.setAdapter(users);
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

        // Initialize the SQLite database
        database = openOrCreateDatabase("last_hope.dp.db", MODE_PRIVATE, null);
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<DataUsers> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (DataUsers item : list) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getUserid().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            users.filterList(filteredlist);
            users.notifyDataSetChanged();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Retrieve data from SQLite database
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_TYPE + " = 'user'", null);
        if (cursor.getCount() > 0) {
            list.clear();
            int userIdIndex = cursor.getColumnIndexOrThrow(COLUMN_USER_ID);

            while (cursor.moveToNext()) {
                String userId = cursor.getString(userIdIndex);

                // Here, you need to fetch the additional fields (name, nationality, gender) from the database using the userId

                // Assuming you have columns named "name", "nationality", and "gender" in your database table
                int nameIndex = cursor.getColumnIndexOrThrow("name");
                int nationalityIndex = cursor.getColumnIndexOrThrow("national_id");
                int genderIndex = cursor.getColumnIndexOrThrow("gender");

                String name = cursor.getString(nameIndex);
                String nationality = cursor.getString(nationalityIndex);
                String gender = cursor.getString(genderIndex);

                DataUsers user = new DataUsers();
                user.setUserid(userId);
                user.setName(name);
                user.setNationality(nationality);
                user.setGender(gender);

                list.add(user);
            }

            users.setList(list);
            users.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
    @Override
    public void getUserid(String userId) {
        Intent intent= new Intent(getApplicationContext(),AccountUserByEmployee.class);
        intent.putExtra("user_id", userId);
        startActivity(intent);
    }


}