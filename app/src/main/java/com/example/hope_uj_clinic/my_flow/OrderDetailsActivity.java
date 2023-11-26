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

        binding.patientID.setText(location.getPatientId());
        binding.idEt.setText(location.getPatientId());
        binding.idEt.setEnabled(false);
        binding.nameEt.setEnabled(false);
        binding.notesEt.setEnabled(false);
        binding.personType.setText(location.getPersonType());

        View view = binding.mapFrame.getRootView();

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment supportMapFragment =  SupportMapFragment.newInstance();
        OnMapReadyCallback callback = new OnMapReadyCallback() {

            /**
             * Manipulates the map once available.
             * This callback is triggered when the map is ready to be used.
             * This is where we can add markers or lines, add listeners or move the camera.
             * In this case, we just add a marker near Sydney, Australia.
             * If Google Play services is not installed on the device, the user will be prompted to
             * install it inside the SupportMapFragment. This method will only be triggered once the
             * user has installed Google Play services and returned to the app.
             */
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