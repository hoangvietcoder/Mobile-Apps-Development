package com.example.lab4_ex2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final Context context;
    private final List<Item> items;

    public MyAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);

        return new MyViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Item item = items.get(position);

        holder.title.setText(item.getName());
        holder.title.setOnClickListener(view -> Toast.makeText(context, item.getName(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private MyAdapter adapter;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textViewTitle);

            itemView.findViewById(R.id.buttonRemove).setOnClickListener(view -> {
                adapter.items.remove(getAdapterPosition());
                adapter.notifyItemRemoved(getAdapterPosition());
            });
        }

        public MyViewHolder linkAdapter(MyAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }
}
