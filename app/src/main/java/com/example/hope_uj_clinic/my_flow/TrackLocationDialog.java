package com.example.hope_uj_clinic.my_flow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.FragmentTrackLocationDialogBinding;
import com.google.android.gms.location.FusedLocationProviderClient;

public class TrackLocationDialog extends DialogFragment {

    private FusedLocationProviderClient client;
    private FragmentTrackLocationDialogBinding binding;
    private UserPermissionI userPermissionI;
    public TrackLocationDialog(UserPermissionI userPermissionI) {
        this.userPermissionI = userPermissionI;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrackLocationDialogBinding.inflate(LayoutInflater.from(
                requireContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        binding.otherBtn.setOnClickListener(view1 -> {
            userPermissionI.getPermission(binding.checkbox.isChecked());
            checkLocation();
            getDialog().dismiss();
        });
    }

    private void checkLocation() {
        if(!locationIsEnabled()){
            Toast.makeText(requireContext(), "Please enable location ", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean locationIsEnabled() {
        return false;
    }
}