package com.example.hope_uj_clinic.Employee.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hope_uj_clinic.Employee.Activitys.OnClickKeyUser;
import com.example.hope_uj_clinic.Employee.models.DataUsers;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.Patient.DataBooking;
import com.example.hope_uj_clinic.databinding.ItemViewBookingBinding;

import java.util.ArrayList;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.ViewHolder> {
    private ArrayList<DataUsers> list = new ArrayList<>();
     OnClickKeyUser user;

    public AdapterUsers(OnClickKeyUser user) {
        this.user = user;
    }

    public void filterList(ArrayList<DataUsers> filterlist) {
        list = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemViewBookingBinding bining1 = DataBindingUtil.inflate(inflater, R.layout.item_view_booking, parent, false);

        return new ViewHolder(bining1);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Binding.namesection.setText("name : "+list.get(position).getName());
        holder.Binding.nameDoctor.setText("Nationality : "+list.get(position).getNationality());
        holder.Binding.date.setText("Gender : "+list.get(position).getGender());
        holder.Binding.numberapp.setText(list.get(position).getUserid().substring(0,7));
        holder.Binding.name.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.getUserid(list.get(position).getUserid());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setList(ArrayList<DataUsers> list) {
        this.list = list;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ItemViewBookingBinding Binding;


        public ViewHolder(ItemViewBookingBinding binding) {
            super(binding.getRoot());
            this.Binding = binding;
        }
    }
}
