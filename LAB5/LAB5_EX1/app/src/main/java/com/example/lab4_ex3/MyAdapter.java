package com.example.lab4_ex3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private final Context context;
    private final List<Phone> phones;

    public MyAdapter(Context context, List<Phone> phones) {
        this.context = context;
        this.phones = phones;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Phone phone = phones.get(position);
        holder.title.setText(phone.getTitle());
        holder.checkBox.setChecked(phone.isChecked());

        holder.title.setOnClickListener(view -> Toast.makeText(context, phone.getTitle(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkBox;
        private final TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
            title = itemView.findViewById(R.id.textView);

            checkBox.setOnClickListener(view -> phones.get(getAdapterPosition()).setChecked(!phones.get(getAdapterPosition()).isChecked()));
        }
    }
}

