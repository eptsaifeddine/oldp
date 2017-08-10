package com.vpdev.ajp.ajp;

/**
 * Created by saifeddine on 09/08/17.
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseListAdapter;
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

public class onlineUsersActivity extends AppCompatActivity {
    private ListView mListview  ;
    private static final String TAG = "PostDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.users_layout);
        final DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReferenceFromUrl("https://ajpappalpha.firebaseio.com/users/Bz6nU08Pb1ayHuQ3azoUB5JPpSC3/Saif") ;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.w(TAG,"Data"+dataSnapshot.getValue());

              /*Map<String,Object> m=(Map<String,Object>) dataSnapshot.getValue(Map.class) ;
                Toast.makeText(onlineUsersActivity.this,m.size(), Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }) ;




        mListview = (ListView)findViewById(R.id.list_view)  ;
        FirebaseListAdapter<String> firebaseListAdapter =new FirebaseListAdapter<String>(this,String.class,
                android.R.layout.simple_list_item_1,databaseReference
                ) {
            @Override
            protected void populateView(View v, String model, int position) {
                TextView textView=(TextView) v.findViewById(android.R.id.text1) ;




                textView.setText(model);
            }
        };
        mListview.setAdapter(firebaseListAdapter);
 mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
     @Override
     public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
         String s=mListview.getItemAtPosition(i).toString() ;
        //
     }
 });
    }


}
