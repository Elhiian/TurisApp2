package com.example.elhiian.turisapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elhiian.turisapp.clases.Sitios;
import com.squareup.picasso.Picasso;

public class DetalleActivity extends AppCompatActivity {
    ImageView imagen;
    TextView nombre,descripcion;
    private final int Requestcode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirMapa();

            }
        });

        nombre=findViewById(R.id.detallenombre);
        descripcion=findViewById(R.id.detalledescripcion);
        imagen=findViewById(R.id.detalleImagen);


        Sitios sitio=null;
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            sitio= (Sitios) bundle.getSerializable("sitio");
            nombre.setText(sitio.getNombre());
            descripcion.setText(sitio.getDescripcion());
            Picasso.with(this).load(Configuracion.servidor+"/"+sitio.getFoto()).into(imagen);
        }
    }

    private void abrirMapa() {
        if (ContextCompat.checkSelfPermission(DetalleActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DetalleActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Requestcode);
        }else{
            Intent intent=new Intent(DetalleActivity.this,MapsRutaActivity.class);
            intent.putExtras(getIntent().getExtras());
            startActivity(intent);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
