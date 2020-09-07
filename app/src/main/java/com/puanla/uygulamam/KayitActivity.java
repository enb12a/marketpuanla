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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class KayitActivity extends AppCompatActivity {
    EditText edt_kullaniciAdi,edt_Ad,edt_Email,edt_Sifre;
    Button btn_kaydol;
    TextView txt_GirisSayfasinaGit;
    FirebaseAuth yetki;
    DatabaseReference yol;
    ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        edt_kullaniciAdi=findViewById(R.id.edt_kullaniciAdi);
        edt_Ad=findViewById(R.id.edt_Ad);
        edt_Email=findViewById(R.id.edt_email);
        edt_Sifre=findViewById(R.id.edt_sifre);

        btn_kaydol=findViewById(R.id.btn_kaydol_activity);
        txt_GirisSayfasinaGit=findViewById(R.id.txt_giris_sayfasinaGit);
        yetki=FirebaseAuth.getInstance();



        txt_GirisSayfasinaGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KayitActivity.this,GirisActivity.class));
            }
        });
        btn_kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd =new ProgressDialog(KayitActivity.this);
                pd.setMessage("Lütfen Bekleyin");
                pd.show();


                String str_kullaniciAdi =edt_kullaniciAdi.getText().toString();
                String str_Ad =edt_Ad.getText().toString();
                String str_Email =edt_Email.getText().toString();
                String str_Sifre =edt_Sifre.getText().toString();
                if(TextUtils.isEmpty(str_kullaniciAdi)||TextUtils.isEmpty(str_Ad)||TextUtils.isEmpty(str_Email)
                        || TextUtils.isEmpty(str_Sifre))
                {
                    Toast.makeText(KayitActivity.this,"Lütfen bütün alanları doldurun...",Toast.LENGTH_SHORT).show();

                }
                else if(str_Sifre.length()<6)
                {
                    Toast.makeText(KayitActivity.this,"Şifreniz Minimum 6 karakter olmalı",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    //yeni kullanıcı kayıt etme kodlarını çağır
                    kaydet(str_kullaniciAdi,str_Ad,str_Email,str_Sifre);

                }

            }
        });
    }


    private void kaydet(final String kullaniciadi, final String ad, final String email, final String sifre)
    {
        //yeni kullanıcı kayıt etme kodları
        yetki.createUserWithEmailAndPassword(email,sifre)
                .addOnCompleteListener(KayitActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                         {
                             FirebaseUser firebaseKullanici=yetki.getCurrentUser();
                             String kullaniciId=firebaseKullanici.getUid();//getuid firebasein kullanıcıya verdiği id

                             yol= FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(kullaniciId);

                             //Birden fazla veriyi göndermek icin hashmap kullanırılır
                             HashMap<String,Object> hashMap = new HashMap<>();
                             hashMap.put("id",kullaniciId);
                             hashMap.put("kullanıcıadi",kullaniciadi.toLowerCase());
                             hashMap.put("ad",ad);
                             hashMap.put("e-mail",email);
                             hashMap.put("sifre",sifre);

                             yol.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task)
                                 {
                                     if (task.isSuccessful())
                                     {
                                         pd.dismiss();
                                         Intent intent = new Intent(KayitActivity.this,PuanSayfasi.class);
                                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                         startActivity(intent);

                                     }

                                 }
                             });

                        }
                        else
                        {
                            pd.dismiss();
                            Toast.makeText(KayitActivity.this,"Bu mail veya şifre ile kayıt başarısız..",Toast.LENGTH_LONG).show();


                        }

                    }
                });

    }
}
