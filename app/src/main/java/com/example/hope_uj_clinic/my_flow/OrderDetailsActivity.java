package com.example.hope_uj_clinic.my_flow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.hope_uj_clinic.Employee.models.PatientLocation;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityOredrDeatailsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class OrderDetailsActivity extends AppCompatActivity {

    private PatientLocation location;
    private ActivityOredrDeatailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOredrDeatailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        location = getIntent().getParcelableExtra("patient");

        if (location.getPersonType().equals("yourself") || location.getPersonType().equals("himself")){
            binding.nameLayout.setVisibility(View.GONE);
            binding.idLayout.setVisibility(View.GONE);
            binding.nameTV.setVisibility(View.GONE);
            binding.idTV.setVisibility(View.GONE);
        }

        binding.patientID.setText(location.getPatientId());
        binding.mrn.setText("MRN - ".concat(location.getUserName().split("@")[0]));
        binding.idEt.setText(location.getPatientId());
        binding.patientName.setText(location.getName());
        binding.notesEt.setText(location.getNote());
        binding.nameEt.setText(location.getName());
        binding.personType.setText(location.getPersonType());

        binding.idEt.setEnabled(false);
        binding.nameEt.setEnabled(false);
        binding.notesEt.setEnabled(false);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment supportMapFragment =  SupportMapFragment.newInstance();
        OnMapReadyCallback callback = new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng patientPosition = new LatLng(Double.parseDouble(location.getLatitude()),
                        Double.parseDouble(location.getLongitude()));
                googleMap.addMarker(new MarkerOptions().position(patientPosition).title("Marker in patient place"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(patientPosition, 16f));
            }
        };
        supportMapFragment.getMapAsync(callback);
        fm.beginTransaction().replace(binding.mapFrame.getId(), supportMapFragment).commit();
    }
}