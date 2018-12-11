package com.example.elhiian.turisapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elhiian.turisapp.clases.Sitios;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.Holder> implements View.OnClickListener {
    ArrayList<Sitios> listaSitios;
    Context context;
    View.OnClickListener listener;
    Boolean typeList;

    public ListaAdapter(ArrayList<Sitios> listaSitios, Context context, Boolean typeList) {
        this.listaSitios = listaSitios;
        this.context = context;
        this.typeList=typeList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layout;
        if (typeList==true){
            layout=R.layout.list_recycler_sitios;
        }else{
            layout=R.layout.list_recycler_sitios_grid;
        }
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(layout,viewGroup,false);
        view.setOnClickListener(this);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.nombre.setText(listaSitios.get(i).getNombre());
        Picasso.with(context).load(Configuracion.servidor+"/"+listaSitios.get(i).getFoto()).error(R.mipmap.ic_launcher).into(holder.imagen);
        System.out.println(Configuracion.servidor+"/"+listaSitios.get(i).getFoto());

        if (typeList==true){
            if (Configuracion.land!=true){
                holder.ubicacion.setText(listaSitios.get(i).getUbicacion());
                holder.descripcion.setText(listaSitios.get(i).getDescripcioncorta());
            }
        }else{
            holder.ubicacion.setText(listaSitios.get(i).getUbicacion());
        }
    }

    @Override
    public int getItemCount() {
        return listaSitios.size();
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public  void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView nombre,descripcion,ubicacion;
        public Holder(@NonNull View itemView) {
            super(itemView);
            if (typeList==true){
                imagen=itemView.findViewById(R.id.listaimagen);
                nombre=itemView.findViewById(R.id.listnombre);
                if (Configuracion.land==false){
                    descripcion=itemView.findViewById(R.id.listdescripcion);
                    ubicacion=itemView.findViewById(R.id.listdireccion);
                }
            }else{
                imagen=itemView.findViewById(R.id.gridImage);
                nombre=itemView.findViewById(R.id.gridName);
                ubicacion=itemView.findViewById(R.id.gridUbication);
            }
        }
    }
}
