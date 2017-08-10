package com.vpdev.ajp.ajp;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Random;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://ajpappalpha.firebaseio.com/") ;
private Button buttonRegister ;
private EditText editTextloginEmail ;
private EditText editTextloginPassword ;
private Button loginbtn ;
private TextView registertext ;
    private DatabaseReference mDatabase ;
private ProgressDialog progressDialog ;
public static int already_registered =0 ;
public static String Name ;
    private DatabaseReference user_database ;
private FirebaseAuth mAuth ;
   static String user_id ;
//private Snackbar snackbar ;
    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //
        progressDialog =new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Signing in ...");


        setContentView(R.layout.activity_main);
        if (already_registered==1 )

            Snackbar.make(getWindow().getDecorView().getRootView(),"Registered Successfully you can login now ",Snackbar.LENGTH_LONG).show();

        mAuth=FirebaseAuth.getInstance() ;
        editTextloginEmail=(EditText) findViewById(R.id.EditTextloginEmail)  ;
        editTextloginPassword =(EditText) findViewById(R.id.EditTextloginpassword) ;
        loginbtn =(Button) findViewById(R.id.Buttonlogin)  ;
        loginbtn.setOnClickListener(this);
        registertext =(TextView) findViewById(R.id.textview_register) ;
        mDatabase= FirebaseDatabase.getInstance().getReference().child("users") ;

    }

    @Override
    public void onClick(View view) {
        if (view ==loginbtn)
            checklogin() ;
        if (view == registertext )
        {
            Intent intentregister= new Intent(MainActivity.this,RegsiterActivity.class) ;
            MainActivity.this.startActivity(intentregister) ; // start the register class .
            //Snackbar.make(getWindow().getDecorView().getRootView(),"wait",Snackbar.LENGTH_SHORT).show();


        }

    }

    private void checklogin() {
        String email=editTextloginEmail.getText().toString().trim() ;
        String password =editTextloginPassword.getText().toString().trim() ;
        if (!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password))
        {

           progressDialog.show();
          mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful())
                  {

                      progressDialog.show();
                      ProfileActivity.Store();
                      Handler handler = new Handler();


                      handler.postDelayed(new Runnable() {
                          public void run() {

                              //a change has been made here and the following line os the old line
                            Intent intent = new Intent(MainActivity.this,ProfileActivity.class) ;

                             //Intent intent = new Intent(MainActivity.this,onlineUsersActivity.class) ;
                              MainActivity.this.startActivity(intent)  ;

                              //mDatabase.child("Online users").child("user "+Integer.toString(i1)).setValue(ProfileActivity.user_name) ;

                              final DatabaseReference dataName=mDatabase.child(user_id) ;
                              mDatabase.addValueEventListener(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(DataSnapshot dataSnapshot) {
                                         // Name=dataSnapshot.getValue(String.class) ;
                                      Log.v("Data","Data shit"+mAuth.getCurrentUser().getDisplayName()) ;
                                  }

                                  @Override
                                  public void onCancelled(DatabaseError databaseError) {

                                  }
                              }) ;

                          }
                      }, 10000);


                      Random r = new Random();
                      int i = r.nextInt(10000 - 65) + 65;
                      databaseReference.child("Online Users").child("User "+Integer.toString(i)).setValue(Name);
                      progressDialog.dismiss();

                     //progressDialog.dismiss();
                     // Snackbar.make(getWindow().getDecorView().getRootView(),"Success",Snackbar.LENGTH_SHORT).show();

                      user_id =mAuth.getCurrentUser().getUid() ;
                     user_database=mDatabase.child(user_id) ;
                    Toast.makeText(MainActivity.this,"Logged IN successfully", Toast.LENGTH_LONG).show();

                   /*   Intent intentprofile= new Intent(MainActivity.this,ProfileActivity.class) ;
                      MainActivity.this.startActivity(intentprofile) ;*/




                  }
                  else
                  {
                      progressDialog.dismiss();
                     Toast.makeText(MainActivity.this,"Error please check your infos, internet and try again ...", LENGTH_SHORT).show();

                  }
              }
          }) ;



        }
        else
        {
            Toast.makeText(MainActivity.this,"Email or password are empty try again please ...", LENGTH_SHORT) .show();
        }




    }








}
