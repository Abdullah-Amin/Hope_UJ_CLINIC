package com.example.hope_uj_clinic.my_flow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityYourselfBinding;

public class YourselfActivity extends AppCompatActivity {

    private ActivityYourselfBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityYourselfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void send(View view) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(new TrackLocationDialog(new UserPermissionI() {
                    @Override
                    public void getPermission(boolean permission) {

                    }
                }), "track")
                .commit();
        String txtNote = binding.notesEt.getText().toString();
    }
}