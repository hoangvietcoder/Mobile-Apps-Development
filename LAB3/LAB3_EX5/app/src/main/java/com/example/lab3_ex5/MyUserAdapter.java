package com.example.lab3_ex5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyUserAdapter extends ArrayAdapter<User> {

    private final Context context;
    private final int layout;
    private final List<User> data;

    public MyUserAdapter(@NonNull Context context, int layout, @NonNull List<User> data) {
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

            viewHolder.textViewName = view.findViewById(R.id.textViewName);
            viewHolder.textViewEmail = view.findViewById(R.id.textViewEmail);

            view.setTag(viewHolder);
            
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        User user = data.get(position);

        viewHolder.textViewName.setText(user.getName());
        viewHolder.textViewEmail.setText(user.getEmail());

        view.setOnClickListener(view1 -> Toast.makeText(context, user.getName(), Toast.LENGTH_SHORT).show());

        return view;
    }

    static class ViewHolder {

        private TextView textViewName;
        private TextView textViewEmail;

    }
}
