package com.example.hope_uj_clinic.Employee.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.Employee.Adapters.AdapterViewClinec;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.Patient.DataClinics;
import com.example.hope_uj_clinic.databinding.ActivityShowByEmployeeBinding;

import java.util.ArrayList;

public class ShowByEmployee extends AppCompatActivity implements OnClickgetDate {
    ActivityShowByEmployeeBinding binding;
    AdapterViewClinec clinec;
    DatabaseHelper databaseHelper;
    ArrayList<DataClinics> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_by_employee);
        databaseHelper = new DatabaseHelper(this);
        clinec = new AdapterViewClinec(this);
        binding.rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.rec.setAdapter(clinec);
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Clinic", null);

        if (cursor.moveToFirst()) {
            int clinicNameIndex = cursor.getColumnIndex("clinic_name");
            if (clinicNameIndex >= 0) {
                do {
                    String clinicName = cursor.getString(clinicNameIndex);
                    DataClinics dataClinics = new DataClinics();
                    dataClinics.setName(clinicName);
                    list.add(dataClinics);
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
            }
        }

        cursor.close();
        database.close();

        if (list.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            clinec.setList(list);
            clinec.notifyDataSetChanged();
        }
    }

    @Override
    public void getDate(String name) {
        Intent intent = new Intent(getApplicationContext(), DetilseClinecEmployee.class);
        intent.putExtra("name", name);
        setContentView(binding.getRoot());
        startActivity(intent);
    }
}