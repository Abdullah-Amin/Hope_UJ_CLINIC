package com.example.hope_uj_clinic.my_flow;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

    FusedLocationProviderClient fusedLocationProviderClient;

    private PatientLocation patientLocation;
    private ActivityOthersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOthersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void send(View view) {
        fusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(OthersActivity.this);

        getSupportFragmentManager()
                .beginTransaction()
                .add(new TrackLocationDialog(new UserPermissionI() {
                    @Override
                    public void getPermission(boolean permission) {
                        if (permission){
                            getLocation();
                        }
                    }
                }), "track")
                .commit();

        String txtNote = binding.notesEt.getText().toString();
    }

    private void getLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkPermission()) {
                if (isLocationEnabled()) {
                    fusedLocationProviderClient.getLastLocation()
                            .addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {
                                        patientLocation =
                                                new PatientLocation(location.getLatitude(), location.getLongitude());
                                        Log.i("abdo", "onSuccess: "+ patientLocation.toString());
                                        binding.notesEt.setText((int) location.getLatitude());
                                    }else {
                                        requestNewLocation();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }else {
                    Toast.makeText(OthersActivity.this, "Location permission needed", Toast.LENGTH_SHORT).show();
                }
            }else {
                requestPermissions();
            }
        }
    }

    private void requestNewLocation() {
        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

    }

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Log.i("abdo", "onLocationResult: " + locationResult.getLocations());
            Location mLastLocation = locationResult.getLastLocation();
            patientLocation = new PatientLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    };

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }
}