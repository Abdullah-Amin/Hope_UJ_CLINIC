package com.example.hope_uj_clinic.Patient.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.Patient.DataBooking;
import com.example.hope_uj_clinic.databinding.ItemViewBookingBinding;

import java.util.ArrayList;

public class AdapterViewBooking extends RecyclerView.Adapter<AdapterViewBooking.ViewHolder> {
    private ArrayList<DataBooking> bookingList = new ArrayList<>();

    // Method for filtering our RecyclerView items.
    public void filterList(ArrayList<DataBooking> filterList) {
        bookingList.clear();
        bookingList.addAll(filterList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemViewBookingBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_view_booking, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataBooking booking = bookingList.get(position);
        holder.binding.namesection.setText(booking.getClinics());
        holder.binding.nameDoctor.setText(booking.getDoctor());
        holder.binding.date.setText(booking.getDate());
        holder.binding.time.setText(booking.getTime());
        holder.binding.numberapp.setText(booking.getAppid());
        holder.binding.name.setText(booking.getName());

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public void setBookingList(ArrayList<DataBooking> bookingList) {
        this.bookingList = bookingList;
        notifyDataSetChanged();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemViewBookingBinding binding;

        public ViewHolder(ItemViewBookingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}