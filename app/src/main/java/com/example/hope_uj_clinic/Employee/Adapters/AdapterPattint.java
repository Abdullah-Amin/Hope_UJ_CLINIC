package com.example.hope_uj_clinic.Employee.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hope_uj_clinic.Admin.ModelUser;
import com.example.hope_uj_clinic.Employee.Activitys.OnClickItemPationt;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ItemPationtBinding;
import java.util.ArrayList;

public class AdapterPattint extends RecyclerView.Adapter<AdapterPattint.ViewHolder> {
    private ArrayList<ModelUser> list = new ArrayList<>();
    private OnClickItemPationt onClickItemPationt;

    public AdapterPattint(OnClickItemPationt onClickItemPationt) {
        this.onClickItemPationt = onClickItemPationt;
    }

    public ArrayList<ModelUser> getList() {
        return list;
    }

    public void setList(ArrayList<ModelUser> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemPationtBinding binding = ItemPationtBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelUser user = list.get(position);
        holder.binding.name.setText(user.getName());
        holder.binding.textView.setText(user.getUserid().substring(0, 7));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemPationt.getdata(user.getKey(), user.getName(), user.getUserid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(ArrayList<ModelUser> filterList) {
        list = filterList;
        notifyDataSetChanged();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPationtBinding binding;

        public ViewHolder(ItemPationtBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}