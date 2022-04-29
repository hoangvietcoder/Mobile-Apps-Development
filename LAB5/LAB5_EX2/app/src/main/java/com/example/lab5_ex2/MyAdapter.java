package com.example.lab5_ex2;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final List<Event> data;
    private final Context context;

    private int position;

    public MyAdapter(Context context, List<Event> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Event event = data.get(position);
        holder.name.setText(event.getName());
        holder.place.setText(event.getPlace());
        holder.dateTime.setText(String.format("%s %s", event.getDate(), event.getTime()));
        holder.switchCompat.setChecked(false);

        holder.itemView.setOnLongClickListener(view -> {
            setPosition(holder.getAdapterPosition());
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private final SwitchCompat switchCompat;
        private final TextView name;
        private final TextView place;
        private final TextView dateTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            switchCompat = itemView.findViewById(R.id.switchUI);
            name = itemView.findViewById(R.id.TextView_Title);
            place = itemView.findViewById(R.id.TextView_Location);
            dateTime = itemView.findViewById(R.id.TextView_DataTime);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(Menu.NONE, R.id.menu_delete, Menu.NONE, R.string.delete);
            contextMenu.add(Menu.NONE, R.id.menu_edit, Menu.NONE, R.string.edit);
        }
    }
}

