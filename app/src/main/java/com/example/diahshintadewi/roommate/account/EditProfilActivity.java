package com.example.diahshintadewi.roommate.account;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.example.diahshintadewi.roommate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfilActivity extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = EditProfilActivity.this;

    private TextInputEditText name, phone;
    private AppCompatTextView deleteAccount, save, email;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private String idUser, emailUser;
    ProgressDialog dialog;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        name = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        phone = (TextInputEditText) findViewById(R.id.textInputEditTextPhone);
        email = (AppCompatTextView) findViewById(R.id.email);
        deleteAccount = (AppCompatTextView) findViewById(R.id.appCompatDeleteAccount);
        save = (AppCompatTextView) findViewById(R.id.appCompatSave);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        FirebaseUser user = auth.getCurrentUser();
        idUser = user.getUid();
        emailUser = user.getEmail();
        dialog = new ProgressDialog(this);

        email.setText(emailUser);

        save.setOnClickListener(this);

        }

        @Override
    public void onClick(View v){
            if(v == save){
                saveData();
                finish();
            }
            if (v == deleteAccount){
                delete();
                finish();
            }
        }

    private void delete() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
             dialog.setMessage("Delete account?");
            dialog.show();
            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Account deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Account could not be deleted", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void saveData(){
            String nama = name.getText().toString().trim();
            String telpon = phone.getText().toString().trim();

            if(nama.isEmpty()){
                name.setError("*Required");
            }
            if(telpon.isEmpty()){
                phone.setError("*Required");
            }

            databaseReference.child(idUser).child("Name").setValue(nama);
            databaseReference.child(idUser).child("Phone").setValue(telpon);
            Toast.makeText(activity, "Data Saved.", Toast.LENGTH_SHORT).show();

            AccountFragment Fragment = new AccountFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.nestedScrollView, Fragment);
            fragmentTransaction.commit();
            finish();
        }
}
