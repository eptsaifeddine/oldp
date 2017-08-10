package com.vpdev.ajp.ajp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by saifeddine on 03/07/17.
 */




public class RegsiterActivity extends AppCompatActivity implements View.OnClickListener {
    static String user_id  ;
    private FirebaseAuth mAuth ;
    private EditText editTextemail;
    private EditText editTextpassword;
    private EditText editTextname ;
    private EditText editTextphonenumber  ;
    private Button registerButton  ;
    private DatabaseReference mDatabase ;
    private ProgressDialog progressDialog  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        {
            setContentView(R.layout.activity_register);
            mAuth=FirebaseAuth.getInstance() ;
            progressDialog=new ProgressDialog(this) ;
            progressDialog.setMessage("Creating user ....");

            editTextemail=(EditText) findViewById(R.id.EditTextEmail) ;
            editTextpassword =(EditText) findViewById(R.id.EditTextpassword)  ;
            editTextname=(EditText)findViewById(R.id.EditTextusername) ;
            editTextphonenumber=(EditText) findViewById(R.id.EditTextphonenumber)  ;
            registerButton=(Button) findViewById(R.id.registerButton) ;
            registerButton.setOnClickListener(this);
            mDatabase= FirebaseDatabase.getInstance().getReference().child("users") ;

        }


    }

    @Override
    public void onClick(View view) {
        if (view ==registerButton)
        {
            register_user () ;
        }

    }

    private void register_user() {
        final String email=editTextemail.getText().toString().trim() ;
        String password =editTextpassword.getText().toString().trim() ;
        final String name=editTextname.getText().toString().trim() ;
        final String phonenumber=editTextphonenumber.getText().toString().trim() ;


        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)  )
        { progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    String user_id =mAuth.getCurrentUser().getUid() ;
                    DatabaseReference user_database=mDatabase.child(user_id) ;
                    user_database.child("Nom et prenom").setValue(name) ;
                    user_database.child("Email").setValue(email) ;
                    user_database.child("Phone Number").setValue(phonenumber) ;
                    user_database.child("Depenses").setValue("0") ;
                    user_database.child("Essais").setValue("0");
                    user_database.child("Nombre de sorties").setValue("0")  ;
                    MainActivity.already_registered=1 ;
                    Intent intent = new Intent(RegsiterActivity.this,MainActivity.class) ;
                    RegsiterActivity.this.startActivity(intent);
                    //add blog intent here .
                    progressDialog.dismiss();
                }
                else
                {
                    progressDialog.dismiss();
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Error try again please..",Snackbar.LENGTH_LONG).show();
                }

            }
        }) ;










        }





    }
}
