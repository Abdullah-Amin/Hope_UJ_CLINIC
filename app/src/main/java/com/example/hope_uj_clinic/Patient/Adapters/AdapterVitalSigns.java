package com.example.hope_uj_clinic.Patient.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.Patient.DataBooking;
import com.example.hope_uj_clinic.Patient.ModelVitalSigns;
import com.example.hope_uj_clinic.databinding.ItemViewBookingBinding;
import com.example.hope_uj_clinic.databinding.ItemVitalSignsBinding;

import java.util.ArrayList;

public class AdapterVitalSigns extends RecyclerView.Adapter<AdapterVitalSigns.ViewHolder> {
    ArrayList<ModelVitalSigns> list = new ArrayList<>();




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemVitalSignsBinding bining1 = DataBindingUtil.inflate(inflater, R.layout.item_vital_signs, parent, false);

        return new ViewHolder(bining1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Binding.Date.setText(list.get(position).getDate());
        holder.Binding.rate.setText(list.get(position).getRate());
        holder.Binding.time.setText(list.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setList(ArrayList<ModelVitalSigns> list) {
        this.list = list;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ItemVitalSignsBinding Binding;


        public ViewHolder(ItemVitalSignsBinding binding) {
            super(binding.getRoot());
            this.Binding = binding;
        }
    }
}
