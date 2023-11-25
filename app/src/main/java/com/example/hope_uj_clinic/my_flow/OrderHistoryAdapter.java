package com.example.hope_uj_clinic.my_flow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hope_uj_clinic.Employee.models.PatientLocation;
import com.example.hope_uj_clinic.databinding.ItemOrderBinding;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.Holder> {

    private ArrayList<PatientLocation> patientLocation;
    private OrderDetailsI orderDetailsI;

    public OrderHistoryAdapter(ArrayList<PatientLocation> patientLocation, OrderDetailsI orderDetailsI){
        this.patientLocation = patientLocation;
        this.orderDetailsI = orderDetailsI;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemOrderBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.binding.orderId.setText(patientLocation.get(position).getPatientId());
        holder.binding.orderStatus.setText(patientLocation.get(position).getPersonType());

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
