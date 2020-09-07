package com.puanla.uygulamam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EnYakinMarket extends AppCompatActivity {
    ListView listenyakin;

    ArrayList<String>strings=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_en_yakin_market);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        Intent intent=getIntent();
        final String marketler=intent.getStringExtra("marketler");
        final String[] sıralımarketbilgileri = marketler.split(",");




        listenyakin=findViewById(R.id.listenyakin);


        ArrayAdapter<String>adapter=new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,android.R.id.text1,sıralımarketbilgileri);
        listenyakin.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent ıntent=new Intent(getApplicationContext(),MapsActivity.class);
            NavUtils.navigateUpTo(this,ıntent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
