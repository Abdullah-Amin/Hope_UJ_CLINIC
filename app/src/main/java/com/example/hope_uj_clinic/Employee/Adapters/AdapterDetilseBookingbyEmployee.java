package com.example.hope_uj_clinic.Employee.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hope_uj_clinic.Patient.DataBooking;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.Patient.DataBooking;
import com.example.hope_uj_clinic.databinding.ItemDetilseDateBinding;

import java.util.ArrayList;

public class AdapterDetilseBookingbyEmployee extends RecyclerView.Adapter<AdapterDetilseBookingbyEmployee.ViewHolder> {
    ArrayList<DataBooking> list = new ArrayList<>();




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDetilseDateBinding bining1 = DataBindingUtil.inflate(inflater, R.layout.item_detilse_date, parent, false);

        return new ViewHolder(bining1);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String idPatient = list.get(position).getuser_id();
        String shortenedId = idPatient.substring(0, Math.min(idPatient.length(), 7));
        holder.Binding.idapp.setText(list.get(position).getAppid());
        holder.Binding.namedoctor.setText(list.get(position).getDoctor());
        holder.Binding.time.setText(list.get(position).getTime());
        holder.Binding.idpationt.setText(shortenedId);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setList(ArrayList<DataBooking> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ItemDetilseDateBinding Binding;


        public ViewHolder(ItemDetilseDateBinding binding) {
            super(binding.getRoot());
            this.Binding = binding;
        }
    }
}

