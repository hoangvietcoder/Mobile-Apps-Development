package com.example.lab4_ex4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final List<PC> pcList;
    private MyPCClickListener listener;

    public void setPCListener(MyPCClickListener listener) {
        this.listener = listener;
    }

    public MyAdapter(List<PC> pcList) {
        this.pcList = pcList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        PC pc = pcList.get(position);
        holder.bind(pc, listener);
    }

    @Override
    public int getItemCount() {
        return pcList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView icon;
        private final TextView title;

        public void bind(PC pc, MyPCClickListener listener) {
            title.setText(pc.getLabel());

            if (pc.isOn()) {
                icon.setImageResource(R.drawable.monitor_on);
            }
            else {
                icon.setImageResource(R.drawable.monitor_off);
            }

            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onMyPCClicked(pc, getAdapterPosition());
                }
            });
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
        }
    }
}
