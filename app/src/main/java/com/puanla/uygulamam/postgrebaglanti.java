package com.puanla.uygulamam;

import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class postgrebaglanti extends AppCompatActivity {
    String mesaj =null;
    String conmesaj= null;



    Connection conn=null;
    Statement st = null;

    public Connection getConn(){
        try {
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection("jbdc:postgresql://localhost:5432/marketapp","posgresql"
                    ,"kartal..12");

            st=conn.createStatement();



            ResultSet rs =st.executeQuery("SELECT * FROM public.marketlistesi");
            while(rs.next()){
                //Retrieve by column name
                String first = rs.getString("isim");
                String last = rs.getString("adres_bilgileri");

                //Display values
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }


        }


         catch (Exception er) {
            System.err.println(er.getMessage());
        }
        return conn;
    }


}
