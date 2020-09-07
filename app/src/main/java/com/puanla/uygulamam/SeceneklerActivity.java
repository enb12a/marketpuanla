package com.puanla.uygulamam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SeceneklerActivity extends AppCompatActivity {
    TextView txtsecenekler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secenekler);
        txtsecenekler=findViewById(R.id.txt_seceneklerActivity);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

      txtsecenekler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(SeceneklerActivity.this,MapsActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });
    }

}
