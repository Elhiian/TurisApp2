package com.example.elhiian.turisapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,InicioFragment.OnFragmentInteractionListener,
        SitiosFragment.OnFragmentInteractionListener, DetalleFragment.OnFragmentInteractionListener{


    MenuItem item;
    Fragment fragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


       if (Configuracion.fragment==null){
           getSupportFragmentManager().beginTransaction().replace(R.id.contenedorprincipal,new InicioFragment()).commit();
       }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item=menu.findItem(R.id.action_settings);
        if (Configuracion.isSitios==false){
            item.setVisible(false);
        }else{
            item.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            fragment=new InicioFragment();
            Configuracion.isSitios=false;

        } else if (id == R.id.nav_hoteles) {
            fragment=new SitiosFragment();
            Bundle datos=new Bundle();
            datos.putString("action","hoteles");
            fragment.setArguments(datos);
            Configuracion.isSitios=true;

        } else if (id == R.id.nav_Restaurantes) {
            fragment=new SitiosFragment();
            Bundle datos=new Bundle();
            Configuracion.isSitios=true;
            datos.putString("action","restaurantes");
            fragment.setArguments(datos);

        } else if (id == R.id.nav_sitios) {
            fragment=new SitiosFragment();
            Bundle datos=new Bundle();
            Configuracion.isSitios=true;
            datos.putString("action","sitios");
            fragment.setArguments(datos);

        } else if (id == R.id.nav_salir) {
            finish();
        }
        Configuracion.fragment=fragment;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedorprincipal,fragment).commit();
        }

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
