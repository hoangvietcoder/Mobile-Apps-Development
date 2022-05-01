package com.example.ex2_lab5;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.eventHolder> {


    private final MainActivity context;
    private final List<Events> data;
    private final Database database;

    public CustomAdapter(MainActivity context, List<Events> event) {
        this.context = context;
        this.data = event;
        this.database = new Database(context, "ex3lab6.sqlite", null, 1);
    }

    @NonNull
    @Override
    public eventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_custom_rows, parent, false);
        return new eventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull eventHolder holder, @SuppressLint("RecyclerView") int position) {
        Events event = data.get(position);
        holder.title.setText(event.getTitle());
        holder.room.setText(event.getRoom());
        holder.time.setText(event.getTime());
        holder.checked.setChecked(event.getChecked());

        holder.checked.setOnCheckedChangeListener((compoundButton, isChecked) -> {
//                Log.e("test",""+event.getId());
            String cond;
            if (isChecked) {
                cond = "checked = 1 WHERE id = " + event.getId() + "";
                holder.checked.setChecked(true);
            } else {
                cond = "checked = 0 WHERE id = " + event.getId() + "";
                holder.checked.setChecked(false);
            }
            Log.e("test", "adapter : " + holder.checked.isChecked());
            updateItem(cond);

        });


    }

    private void updateItem(String cond) {
        try {
            database.QueryData("UPDATE ghichu " +
                    "SET " + cond + "");
        } catch (Exception e) {
            Log.e("msg", "error : " + e);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class eventHolder extends RecyclerView.ViewHolder {

        private final SwitchCompat checked;
        private final TextView title;
        private final TextView room;
        private final TextView time;

        public eventHolder(@NonNull View itemView) {
            super(itemView);

            this.checked = itemView.findViewById(R.id.switchUI);
            this.title = itemView.findViewById(R.id.TextView_Title);
            this.room = itemView.findViewById(R.id.TextView_Location);
            this.time = itemView.findViewById(R.id.TextView_DataTime);

            itemView.setOnLongClickListener(view -> {
                itemView.setOnCreateContextMenuListener((contextMenu, newview, contextMenuInfo) -> {
                    contextMenu.add(0, newview.getId(), 0, "Delete").setOnMenuItemClickListener(menuItem -> {

//                            DELETE_ITEM("DELETE FROM ghichu WHERE id = ");
                        Log.e("test", "item id: " + itemView.getId());
                        return false;
                    });
                    contextMenu.add(0, newview.getId(), 0, "Edit");
                });
                return false;
            });

        }

    }
}
