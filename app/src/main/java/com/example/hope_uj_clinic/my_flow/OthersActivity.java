package com.example.hope_uj_clinic.my_flow;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.Employee.models.PatientLocation;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityOthersBinding;
import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class OthersActivity extends AppCompatActivity {

    FusedLocationProviderClient client;

    private PatientLocation patientLocation;
    private ActivityOthersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOthersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        client = LocationServices
                .getFusedLocationProviderClient(OthersActivity.this);

    }

    public void send(View view) {

        getSupportFragmentManager()
                .beginTransaction()
                .add(new TrackLocationDialog(new UserPermissionI() {
                    @Override
                    public void getPermission(boolean permission) {
                        if (permission) {
                            if (!isLocationEnabled()){
                                Toast.makeText(OthersActivity.this, "Please enable location ", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (ActivityCompat.checkSelfPermission(OthersActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                    && ActivityCompat.checkSelfPermission(OthersActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions();
                                return;
                            }
                            client.getLastLocation()
                                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            if (location == null){
                                                requestNewLocationData();
                                                return;
                                            }
                                            patientLocation = new PatientLocation(location.getLatitude(), location.getLongitude());
                                            binding.notesEt.setText((int) location.getLatitude());
                                            Log.i("abdo", "onSuccess: " + location.getLatitude());
                                            DatabaseHelper db = new DatabaseHelper(OthersActivity.this);
                                            db.insertNewOthersOrder(binding.idEt.getText().toString().isEmpty() ? " " : binding.idEt.getText().toString(),
                                                    binding.nameEt.getText().toString().isEmpty() ? " " : binding.nameEt.getText().toString(),
                                                    binding.notesEt.getText().toString().isEmpty() ? " " : binding.notesEt.getText().toString(),
                                                    location.getLatitude(), location.getLongitude());
                                        }
                                    });
                        }
                    }
                }), "track")
                .commit();

        String txtNote = binding.notesEt.getText().toString();
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        client = LocationServices.getFusedLocationProviderClient(this);
        client.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Log.i("abdo", "onLocationResult: " + mLastLocation.getLatitude());
            patientLocation = new PatientLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());

        }
    };

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                client.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                Log.i("abdo", "onSuccess: " + location.getLatitude());
                                patientLocation = new PatientLocation(location.getLatitude(), location.getLongitude());
                            }
                        });
            }
        }
    }
}