package com.example.hope_uj_clinic.my_flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.Employee.models.PatientLocation;
import com.example.hope_uj_clinic.databinding.ActivityYourselfBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class YourselfActivity extends AppCompatActivity {

    FusedLocationProviderClient client;
    private ActivityYourselfBinding binding;
    private boolean isDialogVisible = true;

    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityYourselfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        client = LocationServices
                .getFusedLocationProviderClient(YourselfActivity.this);

        userId = getIntent().getStringExtra("user_id");
    }

    public void send(View view) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(new TrackLocationDialog(permission -> {
                    if (permission) {
                        if (!isLocationEnabled()){
                            Toast.makeText(YourselfActivity.this, "Please enable location ", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (ActivityCompat.checkSelfPermission(YourselfActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(YourselfActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions();
                            return;
                        }
                        client.getLastLocation()
                                .addOnSuccessListener(location -> {
                                    if (location == null){
                                        requestNewLocationData();
                                        return;
                                    }
                                    Log.i("abdo", "onSuccess: " + location.getLatitude());
                                    DatabaseHelper db = new DatabaseHelper(YourselfActivity.this);
                                    db.insertNewOrder(
                                            patientLocation().getName(), patientLocation().getPatientId(),
                                            binding.notesEt.getText().toString().isEmpty() ? " " : binding.notesEt.getText().toString(),
                                            "yourself", location.getLatitude(), location.getLongitude()
                                    );
                                    Toast.makeText(YourselfActivity.this, "Order sent successfully", Toast.LENGTH_LONG).show();
                                    isDialogVisible = false;
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(YourselfActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    }else {
                        Toast.makeText(this, "Location must be provided\nplease enable location", Toast.LENGTH_SHORT).show();
                    }
                }), "track")
                .commit();
        if (!isDialogVisible){
            Intent intent = new Intent(YourselfActivity.this, EmergencyActivity.class);
            intent.putExtra("userType", "yourself");
            startActivity(intent);
            finish();
        }
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        client = LocationServices.getFusedLocationProviderClient(this);
        client.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Log.i("abdo", "onLocationResult: " + mLastLocation.getLatitude());
            Log.i("abdo", "onSuccess: " + mLastLocation.getLatitude());
            DatabaseHelper db = new DatabaseHelper(YourselfActivity.this);
            db.insertNewOrder(
                    patientLocation().getName(), patientLocation().getPatientId(),
                    binding.notesEt.getText().toString().isEmpty() ? " " : binding.notesEt.getText().toString(),
                    "yourself", mLastLocation.getLatitude(), mLastLocation.getLongitude());
            Toast.makeText(YourselfActivity.this, "Order sent successfully", Toast.LENGTH_LONG).show();
            isDialogVisible = false;
            if (!isDialogVisible){
                Intent intent = new Intent(YourselfActivity.this, EmergencyActivity.class);
                intent.putExtra("userType", "yourself");
                startActivity(intent);
                finish();
            }
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
            if (grantResults.length > 0 && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {
                client.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                Log.i("abdo", "onSuccess: " + location.getLatitude());
                                DatabaseHelper db = new DatabaseHelper(YourselfActivity.this);
                                db.insertNewOrder(
                                        patientLocation().getName(), patientLocation().getPatientId(),
                                        binding.notesEt.getText().toString().isEmpty() ? " " : binding.notesEt.getText().toString(),
                                        "yourself", location.getLatitude(), location.getLongitude());
                                Toast.makeText(YourselfActivity.this, "Order sent successfully", Toast.LENGTH_LONG).show();
                                isDialogVisible = false;
                                if (!isDialogVisible){
                                    Intent intent = new Intent(YourselfActivity.this, EmergencyActivity.class);
                                    intent.putExtra("userType", "yourself");
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(YourselfActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }
    }
    public PatientLocation patientLocation(){

//        userId = userId.split(".")[0];

        DatabaseHelper db = new DatabaseHelper(YourselfActivity.this);
        Cursor cursor = db.getUser(userId);

        Log.i("abdo", "patientLocation: cursor " + userId);

        PatientLocation patientLocation = null;

        if (cursor.moveToNext()){
            Log.i("abdo", "patientLocation: cursor " + cursor.getString(0));
            patientLocation =
                    new PatientLocation(
                            cursor.getString(8),
                            "",
                            cursor.getString(1),
                            "",
                            cursor.getString(6),
                            "", "");
        }
        return patientLocation;
    }
}