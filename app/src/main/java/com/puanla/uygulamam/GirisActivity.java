package com.puanla.uygulamam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GirisActivity extends AppCompatActivity {
    EditText edt_email_giris,edt_sifre_giris;
    Button btn_Giris_activity;
    TextView txt_kayitsayfasinaGit;
    FirebaseAuth girisyetkisi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        edt_email_giris=findViewById(R.id.edt_email_giris);
        edt_sifre_giris=findViewById(R.id.edt_sifre_giris);
        btn_Giris_activity=findViewById(R.id.btn_Giris_activity);
        girisyetkisi=FirebaseAuth.getInstance();
        txt_kayitsayfasinaGit=findViewById(R.id.txt_kayitsayfasinaGit);
        txt_kayitsayfasinaGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GirisActivity.this,KayitActivity.class));
            }
        });
        btn_Giris_activity.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final ProgressDialog pdGiris=new ProgressDialog(GirisActivity.this);
            pdGiris.setMessage("Giriş Yapılıyor..");
            pdGiris.show();
            String str_emailGiris=edt_email_giris.getText().toString();
            String str_sifreGiris=edt_sifre_giris.getText().toString();
            if (TextUtils.isEmpty(str_emailGiris)||TextUtils.isEmpty(str_sifreGiris))
            {
                Toast.makeText(GirisActivity.this,"Bütün Alanları Doldurun", Toast.LENGTH_LONG).show();

            }
            else
            {
                //Giriş yapma kodları
                girisyetkisi.signInWithEmailAndPassword(str_emailGiris,str_sifreGiris)
                        .addOnCompleteListener(GirisActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    DatabaseReference yolGiris= FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(girisyetkisi.getCurrentUser().getUid());
                                    yolGiris.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            pdGiris.dismiss();
                                            Intent intent=new Intent(GirisActivity.this,MapsActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            pdGiris.dismiss();


                                        }
                                    });



                                }
                                else
                                {
                                    pdGiris.dismiss();
                                    Toast.makeText(GirisActivity.this, "Giris Başarısız Oldu..", Toast.LENGTH_LONG).show();
                                }

                            }
                        });

            }






        }
    });

    }

}
