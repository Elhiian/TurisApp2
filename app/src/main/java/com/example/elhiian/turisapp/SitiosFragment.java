package com.example.elhiian.turisapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.elhiian.turisapp.clases.Sitios;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SitiosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SitiosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SitiosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SitiosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SitiosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SitiosFragment newInstance(String param1, String param2) {
        SitiosFragment fragment = new SitiosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }
    View view;
    RecyclerView recycler;
    ArrayList<Sitios> listaSitios;
    FloatingActionButton botonflotante;
    Boolean typeList=true;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_sitios, container, false);
        recycler=view.findViewById(R.id.recyclerSitios);
        botonflotante=view.findViewById(R.id.floatingbutton);

        if (view.findViewById(R.id.contenedorDetalleSitio)!=null){
            Configuracion.land=true;
            Configuracion.isSitios=false;

        }else{
            Configuracion.isSitios=true;
            Configuracion.land=false;
            botonflotante.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),MapsAllActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("lista",listaSitios);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });
        }
        getActivity().invalidateOptionsMenu();


        cargarDatos();


        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_settings){
            if (typeList==true){
                typeList=false;
            }else{
                typeList=true;
            }

            cambiarVista();
        }

        return super.onOptionsItemSelected(item);
    }

    private void cambiarVista() {
        if (typeList==true){
            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            recycler.setLayoutManager(new GridLayoutManager(getActivity(),2));
        }
        ListaAdapter adapter=new ListaAdapter(listaSitios,getActivity(),typeList);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Sitios objeto=listaSitios.get(recycler.getChildAdapterPosition(v));
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("sitio",objeto);
                    Intent intent=new Intent(getActivity(),DetalleActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

            }
        });
        recycler.setAdapter(adapter);
    }

    public void cargarDatos(){
        listaSitios=new ArrayList<>();
        AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
        String url="http://turisapp.esy.es/turisapp/informacion.php?action="+getArguments().getString("action");
        System.out.println(url);
        asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    String response=new String(responseBody);
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i=0; i<jsonArray.length(); i++){
                            Sitios sitios=new Sitios();
                            sitios.setId(jsonArray.getJSONObject(i).getString("id"));
                            sitios.setNombre(jsonArray.getJSONObject(i).getString("nombre"));
                            sitios.setDescripcioncorta(jsonArray.getJSONObject(i).getString("descripcioncorta"));
                            sitios.setUbicacion(jsonArray.getJSONObject(i).getString("ubicacion"));
                            sitios.setDescripcion(jsonArray.getJSONObject(i).getString("descripcion"));
                            sitios.setLatitud(jsonArray.getJSONObject(i).getString("latitud"));
                            sitios.setLongitud(jsonArray.getJSONObject(i).getString("longitud"));
                            sitios.setFoto(jsonArray.getJSONObject(i).getString("foto"));
                            listaSitios.add(sitios);
                        }

                        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

                        ListaAdapter adapter=new ListaAdapter(listaSitios,getActivity(),typeList);
                        adapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Configuracion.land==false){
                                    Sitios objeto=listaSitios.get(recycler.getChildAdapterPosition(v));
                                    Bundle bundle=new Bundle();
                                    bundle.putSerializable("sitio",objeto);
                                    Intent intent=new Intent(getActivity(),DetalleActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }else{
                                    Fragment fragment=new DetalleFragment();
                                    Bundle bundle=new Bundle();
                                    bundle.putSerializable("sitio",listaSitios.get(recycler.getChildAdapterPosition(v)));
                                    fragment.setArguments(bundle);
                                    getFragmentManager().beginTransaction().replace(R.id.contenedorDetalleSitio,fragment).commit();
                                }
                            }
                        });

                        recycler.setAdapter(adapter);
                        if (Configuracion.land==true){
                            Fragment fragment=new DetalleFragment();
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("sitio",listaSitios.get(0));
                            fragment.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.contenedorDetalleSitio,fragment).commit();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity(), "No se pudo conectar al servidor " +error.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("No se pudo conectar al servidor " +error.toString());
            }
        });
    }






    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
