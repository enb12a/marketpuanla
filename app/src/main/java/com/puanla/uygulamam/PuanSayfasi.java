package com.puanla.uygulamam;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;


public class PuanSayfasi<markergetir> extends AppCompatActivity {
    TextView kafeisim;
    TextView ortpuan;
    TextView iletisim;

    Button btnpuanver,cikis,geri,yorumyap,yorumgör;

    RatingBar puansistem;

    TextView adres,yorumTxtPuanSayfasi;
    EditText edt_ortalama;
    FirebaseDatabase database;
    DatabaseReference mRef;
    DatabaseReference getmRef;
    DatabaseReference markergetir;


    float toppuan=0;
    float kisisay=0;
    float ortapuan=0;
    float yenort =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puan_sayfasi);

        ortpuan=findViewById(R.id.ortpuan);
        kafeisim=findViewById(R.id.kafeisim);
        geri=findViewById(R.id.geri);
        geri.setBackgroundColor(Color.WHITE);
        iletisim=findViewById(R.id.iletisim);
        btnpuanver=findViewById(R.id.btnpuanver);
        puansistem=findViewById(R.id.puansistem);
        adres=findViewById(R.id.adres);
        edt_ortalama=findViewById(R.id.edt_ortalama);
        cikis=findViewById(R.id.cikis);
        cikis.setBackgroundColor(Color.WHITE);
        yorumgör=findViewById(R.id.btnyorumgör);
        yorumTxtPuanSayfasi=findViewById(R.id.yorumtxt_Puansayfasi);
        yorumyap=findViewById(R.id.btnyorumyap);



        //yeni method acma yeri
        final String ortalamaa=edt_ortalama.getText().toString();
        //Maps activitiden gelen adres isim ve marker bilgilerini alma yeri
        Intent intos=getIntent();
        final String data=intos.getStringExtra("name");
        kafeisim.setText(data);
        System.out.println("FBVV"+data);

         Intent into=getIntent();
        String al2=into.getStringExtra("adresal");
        adres.setText(al2);
        System.out.println("FBVV isim:"+al2);

        Intent into2=getIntent();
        final String MarkerID2=into2.getStringExtra("MarkerID");

        yorumgör.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mıd=new Intent(PuanSayfasi.this,YorumlarActivity.class);
                mıd.putExtra("MarkerID2",MarkerID2);
                startActivity(mıd);


            }
        });


        yorumyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yorumTxtPuanSayfasi.getText().toString().equals(""))
                {
                    Toast.makeText(PuanSayfasi.this, "Boş Yorum Gönderemezsiniz", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    yorumekle();

                }


            }
        });






        final String userrID=FirebaseAuth.getInstance().getCurrentUser().getUid();



        Intent intop=getIntent();
        final String MarkerID=intop.getStringExtra("MarkerID");
        //Firebase'e kayıtlı olan puan ve ortalama hesaplama yeri
        markergetir=FirebaseDatabase.getInstance().getReference("Market Puanları").child(MarkerID);

         markergetir.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


              for (DataSnapshot ds:dataSnapshot.getChildren()){

                  float rat=Float.parseFloat(ds.child("Puan ").getValue().toString());
                  toppuan= (float) (toppuan+rat);
                  kisisay=(float) dataSnapshot.getChildrenCount();
                  ortapuan=toppuan/kisisay;
                  String s=String.format("%.2f",ortapuan);
                  edt_ortalama.setText(s);


                  /// yenort=(ortapuan*kisisay+puansistem.getRating())/(kisisay);
                  ///System.out.println("puangelç"+yenort);

                  System.out.println("ortapuan"+ortapuan);
                  System.out.println("Kişi sayısı:"+kisisay);
                  System.out.println("toplam puan"+toppuan);
                  System.out.println("toplam Puan:"+rat);

              }

          }
          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
          });


        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PuanSayfasi.this,SeceneklerActivity.class));
            }
        });
        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PuanSayfasi.this,MapsActivity.class));
            }
        });


        kaydet(ortalamaa);


        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
    }

    private void yorumekle() {
        final String userrID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent intot=getIntent();
        String MarkerID=intot.getStringExtra("MarkerID");
        DatabaseReference yorumyol=FirebaseDatabase.getInstance().getReference("Yorumlar").child(MarkerID).child(userrID);
        HashMap<String,Object>hashMap =new HashMap<>();
        hashMap.put("yorum",yorumTxtPuanSayfasi.getText().toString());
        //push verilerin üst üste binmemesi için kullanılır
        yorumyol.push().setValue(hashMap);
        yorumTxtPuanSayfasi.setText("");

    }


    private void kaydet(String ortalamaa)
    {

        final String userrID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        btnpuanver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                puansistem.setVisibility(View.VISIBLE);
                final String rating =""+puansistem.getRating();
                Intent intop=getIntent();
                final String MarkerID=intop.getStringExtra("MarkerID");
                getmRef=FirebaseDatabase.getInstance().getReference().child("Market Puanları").child(MarkerID).child(userrID);
                HashMap<String, String> puanma=new HashMap<>();
                puanma.put("Puan ", rating);
                getmRef.setValue(puanma).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            yenort=(ortapuan*kisisay+puansistem.getRating())/(kisisay+1);
                            System.out.println("PCcode"+yenort);
                            String s=String.format("%.2f",yenort);
                            edt_ortalama.setText(s);

                            Toast.makeText(PuanSayfasi.this,rating,Toast.LENGTH_LONG).show();

                        }
                    }
                });


            }
        });

    }

}






