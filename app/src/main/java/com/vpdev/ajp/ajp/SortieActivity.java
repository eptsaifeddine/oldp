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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by saifeddine on 22/07/17.
 */

public class SortieActivity extends AppCompatActivity  {

   private EditText editText_entreprise ;
   private EditText editText_date ;
   private EditText editText_depense ;
  //private double depense =0.0 ;
   private CheckBox checkBox_avec ;
   private CheckBox checkBox_sans  ;

   private ProgressDialog progressDialog  ;
   private Button button_enregistrer ;
    private DatabaseReference user_database;
    private FirebaseAuth mAuth;
    private String user_id;
    private DatabaseReference mDatabase;
    private DatabaseReference mGlobaldata ;
    private FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.sortie_layout);
        super.onCreate(savedInstanceState);

        editText_entreprise =(EditText) findViewById(R.id.EditText_entrerpise) ;
        editText_date=(EditText) findViewById(R.id.EditTextdate_sortie) ;
        editText_depense=(EditText)findViewById(R.id.EditText_depense) ;
        button_enregistrer=(Button)findViewById(R.id.ButtonEnregistrer)  ;
        button_enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_user();
                add_total();
            }
        });
        checkBox_avec=(CheckBox)findViewById(R.id.checkbox_avec) ;
        checkBox_sans=(CheckBox)findViewById(R.id.checkbox_sans) ;
        //depense =Double.parseDouble(editText_depense.getText().toString().trim() );
        progressDialog=new ProgressDialog(this) ;
        progressDialog.setMessage("Enregistrement en cours ...");

        mAuth = FirebaseAuth.getInstance();
        mGlobaldata=FirebaseDatabase.getInstance().getReference().child("Toltal depenses") ;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users") ;
        user_id = mAuth.getCurrentUser().getUid();
        user_database = mDatabase.child(user_id);

    }






private void add_total()
{
    String depenses=editText_depense.getText().toString().trim() ;
    Double dd=Double.parseDouble(depenses) ;
    if (ProfileActivity.total_depenses!=null) {
        dd = dd + Double.parseDouble(ProfileActivity.total_depenses);

        mGlobaldata.setValue(dd.toString());
    }
    else
        mGlobaldata.setValue(dd.toString());
    /*DatabaseReference mRef_total = database.getReference().child("Toltal depenses");

    mRef_total.addValueEventListener(new ValueEventListener() {

        public void onDataChange(DataSnapshot dataSnapshot) {

            Double total ;
            if (dataSnapshot.getValue(String.class)==null)
                total=dd ;
            else
                total=Double.parseDouble(dataSnapshot.getValue(String.class) +Double.toString(dd) ) ;

        }


        public void onCancelled(DatabaseError databaseError) {

        }
    }) ;*/
}


    private void update_user() {
         progressDialog.show();
        String entreprise=editText_entreprise.getText().toString().trim() ;
        String date =editText_date.getText().toString().trim() ;
        String depenses=editText_depense.getText().toString().trim() ;
        Double d=Double.parseDouble(depenses) ;
        int nombresorties=Integer.parseInt(ProfileActivity.nombre_sorties) ;
       // user_database.child("saifvvv").setValue("voillllla") ;



      user_database.child("Nombre de sorties").setValue( Integer.toString(nombresorties+1)) ;
        user_database.child("Sorties").child("Sortie "+Integer.toString(nombresorties+1)).child("Entreprise").setValue(entreprise) ;
        user_database.child("Sorties").child("Sortie "+Integer.toString(nombresorties+1)).child("Date").setValue(date) ;
        user_database.child("Sorties").child("Sortie "+Integer.toString(nombresorties+1)).child("Depenses").setValue(depenses);
       //user_database.child("Sorties").child("Sortie "+user_database.child("Nombre de sorties").getKey()).child("Depenses").setValue(( depense));
       // user_database.child("Depenses").setValue(Double.parseDouble(user_database.child("Depenses").getKey())+ depense) ;
            d=d+Double.parseDouble(ProfileActivity.depenses );
            user_database.child("Depenses").setValue(Double.toString(d)) ;


        ProfileActivity.Store();

        if (checkBox_avec.isChecked())
            user_database.child("Sorties").child("Sortie "+Integer.toString(nombresorties+1)).child("Rendez-vous").setValue("oui");
        if(checkBox_sans.isChecked())
            user_database.child("Sorties").child("Sortie "+Integer.toString(nombresorties+1)).child("Rendez-vous").setValue("non");
        progressDialog.dismiss();
        Snackbar.make(getWindow().getDecorView().getRootView(),"Merci et bonne chance.",Snackbar.LENGTH_LONG).show();

        Intent intent =new Intent (SortieActivity.this,ProfileActivity.class) ;
        SortieActivity.this.startActivity(intent) ;





    }
}
