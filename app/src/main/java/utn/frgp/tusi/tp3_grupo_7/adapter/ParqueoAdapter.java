package utn.frgp.tusi.tp3_grupo_7.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import dominio.Parqueo;
import utn.frgp.tusi.tp3_grupo_7.R;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ParqueoAdapter extends BaseAdapter {

    private ArrayList<Parqueo> elementos;
    private Context context;

    public ParqueoAdapter(Context context, ArrayList<Parqueo> elementos) {
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public int getCount() {
        return elementos.size();
    }

    @Override
    public Parqueo getItem(int position) {
        return elementos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view = convertView;
        if (convertView == null){
            view = inflater.inflate(R.layout.card_template, null);
        }

        TextView patente = (TextView) view.findViewById(R.id.txt_patente);
        TextView tiempo = (TextView) view.findViewById(R.id.txt_tiempo);
        patente.setText(getItem(position).getPatente());
        tiempo.setText(getItem(position).getTiempo().toString());

        return view;
    }
}
