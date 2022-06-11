package com.example.notesappfinal;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Fragment02 extends Fragment {

    EditText searchEtf2;
    Button create_f2;
    ImageButton sortBtn;
    RecyclerView recyclerView;
    DatabaseReference roomref, nameref, memberRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String currentuid, name, category, time;
    LinearLayoutManager linearLayoutManager;
    ModalRoom modalRoom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment02, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentuid = user.getUid();

        modalRoom = new ModalRoom();
        searchEtf2 = getActivity().findViewById(R.id.searchEtf2);
        create_f2 = getActivity().findViewById(R.id.create_f2);
        sortBtn = getActivity().findViewById(R.id.ib_sort_f2);
        recyclerView = getActivity().findViewById(R.id.rc_f2);

        roomref = database.getReference("rooms").child(currentuid);
        nameref = database.getReference("users");
        memberRef = database.getReference("members");

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        create_f2.setOnClickListener(view -> {
            createRoom();
        });

        sortBtn.setOnClickListener(view -> {

        });

        nameref.child(currentuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    name = (String) snapshot.child("name").getValue();
                    category = (String) snapshot.child("category").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchEtf2.addTextChangedListener(new TextWatcher() {
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

    private void createRoom() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.create_room_bs);

        EditText roomet = dialog.findViewById(R.id.Room_name);
        Button createbtn = dialog.findViewById(R.id.btn_createRoom);

        Calendar date = Calendar.getInstance();
        SimpleDateFormat currDate = new SimpleDateFormat("dd-MMM-yyyy");
        final String savedate = currDate.format(date.getTime());

        Calendar time1 = Calendar.getInstance();
        SimpleDateFormat currTime = new SimpleDateFormat("HH:mm:ss a");
        final String saveTime = currTime.format(time1.getTime());

        time = savedate+saveTime;

        createbtn.setOnClickListener(view -> {
            String roomname = roomet.getText().toString().trim();
            final String address = roomname+currentuid+System.currentTimeMillis();

            if(roomname != null) {
                modalRoom.setAddress(address);
                modalRoom.setAdminid(currentuid);
                modalRoom.setCreated(name);
                modalRoom.setSearch(roomname.toLowerCase());
                modalRoom.setRooname(roomname);
                modalRoom.setTime(time);
                modalRoom.setMembers("0");

                roomref.child(address).setValue(modalRoom);
                MemberModal memberModal = new MemberModal();

                memberModal.setCat(category);
                memberModal.setDate(savedate + "Time: "+ saveTime);
                memberModal.setName(name);
                memberModal.setStatus("admin");

                memberRef.child(address).child(currentuid).setValue(memberModal);

                Toast.makeText(getActivity(), "Created successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(getActivity(), "Please enter a name", Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.bottomanim;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onStart() {
        super.onStart();

        getTags();
    }

    private void getTags() {
        FirebaseRecyclerOptions<ModalRoom> options = new FirebaseRecyclerOptions.Builder<ModalRoom>()
                .setQuery(roomref, ModalRoom.class)
                .build();

        FirebaseRecyclerAdapter<ModalRoom, RoomVH> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ModalRoom, RoomVH>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull RoomVH holder, int position, @NonNull ModalRoom model) {
                        holder.setroom(getActivity(), model.getRooname(), model.getAdminid(), model.getAddress(), model.getTime(), model.getMembers(), model.getCreated(), model.getSearch());
                    }

                    @NonNull
                    @Override
                    public RoomVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.room_layout, parent,false);
                        return new RoomVH(view);
                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    private void search() {
        String query = searchEtf2.getText().toString().toLowerCase().trim();
        Query query1 = roomref.orderByChild("search").startAt(query).endAt(query+"uf0ff");

        FirebaseRecyclerOptions<ModalRoom> options = new FirebaseRecyclerOptions.Builder<ModalRoom>()
                .setQuery(query1, ModalRoom.class)
                .build();

        FirebaseRecyclerAdapter<ModalRoom, RoomVH> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ModalRoom, RoomVH>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull RoomVH holder, int position, @NonNull ModalRoom model) {
                        holder.setroom(getActivity(), model.getRooname(), model.getAdminid(), model.getAddress(), model.getTime(), model.getMembers(), model.getCreated(), model.getSearch());
                    }

                    @NonNull
                    @Override
                    public RoomVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.room_layout, parent,false);
                        return new RoomVH(view);
                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }
}
