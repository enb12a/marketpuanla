package com.puanla.uygulamam;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager ;
    LocationListener locationListener;
    FirebaseDatabase firebaseDatabase ;
    DatabaseReference myRef =FirebaseDatabase.getInstance().getReference();
    private ChildEventListener mChildEventListener;
    private DatabaseReference mkoordinatlar;
    Marker marker;
    FirebaseUser baslangicKullanici;
    private String MarkerID ="";
    public DatabaseReference puanref;
    Button enyakinmarket;
    ArrayList<String> listItems = new ArrayList<String>();
    List<marketyolhesap> hesaps = new ArrayList<marketyolhesap>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        enyakinmarket=findViewById(R.id.enyakinmarket);
        myRef =firebaseDatabase.getReference();
       //ChildEventListener mChildEventListener;
        mkoordinatlar=FirebaseDatabase.getInstance().getReference("koordinatlar");
        mkoordinatlar.push().setValue(marker);




    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        mkoordinatlar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s: dataSnapshot.getChildren())
                {
                    FirebaseMarker firebaseMarker=s.getValue(FirebaseMarker.class);
                    LatLng location= new LatLng(firebaseMarker.enlem,firebaseMarker.boylam);
                    mMap.addMarker(new MarkerOptions().position(location).title(firebaseMarker.isim)
                            .snippet(firebaseMarker.adres_bilgileri)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.xz)));

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //Title tıklayınca baslangıc sayfasına gider

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {

                    baslangicKullanici= FirebaseAuth.getInstance().getCurrentUser();
                    //Eğer kullanıcı veri tabaınn da varsa direk anasayfayfauya gönder
                    if  (baslangicKullanici!=null)
                    {

                        MarkerID=marker.getId();
                        System.out.println("FBVVv:" +MarkerID);


                        Intent into =new Intent(MapsActivity.this,PuanSayfasi.class);
                        into.putExtra("name", marker.getTitle());
                        into.putExtra("adresal", marker.getSnippet());
                        into.putExtra("MarkerID",MarkerID);
                        startActivity(into);
                        finish();

                    }
                    else

                        startActivity(new Intent(MapsActivity.this,baslangic.class));
                        finish();



            }
        });



        locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                System.out.println("Location: "+ location.toString());
                Toast.makeText(MapsActivity.this, "Konumunuz Alınmıştır", Toast.LENGTH_SHORT).show();


                mkoordinatlar.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot s: dataSnapshot.getChildren())
                        {
                            FirebaseMarker firebaseMarker=s.getValue(FirebaseMarker.class);
                            LatLng location1= new LatLng(firebaseMarker.enlem,firebaseMarker.boylam);
                            Location loc2=new Location("");
                            loc2.setLatitude(location1.latitude);
                            loc2.setLongitude(location1.longitude);

                            int[] distance= {(int) location.distanceTo(loc2)};
                            ArrayList<Double> doubles =new ArrayList<Double>();
                            doubles.add((double) location.distanceTo(loc2));


                            //System.out.println("mesafeler: "+distance);
                            //float[] mesafeler= new float[(int) distance];
                            // System.out.println("Marketii  = "+location.distanceTo(loc2)+"m"+"     "+firebaseMarker.isim);

                           /// listItems.add(firebaseMarker.isim+"     "+location.distanceTo(loc2)+"m");
                           // listItems.add(location.distanceTo(loc2)+"m"+"     "+firebaseMarker.isim);
                            System.out.println("selamm "+location.distanceTo(loc2));
                            //Arrays.sort(listItems);


                            final marketyolhesap  marketyolhesap12=new marketyolhesap(firebaseMarker.isim,location.distanceTo(loc2));
                            System.out.println("görelim"+marketyolhesap12);

                            hesaps.add(marketyolhesap12);
                            Collections.sort(hesaps);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        enyakinmarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent gönder=new Intent(MapsActivity.this,EnYakinMarket.class);
                String un=hesaps.toString();
                //  String[] unu=un.split(",");
                // System.out.println("virgül ayır"+unu);

                gönder.putExtra("marketler",un);


                System.out.println("OOPç "+un);

                startActivity(gönder);


            }
        });

        if (Build.VERSION.SDK_INT >= 23 ){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1);
            }
            else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,50000,100,locationListener);
            }
              }
        else{locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,50000,100,locationListener);

        }


       LatLng kayserikalesi = new LatLng(38.721110, 35.488796);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kayserikalesi,15));
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);


    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 ){
            if (requestCode==1){
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,30,locationListener);

                }

            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
