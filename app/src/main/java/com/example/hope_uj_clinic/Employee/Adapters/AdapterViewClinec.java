package com.example.hope_uj_clinic.Employee.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hope_uj_clinic.Employee.Activitys.OnClickgetDate;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.Patient.DataBooking;
import com.example.hope_uj_clinic.Patient.DataClinics;
import com.example.hope_uj_clinic.databinding.ItemCliencBinding;
import com.example.hope_uj_clinic.databinding.ItemViewBookingBinding;

import java.util.ArrayList;

public class AdapterViewClinec extends RecyclerView.Adapter<AdapterViewClinec.ViewHolder> {
    ArrayList<DataClinics> list = new ArrayList<>();

    OnClickgetDate date;

    public AdapterViewClinec(OnClickgetDate date) {
        this.date = date;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCliencBinding bining1 = DataBindingUtil.inflate(inflater, R.layout.item_clienc, parent, false);

        return new ViewHolder(bining1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Binding.text.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    date.getDate(list.get(clickedPosition).getName());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setList(ArrayList<DataClinics> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCliencBinding Binding;


        public ViewHolder(ItemCliencBinding binding) {
            super(binding.getRoot());
            this.Binding = binding;
        }
    }
}
