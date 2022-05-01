package com.example.lab3_ex2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyCustomAdapter extends ArrayAdapter<Item> {

    private final Context context;
    private final int layout;
    private final List<Item> data;

    public MyCustomAdapter(@NonNull Context context, int layout, @NonNull List<Item> data) {
        super(context, layout, data);
        this.context = context;
        this.layout = layout;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        ViewHolder viewHolder ;

        if(convertView == null) {
            view = LayoutInflater.from(context).inflate(layout, parent, false);

            viewHolder  = new ViewHolder();

            viewHolder.textView = view.findViewById(R.id.textViewTitle);
            viewHolder.buttonRemove = view.findViewById(R.id.buttonRemove);

            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        Item item = data.get(position);

        viewHolder.textView.setText(item.getTitle());
        viewHolder.buttonRemove.setText(item.getButtonRemove());

        viewHolder.buttonRemove.setOnClickListener(view1 -> {
            data.remove(position);
            notifyDataSetChanged();
        });

        view.setOnClickListener(view12 -> Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show());

        return view;
    }

    static class ViewHolder {
        private TextView textView;
        private Button buttonRemove;
    }
}