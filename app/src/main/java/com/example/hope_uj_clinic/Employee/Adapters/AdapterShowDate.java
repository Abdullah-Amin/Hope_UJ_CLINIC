package com.example.hope_uj_clinic.Employee.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hope_uj_clinic.Employee.Activitys.OnClickgetDate;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ItemDateCliencBinding;

import java.util.ArrayList;

public class AdapterShowDate extends RecyclerView.Adapter<AdapterShowDate.ViewHolder> {
    ArrayList<String> list = new ArrayList<>();

    OnClickgetDate user;

    public AdapterShowDate(OnClickgetDate user) {
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDateCliencBinding bining1 = DataBindingUtil.inflate(inflater, R.layout.item_date_clienc, parent, false);

        return new ViewHolder(bining1);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Binding.date.setText(list.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    user.getDate(list.get(clickedPosition));
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setList(ArrayList<String> list) {

        this.list = list;
        notifyDataSetChanged();

    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ItemDateCliencBinding Binding;


        public ViewHolder(ItemDateCliencBinding binding) {
            super(binding.getRoot());
            this.Binding = binding;
        }
    }
}
