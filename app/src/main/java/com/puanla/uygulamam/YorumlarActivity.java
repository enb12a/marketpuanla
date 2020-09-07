package com.puanla.uygulamam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class YorumlarActivity extends AppCompatActivity {

    Button geri;
    DatabaseReference yorumlarıoku;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yorumlar);
        listView=findViewById(R.id.listeler);
        final ArrayList<String> arrayList=new ArrayList<>();
        final ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);



        String userrID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent intpl=getIntent();
        String MarkerID=intpl.getStringExtra("MarkerID2");
        System.out.println("yorummu 1 "+MarkerID);


        geri=findViewById(R.id.gerigider);
        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YorumlarActivity.this,MapsActivity.class));

            }
        });


       yorumlarıoku=FirebaseDatabase.getInstance().getReference("Yorumlar").child(MarkerID).child(userrID);
        yorumlarıoku.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("yorummu "+dataSnapshot.getValue());
              //  System.out.println("yorummu 4"+dataSnapshot.child("yorum").getValue().toString());

                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    System.out.println("yorummu 4"+ds.child("yorum").getValue().toString());
                    arrayList.add(""+ds.child("yorum").getValue().toString());
                    listView.setAdapter(adapter);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();






    }

}
