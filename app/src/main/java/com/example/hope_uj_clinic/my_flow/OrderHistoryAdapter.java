package com.example.hope_uj_clinic.my_flow;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hope_uj_clinic.Employee.models.PatientLocation;
import com.example.hope_uj_clinic.databinding.ItemOrderBinding;

import java.util.ArrayList;
import java.util.Objects;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.Holder> {

    private ArrayList<PatientLocation> patientLocation;
    private OrderDetailsI orderDetailsI;
    private String userType;

    public OrderHistoryAdapter(ArrayList<PatientLocation> patientLocation,
                               String userType,
                               OrderDetailsI orderDetailsI) {
        this.patientLocation = patientLocation;
        this.orderDetailsI = orderDetailsI;
        this.userType = userType;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemOrderBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.binding.orderId.setText(patientLocation.get(position).getOrderId());
        String userName = patientLocation.get(position).getUserName().split("@")[0];
        holder.binding.mrn.setText("MRN - ".concat(userName));
        holder.binding.orderStatus.setText("Order Status: ".concat(patientLocation.get(position).getOrderState()));
        if (userType.equals("employee")) {
            Log.i("abdo", "onBindViewHolder: " + patientLocation.get(position).getPersonType());
            if (patientLocation.get(position).getPersonType().equals("yourself")) {
                holder.binding.orderFor.setText("Order For: himself");
                patientLocation.get(position).setPersonType("himself");
            }else {
                holder.binding.orderFor.setText("Order For: ".concat(patientLocation.get(position).getPersonType()));
            }
        }else {
            holder.binding.orderFor.setText("Order For: ".concat(patientLocation.get(position).getPersonType()));
//            patientLocation.get(position).setPersonType("yourself");
        }

        holder.itemView.setOnClickListener(view -> {
            orderDetailsI.getOrder(patientLocation.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return patientLocation.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemOrderBinding binding;

        public Holder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
