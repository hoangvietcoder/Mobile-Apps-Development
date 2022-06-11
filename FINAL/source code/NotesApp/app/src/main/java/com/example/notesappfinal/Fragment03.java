package com.example.notesappfinal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;


public class Fragment03 extends Fragment {

    private TextView namef3, categoryf3;
    private LinearLayout btn_edit, btn_logout, btn_change, btn_about;
    private FirebaseAuth mAuth;
    NameModal modal;
    DatabaseReference nameRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String currentuid, name, category;
    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment03, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        modal = new NameModal();
        progressDialog = new ProgressDialog(getActivity());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentuid = user.getUid();


        namef3 = getActivity().findViewById(R.id.tv_namef3);
        categoryf3 = getActivity().findViewById(R.id.tv_category);
        btn_edit = getActivity().findViewById(R.id.edit_f3);
        btn_logout = getActivity().findViewById(R.id.logout_f3);
        btn_change = getActivity().findViewById(R.id.changepass_f3);
        btn_about = getActivity().findViewById(R.id.about_f3);

        nameRef = database.getReference("users");

        btn_about.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), About.class);
            startActivity(i);
        });


        btn_edit.setOnClickListener(view -> {
            showbottomsheet();
        });

        btn_change.setOnClickListener(view -> {
            changepass();
        });

        btn_logout.setOnClickListener(view -> {
            singOut();
        });

        nameRef.child(currentuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    name = (String) snapshot.child("name").getValue();
                    category = (String) snapshot.child("category").getValue();

                    namef3.setText(name);
                    categoryf3.setText(category);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void changepass() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.changepass_bottoms);

        EditText newpass = dialog.findViewById(R.id.newpass);
        EditText renewpass = dialog.findViewById(R.id.renewpass);
        Button btn_change = dialog.findViewById(R.id.btn_changepass);

        btn_change.setOnClickListener(view -> {
            String newpassString = newpass.getText().toString().trim();
            String renewpassString = renewpass.getText().toString().trim();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if(newpassString.equals(renewpassString)) {
                progressDialog.show();
                user.updatePassword(newpassString)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "change successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            } else {
                Toast.makeText(getActivity(), "Re pass don't match", Toast.LENGTH_SHORT).show();
            }

        });



        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.bottomanim;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void showbottomsheet() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.name_bottoms);

        EditText nameEt = dialog.findViewById(R.id.uname_et_f3);
        EditText categoryEt = dialog.findViewById(R.id.ucategory_et_f3);
        Button btn_update = dialog.findViewById(R.id.update_et_f3);

        nameEt.setText(name);
        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(currentuid)) {
                    btn_update.setText("Update");
                } else {
                    btn_update.setText("Add name");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nameRef.child(currentuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameEt.setText(name);
                categoryEt.setText(category);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_update.setOnClickListener(view -> {
            String name = nameEt.getText().toString().trim();
            String cate = categoryEt.getText().toString().trim();

            modal.setName(name);
            modal.setCategory(cate);
            modal.setSearch(name.toLowerCase());

            nameRef.child(currentuid).setValue(modal).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getActivity(), "Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.bottomanim;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void singOut() {
        mAuth.signOut();
        Intent i = new Intent(getActivity(), Login.class);
        startActivity(i);
    }
}
