package com.example.lab3_ex3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PhoneAdapter extends ArrayAdapter<Phone> {

    private final Context context;
    private final int layout;
    private final List<Phone> data;

    public PhoneAdapter(@NonNull Context context, int layout, @NonNull List<Phone> data) {
        super(context, layout, data);
        this.context = context;
        this.layout = layout;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(layout, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.icon = view.findViewById(R.id.imageView);
            viewHolder.textView = view.findViewById(R.id.textView);
            viewHolder.checkBox = view.findViewById(R.id.checkBox);

            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        Phone phone = data.get(position);

        viewHolder.icon.setImageResource(phone.getIcon());
        viewHolder.textView.setText(phone.getName());
        viewHolder.checkBox.setChecked(phone.isChecked());

        viewHolder.checkBox.setOnClickListener(view1 -> data.get(position).setChecked(!data.get(position).isChecked()));
        view.setOnClickListener(view12 -> Toast.makeText(context, phone.getName(), Toast.LENGTH_SHORT).show());

        return view;
    }

    static class ViewHolder {

        private ImageView icon;
        private TextView textView;
        private CheckBox checkBox;

    }
}
