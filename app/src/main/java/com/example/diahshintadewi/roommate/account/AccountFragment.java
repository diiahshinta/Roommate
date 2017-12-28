package com.example.diahshintadewi.roommate.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diahshintadewi.roommate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {
    Toolbar toolbar;
    private TextView name, email, phone;
    private AppCompatTextView editProfile, logout;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private String idUser, emailUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.account_tab, container, false);

        editProfile = (AppCompatTextView) rootView.findViewById(R.id.appCompatEditProfile);
        logout = (AppCompatTextView) rootView.findViewById(R.id.appCompatLogout);
        name = (TextView) rootView.findViewById(R.id.Name);
        email = (TextView) rootView.findViewById(R.id.Email);
        phone = (TextView) rootView.findViewById(R.id.Phone);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        idUser = user.getUid();
        emailUser = user.getEmail();
        loadData();


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountFragment.this.getActivity(), EditProfilActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(AccountFragment.this.getActivity(), Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

            }
        });

        return rootView;
    }

    private void loadData() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("user").child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null){
                    name.setText(user.getName());
                    email.setText(emailUser);
                    phone.setText(user.getPhone());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
