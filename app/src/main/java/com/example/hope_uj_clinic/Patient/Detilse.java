package com.example.hope_uj_clinic.Patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.Patient.Adapters.AdapterVitalSigns;
import com.example.hope_uj_clinic.databinding.ActivityDetilseBinding;

import java.util.ArrayList;

public class Detilse extends AppCompatActivity {
    ActivityDetilseBinding binding;
    SQLiteDatabase database;
    DatabaseHelper databaseHelper;
    AdapterVitalSigns vitalSigns;
    ArrayList<ModelVitalSigns> list = new ArrayList<>();
    String type;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detilse);
        type = getIntent().getStringExtra("type");
        uid = getCurrentUserId();

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getReadableDatabase();

        vitalSigns = new AdapterVitalSigns();
        binding.rec.setLayoutManager(new LinearLayoutManager(this));
        binding.rec.setAdapter(vitalSigns);

        // Retrieve the type from the intent extras
        type = getIntent().getStringExtra("type");
        retrieveVitalSigns();
    }

    private void retrieveVitalSigns() {
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_VITAL_SIGNS, // table name
                null, // columns (null retrieves all columns)
                DatabaseHelper.COLUMN_VITAL_USER_NAME + "=? AND " + DatabaseHelper.COLUMN_VITAL_TYPE + "=?", // selection
                new String[]{uid, type}, // selectionArgs
                null, // groupBy
                null, // having
                null // orderBy
        );

        int columnIndexType = cursor.getColumnIndex(DatabaseHelper.COLUMN_VITAL_TYPE);
        int columnIndexRate = cursor.getColumnIndex(DatabaseHelper.COLUMN_VITAL_RATE);
        int columnIndexDate = cursor.getColumnIndex(DatabaseHelper.COLUMN_VITAL_DATE);
        int columnIndexTime = cursor.getColumnIndex(DatabaseHelper.COLUMN_VITAL_TIME);
        int columnIndexUserName = cursor.getColumnIndex(DatabaseHelper.COLUMN_VITAL_USER_NAME);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ModelVitalSigns signs = new ModelVitalSigns();
                // Populate signs object with cursor data
                signs.setType(cursor.getString(columnIndexType));
                signs.setRate(cursor.getString(columnIndexRate));
                signs.setDate(cursor.getString(columnIndexDate));
                signs.setTime(cursor.getString(columnIndexTime));
                signs.setuser_id(cursor.getString(columnIndexUserName));
                // ...

                list.add(signs);
            } while (cursor.moveToNext());
            cursor.close();

            vitalSigns.setList(list);
            vitalSigns.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
    }
    private String getCurrentUserId() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("user_id", null);
    }
}