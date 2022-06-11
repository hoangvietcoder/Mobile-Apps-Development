package com.example.notesappfinal;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Fragment01 extends Fragment {

    private EditText search_note;
    private Button btn_more;
    private RecyclerView recyclerView;
    private FloatingActionButton fone, ftwo, fthree, ffour;
    private Float translationYaxis = 100f;
    private Boolean menuOpen = false;
    OvershootInterpolator interpolator = new OvershootInterpolator();
    Uri imageuri;
    DatabaseReference notesref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment01, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_more = getActivity().findViewById(R.id.btn_search);
        search_note = getActivity().findViewById(R.id.search_note);
        recyclerView = getActivity().findViewById(R.id.rv_f01);
        fone = getActivity().findViewById(R.id.fab1);
        ftwo = getActivity().findViewById(R.id.fab2);
        fthree = getActivity().findViewById(R.id.fab3);
        ffour = getActivity().findViewById(R.id.fab4);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();

        notesref = FirebaseDatabase.getInstance().getReference("Notes").child(currentuid);

        showMenu();

        btn_more.setOnClickListener(view -> {
            switch (view.getId()) {
                case R.id.btn_search:
                    sortBottomsheet();
            }

        });

        search_note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                search();
            }
        });

    }

    private void sortBottomsheet() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sort_bottom);

        TextView onlytext = dialog.findViewById(R.id.only_text);
        TextView onlyimage = dialog.findViewById(R.id.only_image);
        TextView all = dialog.findViewById(R.id.all);

        all.setOnClickListener(view -> {
            String type = "";
            sorting(type);
            dialog.dismiss();
        });


        onlytext.setOnClickListener(view -> {
            String type = "TXT";
            sorting(type);
            dialog.dismiss();
        });

        onlyimage.setOnClickListener(view -> {
            String type = "IMG";
            sorting(type);
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.bottomanim;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void showMenu() {

        fone.setAlpha(0f);
        ftwo.setAlpha(0f);
        fthree.setAlpha(0f);

        fone.setTranslationY(translationYaxis);
        ftwo.setTranslationY(translationYaxis);
        fthree.setTranslationY(translationYaxis);

        ffour.setOnClickListener(view -> {
           if(menuOpen) {
               closeMenu();
           } else {
               onpenMenu();
           }
        });

        fone.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), Test.class);
            startActivity(i);
            closeMenu();
        });

        ftwo.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), DrawActivity.class);
            startActivity(i);
            closeMenu();
        });

        fthree.setOnClickListener(view -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(i, 1);
            closeMenu();
        });


    }

    private void onpenMenu() {
        menuOpen = !menuOpen;
        ffour.setImageResource(R.drawable.ic_baseline_close_24);
        fone.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        ftwo.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fthree.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
    }


    private void closeMenu() {
        menuOpen = !menuOpen;
        ffour.setImageResource(R.drawable.ic_baseline_add_24);
        fone.animate().translationY(translationYaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        ftwo.animate().translationY(translationYaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fthree.animate().translationY(translationYaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if(requestCode == 1 || resultCode == RESULT_OK || data != null || data.getData() != null) {
                imageuri = data.getData();

                if(requestCode == 1) {
                    String uri = imageuri.toString();
                    Intent i = new Intent(getActivity(), Images.class);
                    i.putExtra("u", uri);
                    startActivity(i);
                }

            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), ""+ e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getnotes() {
        FirebaseRecyclerOptions<Notemember> options = new FirebaseRecyclerOptions.Builder<Notemember>()
                .setQuery(notesref, Notemember.class)
                .build();

        FirebaseRecyclerAdapter<Notemember, NotesVH> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Notemember, NotesVH>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull NotesVH holder, int position, @NonNull Notemember model) {
                        holder.setnote(getActivity(), model.getTitle(), model.getNotes(), model.getSearch(), model.getUrl(), model.getDelete(), model.getType());

                        String postKey = getRef(position).getKey();
                        String notes = getItem(position).getNotes();
                        String title = getItem(position).getTitle();
                        String url = getItem(position).getUrl();
                        String delete = getItem(position).getDelete();
                        String type = getItem(position).getType();


                        holder.moreoptionsbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                moreoptions(postKey, title, notes, url, delete, type);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public NotesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.notes_layout, parent,false);
                        return new NotesVH(view);
                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    private void moreoptions(String postKey, String title, String notes, String url, String delete, String type) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.more_options);

        TextView edittv = dialog.findViewById(R.id.tv_edit);
//        TextView forwardtv = dialog.findViewById(R.id.tv_forward);
//        TextView downloadtv  = dialog.findViewById(R.id.tv_download);
        TextView deletetv = dialog.findViewById(R.id.tv_delete);

//        if(type.equals("TXT")) {
//            downloadtv.setVisibility(View.GONE);
//        } else if (type.equals("IMG")) {
//            downloadtv.setVisibility(View.VISIBLE);
//        }

        edittv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editDialog(postKey, title, notes);
                dialog.dismiss();

            }
        });

