package com.vpdev.ajp.ajp;

/**
 * Created by saifeddine on 22/07/17.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    public static DatabaseReference user_database;
    public static FirebaseAuth mAuth;
    public static String user_id;
    public static String total_depenses ;
    public static DatabaseReference mDatabase;
    public static TextView textView_nom;
    private TextView textView_nombre_de_sorties;
    private TextView textView_depenses;
    private Button button_ajouter_une_sortie;
    public static FirebaseDatabase database;
    public static String user_name, phone_number, email, nombre_sorties, depenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       this.Store();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        button_ajouter_une_sortie = (Button) findViewById(R.id.Button_ajouter_une_sortie);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        user_id = mAuth.getCurrentUser().getUid();
        user_database = mDatabase.child(user_id);
        database = FirebaseDatabase.getInstance();
        textView_nom = (TextView) findViewById(R.id.TextView_name);
        textView_nombre_de_sorties=(TextView) findViewById(R.id.TextView_nombre_de_sorties) ;
        textView_nom.setText(user_name);

        textView_depenses=(TextView)findViewById(R.id.TextView_depenses) ;
        textView_depenses.setText(depenses+" DT");
        textView_nombre_de_sorties.setText(nombre_sorties);



        button_ajouter_une_sortie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentregister = new Intent(ProfileActivity.this, SortieActivity.class);
                ProfileActivity.this.startActivity(intentregister); // start the register class .

            }
        });


    }
    static public void Store() {

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        user_id = mAuth.getCurrentUser().getUid();
        user_database = mDatabase.child(user_id);
        database = FirebaseDatabase.getInstance();
        DatabaseReference mRef_total = database.getReference().child("Toltal depenses");
        DatabaseReference mRef_name = database.getReference().child("users").child(user_id).child("Nom et prenom");
        DatabaseReference mRef_email = database.getReference().child("users").child(user_id).child("Email");
        DatabaseReference mRef_depenses = database.getReference().child("users").child(user_id).child("Depenses");
        DatabaseReference mRef_nombre_de_sorties = database.getReference().child("users").child(user_id).child("Nombre de sorties");
        DatabaseReference mRef_phone = database.getReference().child("users").child(user_id).child("Phone Number");
        // Name ..........
        mRef_total.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             total_depenses=dataSnapshot.getValue(String.class)    ;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }) ;
        mRef_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user_name = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Email..............

        mRef_email.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                email = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Depenses ...........

        mRef_depenses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                depenses = dataSnapshot.getValue(String.class);
            }
            //voila

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Nombre_sorties..............

        mRef_nombre_de_sorties.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nombre_sorties = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Phone Number ..............

        mRef_phone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phone_number = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}





