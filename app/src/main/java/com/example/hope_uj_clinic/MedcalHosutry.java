package com.example.hope_uj_clinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hope_uj_clinic.Employee.Activitys.DetilseClinecEmployee;
import com.example.hope_uj_clinic.Employee.Activitys.OnClickgetDate;
import com.example.hope_uj_clinic.Employee.Adapters.AdapterViewClinec;
import com.example.hope_uj_clinic.Patient.DataClinics;
import com.example.hope_uj_clinic.databinding.ActivityMedcalHosutryBinding;
import com.example.hope_uj_clinic.databinding.ActivityMedcalHosutryBinding;
import com.example.hope_uj_clinic.DatabaseHelper;

import java.util.ArrayList;

public class MedcalHosutry extends AppCompatActivity implements OnClickgetDate {
    ActivityMedcalHosutryBinding binding;
    AdapterViewClinec clinec;
    DatabaseHelper databaseHelper;
    ArrayList<DataClinics> list= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_medcal_hosutry);
        databaseHelper = new DatabaseHelper(this);
        clinec = new AdapterViewClinec(this);
        binding.rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.rec.setAdapter(clinec);
    }

    @Override
    public void getDate(String name) {
        Intent intent = new Intent(getApplicationContext(), DetilseClinec.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        // Retrieve clinic data from SQLite database
        ArrayList<DataClinics> clinics = databaseHelper.getAllClinics();
        if (!clinics.isEmpty()) {
            list.addAll(clinics);
            clinec.setList(list);
            clinec.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "No data found ", Toast.LENGTH_SHORT).show();
        }
    }
}