//        forwardtv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });


        deletetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();

                if(type.equals("IMG")) {
                    Query query = notesref.orderByChild("delete").equalTo(delete);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                dataSnapshot.getRef().removeValue();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(url);
                    reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                } else if(type.equals("TXT")) {
                    Query query2 = notesref.orderByChild("delete").equalTo(delete);
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                dataSnapshot.getRef().removeValue();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }


//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Delete");
//                builder.setMessage("Are you sure to delete?");
//                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    };
//                });

            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.bottomanim;
        dialog.getWindow().setGravity(Gravity.BOTTOM);



    }

    private void editDialog(String postKey, String title, String notes) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.edit_layput, null);

        EditText titleet = view.findViewById(R.id.edit_et_title);
        EditText noteet = view.findViewById(R.id.edit_et_notes);
        Button btn_update = view.findViewById(R.id.btn_update);

        titleet.setText(title);
        noteet.setText(notes);



        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        alertDialog.show();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<>();
                map.put("title", titleet.getText().toString());
                map.put("notes", noteet.getText().toString());
                map.put("search", titleet.getText().toString().toLowerCase());

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currentuid = user.getUid();

                FirebaseDatabase.getInstance().getReference()
                        .child("Notes").child(currentuid)
                        .child(postKey)
                        .updateChildren(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });
            }
        });


    }

    private void search() {
        String query = search_note.getText().toString().toLowerCase().trim();
        Query query1 = notesref.orderByChild("search").startAt(query).endAt(query+"uf0ff");

        FirebaseRecyclerOptions<Notemember> options = new FirebaseRecyclerOptions.Builder<Notemember>()
                .setQuery(query1, Notemember.class)
                .build();

        FirebaseRecyclerAdapter<Notemember, NotesVH> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Notemember, NotesVH>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull NotesVH holder, int position, @NonNull Notemember model) {
                        holder.setnote(getActivity(), model.getTitle(), model.getNotes(), model.getSearch(), model.getUrl(), model.getDelete(), model.getType());


                    }

                    @NonNull
                    @Override
                    public NotesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.notes_layout, parent,false);
                        return new NotesVH(view);
                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    private void sorting(String type) {
        Query query1 = notesref.orderByChild("type").startAt(type).endAt(type+"uf0ff");

        FirebaseRecyclerOptions<Notemember> options = new FirebaseRecyclerOptions.Builder<Notemember>()
                .setQuery(query1, Notemember.class)
                .build();

        FirebaseRecyclerAdapter<Notemember, NotesVH> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Notemember, NotesVH>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull NotesVH holder, int position, @NonNull Notemember model) {
                        holder.setnote(getActivity(), model.getTitle(), model.getNotes(), model.getSearch(), model.getUrl(), model.getDelete(), model.getType());
                    }

                    @NonNull
                    @Override
                    public NotesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.notes_layout, parent,false);
                        return new NotesVH(view);
                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }

    @Override
    public void onStart() {
        super.onStart();
        
        getnotes();
    }
    
}
